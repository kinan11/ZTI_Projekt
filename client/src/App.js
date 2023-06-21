import "./App.css";
import axios from "axios";
import { React, useState, createContext } from "react";
import jwt_decode from "jwt-decode";
import { Login } from "./components/Login";
import { Register } from "./components/Register";
import { Home } from "./components/Home";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";

export const UserContext = createContext(null);

/**
 * Kontekst użytkownika.
 *
 * @typedef {Object} UserContext
 * @property {Object} user - Zalogowany użytkownik.
 * @property {Function} setUser - Funkcja ustawiająca zalogowanego użytkownika.
 */

/**
 * Główna aplikacja.
 *
 * @function
 * @name App
 * @returns {JSX.Element} - Zwraca komponent aplikacji.
 */
function App() {
  const [user, setUser] = useState(null);

  const axiosJWT = axios.create();

  /**
   * Interceptor żądania axios, który dodaje nagłówek JWT do zapytań.
   *
   * @param {Object} config - Konfiguracja żądania.
   * @returns {Object} - Zmodyfikowana konfiguracja żądania.
   * @throws {Error} - Błąd, jeśli wystąpi problem z tokenem JWT.
   */
  axiosJWT.interceptors.request.use(
    async (config) => {
      let currentDate = new Date();
      const decodedToken = jwt_decode(user.token);
      if (decodedToken.exp * 1000 < currentDate.getTime()) {
        config.headers["authorization"] = "Bearer " + user.token;
      }
      return config;
    },
    (error) => {
      return Promise.reject(error);
    }
  );

  return (
    <div className="container">
      <UserContext.Provider value={[user, setUser]}>
        <Router>
          <div className="App">
            <Routes>
              <Route path="/" element={<Login />} />
              <Route path="/day" element={<Home />} />
              <Route path="/login" element={<Login />} />
              <Route path="/register" element={<Register />} />
            </Routes>
          </div>
        </Router>
      </UserContext.Provider>
    </div>
  );
}

export default App;
