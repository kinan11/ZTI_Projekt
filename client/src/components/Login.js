import React, { useState, useContext } from "react";
import { useNavigate } from "react-router-dom";
import { UserContext } from "../App.js";
import axios from "axios";

/**
 * Komponent logowania użytkownika.
 * @component
 * @param {Object} props - Właściwości komponentu.
 * @returns {JSX.Element} - JSX komponentu logowania.
 */
export const Login = (props) => {
  const [login, setLogin] = useState("");
  const [password, setPassword] = useState("");
  const [user, setUser] = useContext(UserContext);
  const [message, setMessage] = useState(null);
  const navigate = useNavigate();

  /**
   * Obsługuje submit formularza logowania.
   * @param {Event} e - Obiekt zdarzenia submit.
   */
  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const res = await axios.post("http://localhost:8088/login", {
        login,
        password,
      });
      setUser(res.data);
      console.log(res.message);
      setMessage(null);
      navigate("/day");
    } catch (err) {
      console.log(err);
      setMessage(err.response.data.message);
    }
  };

  /**
   * Obsługuje kliknięcie przycisku rejestracji.
   */
  const handleRegisterClick = () => {
    navigate("/register");
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
      <h2>Login</h2>
      <form className="login-form" onSubmit={handleSubmit}>
        <label htmlFor="username">Login</label>
        <input
          type="text"
          placeholder="login"
          id="login"
          name="login"
          onChange={(e) => setLogin(e.target.value)}
        />
        <label htmlFor="password">Hasło</label>
        <input
          type="password"
          placeholder="********"
          id="password"
          name="password"
          onChange={(e) => setPassword(e.target.value)}
        />
        <button type="submit" className="submitButton">
          Login
        </button>
        <button className="link-btn" onClick={handleRegisterClick}>
          Nie masz konta? Zarejestruj się
        </button>
      </form>
    </div>
  );
};
