import React from "react";

import "./ChartHeatmap.css";

/**
 * Komponent reprezentujący wykres heatmapy dla samopoczucia.
 *
 * @component
 * @param {Object[]} data - Tablica zawierająca dane do wygenerowania heatmapy.
 * @param {number} data[].x - Współrzędna x punktu (samopoczucie fizyczne).
 * @param {number} data[].y - Współrzędna y punktu (samopoczucie psychiczne).
 * @returns {JSX.Element} - Zwraca element reprezentujący wykres heatmapy.
 */
const ChartWellBeeing = ({ data }) => {
  const matrix = Array.from({ length: 10 }, () =>
    Array.from({ length: 10 }, () => 0)
  );

  data.forEach((point) => {
    const { x, y } = point;
    matrix[y - 1][x - 1]++;
  });

  const minValue = Math.min(...matrix.flat());
  const maxValue = Math.max(...matrix.flat());

  /**
   * Funkcja interpolująca jasność w zależności od wartości.
   *
   * @param {number} value - Wartość do interpolacji.
   * @param {number} min - Minimalna wartość w macierzy.
   * @param {number} max - Maksymalna wartość w macierzy.
   * @returns {string} - Zwraca wartość koloru w formacie hsl.
   */
  const interpolateLightness = (value, min, max) => {
    const normalizedValue = (value - min) / (max - min);
    const lightness = 80 - normalizedValue * 60;
    return `hsl(240, 100%, ${lightness}%)`;
  };

  return (
    <div className="cont">
      <div className="podpis">
        <div className="fizyczne">Samopoczucie psychiczne</div>
        <div className="axis">
          {[...Array(10)].map((_, index) => (
            <div
              key={index}
              style={{
                width: "100%",
                marginLeft: "30px",
                height: "43px",
                display: "flex",
                fontSize: "20px",
                fontWeight: "bold",
                justifyContent: "center",
                alignItems: "center",
              }}
            >
              {index + 1}
            </div>
          ))}
        </div>
      </div>

      <div className="grid">
        <div
          style={{
            display: "grid",
            gridTemplateColumns: "repeat(10, 1fr)",
            gap: "4px",
            marginTop: "30px",
            marginRight: "0px",
            marginLeft: "auto",
            width: "100%",
          }}
          className="grid"
        >
          {matrix.map((row, rowIndex) =>
            row.map((value, columnIndex) => (
              <div
                key={`${rowIndex}-${columnIndex}`}
                style={{
                  width: "100%",
                  height: "40px",
                  backgroundColor: interpolateLightness(
                    value,
                    minValue,
                    maxValue
                  ),
                  color: "#FFFFFF",
                  display: "flex",
                  justifyContent: "center",
                  alignItems: "center",
                }}
              >
                {value}
              </div>
            ))
          )}
        </div>
        <div
          style={{
            display: "grid",
            gridTemplateColumns: "repeat(10, 1fr)",
            gap: "4px",
            marginTop: "10px",
            width: "100%",
            marginRight: "0px",
            marginLeft: "auto",
          }}
        >
          {[...Array(10)].map((_, index) => (
            <div
              key={index}
              style={{
                width: "100%",
                height: "40px",
                display: "flex",
                fontSize: "20px",
                fontWeight: "bold",
                justifyContent: "center",
                alignItems: "center",
              }}
            >
              {index + 1}
            </div>
          ))}
        </div>
        <div
          style={{
            marginTop: "10px",
            textAlign: "center",
            fontSize: "20px",
            fontWeight: "bold",
            width: "100%",
            marginRight: "0px",
            marginLeft: "auto",
          }}
        >
          Samopoczucie fizyczne
        </div>
      </div>
    </div>
  );
};

export default ChartWellBeeing;
