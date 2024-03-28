package stepDefinition;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import support.Support;

import java.util.UUID;

import static org.testng.AssertJUnit.assertEquals;

public class DeleteUser {
    String id;
    JsonPath js;
    String email = generateRandomEmail();
    Support support = new Support();
    Response response;


    private String generateRandomEmail() {
        String angka = UUID.randomUUID().toString().substring(1, 9);
        return "mardoni" + angka + "@qa.id";
    }

    @Before
    public void setup() {
        RestAssured.baseURI = "https://gorest.co.in/public/v2";
    }

    @Given("User melakukan persiapan data")
    public void persiapan_data() {
        System.out.println("ini and pertama");
        support.createUser(email);
        response = support.getResponse();
        js = response.jsonPath();


        id = js.get("id").toString();

    }

    @And("User melakukan hit endpoint")
    public void hit_endpoint() {

        support.deleteUser(id);
        response = support.getResponse();
        js = response.jsonPath();
        System.out.println(response);
        response.prettyPrint();

    }

    @Then("User Berhasil Terhapus")
    public void user_deleted() {
        assertEquals(200, response.getStatusCode());
    }
}
