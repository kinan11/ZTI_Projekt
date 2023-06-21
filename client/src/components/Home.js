import React, { useState, useContext, useEffect } from "react";
import { Form } from "./Form";
import { Navbar } from "./Navbar";
import { History } from "./History";
import { Statistics } from "./Statistics";
import Box from "@mui/material/Box";
import { UserContext } from "../App.js";
import { Route, Navigate, Routes, useNavigate } from "react-router-dom";

export const ViewContext = React.createContext(null);

/**
 * Komponent głównej strony aplikacji.
 * @component
 * @param {Object} props - Właściwości komponentu.
 * @returns {JSX.Element} - JSX komponentu strony głównej.
 */
export const Home = (props) => {
  const [currentView, setCurrentView] = useState("mainForm");
  const [currentDate, setCurrentDate] = useState(new Date());
  const [unsaved, setUnsaved] = useState(false);
  const [user, setUser] = useContext(UserContext);

  /**
   * Przełącza widok strony na podany widok.
   * @param {string} currentView - Aktualny widok.
   * @param {Date} currentDate - Aktualna data.
   */
  const toggleView = (currentView, currentDate) => {
    if (unsaved) {
      const confirmNavigation = window.confirm(
        "Uwaga! Dane w formularzu mogą nie zostać zapisane. Czy na pewno chcesz opuścić stronę?"
      );
      if (!confirmNavigation) {
        return;
      }
    }

    if (currentDate != null) {
      setCurrentDate(currentDate);
      console.log(currentDate);
    } else {
      setCurrentDate(new Date());
    }
    setUnsaved(false);
    setCurrentView(currentView);
    if (currentView === "logout") {
      logout();
    }
    setUnsaved(false);
  };

  /**
   * Wylogowuje użytkownika i przenosi na stronę logowania.
   * @returns {JSX.Element} - Komponent nawigacji do strony logowania.
   */
  const logout = () => {
    setUser(null);
    return <Navigate to="/login" />;
  };

  if (!user) {
    return <Navigate to="/login" />;
  }

  return (
    <div>
      <Navbar onViewSwitch={toggleView} />
      <ViewContext.Provider
        value={{
          currentView,
          toggleView,
          currentDate,
          setCurrentDate,
          unsaved,
          setUnsaved,
        }}
      >
        <Box sx={{ marginTop: "100px" }}>
          {currentView === "mainForm" ? (
            <Form currentDate={currentDate} key={currentDate} />
          ) : currentView === "history" ? (
            <History />
          ) : currentView === "statistics" ? (
            <Statistics />
          ) : (
            <Form />
          )}
        </Box>
      </ViewContext.Provider>
    </div>
  );
};
