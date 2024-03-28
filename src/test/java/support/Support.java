package support;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

import static io.restassured.RestAssured.given;

public class Support {

    Response response;
    String data;
    JSONObject tokenJson = readJsonData(".\\jsonfiles\\token.json");
    String token = tokenJson.get("token").toString();
    JSONObject jsonData = readJsonData(".\\jsonfiles\\user.json");

    // INI UNTUK MEMBACA FILE JSON
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


    public void createUser(String email) {
        if (Objects.equals(email, "")) {
            jsonData.put("email", "");
            data = jsonData.toString();
            System.out.println("if " + data);
        } else if (Objects.equals(email, "sama")) {
            jsonData.put("email", "mardoni@qa.id");
            data = jsonData.toString();
            System.out.println("else if " + data);
        } else {
            jsonData.put("email", email);
            data = jsonData.toString();
            System.out.println("else" + data);
        }

        sendRequestPost(data);
    }

    private void sendRequestPost(String data) {
        response = given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(data)
                .post("/users");
    }


    public void detailUser(String id) {
        sendRequestGet(id);
    }

    private void sendRequestGet(String id) {
        response = given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .get("/users/" + id);
    }


    public void updateUser(String id, String email) {
        if (Objects.equals(email, "")) {
            jsonData.put("email", "");
            data = jsonData.toString();
            System.out.println("if " + data);
        } else if (Objects.equals(email, "sama")) {
            jsonData.put("email", "mardoni@qa.id");
            data = jsonData.toString();
            System.out.println("else if " + data);
        } else {
            jsonData.put("email", email);
            data = jsonData.toString();
            System.out.println("else" + data);
        }

        sendRequestPost(data);
    }

    public void deleteUser(String id) {
        sendRequestDelete(id);
    }

    private void sendRequestDelete(String id) {
        response = given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .get("/users/" + id);
    }

    private void sendRequestUpdate(String id, String data) {
        response = given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(data)
                .put("/users/" + id);
    }


    private String generateRandomEmail() {
        String angka = UUID.randomUUID().toString().substring(1, 9);
        return "mardoni" + angka + "@qa.id";
    }


    public Response getResponse() {
        return response;
    }

    public JSONObject getJsonObj() {
        return jsonData = readJsonData(".\\jsonfiles\\user.json");
    }


}
