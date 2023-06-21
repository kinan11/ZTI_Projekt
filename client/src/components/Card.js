import React, { useEffect, useContext, useState } from "react";
import DeleteForeverIcon from "@mui/icons-material/DeleteForever";
import EditIcon from "@mui/icons-material/Edit";
import { ViewContext } from "./Home.js";

/**
 * Komponent reprezentujący kartę.
 *
 * @component
 * @param {Object} props - Właściwości przekazywane do komponentu.
 * @param {string} props.date - Data dnia w formacie "yyyy-mm-dd".
 * @param {string} props.mentalWellBeing - Samopoczucie psychiczne.
 * @param {string} props.physicalWellBeing - Samopoczucie fizyczne.
 * @param {Function} props.onDelete - Funkcja obsługująca usuwanie dnia.
 * @returns {JSX.Element} - Zwraca komponent karty dnia.
 */
export const Card = (props) => {
  const { toggleView } = useContext(ViewContext);
  const [formattedDate, setFormattedDate] = useState("");

  /**
   * Obsługa kliknięcia przycisku edycji.
   */
  const handleEdit = () => {
    toggleView("mainForm", new Date(props.date));
  };

  /**
   * Obsługa kliknięcia przycisku usuwania.
   */
  const handleDeleteClick = () => {
    props.onDelete();
  };

  useEffect(() => {
    const parts = props.date.split("-");
    setFormattedDate(`${parts[2]}.${parts[1]}.${parts[0]}`);
  }, []);

  return (
    <div
      className="card"
      style={{
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        justifyContent: "space-between",
      }}
    >
      <div
        style={{
          display: "flex",
          alignItems: "center",
          justifyContent: "space-between",
          width: "100%",
        }}
      >
        <label
          style={{
            color: "black",
            fontFamily: "Arial",
            fontWeight: "bold",
            fontSize: "23px",
            margin: "10px",
          }}
        >
          {formattedDate}
        </label>
        <div>
          <EditIcon
            sx={{ color: "black", marginRight: "5px", fontSize: "30px" }}
            onClick={handleEdit}
          />
          <DeleteForeverIcon
            sx={{ color: "red", marginRight: "10px", fontSize: "30px" }}
            onClick={handleDeleteClick}
          />
        </div>
      </div>
      <div
        style={{
          display: "flex",
          justifyContent: "space-between",
          width: "100%",
          color: "black",
          fontSize: "18px",
          fontFamily: "Arial",
        }}
      >
        <label style={{ marginLeft: "10px", marginBottom: "10px" }}>
          Samopoczucie psychiczne: {props.mentalWellBeing}
        </label>
        <label style={{ marginRight: "10px", marginBottom: "10px" }}>
          Samopoczucie fizyczne: {props.physicalWellBeing}
        </label>
      </div>
    </div>
  );
};
