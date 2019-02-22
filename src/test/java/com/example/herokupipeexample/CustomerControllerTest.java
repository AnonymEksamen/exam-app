package com.example.herokupipeexample;


import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;



@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerControllerTest
{
    @LocalServerPort
    protected int port = 0;

    @Autowired
    private CustomerRepository repo;

    @Before
    @After
    public void init()
    {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        repo.deleteAll();
    }

    @Test
    public void testCreate()
    {
        String firstName = "Fornavn";
        String lastName = "Etternavn";
        Customer foo = new Customer(firstName,lastName);

        Customer customer = given().contentType(ContentType.JSON)
                .body(foo)
                .post()
                .then()
                .statusCode(200)
                .extract().as(Customer.class);

        assertEquals(customer.getFirstName(), firstName);
        assertEquals(customer.getLastName(), lastName);


    }

    @Test
    public void testDelete()
    {
        String firstName = "Fornavn";
        String lastName = "Etternavn";
        Customer foo = new Customer(firstName,lastName);

        Customer customer = given().contentType(ContentType.JSON)
                .body(foo)
                .post()
                .then()
                .statusCode(200)
                .extract().as(Customer.class);

        assertEquals(customer.getFirstName(), firstName);
        assertEquals(customer.getLastName(), lastName);

        Customer deletedCustomer = given().contentType(ContentType.JSON)
                .body(foo)
                .post()
                .then()
                .statusCode(200)
                .extract().as(Customer.class);

        assertEquals(customer.getFirstName(), firstName);
        assertEquals(customer.getLastName(), lastName);
    }

    @Test
    public void testCreateAndGet()
    {
        String firstName = "Fornavn";
        String lastName = "Etternavn";
        Customer customer1 = new Customer(firstName,lastName);

        Customer customer = given().contentType(ContentType.JSON)
                .body(customer1)
                .post()
                .then()
                .statusCode(200)
                .extract().as(Customer.class);


        given().accept(ContentType.JSON)
                .get("/list?lastName=Etternavn")
                .then()
                .statusCode(200)
                .body("firstName", equalTo(Collections.singletonList(firstName)))
                .body("lastName", equalTo(Collections.singletonList(lastName)));
    }

    @Test
    public void testGetOnEmptyList()
    {
        given().accept(ContentType.JSON)
                .get("/list?lastName=Etternavn")
                .then()
                .statusCode(200)
                .body("size()", equalTo(0));
    }
}

