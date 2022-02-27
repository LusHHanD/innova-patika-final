import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import "bootstrap/dist/css/bootstrap.min.css";
import { Container } from "reactstrap";
import { CustomerEdit, CustomerList, CustomerNew } from "./customer";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import { NavbarComponent } from "./Navbar";
import { CreditList } from "./credit";

ReactDOM.render(
  <Container>
    <Router>
      <React.StrictMode>
        <NavbarComponent />
        <Routes>
          <Route path="/" element={null} />
          <Route path="/customer" element={<CustomerList />} />
          <Route path="/customer/:id" element={<CustomerEdit />} />
          <Route path="/customer/new" element={<CustomerNew />} />
          <Route path="/credit" element={<CreditList />} />
        </Routes>
      </React.StrictMode>
    </Router>
  </Container>,
  document.getElementById("root")
);
