import React from "react";
import {
  LineChart,
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  Legend,
} from "recharts";

/**
 * Komponent reprezentujący wykres liniowy.
 *
 * @component
 * @param {Object} props - Właściwości komponentu.
 * @param {Array} props.data - Tablica zawierająca dane do wykresu.
 * @param {string} props.name - Nazwa parametru.
 * @returns {JSX.Element} - Zwraca element reprezentujący wykres liniowy.
 */
const ChartParameter = (props) => {
  console.log(props.data.length === 0);
  return (
    <div
      style={{ display: "flex", justifyContent: "center", marginTop: "50px" }}
    >
      {props.data.length !== 0 ? (
        <LineChart
          data={props.data}
          width={700}
          height={350}
          style={{
            backgroundColor: "white",
            padding: "30px",
            borderRadius: "20px",
          }}
        >
          <CartesianGrid strokeDasharray="3 3" />
          <XAxis
            dataKey="x"
            label={{
              value: "Data",
              position: "insideBottom",
              dy: 18,
              fontSize: 20,
            }}
          />
          <YAxis
            label={{
              value: "Wartość",
              position: "insideLeft",
              dx: 20,
              dy: 30,
              angle: -90,
              fontSize: 20,
            }}
          />
          <Tooltip />
          <Legend />
          <Line
            type="monotone"
            dataKey="y"
            stroke="#8884d8"
            name={props.name}
          />
        </LineChart>
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

export default ChartParameter;
