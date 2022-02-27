import React, { useEffect, useState } from "react";
import { Button, ButtonGroup, Table } from "reactstrap";
import axios from "axios";
import { Link, useNavigate } from "react-router-dom";

export const CustomerList = () => {
  const navigate = useNavigate();
  const [customers, setCustomers] = useState([]);
  const getCustomers = () => {
    axios
      .get("/api/customer")
      .then((r) => setCustomers(r.data))
      .catch((e) => console.error(e));
  };

  const deleteCustomerById = (id) => {
    axios
      .delete(`/api/customer/${id}`)
      .then(() => getCustomers())
      .catch((e) => console.error(e));
  };

  const applyForCredit = (id) => {
    axios.post("/api/credit", { identityNumber: id }).then(navigate("/credit"));
  };

  useEffect(() => {
    getCustomers();
  }, []);
  return (
    <>
      <Button color="success" tag={Link} to="/customer/new">
        New Customer
      </Button>
      <Table striped>
        <thead>
          <tr>
            <th>Identity Number</th>
            <th>Name</th>
            <th>Surname</th>
            <th>Phone Number</th>
            <th>Monthly Income</th>
            <th className="text-right">Actions</th>
          </tr>
        </thead>
        <tbody>
          {customers.map((customer) => (
            <tr key={customer.identityNumber}>
              <td>{customer.identityNumber}</td>
              <td>{customer.name}</td>
              <td>{customer.surname}</td>
              <td>{customer.phoneNumber}</td>
              <td>{customer.income} ₺</td>
              <td className="text-right">
                <ButtonGroup>
                  <Button
                    color="primary"
                    size="sm"
                    onClick={() => applyForCredit(customer.identityNumber)}
                  >
                    Apply For Credit
                  </Button>
                  <Button
                    color="warning"
                    size="sm"
                    tag={Link}
                    to={`/customer/${customer.identityNumber}`}
                  >
                    Update
                  </Button>
                  <Button
                    color="danger"
                    size="sm"
                    onClick={() => deleteCustomerById(customer.identityNumber)}
                  >
                    Delete
                  </Button>
                </ButtonGroup>
              </td>
            </tr>
          ))}
        </tbody>
      </Table>
    </>
  );
};
