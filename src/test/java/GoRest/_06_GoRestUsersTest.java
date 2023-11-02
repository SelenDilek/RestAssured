package GoRest;

import Model.User;
import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

//POSTMANDEKI Users kismini restassured ile yapiyoruz
public class _06_GoRestUsersTest {
    //bilgileri postmanden aldik manuel olarak yapmistik ilk basta
// {
// "name":"{{$randomFullName}}",
// "gender":"male",
// "email":"{{$randomEmail}}",
// "status":"active"
// }
    Faker randomUretici = new Faker();
    int userID=0;

    RequestSpecification reqSpec;

    @BeforeClass
    public void setup(){

        baseURI="https://gorest.co.in/public/v2/users"; //baseURI mutlaka ilk tanimlanmali
        reqSpec=new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer b1aaa4d8bfef49260c26060bb827fe1732490b76c6abd2d68480dabb2709f1b5")
                .setContentType(ContentType.JSON)
                .build();
    }
    @Test(enabled = false)
    public void createUser(){

        String rndFullName= randomUretici.name().fullName();
        String rndEmail= randomUretici.internet().emailAddress();
         userID=
        given() //giden body, token, contentType
                .header("Authorization","Bearer dcc3254806885a9bfb9137e6b22e9f6637ca8bfcd05268fd911eafc373e9bf0f")
                .body("\"name\":\""+rndFullName+"\",\"gender\":\"male\",\"email\":\""+rndEmail+"\",\"status\":\"active\"") //giden body
                .contentType(ContentType.JSON)

                .when()
                .post("https://gorest.co.in/public/v2/users")


                .then()
                .log().body()
                .statusCode(201)
                .extract().path("id");
                ;
        System.out.println("userID = " + userID);



    }

    @Test
    public void createUserMap(){

        String rndFullName= randomUretici.name().fullName();
        String rndEmail= randomUretici.internet().emailAddress();

        Map<String,String> newUser = new HashMap<>();
        newUser.put("name",rndFullName);
        newUser.put("gender","male");
        newUser.put("email",rndEmail);
        newUser.put("status","active");


        userID=
                given() //giden body, token, contentType
                        .spec(reqSpec)
                        .body(newUser) //giden body tipi zaten JSON sen bunu direkt koy kendisi otomatik JSON a cevirip gonderiyor
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


    @Test(enabled = false) //hepsi calismasin diye
    public void createUserClass(){

        String rndFullName= randomUretici.name().fullName();
        String rndEmail= randomUretici.internet().emailAddress();

        User newUser = new User();
        newUser.name=rndFullName; //rahat erisebilmek icin public yaptik
        newUser.email=rndEmail;
        newUser.gender="male";
        newUser.status="active";


        userID=
                given() //giden body, token, contentType
                        .header("Authorization","Bearer 2446fe03e453a750f034d72e3c86ca039ed317f84bd5bc7f03db92e8c2e3150b")
                        .body(newUser) //giden body tipi zaten JSON sen bunu direkt koy kendisi otomatik JSON a cevirip gonderiyor
                        .contentType(ContentType.JSON)

                        .when()
                        .post("https://gorest.co.in/public/v2/users")


                        .then()
                        .log().body()
                        .statusCode(201)
                        .extract().path("id");
        ;
        System.out.println("userID = " + userID);

    }

    @Test(dependsOnMethods = "createUserMap") //bunu calisitirnca digerini de beraber calistirir
    public void getUserById(){ //giden body yok ama authoriztn var
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


    @Test(dependsOnMethods = "getUserById") //collection run gibi oldu postmandeki sirali calisacak birbirine bagladik
    public  void updateUser(){
        Map<String,String> updateUser = new HashMap<>();
        updateUser.put("name","DSln");

        given()
                .spec(reqSpec)
                .body(updateUser)

                .when()
                .put(""+userID)

                .then()
                .log().body()
                .statusCode(200)
                .body("id",equalTo(userID))
                .body("name", equalTo("DSln"))
                ;
    }

    @Test(dependsOnMethods = "updateUser")
    public void deleteUser(){
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

    @Test(dependsOnMethods = "deleteUser")
    public void deleteUserNegativeTest(){
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
