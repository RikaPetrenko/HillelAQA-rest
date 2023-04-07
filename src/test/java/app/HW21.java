package app;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;


public class HW21 {

    private static final String BASE_URL = "https://reqres.in/api";

    @Test
    public void testGetSingleUser() {
        Response response = RestAssured.get(BASE_URL + "/users/1");
        Assert.assertEquals(response.getStatusCode(), 200);

        String firstName = response.jsonPath().get("data.first_name");
        String lastName = response.jsonPath().get("data.last_name");
        String email = response.jsonPath().get("data.email");

        Assert.assertEquals(firstName, "George");
        Assert.assertEquals(lastName, "Bluth");
        Assert.assertEquals(email, "george.bluth@reqres.in");
    }

    @Test
    public void testCreateUser() {
        String requestBody = "{\n" +
                "    \"name\": \"John\",\n" +
                "    \"job\": \"Software Engineer\"\n" +
                "}";
        Response response = RestAssured.given()
                .header("Content-Type", "application/json")
                .body(requestBody)
                .post(BASE_URL + "/users");
        Assert.assertEquals(response.getStatusCode(), 201);

        String name = response.jsonPath().get("name");
        String job = response.jsonPath().get("job");

        Assert.assertEquals(name, "John");
        Assert.assertEquals(job, "Software Engineer");
    }

    @Test
    public void testListResources() {
        Response response = RestAssured.get(BASE_URL + "/unknown");
        Assert.assertEquals(response.getStatusCode(), 200);

        int totalPages = response.jsonPath().get("total_pages");
        int perPage = response.jsonPath().get("per_page");
        int total = response.jsonPath().get("total");
        int actualPage = response.jsonPath().get("page");

        Assert.assertEquals(totalPages, 2);
        Assert.assertEquals(perPage, 6);
        Assert.assertEquals(total, 12);
        Assert.assertEquals(actualPage, 1);
    }
}
