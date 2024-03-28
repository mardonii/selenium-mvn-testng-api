package stepDefinition;

import io.cucumber.java.Before;
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

public class UpdateUser {
    String id;
    JsonPath js;
    String email = generateRandomEmail();
    String email2 = generateRandomEmail();
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
        support.createUser(email);
        response = support.getResponse();
        js = response.jsonPath();
        id = js.get("id").toString();
    }


    @When("User melakukan Update")
    public void melakukan_update() {
        support.updateUser(id, email2);
        response = support.getResponse();
        js = response.jsonPath();
        response.prettyPrint();

    }


    @Then("Berhasil Melakukan Update")
    public void berhasil_update() {
        // Periksa respons JSON
        assertEquals(201, response.getStatusCode());
        assertEquals(jsonData.get("name"), js.get("name"));
        assertEquals(jsonData.get("email"), js.get("email"));
        assertEquals(jsonData.get("gender"), js.get("gender"));
        assertEquals(jsonData.get("status"), js.get("status"));
    }


    //TEST 2
    @When("Pada Email dikosongkan")
    public void email_kosong() {
        System.out.println("ini when email dikosongkan");
        support.updateUser(id, "");
        response = support.getResponse();
    }

    @Then("Tidak berhasil melakukan Update")
    public void tidak_berhasil_update() {
        System.out.println("ini then");

        // Periksa status kode respons
        js = response.jsonPath();
        response.prettyPrint();

        // Periksa respons JSON
        assertEquals(422, response.getStatusCode());
        assertEquals("email", js.getList("field").get(0));
        assertEquals("can't be blank", js.getList("message").get(0));
    }


    //TEST 3

    @When("Update dengan email yang sudah digunakan")
    public void status_beda() {
        support.createUser("sama");
        response = support.getResponse();
        js = response.jsonPath();
    }

    @Then("Gagal melakukan Update")
    public void gagal_update() {

        // Periksa status kode respons
        assertEquals(422, response.getStatusCode());

        assertEquals("email", js.getList("field").get(0));
        assertEquals("has already been taken", js.getList("message").get(0));
    }
}