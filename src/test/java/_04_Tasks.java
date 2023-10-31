import Model.todos2;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class _04_Tasks {
    /**
     * create a request to https://jsonplaceholder.typicode.com/todos/2
     * expect status 200
     * expect content type JSON
     * expect title in response body to be "quis ut nam facilis et officia qui"
     */

    @Test
    public void task1() {

        given()

                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")

                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("title", equalTo("quis ut nam facilis et officia qui"))
        ;

    }

//
//    Task 2
//    create a request to https://jsonplaceholder.typicode.com/todos/2
//    expect status 200
//    expect content type JSON
//    1.expect response completed status to be false(hamcrest)
//    2.extract completed field and testNG assertion(testNG)

    @Test
    public void task2() {
        //1.yontem
        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("completed", equalTo(false))
        ;

        boolean completed =
                given()
                        .when()
                        .get("https://jsonplaceholder.typicode.com/todos/2")

                        .then()
                        .statusCode(200)
                        .contentType(ContentType.JSON)
                        .extract().path("completed");
        Assert.assertTrue(completed); //sonuca bak string degil boolean o yuzden boolean diye tanimladik

    }

    /** Task 3

     create a request to https://jsonplaceholder.typicode.com/todos/2
     expect status 200
     Converting Into POJO*/

    @Test
    public void task3(){

        todos2 todos =
        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")
                .then()
                .statusCode(200)
                .extract().body().as(Model.todos2.class)

                ;

        Assert.assertTrue(todos.getId()==2);

    }


}
