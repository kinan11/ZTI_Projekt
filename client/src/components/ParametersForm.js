import React, { useState, useEffect, useContext } from "react";
import Select from "@mui/material/Select";
import MenuItem from "@mui/material/MenuItem";
import moment from "moment";
import { TextField } from "@mui/material";
import ChartWellBeeingParameter from "./ChartWellBeeingParameter";
import ChartWellBeeing from "./ChartWellBeeing";
import ChartParameter from "./ChartParameter";
import axios from "axios";
import ChartStats from "./ChartStats";
import { UserContext } from "../App.js";
import { ChartContext } from "./Statistics";

/**
 * Komponent React odpowiedzialny za formularz parametrów.
 *
 * @component
 * @param {Object} props - Obiekt z parametrami.
 * @param {string} props.chart - Typ wykresu do wyświetlenia.
 * @returns {JSX.Element} Komponent ParametersForm.
 */
export const ParametersForm = (props) => {
  const [wellBeing, setWellBeing] = useState("physical");
  const [param, setParam] = useState("workH");
  const [period, setPeriod] = useState("week");
  const [periodFrom, setPeriodFrom] = useState(
    moment().subtract(1, "week").format("YYYY-MM-DD")
  );
  const [periodTo, setPeriodTo] = useState(moment().format("YYYY-MM-DD"));
  const [drug, setDrug] = useState("");
  const [supplement, setSupplement] = useState("");
  const [paramName, setParamName] = useState("");
  const [wellName, setWellName] = useState("");
  const [min, setMin] = useState(null);
  const [maks, setMaks] = useState(null);
  const [avg, setAvg] = useState(null);
  const [drugsFields, setDrugsFields] = useState(false);
  const [supplementsFields, setSupplementsFields] = useState(false);
  const [showChart, setShowChart] = useContext(ChartContext);
  const [data, setData] = useState([]);
  const [data2, setData2] = useState([]);
  const [data3, setData3] = useState([]);
  const [chart, setChart] = useState("WellBeeingParameter");
  const colors = ["#8884d8", "#82ca9d", "#ffc658"];
  const [user, setUser] = useContext(UserContext);
  const [color, setColor] = useState(true);

  /**
   * Obsługuje zmianę wartości samopoczucia.
   * @component
   * @param {string} value - Nowa wartość samopoczucia.
   * @returns {void}
   */
  const handleWellBeingChange = (event) => {
    setWellBeing(event.target.value);
    setColor(false);
  };

  /**
   * Obsługuje zmianę wartości parametru.
   *
   * @param {string} value - Nowa wartość parametru.
   * @returns {void}
   */
  const handleParamChange = (event) => {
    setParam(event.target.value);
    setSupplementsFields(false);
    setDrugsFields(false);
    if (event.target.value === "drugs") {
      setDrugsFields(true);
    }
    if (event.target.value === "supplements") {
      setSupplementsFields(true);
    }
    setColor(false);
  };

  /**
   * Obsługuje zmianę wartości okresu.
   *
   * @param {string} value - Nowa wartość okresu.
   * @returns {void}
   */
  const handlePeriodChange = (event) => {
    setPeriod(event.target.value);
    setPeriodTo(event.target.value);
    setColor(false);
  };

  /**
   * Obsługuje zmianę wartości wybranego leku.
   *
   * @param {string} value - Nowa wartość wybranego leku.
   * @returns {void}
   */
  const handleDrugChange = (value) => {
    setDrug(value.target.value);
    setColor(false);
  };

  /**
   * Obsługuje zmianę wartości wybranego suplementu.
   *
   * @param {string} value - Nowa wartość wybranego suplementu.
   * @returns {void}
   */
  const handleSupplementChange = (value) => {
    setSupplement(value.target.value);
    setColor(false);
  };

  /**
   * Obsługuje złożenie formularza.
   *
   * @returns {void}
   */
  const handleSubmit = async () => {
    const config = {
      headers: { Authorization: "Bearer " + user.token },
    };

    switch (props.chart) {
      case "wellBeeingParameter":
        const bodyParameters1 = {
          period: period,
          userId: user.id,
          wellBeeing: wellBeing,
          parameter: param,
          name:
            param === "drugs" || param === "supplements"
              ? param === "drugs"
                ? drug
                : supplement
              : null,
        };

        console.log(drug);
        const searchParams1 = new URLSearchParams(bodyParameters1);
        const response1 = await axios.get(
          "http://localhost:8088/wellBeeingParameter?" +
            searchParams1.toString(),
          config
        );
        setData(response1.data.statistics);
        setParamName(response1.data.parameterName);
        setWellName(response1.data.wellBeeingName);
        console.log(response1.data.statistics);
        break;

      case "wellBeeing":
        const bodyParameters = {
          period: period,
          userId: user.id,
        };
        const searchParams = new URLSearchParams(bodyParameters);
        const response = await axios.get(
          "http://localhost:8088/wellBeeing?" + searchParams.toString(),
          config
        );
        setData(response.data.statistics);
        console.log(response.data.statistics);
        break;

      case "parameter":
        const bodyParameters3 = {
          period: period,
          userId: user.id,
          parameter: param,
          name:
            param === "drugs" || param === "supplements"
              ? param === "drugs"
                ? drug
                : supplement
              : null,
        };
        const searchParams3 = new URLSearchParams(bodyParameters3);
        const response3 = await axios.get(
          "http://localhost:8088/parameter?" + searchParams3.toString(),
          config
        );
        setData2(response3.data.statistics);
        setParamName(response3.data.parameterName);
        break;

      case "statistics":
        const bodyParameters4 = {
          period: period,
          userId: user.id,
          parameter: param,
          name:
            param === "drugs" || param === "supplements"
              ? param === "drugs"
                ? drug
                : supplement
              : null,
        };
        const searchParams4 = new URLSearchParams(bodyParameters4);
        const response4 = await axios.get(
          "http://localhost:8088/statistics?" + searchParams4.toString(),
          config
        );
        setData3(response4.data.statistics);
        setParamName(response4.data.parameterName);
        setMin(response4.data.min);
        setMaks(response4.data.maks);
        setAvg(response4.data.avg);
        setColor(true);
        break;

      default:
        break;
    }

    setShowChart(true);
  };

  return (
    <div>
      <div>
        {props.chart === "wellBeeingParameter" && (
          <Select
            label="Samopoczucie"
            value={wellBeing}
            onChange={handleWellBeingChange}
            inputProps={{
              name: "well-being",
              id: "well-being-select",
            }}
            style={{
              backgroundColor: "rgba(255, 255, 255, 0.5)",
              border: "2px solid #7439db",
              borderRadius: "10px",
              marginRight: "5px",
              color: "#7439db",
              fontWeight: "bold",
            }}
          >
            <MenuItem value="physical">Samopoczucie fizyczne</MenuItem>
            <MenuItem value="mental">Samopoczucie psychiczne</MenuItem>
          </Select>
        )}
        {props.chart === "wellBeeingParameter" && (
          <Select
            label="Parametr"
            value={param}
            onChange={handleParamChange}
            inputProps={{
              name: "param",
              id: "param-select",
            }}
            style={{
              backgroundColor: "rgba(255, 255, 255, 0.5)",
              border: "2px solid #7439db",
              borderRadius: "10px",
              marginRight: "5px",
              color: "#7439db",
              fontWeight: "bold",
            }}
          >
            <MenuItem value="workH">Przepracowane godziny</MenuItem>
            <MenuItem value="studyH">Godziny na uczelni</MenuItem>
            <MenuItem value="learnH">Godziny nauki</MenuItem>
            <MenuItem value="sleepH">Przespane godziny</MenuItem>
            <MenuItem value="sportH">Godziny spędzone na sporcie</MenuItem>
            <MenuItem value="restH">Godziny odpoczynku</MenuItem>
            <MenuItem value="mealCount">Liczba posiłków</MenuItem>
            <MenuItem value="waterCount">Ilość wypitej wody</MenuItem>
            <MenuItem value="drugs">Używki</MenuItem>
            <MenuItem value="supplements">Suplementy</MenuItem>
            <MenuItem value="meetingH">
              Ilość czasu na spotkaniach towarzyskich
            </MenuItem>
            <MenuItem value="peopleCount">Ilość spotkanych osób</MenuItem>
          </Select>
        )}
        {(props.chart === "parameter" || props.chart === "statistics") && (
          <Select
            label="Parametr"
            value={param}
            onChange={handleParamChange}
            inputProps={{
              name: "param",
              id: "param-select",
            }}
            style={{
              backgroundColor: "rgba(255, 255, 255, 0.5)",
              border: "2px solid #7439db",
              borderRadius: "10px",
              marginRight: "5px",
              color: "#7439db",
              fontWeight: "bold",
            }}
          >
            <MenuItem value="workH">Samopoczucie fizyczne</MenuItem>
            <MenuItem value="studyH">Samopoczucie psychiczne</MenuItem>
            <MenuItem value="workH">Przepracowane godziny</MenuItem>
            <MenuItem value="studyH">Godziny na uczelni</MenuItem>
            <MenuItem value="learnH">Godziny nauki</MenuItem>
            <MenuItem value="sleepH">Przespane godziny</MenuItem>
            <MenuItem value="sportH">Godziny spędzone na sporcie</MenuItem>
            <MenuItem value="restH">Godziny odpoczynku</MenuItem>
            <MenuItem value="mealCount">Liczba posiłków</MenuItem>
            <MenuItem value="waterCount">Ilość wypitej wody</MenuItem>
            <MenuItem value="drugs">Używki</MenuItem>
            <MenuItem value="supplements">Suplementy</MenuItem>
          </Select>
        )}
        {supplementsFields &&
          (props.chart === "wellBeeingParameter" ||
            props.chart === "parameter" ||
            props.chart === "statistics") && (
            <TextField
              InputProps={{
                style: { color: "#7439db", fontWeight: "bold" },
              }}
              type="text"
              defaultValue={"Suplement"}
              onChange={handleSupplementChange}
              style={{
                backgroundColor: "rgba(255, 255, 255, 0.5)",
                border: "2px solid #7439db",
                borderRadius: "10px",
                marginRight: "5px",
                color: "#7439db",
                fontWeight: "bold",
                marginTop: "22px",
              }}
            />
          )}
        {drugsFields &&
          (props.chart === "wellBeeingParameter" ||
            props.chart === "parameter" ||
            props.chart === "statistics") && (
            <TextField
              InputProps={{
                style: { color: "#7439db", fontWeight: "bold" },
              }}
              type="text"
              defaultValue={"Używka"}
              onChange={handleDrugChange}
              style={{
                backgroundColor: "rgba(255, 255, 255, 0.5)",
                border: "2px solid #7439db",
                borderRadius: "10px",
                marginRight: "5px",
                color: "#7439db",
                fontWeight: "bold",
                marginTop: "22px",
              }}
            />
          )}

        <Select
          label="Okres"
          value={period}
          onChange={handlePeriodChange}
          inputProps={{
            name: "period",
            id: "period-select",
          }}
          style={{
            backgroundColor: "rgba(255, 255, 255, 0.5)",
            border: "2px solid #7439db",
            borderRadius: "10px",
            marginRight: "5px",
            color: "#7439db",
            fontWeight: "bold",
          }}
        >
          <MenuItem value="week">Ostatni tydzień</MenuItem>
          <MenuItem value="month">Ostatni miesiąc</MenuItem>
          <MenuItem value="year">Ostatni rok</MenuItem>
        </Select>
        <button
          type="submit"
          className="submitFormButton"
          onClick={handleSubmit}
          style={{ backgroundColor: "rgba(255, 255, 255, 0.5)" }}
        >
          Pokaż
        </button>
      </div>

      {showChart && props.chart === "wellBeeingParameter" && (
        <ChartWellBeeingParameter
          data={data}
          paramName={paramName}
          wellName={wellName}
        />
      )}
      {showChart && props.chart === "wellBeeing" && (
        <ChartWellBeeing data={data} period={period} />
      )}
      {showChart && props.chart === "parameter" && (
        <ChartParameter data={data2} name={paramName} />
      )}
      {showChart && props.chart === "statistics" && (
        <ChartStats
          data={data3}
          min={min}
          maks={maks}
          avg={avg}
          name={paramName}
          color={color}
        />
      )}
    </div>
  );
};
