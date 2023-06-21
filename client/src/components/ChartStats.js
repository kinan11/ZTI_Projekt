import React, { useState } from "react";
import { PieChart, Pie, Cell, Tooltip, Legend } from "recharts";

/**
 * Komponent reprezentujący wykres kołowy.
 *
 * @component
 * @param {Object} props - Właściwości komponentu.
 * @param {Array} props.data - Tablica zawierająca dane do wykresu.
 * @param {number} props.maks - Największa wartość parametru.
 * @param {number} props.min - Najmniejsza wartość parametru.
 * @param {number} props.avg - Średnia wartość parametrów.
 * @returns {JSX.Element} - Zwraca element reprezentujący wykres kołowy.
 */
const ChartStats = (props) => {
  const counts = {};

  if (props.data != null) {
    props.data.forEach((value) => {
      counts[value] = (counts[value] || 0) + 1;
    });
    console.log(props.data);
  }

  const chartData = Object.keys(counts).map((key) => ({
    name: key,
    value: counts[key],
  }));

  const getRandomColor = () => {
    const letters = "0123456789ABCDEF";
    let color = "#";
    for (let i = 0; i < 6; i++) {
      color += letters[Math.floor(Math.random() * 16)];
    }
    return color;
  };

  const [COLORS, setColors] = useState(chartData.map(() => getRandomColor()));

  return (
    <div style={{ display: "flex", justifyContent: "center" }}>
      {props.data != null ? (
        <div
          style={{
            display: "flex",
            justifyContent: "center",
            marginTop: "50px",
            backgroundColor: "white",
            padding: "30px",
            borderRadius: "20px",
            width: "900px",
          }}
        >
          <PieChart
            width={500}
            height={400}
            style={{
              backgroundColor: "white",
              padding: "20px",
              borderRadius: "20px",
            }}
          >
            <Pie
              data={chartData}
              dataKey="value"
              nameKey="name"
              cx="50%"
              cy="50%"
              outerRadius={150}
              label
              labelLine={false}
              fill={(entry, index) => COLORS[index % COLORS.length]}
            >
              {props.data.map((entry, index) => (
                <Cell
                  key={`cell-${index}`}
                  fill={COLORS[index % COLORS.length]}
                />
              ))}
            </Pie>
            <Tooltip />
            <Legend />
          </PieChart>
          <div
            style={{
              display: "flex",
              justifyContent: "center",
              alignItems: "center",
            }}
          >
            <div
              style={{
                color: "black",
                display: "flex",
                flexDirection: "column",
                alignItems: "center",
                fontSize: "28px",
                fontFamily: "Arial",
              }}
            >
              <div>Największa wartość: {props.maks}</div>
              <div>Najmniejsza wartość: {props.min}</div>
              <div>
                Średnia wartość:{" "}
                {props.avg != null ? props.avg.toFixed(2) : null}
              </div>
            </div>
          </div>
        </div>
      ) : (
        <div
          style={{ marginTop: "50px", fontSize: "30px", fontWeight: "bold" }}
        >
          Brak danych
        </div>
      )}
    </div>
  );
};

export default ChartStats;
