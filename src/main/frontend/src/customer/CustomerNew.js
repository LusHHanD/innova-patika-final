import React, { useState } from "react";
import { CustomerForm } from "./CustomerForm";
import { useNavigate } from "react-router-dom";
import axios from "axios";

export const CustomerNew = () => {
  const navigate = useNavigate();
  const [values, setValues] = useState({});

  const handleOnChange = (value, key) => {
    setValues({ ...values, [key]: value });
  };

  const handleOnSubmit = (e) => {
    e.preventDefault();

    axios.post("/api/customer", values).then(() => navigate("/customer"));
  };
  return (
    <CustomerForm
      values={values}
      handleOnChange={handleOnChange}
      handleOnSubmit={handleOnSubmit}
    />
  );
};
