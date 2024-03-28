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

public class DetailUser {
    String id;
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

    @Given("User mempersiapkan data")
    public void menyiapkan_data() {
        System.out.println("ini and pertama");
        support.createUser(email);
        response = support.getResponse();
        js = response.jsonPath();


        id = js.get("id").toString();

        System.out.println("ini id: " + id);
    }


    @And("User menjalankan endpoint")
    public void jalankan_endoint() {
        support.detailUser(id);
        response = support.getResponse();
        js = response.jsonPath();
        System.out.println(response);
        response.prettyPrint();

        assertEquals(200, response.getStatusCode());

    }

    @Then("Response sudah sesuai")
    public void response_sesuai() {

        // Periksa respons JSON
        assertEquals(jsonData.get("name"), js.get("name"));
        assertEquals(jsonData.get("email"), js.get("email"));
        assertEquals(jsonData.get("gender"), js.get("gender"));
        assertEquals(jsonData.get("status"), js.get("status"));
    }
}
