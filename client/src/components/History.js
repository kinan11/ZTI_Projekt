import React, { useState, useEffect, useContext } from "react";
import { ViewContext } from "./Home.js";
import axios from "axios";
import { UserContext } from "../App.js";
import { parseISO, format } from "date-fns";

import { Card } from "./Card";

/**
 * Komponent historii wyświetlający listę wcześniejszych dni.
 * @component
 * @param {Object} props - Właściwości komponentu.
 * @returns {JSX.Element} - JSX komponentu historii.
 */
export const History = (props) => {
  const [historyList, setHistoryList] = useState([]);
  const { currentView, toggleView } = React.useContext(ViewContext);
  const [user, setUser] = useContext(UserContext);
  const [isLoading, setIsLoading] = useState(true);

  /**
   * Pobiera historię dni z serwera.
   */
  useEffect(() => {
    const fetchData = async () => {
      try {
        const config = {
          headers: { Authorization: "Bearer " + user.token },
        };
        const bodyParameters = {
          userId: user.id,
        };

        const searchParams = new URLSearchParams(bodyParameters);
        const response = await axios.get(
          "http://localhost:8088/days?" + searchParams.toString(),
          config
        );
        setHistoryList(response.data.days);
        console.log(response.data);
      } catch (error) {
        console.log(error);
      }
      setIsLoading(false);
    };

    fetchData();
  }, []);

  /**
   * Obsługuje usuwanie danych z danego dnia.
   * @param {string} id - ID dnia do usunięcia.
   * @param {string} date - Data dnia do usunięcia.
   */
  const handleDelete = async (id, date) => {
    const confirmNavigation = window.confirm(
      "Czy napewno chcesz usunąć dane z dnia " +
        format(parseISO(date), "dd.MM.yyyy") +
        "?"
    );
    if (!confirmNavigation) {
      return;
    }
    const config = {
      headers: { Authorization: "Bearer " + user.token },
    };
    const bodyParameters = {
      date: date.slice(0, 10),
      userId: user.id,
    };
    const searchParams = new URLSearchParams(bodyParameters);
    setHistoryList((prevList) => prevList.filter((item) => item.id !== id));
    const response = await axios.delete(
      "http://localhost:8088/deleteDay?" + searchParams.toString(),
      config
    );
    console.log(currentView);
  };

  return (
    <div className="history">
      {isLoading ? (
        <div>Ładowanie...</div>
      ) : (
        <div>
          {historyList.map((item, index) => (
            <Card
              key={index}
              id={item.id}
              date={item.date}
              mentalWellBeing={item.mentalWellBeeing}
              physicalWellBeing={item.physicalWellBeeing}
              onDelete={() => handleDelete(item.id, item.date)}
            />
          ))}
        </div>
      )}
    </div>
  );
};
