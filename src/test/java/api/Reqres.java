package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


import java.io.FileReader;
import java.io.IOException;

import static io.restassured.RestAssured.given;


public class Reqres {

    String reqres = "https://reqres.in/api" ;
    String requestBody = """
            {
            "name": "John",
            "job": "Tester"
            }
            """;


    @Test
    public void getListParam() {
        RestAssured.baseURI = reqres;
        Response response = given().param("page", 2).when().get();
    }


    @Test
    public void post() {
        RestAssured.baseURI = reqres;
        Response resp = given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post("/users");
        System.out.println(resp.getBody().asString());
        resp.prettyPrint();
    }

}
