package GoRest;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
// GoRest Comment API Testing
public class _07_GoRestCommentTest {
    Faker randomUretici = new Faker();
    int userID=0;

    RequestSpecification reqSpec;

    @BeforeClass
    public void setup(){

        baseURI="https://gorest.co.in/public/v2/comments"; //baseURI mutlaka ilk tanimlanmali
        reqSpec=new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer 1af0861107aa50e5e00f92dab2f89764d060fe745172a161e8f14a67a72b319c")
                .setContentType(ContentType.JSON)
                .build();

    }

    @Test
    public void getComments(){


        given()
                .spec(reqSpec)
                .when()
                .then()
                .statusCode(200)
                ;


    }


    @Test(dependsOnMethods = "getComments")
    public void createCommentsMap(){

        String rndFullName= randomUretici.name().fullName();
        String rndEmail= randomUretici.internet().emailAddress();
        String rndBody =randomUretici.expression("Maiores aut id. Exercitationem quae.");


        Map<String,String> newComments = new HashMap<>();

        newComments.put("post_id","82477");
        newComments.put("name",rndFullName);
        newComments.put("email",rndEmail);
        newComments.put("body",rndBody);



        userID=
                given() //giden body, token, contentType
                        .spec(reqSpec)
                        .body(newComments) //giden body tipi zaten JSON sen bunu direkt koy kendisi otomatik JSON a cevirip gonderiyor
                        .contentType(ContentType.JSON)

                        .when()
                        .post("")


                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().path("id");
        ;
        System.out.println("userID = " + userID);


    }
    @Test(dependsOnMethods = "createCommentsMap") //bunu calisitirnca digerini de beraber calistirir
    public void getCommentsById(){ //giden body yok ama authoriztn var
        given()
                .spec(reqSpec)

                .when()
                .get(""+userID)

                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id",equalTo(userID))
        ;

    }

    @Test(dependsOnMethods = "getCommentsById") //collection run gibi oldu postmandeki sirali calisacak birbirine bagladik
    public  void updateComments(){
        Map<String,String> updateComments = new HashMap<>();
        updateComments.put("name","DSln");

        given()
                .spec(reqSpec)
                .body(updateComments)

                .when()
                .put(""+userID)

                .then()
                .log().body()
                .statusCode(200)
                .body("id",equalTo(userID))
                .body("name", equalTo("DSln"))
        ;
    }
    @Test(dependsOnMethods = "updateComments")
    public void deleteComments(){
        given()
                .spec(reqSpec)

                .when()
                .delete(""+userID)

                .then()
                .log().all()
                .statusCode(204)

        ;
//tumunu calistirmak lazim class in tumunu
    }

    @Test(dependsOnMethods = "deleteComments")
    public void deleteCommentsNegativeTest(){
        given()
                .spec(reqSpec)

                .when()
                .delete(""+userID)

                .then()
                // .log().all()
                .statusCode(404)
        ;
    }




}
