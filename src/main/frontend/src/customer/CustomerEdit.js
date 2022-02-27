import React, { useCallback, useEffect, useState } from "react";
import { CustomerForm } from "./CustomerForm";
import { useNavigate, useParams } from "react-router-dom";
import axios from "axios";

export const CustomerEdit = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [values, setValues] = useState({});

  const getCustomerById = useCallback(() => {
    axios
      .get(`/api/customer/${id}`)
      .then((r) => setValues(r.data))
      .catch((e) => {
        console.error(e);
        navigate("/customer");
      });
  }, [id]);

  useEffect(() => {
    getCustomerById(id);
  }, [getCustomerById, id]);

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
