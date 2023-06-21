import React, { useState, useContext } from "react";
import { UserContext } from "../App.js";
import axios from "axios";
import { useNavigate } from "react-router-dom";

/**
 * Komponent rejestracji użytkownika.
 * @param {Object} props - Właściwości przekazane do komponentu.
 * @returns {JSX.Element} - Zwraca formularz rejestracji.
 */
export const Register = (props) => {
  const [password, setPassword] = useState("");
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [login, setLogin] = useState("");
  const [user, setUser] = useContext(UserContext);
  const [message, setMessage] = useState(null);
  const navigate = useNavigate();

  /**
   * Obsługuje przesłanie formularza rejestracji.
   * @component
   * @param {Object} e - Obiekt zdarzenia formularza.
   * @returns {Promise<void>} - Obietnica, która zostaje rozwiązana po pomyślnym zarejestrowaniu użytkownika.
   */
  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const res = await axios.post("http://localhost:8088/register", {
        firstName,
        lastName,
        login,
        password,
      });
      setUser(res.data);
      navigate("/day");
    } catch (err) {
      console.log(err);
      setMessage(err.response.data.message);
    }
  };

  /**
   * Obsługuje kliknięcie przycisku "Masz już konto? Zaloguj się".
   * Przekierowuje użytkownika do strony logowania.
   */
  const handleLoginClick = () => {
    navigate("/login");
  };

  return (
    <div className="auth-form-container">
      {message != null ? (
        <div style={{ fontSize: "20px", fontWeight: "bold", color: "white" }}>
          {message}
        </div>
      ) : (
        <div></div>
      )}
      <h2>Rejestracja</h2>
      <form className="register-form" onSubmit={handleSubmit}>
        <label htmlFor="firstName">Imię</label>
        <input
          value={firstName}
          name="firstName"
          onChange={(e) => setFirstName(e.target.value)}
          id="firstName"
          placeholder="Imię"
        />
        <label htmlFor="lastName">Nazwisko</label>
        <input
          value={lastName}
          name="lastName"
          onChange={(e) => setLastName(e.target.value)}
          id="lastName"
          placeholder="Nazwisko"
        />
        <label htmlFor="login">Login</label>
        <input
          value={login}
          name="login"
          onChange={(e) => setLogin(e.target.value)}
          id="login"
          placeholder="Login"
        />
        <label htmlFor="password">Hasło</label>
        <input
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          type="password"
          placeholder="********"
          id="password"
          name="password"
        />
        <button type="submit">Zarejestruj się</button>
      </form>
      <button className="link-btn" onClick={handleLoginClick}>
        Masz już konto? Zaloguj się
      </button>
    </div>
  );
};
