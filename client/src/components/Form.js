import React, { useState, useEffect, useContext } from "react";
import TextField from "@mui/material/TextField";
import Select from "@mui/material/Select";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import ClearIcon from "@mui/icons-material/Clear";
import TextareaAutosize from "@mui/material/TextareaAutosize";
import { UserContext } from "../App.js";
import { ViewContext } from "./Home.js";
import axios from "axios";

/**
 * Komponent formularza służący do wprowadzania codziennych aktywności.
 * @component
 * @param {Object} props - Właściwości komponentu.
 * @returns {JSX.Element} - JSX komponentu formularza.
 */
export const Form = (props) => {
  const [supplementsFields, setSupplementsFields] = useState([]);
  const [drugsFields, setDrugsFields] = useState([]);
  const [user, setUser] = useContext(UserContext);
  const [data, setData] = useState(null);
  const { currentDate } = props;
  const [isLoading, setIsLoading] = useState(true);
  const [errors, setErrors] = useState({});
  const [supplementsErrors, setSupplementsErrors] = useState({});
  const [drugsErrors, setDrugsErrors] = useState({});
  const unsaved = useContext(ViewContext);

  const fetchData = async () => {
    if (!unsaved.unsaved) {
      try {
        setIsLoading(true);
        const config = {
          headers: { Authorization: "Bearer " + user.token },
        };
        const bodyParameters = {
          date: currentDate.toISOString().slice(0, 10),
          userId: user.id,
        };

        const searchParams = new URLSearchParams(bodyParameters);

        const response = await axios.get(
          "http://localhost:8088/day?" + searchParams.toString(),
          config
        );
        console.log(response.data);
        console.log(response.data.workH);

        setData({
          workH: response.data.workH,
          studiesH: response.data.studiesH,
          learningH: response.data.learningH,
          sleepH: response.data.sleepH,
          sportH: response.data.sportH,
          restH: response.data.sportH,
          mealsNumber: response.data.mealsNumber,
          water: response.data.water,
          meeting: response.data.meeting,
          supplements:
            response.data.supplements == null ? [] : response.data.supplements,
          drugs: response.data.drugs == null ? [] : response.data.drugs,
          description: response.data.description,
          physicalWellBeeing: response.data.physicalWellBeeing,
          mentalWellBeeing: response.data.mentalWellBeeing,
        });
      } catch (error) {
        console.log(error);
      }
      setIsLoading(false);
    }
  };

  /**
   * Pobiera dane dla bieżącej daty z serwera.
   */
  useEffect(() => {
    fetchData();
  }, [currentDate]);

  const addSupplementField = () => {
    const newSupplement = {
      id: Date.now(),
      SuplementName: "",
      SuplementAmount: "",
    };
    setSupplementsFields([...data.supplements, newSupplement]);
    setData((prevData) => ({
      ...prevData,
      supplements: [...prevData.supplements, newSupplement],
    }));
    unsaved.setUnsaved(true);
  };
  const addDrugsField = () => {
    const newDrug = { id: Date.now(), drugName: "", drugAmount: "" };
    setDrugsFields([...data.drugs, newDrug]);
    setData((prevData) => ({
      ...prevData,
      drugs: [...prevData.drugs, newDrug],
    }));
    console.log(currentDate);
    unsaved.setUnsaved(true);
  };

  const removeSupplementField = (id) => {
    setSupplementsFields(data.supplements.filter((field) => field.id !== id));
    setData((prevData) => ({
      ...prevData,
      supplements: prevData.supplements.filter(
        (supplement) => supplement.id !== id
      ),
    }));
    unsaved.setUnsaved(true);
  };
  const removeDrugsField = (id) => {
    setDrugsFields(data.drugs.filter((field) => field.id !== id));
    setData((prevData) => ({
      ...prevData,
      drugs: prevData.drugs.filter((drug) => drug.id !== id),
    }));
    unsaved.setUnsaved(true);
  };

  const handleSupplementChange = (event, id) => {
    const { name, value } = event.target;
    setData((prevData) => ({
      ...prevData,
      supplements: prevData.supplements.map((supplement) => {
        if (supplement.id === id) {
          return { ...supplement, [name]: value };
        }
        return supplement;
      }),
    }));

    setSupplementsErrors((prevErrors) => ({
      ...prevErrors,
      [name]: null,
    }));
    unsaved.setUnsaved(true);
  };

  const handleDrugChange = (event, id) => {
    const { name, value } = event.target;
    setData((prevData) => ({
      ...prevData,
      drugs: prevData.drugs.map((drug) => {
        if (drug.id === id) {
          return { ...drug, [name]: value };
        }
        return drug;
      }),
    }));

    setDrugsErrors((prevErrors) => ({
      ...prevErrors,
      [name]: null,
    }));
    unsaved.setUnsaved(true);
  };

  const handleInputChange = (event) => {
    setData({
      ...data,
      [event.target.name]: event.target.value,
    });
    console.log(data);
    unsaved.setUnsaved(true);
  };

  const handleMeetingChange = (event, field) => {
    const { name, value } = event.target;
    setData((prevData) => ({
      ...prevData,
      meeting: {
        ...prevData.meeting,
        [name]: value,
      },
    }));
    unsaved.setUnsaved(true);
  };

  const handleSubmit = async () => {
    console.log(data);
    console.log(user.token);
    const newErrors = {};

    if (data.workH > 24 || data.workH < 0) {
      newErrors["workH"] = "Wartość pola musi być z przedziału od 0 do 24";
    }
    if (data.studiesH > 24 || data.studiesH < 0) {
      newErrors["studiesH"] = "Wartość pola musi być z przedziału od 0 do 24";
    }
    if (data.learningH > 24 || data.learningH < 0) {
      newErrors["learningH"] = "Wartość pola musi być z przedziału od 0 do 24";
    }
    if (data.sleepH > 24 || data.sleepH < 0) {
      newErrors["sleepH"] = "Wartość pola musi być z przedziału od 0 do 24";
    }
    if (data.sportH > 24 || data.sportH < 0) {
      newErrors["sportH"] = "Wartość pola musi być z przedziału od 0 do 24";
    }
    if (data.restH > 24 || data.restH < 0) {
      newErrors["restH"] = "Wartość pola musi być z przedziału od 0 do 24";
    }
    if (
      (data.mealsNumber < 0 ||
        !Number.isInteger(parseFloat(data.mealsNumber))) &&
      data.mealsNumber !== "" &&
      data.mealsNumber != null
    ) {
      newErrors["mealsNumber"] =
        "Wartość pola musi być liczbą całkowitą większą od 0";
    }
    if (data.water < 0) {
      newErrors["water"] = "Wartość pola musi być większa od 0";
    }
    if (data.meeting != null) {
      if (data.meeting.meetingH > 24 || data.meeting.meetingH < 0) {
        newErrors["meetingH"] = "Czas być z przedziału od 0 do 24";
      }
      if (
        (data.meeting.peopleCount < 0 ||
          !Number.isInteger(parseFloat(data.meeting.peopleCount))) &&
        data.meeting.peopleCount !== "" &&
        data.meeting.peopleCount != null
      ) {
        newErrors["peopleCount"] =
          "Ilość osób musi być liczbą całkowitką większą od 0";
      }
    }
    if (data.physicalWellBeeing == null) {
      newErrors["physicalWellBeeing"] = "Musisz uzupełnić to pole";
    }
    if (data.mentalWellBeeing == null) {
      newErrors["mentalWellBeeing"] = "Musisz uzupełnić to pole";
    }
    setErrors(newErrors);

    const newSupplementsErrors = {};
    data.supplements.forEach((supplement) => {
      if (!supplement.supplementName) {
        newSupplementsErrors[supplement.id] = "Musisz uzupełnić nazwę";
      }
      if (supplement.supplementAmount < 0) {
        newSupplementsErrors[supplement.id] = "Liczba musi być większa od 0";
      }
    });

    const newDrugsErrors = {};
    data.drugs.forEach((drug) => {
      if (!drug.drugName) {
        newDrugsErrors[drug.id] = "Musisz uzupełnić nazwę";
      }
      if (drug.drugAmount < 0) {
        newDrugsErrors[drug.id] = "Liczba musi być większa od 0";
      }
      console.log(drug.drugAmount);
    });

    setSupplementsErrors(newSupplementsErrors);
    setDrugsErrors(newDrugsErrors);

    if (
      Object.keys(newErrors).length > 0 ||
      Object.keys(newSupplementsErrors).length > 0 ||
      Object.keys(newDrugsErrors).length > 0
    ) {
      return;
    }

    const config = {
      headers: { Authorization: "Bearer " + user.token },
    };
    const bodyParameters = {
      date: currentDate.toISOString().slice(0, 10),
      userId: user.id,
      workH: data.workH,
      studiesH: data.studiesH,
      learningH: data.learningH,
      sleepH: data.sleepH,
      sportH: data.sportH,
      restH: data.restH,
      mealsNumber: data.mealsNumber,
      water: data.water,
      meetingH: data.meetingH,
      peopleCount: data.peopleCount,
      supplements: data.supplements,
      drugs: data.drugs,
      description: data.description,
      meeting: data.meeting,
      mentalWellBeeing: data.mentalWellBeeing,
      physicalWellBeeing: data.physicalWellBeeing,
    };

    const response = await axios.post(
      "http://localhost:8088/updateDay",
      bodyParameters,
      config
    );
    unsaved.setUnsaved(false);
    alert("Pomyślnie zaposano dane!");
  };

  return (
    <div className="formClass">
      {isLoading ? (
        <div>Ładowanie...</div> // Komunikat ładowania, jeśli isLoading === true
      ) : (
        <div className="form">
          <h1>Opisz swój dzień</h1>
          <div className="formClass">
            <TextField
              className="textField"
              label="Liczba przepracowanych godzin"
              type="number"
              name="workH"
              InputProps={{
                inputProps: {
                  max: 24,
                  min: 0,
                },
              }}
              InputLabelProps={{
                shrink: true,
              }}
              onChange={handleInputChange}
              variant="filled"
              defaultValue={data != null ? data.workH : null}
            />
            {errors && errors["workH"] && (
              <p style={{ color: "red" }}>{errors["workH"]}</p>
            )}

            <TextField
              className="textField"
              label="Liczba godzin spędzonych na studiach"
              type="number"
              name="studiesH"
              InputProps={{
                inputProps: {
                  max: 24,
                  min: 0,
                },
              }}
              InputLabelProps={{
                shrink: true,
              }}
              onChange={handleInputChange}
              defaultValue={data != null ? data.studiesH : null}
              variant="filled"
            />
            {errors && errors["studiesH"] && (
              <p style={{ color: "red" }}>{errors["studiesH"]}</p>
            )}
            <TextField
              className="textField"
              label="Liczba godzin spędzonych na nauce"
              type="number"
              name="learningH"
              InputProps={{
                inputProps: {
                  max: 24,
                  min: 0,
                },
              }}
              InputLabelProps={{
                shrink: true,
              }}
              onChange={handleInputChange}
              defaultValue={data != null ? data.learningH : null}
              variant="filled"
            />
            {errors && errors["learningH"] && (
              <p style={{ color: "red" }}>{errors["learningH"]}</p>
            )}
            <TextField
              className="textField"
              label="Liczba przespanych godzin"
              type="number"
              name="sleepH"
              InputProps={{
                inputProps: {
                  max: 24,
                  min: 0,
                },
              }}
              InputLabelProps={{
                shrink: true,
              }}
              onChange={handleInputChange}
              defaultValue={data != null ? data.sleepH : null}
              variant="filled"
            />
            {errors && errors["sleepH"] && (
              <p style={{ color: "red" }}>{errors["sleepH"]}</p>
            )}
            <TextField
              className="textField"
              label="Liczba godzin spędzonych na uprawianiu sportu"
              type="number"
              name="sportH"
              InputProps={{
                inputProps: {
                  max: 24,
                  min: 0,
                },
              }}
              InputLabelProps={{
                shrink: true,
              }}
              onChange={handleInputChange}
              defaultValue={data != null ? data.sportH : null}
              variant="filled"
            />
            {errors && errors["sportH"] && (
              <p style={{ color: "red" }}>{errors["sportH"]}</p>
            )}
            <TextField
              className="textField"
              label="Liczba godzin poświęconych na odpoczynek"
              type="number"
              name="restH"
              InputProps={{
                inputProps: {
                  max: 24,
                  min: 0,
                },
              }}
              InputLabelProps={{
                shrink: true,
              }}
              onChange={handleInputChange}
              defaultValue={data != null ? data.restH : null}
              variant="filled"
            />
            {errors && errors["restH"] && (
              <p style={{ color: "red" }}>{errors["restH"]}</p>
            )}
            <TextField
              className="textField"
              label="Liczba spożytych posiłków"
              type="number"
              name="mealsNumber"
              InputProps={{
                inputProps: {
                  min: 0,
                },
              }}
              InputLabelProps={{
                shrink: true,
              }}
              onChange={handleInputChange}
              defaultValue={data != null ? data.mealsNumber : null}
              variant="filled"
            />
            {errors && errors["mealsNumber"] && (
              <p style={{ color: "red" }}>{errors["mealsNumber"]}</p>
            )}
            <TextField
              className="textField"
              label="Ilość wypitej wody (w litrach)"
              type="number"
              name="water"
              InputProps={{
                inputProps: {
                  min: 0,
                },
              }}
              InputLabelProps={{
                shrink: true,
              }}
              onChange={handleInputChange}
              defaultValue={data != null ? data.water : null}
              variant="filled"
            />
            {errors && errors["water"] && (
              <p style={{ color: "red" }}>{errors["water"]}</p>
            )}
            <div className="doubleFields">
              <TextField
                className="textField"
                label="Ilość godzin na spotkanich towarzyskich"
                type="number"
                name="meetingH"
                InputProps={{
                  inputProps: {
                    min: 0,
                    max: 24,
                  },
                }}
                InputLabelProps={{
                  shrink: true,
                }}
                onChange={(event) => handleMeetingChange(event, "meetingH")}
                defaultValue={
                  data != null
                    ? data.meeting != null
                      ? data.meeting.meetingH
                      : null
                    : null
                }
                style={{ width: "240px" }}
                variant="filled"
              />

              <TextField
                className="textField"
                label="Liczba spotkanych osób"
                type="number"
                name="peopleCount"
                InputProps={{
                  inputProps: {
                    min: 0,
                  },
                }}
                InputLabelProps={{
                  shrink: true,
                }}
                onChange={(event) => handleMeetingChange(event, "peopleCount")}
                defaultValue={
                  data != null
                    ? data.meeting != null
                      ? data.meeting.peopleCount
                      : null
                    : null
                }
                style={{ width: "150px", marginLeft: "4px" }}
                variant="filled"
              />
            </div>
            {errors && errors["meetingH"] && (
              <p style={{ color: "red" }}>{errors["meetingH"]}</p>
            )}
            {errors && errors["peopleCount"] && (
              <p style={{ color: "red" }}>{errors["peopleCount"]}</p>
            )}
            <button
              onClick={addSupplementField}
              style={{ marginBottom: "10px" }}
              className="submitFormButton"
            >
              Dodaj suplement
            </button>
            {data.supplements.map((field) => (
              <div key={field.id} className="doubleFields">
                <TextField
                  className="textField"
                  label="Nazwa suplementu"
                  type="text"
                  name="supplementName"
                  onChange={(event) => handleSupplementChange(event, field.id)}
                  defaultValue={field.supplementName}
                  InputLabelProps={{
                    shrink: true,
                  }}
                  style={{ width: "150px", marginLeft: "4px" }}
                  variant="filled"
                />
                <TextField
                  className="textField"
                  label="Ilość"
                  type="number"
                  onChange={(event) => handleSupplementChange(event, field.id)}
                  defaultValue={field.supplementAmount}
                  name="supplementAmount"
                  InputProps={{
                    inputProps: {
                      min: 0,
                    },
                  }}
                  InputLabelProps={{
                    shrink: true,
                  }}
                  style={{ width: "70px", marginLeft: "4px" }}
                  variant="filled"
                />
                <ClearIcon
                  onClick={() => removeSupplementField(field.id)}
                  style={{ color: "red", marginLeft: "3px" }}
                />
                {supplementsErrors[field.id] && (
                  <p style={{ color: "red" }}>{supplementsErrors[field.id]}</p>
                )}
              </div>
            ))}
            <button
              onClick={addDrugsField}
              style={{ marginBottom: "10px" }}
              className="submitFormButton"
            >
              Dodaj używkę
            </button>
            {data.drugs.map((field) => (
              <div key={field.id} className="doubleFields">
                <TextField
                  className="textField"
                  label="Nazwa używki"
                  type="text"
                  name="drugName"
                  onChange={(event) => handleDrugChange(event, field.id)}
                  defaultValue={field.drugName}
                  InputLabelProps={{
                    shrink: true,
                  }}
                  style={{ width: "150px", marginLeft: "4px" }}
                  variant="filled"
                />
                <TextField
                  className="textField"
                  label="Ilość"
                  type="number"
                  name="drugAmount"
                  onChange={(event) => handleDrugChange(event, field.id)}
                  defaultValue={field.drugAmount}
                  InputProps={{
                    inputProps: {
                      min: 0,
                    },
                  }}
                  InputLabelProps={{
                    shrink: true,
                  }}
                  style={{ width: "70px", marginLeft: "4px" }}
                  variant="filled"
                />
                <ClearIcon
                  onClick={() => removeDrugsField(field.id)}
                  style={{ color: "red", marginLeft: "3px" }}
                />
                {drugsErrors[field.id] && (
                  <p style={{ color: "red" }}>{drugsErrors[field.id]}</p>
                )}
              </div>
            ))}

            <TextareaAutosize
              minRows={4}
              placeholder="Wprowadź notatkę..."
              className="textField"
              name="description"
              onChange={handleInputChange}
              defaultValue={data != null ? data.description : null}
            />

            <InputLabel id="demo-simple-select-label">
              Samopoczucie psychiczne
            </InputLabel>
            <Select
              labelId="demo-simple-select-label"
              className="samopoczucie"
              label="Samopoczucie psychiczne"
              name="mentalWellBeeing"
              onChange={handleInputChange}
              defaultValue={data != null ? data.mentalWellBeeing : null}
              InputLabelProps={{
                shrink: true,
              }}
            >
              <MenuItem value="1">1</MenuItem>
              <MenuItem value="2">2</MenuItem>
              <MenuItem value="3">3</MenuItem>
              <MenuItem value="4">4</MenuItem>
              <MenuItem value="5">5</MenuItem>
              <MenuItem value="6">6</MenuItem>
              <MenuItem value="7">7</MenuItem>
              <MenuItem value="8">8</MenuItem>
              <MenuItem value="9">9</MenuItem>
              <MenuItem value="10">10</MenuItem>
            </Select>
            {errors && errors["mentalWellBeeing"] && (
              <p style={{ color: "red" }}>{errors["mentalWellBeeing"]}</p>
            )}
            <InputLabel id="demo-simple-select-label">
              Samopoczucie fizyczne
            </InputLabel>
            <Select
              labelId="demo-simple-select-label"
              className="samopoczucie"
              label="Samopoczucie fizyczne"
              name="physicalWellBeeing"
              onChange={handleInputChange}
              defaultValue={data != null ? data.physicalWellBeeing : null}
              InputLabelProps={{
                shrink: true,
              }}
            >
              <MenuItem value="1">1</MenuItem>
              <MenuItem value="2">2</MenuItem>
              <MenuItem value="3">3</MenuItem>
              <MenuItem value="4">4</MenuItem>
              <MenuItem value="5">5</MenuItem>
              <MenuItem value="6">6</MenuItem>
              <MenuItem value="7">7</MenuItem>
              <MenuItem value="8">8</MenuItem>
              <MenuItem value="9">9</MenuItem>
              <MenuItem value="10">10</MenuItem>
            </Select>
          </div>
          {errors && errors["physicalWellBeeing"] && (
            <p style={{ color: "red" }}>{errors["physicalWellBeeing"]}</p>
          )}
          <button
            type="submit"
            className="submitFormButton"
            onClick={() => handleSubmit()}
          >
            Zapisz
          </button>
        </div>
      )}
    </div>
  );
};
