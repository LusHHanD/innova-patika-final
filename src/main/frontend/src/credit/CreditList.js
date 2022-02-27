import React, { useEffect, useState } from "react";
import { Table } from "reactstrap";
import axios from "axios";

export const CreditList = () => {
  const [credits, setCredits] = useState([]);
  const getCredits = () => {
    axios
      .get("/api/credit")
      .then((r) => setCredits(r.data))
      .catch((e) => console.error(e));
  };

  useEffect(() => {
    getCredits();
  }, []);
  return (
    <Table striped>
      <thead>
        <tr>
          <th>Credit ID</th>
          <th>Customer ID</th>
          <th>Limit</th>
          <th> Status</th>
        </tr>
      </thead>
      <tbody>
        {credits.map((credit) => (
          <tr key={credit.id}>
            <td>{credit.id}</td>
            <td>{credit.customer.identityNumber}</td>
            <td>{!credit.status ? "0 ₺" : `${credit.creditAmount} ₺`}</td>
            <td>{credit.status ? "ONAY" : "RED"}</td>
          </tr>
        ))}
      </tbody>
    </Table>
  );
};
