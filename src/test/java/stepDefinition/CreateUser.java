package stepDefinition;


import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import support.Support;

import java.util.UUID;

import static org.testng.AssertJUnit.assertEquals;

public class CreateUser {

    int id;
    JsonPath js;
    String email = generateRandomEmail();
    Support support = new Support();
    Response response;
    JSONObject jsonData = support.getJsonObj();


    private String generateRandomEmail() {
        String angka = UUID.randomUUID().toString().substring(1, 9);
        return "mardoni" + angka + "@qa.id";
    }


    @Before
    public void setup() {
        RestAssured.baseURI = "https://gorest.co.in/public/v2";

    }

    @Given("User preparing data")
    public void user_preparing_data() {
    }


    @And("User Hit Endpoint")
    public void user_hit_endpoint() {
        System.out.println("ini and pertama");
        support.createUser(email);
        response = support.getResponse();
        System.out.println("ini and");
        // Lakukan permintaan POST dan dapatkan respons

    }

    @Then("Response is okay")
    public void response_is_okay() {
        System.out.println("ini then");
        // Periksa status kode respons
        assertEquals(201, response.getStatusCode());

        response.prettyPrint();
        js = response.jsonPath();
        id = js.get("id");

        // Periksa respons JSON
        assertEquals(jsonData.get("name"), js.get("name"));
        assertEquals(jsonData.get("email"), js.get("email"));
        assertEquals(jsonData.get("gender"), js.get("gender"));
        assertEquals(jsonData.get("status"), js.get("status"));
    }

    @When("Email dikosongkan")
    public void email_dikosongkan() {
        System.out.println("ini when email dikosongkan");
        support.createUser("");
        response = support.getResponse();
    }

    @Then("Response email cant be blank")
    public void Response_email_cant_be_blank() {
        System.out.println("ini then");


        // Periksa status kode respons
        js = response.jsonPath();
        response.prettyPrint();


        // Periksa respons JSON
        assertEquals(422, response.getStatusCode());

        assertEquals("email", js.getList("field").get(0));
        assertEquals("can't be blank", js.getList("message").get(0));
    }

    @When("Status diisi dengan selain active & inactive")
    public void status_beda() {
        support.createUser("sama");
        response = support.getResponse();
        js = response.jsonPath();
    }

    @Then("Response email sudah ada")
    public void already_been_taken() {

        // Periksa status kode respons
        assertEquals(422, response.getStatusCode());

        assertEquals("email", js.getList("field").get(0));
        assertEquals("has already been taken", js.getList("message").get(0));
    }
}
