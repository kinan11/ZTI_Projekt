import React from "react";
import {
  ScatterChart,
  Scatter,
  XAxis,
  YAxis,
  ZAxis,
  CartesianGrid,
  Tooltip,
  Legend,
  Label,
} from "recharts";

/**
 * Komponent reprezentujący wykres scatter chart dla parametru i samopoczucia.
 *
 * @component
 * @param {Object} props - Właściwości komponentu.
 * @param {Object[]} props.data - Tablica zawierająca dane do wygenerowania wykresu scatter chart.
 * @param {number} props.data[].x - Wartość parametru x (parametr).
 * @param {number} props.data[].y - Wartość parametru y (samopoczucie).
 * @param {string} props.paramName - Nazwa parametru.
 * @param {string} props.wellName - Rodzaj samopoczucia.
 * @returns {JSX.Element} - Zwraca element reprezentujący wykres scatter chart.
 */
const ChartWellBeeingParameter = (props) => {
  const counts = {};
  props.data.forEach((point) => {
    const key = `${point.x}-${point.y}`;
    counts[key] = (counts[key] || 0) + 1;
  });

  const markData = Object.keys(counts).map((key) => {
    const [x, y] = key.split("-").map(Number);
    const count = counts[key];
    return { x, y, Krotność: count };
  });

  const sortedData = markData.sort((a, b) => a.x - b.x);

  const maxCount = Math.max(...Object.values(counts));
  const minSize = 20;
  const maxSize = 100;

  return (
    <div
      style={{ display: "flex", justifyContent: "center", marginTop: "50px" }}
    >
      {props.data.length !== 0 ? (
        <ScatterChart
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
              value: props.paramName,
              position: "insideBottom",
              dy: 18,
              fontSize: 20,
            }}
          />
          <YAxis
            dataKey="y"
            label={{
              value: props.wellName,
              position: "insideLeft",
              dx: 20,
              dy: 100,
              angle: -90,
              fontSize: 20,
            }}
          />
          <ZAxis
            dataKey="Krotność"
            domain={[1, maxCount]}
            range={[minSize, maxSize]}
          />
          <Tooltip
            cursor={{ strokeDasharray: "3 3" }}
            formatter={(value, name) =>
              name === "Krotność"
                ? ["Krotność: " + value]
                : name === "x"
                ? [props.paramName + ": " + value]
                : [props.wellName + ": " + value]
            }
          />
          <Legend />
          <Scatter
            name={props.paramName}
            data={sortedData} // Używanie posortowanych danych
            fill="#8884d8"
            shape="circle"
            size={(entry) =>
              (entry.Krotność * (maxSize - minSize)) / maxCount + minSize
            }
          >
            <Label
              value="krotność"
              position="center"
              offset={10}
              fill="#000"
              fontSize={12}
            />
          </Scatter>
        </ScatterChart>
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

export default ChartWellBeeingParameter;
