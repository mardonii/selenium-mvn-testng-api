package api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.testng.AssertJUnit.assertEquals;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.FileReader;
import java.io.IOException;

public class GoREST {
    int id;
    JSONObject tokenJson = readJsonData(".\\jsonfiles\\token.json");
    String token = tokenJson.get("token").toString();
    JSONObject jsonData = readJsonData(".\\jsonfiles\\user.json");


    private JSONObject readJsonData(String filePath) {
        try {
            JSONParser parser = new JSONParser();
            FileReader reader = new FileReader(filePath);
            Object obj = parser.parse(reader);
            return (JSONObject) obj;

        } catch (ParseException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://gorest.co.in/public/v2";
    }

    @Test
    public void createUsers() {

        String data = jsonData.toString();
        // Lakukan permintaan POST dan dapatkan respons
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(data)
                .post("/users");

        response.prettyPrint();

        // Periksa status kode respons
        assertEquals(201, response.getStatusCode());

        JsonPath js = response.jsonPath();
        id = js.get("id");


        // Periksa respons JSON
        assertEquals(jsonData.get("name"), js.get("name"));
        assertEquals(jsonData.get("email"), js.get("email"));
        assertEquals(jsonData.get("gender"), js.get("gender"));
        assertEquals(jsonData.get("status"), js.get("status"));
    }

    @Test(dependsOnMethods = {"createUsers"})
    public void detailUsers() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .get("/users/" + id);
        response.prettyPrint();
        assertEquals(200, response.getStatusCode());

        JsonPath js = response.jsonPath();
        js.prettyPrint();

        assertEquals(jsonData.get("name"), js.get("name"));
        assertEquals(jsonData.get("email"), js.get("email"));
        assertEquals(jsonData.get("gender"), js.get("gender"));
        assertEquals(jsonData.get("status"), js.get("status"));
    }

    @Test(dependsOnMethods = {"detailUsers"})
    public void updateUsers() {
        jsonData.put("name", "mardoni");
        jsonData.put("gender", "female");
        jsonData.put("status", "active");
        System.out.println("ini jsondata setelah update: " + jsonData.toString());
        String data = jsonData.toString();
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(data)
                .put("/users/" + id);

        response.prettyPrint();

        // Periksa status kode respons
        assertEquals(200, response.getStatusCode());

        JsonPath js = response.jsonPath();
        id = js.get("id");


        // Periksa respons JSON
        assertEquals(jsonData.get("name"), js.get("name"));
        assertEquals(jsonData.get("email"), js.get("email"));
        assertEquals(jsonData.get("gender"), js.get("gender"));
        assertEquals(jsonData.get("status"), js.get("status"));
    }

    @Test(dependsOnMethods = {"updateUsers"})
    public void deleteUsers() {
        Response response = given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .delete("/users/" + id);

        response.prettyPrint();

        // Periksa status kode respons
        assertEquals(204, response.getStatusCode());
    }

}
