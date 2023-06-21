import React, { useState, createContext } from "react";
import { ParametersForm } from "./ParametersForm";

export const ChartContext = createContext(null);

/**
 * Komponent statystyk.
 * @component
 * @param {Object} props - Właściwości przekazane do komponentu.
 * @returns {JSX.Element} - Zwraca przyciski statystyk oraz formularz parametrów.
 */
export const Statistics = (props) => {
  const [parametersForm, setParametersForm] = useState(false);
  const [chart, setChart] = useState("");
  const [showChart, setShowChart] = useState(false);

  /**
   * Obsługuje kliknięcie przycisku statystyki.
   * @param {string} name - Nazwa wykresu.
   */
  const handleClick = (name) => {
    setParametersForm(true);
    setChart(name);
    setShowChart(false);
    console.log(name);
  };

  return (
    <div>
      <div>
        <button
          onClick={() => handleClick("wellBeeingParameter")}
          style={{ marginBottom: "10px" }}
          className="statisticButton"
        >
          Samopoczucie od wybranego parametru
        </button>
        <button
          onClick={() => handleClick("wellBeeing")}
          style={{ marginBottom: "10px" }}
          className="statisticButton"
        >
          Samopoczucie psychiczne od fizycznego
        </button>
        <button
          onClick={() => handleClick("parameter")}
          style={{ marginBottom: "10px" }}
          className="statisticButton"
        >
          Parametry w czasie
        </button>
        <button
          onClick={() => handleClick("statistics")}
          style={{ marginBottom: "10px" }}
          className="statisticButton"
        >
          Wartości statystyczne parametrów
        </button>
      </div>
      <ChartContext.Provider value={[showChart, setShowChart]}>
        {parametersForm ? (
          <div>
            <ParametersForm chart={chart} />
          </div>
        ) : (
          <div></div>
        )}
      </ChartContext.Provider>
    </div>
  );
};
