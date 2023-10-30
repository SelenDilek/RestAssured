import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
public class _04_Tasks {
    /**
     create a request to https://jsonplaceholder.typicode.com/todos/2
     expect status 200
     expect content type JSON
     expect title in response body to be "quis ut nam facilis et officia qui"
     */

@Test
    public void task3() {

        given()

                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")

                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("title", equalTo("quis ut nam facilis et officia qui"))
        ;

    }







}
