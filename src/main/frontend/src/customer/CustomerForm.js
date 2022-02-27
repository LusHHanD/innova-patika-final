import React from "react";
import { Button, Col, Form, FormGroup, Input, Label, Row } from "reactstrap";
import { Link } from "react-router-dom";

export const CustomerForm = ({ values, handleOnChange, handleOnSubmit }) => {
  return (
    <Form onSubmit={handleOnSubmit}>
      <FormGroup row>
        <Label sm={2}>Identity Number</Label>
        <Input
          type="text"
          value={values.identityNumber}
          onChange={(e) => handleOnChange(e.target.value, "identityNumber")}
        />
      </FormGroup>
      <FormGroup row>
        <Label sm={2}>Name</Label>
        <Input
          type="text"
          value={values.name}
          onChange={(e) => handleOnChange(e.target.value, "name")}
        />
      </FormGroup>
      <FormGroup row>
        <Label sm={2}>Surname</Label>
        <Input
          type="text"
          value={values.surname}
          onChange={(e) => handleOnChange(e.target.value, "surname")}
        />
      </FormGroup>
      <FormGroup row>
        <Label sm={2}>Phone Number</Label>
        <Input
          type="text"
          value={values.phoneNumber}
          onChange={(e) => handleOnChange(e.target.value, "phoneNumber")}
        />
      </FormGroup>
      <FormGroup row>
        <Label sm={2}>Monthly Income</Label>
        <Input
          type="text"
          value={values.income}
          onChange={(e) => handleOnChange(e.target.value, "income")}
        />
      </FormGroup>
      <Row>
        <Col>
          <Button size="md" color="primary" tag={Link} to="/customer">
            Back
          </Button>
          <Button
            size="md"
            color="success"
            className="float-right"
            type="submit"
          >
            Save
          </Button>
        </Col>
      </Row>
    </Form>
  );
};
