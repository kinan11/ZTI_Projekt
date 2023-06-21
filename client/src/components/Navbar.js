import React, { useState, useContext } from "react";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import IconButton from "@mui/material/IconButton";
import MenuIcon from "@mui/icons-material/Menu";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import { Navigate } from "react-router-dom";

const navItems = [
  ["Dziś", "mainForm"],
  ["Historia", "history"],
  ["Statystyki", "statistics"],
  ["Wyloguj się", "logout"],
];

/**
 * Komponent paska nawigacyjnego.
 * @component
 * @param {Object} props - Właściwości komponentu.
 * @param {Function} props.onViewSwitch - Funkcja do zmiany widoku.
 * @returns {JSX.Element} - JSX komponentu paska nawigacyjnego.
 */
export const Navbar = (props) => {
  return (
    <AppBar
      component="nav"
      sx={{ backgroundColor: "#7439db", marginBottom: "20px" }}
    >
      <Toolbar>
        <IconButton
          color="inherit"
          aria-label="open drawer"
          edge="start"
          sx={{ mr: 2, display: { sm: "none" } }}
        >
          <MenuIcon />
        </IconButton>
        <Typography
          variant="h6"
          component="div"
          sx={{ flexGrow: 1, display: { xs: "none", sm: "block" } }}
        >
          Zdrowy student
        </Typography>
        <Box sx={{ display: { xs: "none", sm: "block" } }}>
          {navItems.map((item) => (
            <Button
              key={item[0]}
              sx={{ color: "#fff" }}
              onClick={() => props.onViewSwitch(item[1])}
            >
              {item[0]}
            </Button>
          ))}
        </Box>
      </Toolbar>
    </AppBar>
  );
};
