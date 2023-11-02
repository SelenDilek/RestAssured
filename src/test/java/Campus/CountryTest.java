package Campus;
import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
public class CountryTest {

    RequestSpecification reqSpec;
    Faker randomUretici = new Faker();
    String countryID="";
    @BeforeClass
    public void Setup(){

        baseURI="https://test.mersys.io/";
        Map<String ,String> userCredantial = new HashMap<>();
        userCredantial.put("username","turkeyts");
        userCredantial.put("password","TechnoStudy123");
        userCredantial.put("rememberMe","true");

        Cookies cookies =
        given()
                .body(userCredantial)
                .contentType(ContentType.JSON)

                .when()
                .post("/auth/login")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response().getDetailedCookies();
                ;

        //yukaridan cookie alinir spec e eklenir
        reqSpec=new RequestSpecBuilder()
                .addCookies(cookies) //campusun arkada yaptigi islemi yaptik
                .setContentType(ContentType.JSON)
                .build();
    }


    @Test
    public void countryTest(){ //dependson a gerek yok zaten bagli
        String rndCountryName = randomUretici.address().country()+randomUretici.address().countryCode();
        String rndCountryCode = randomUretici.address().countryCode();


        Map<String,String> newCountry= new HashMap<>();
        newCountry.put("name",rndCountryName);
        newCountry.put("code",rndCountryCode);
        //newCountry.put("translateName","[]");


        countryID= //bizim id tipimiz string onu donusturemez int yaparsak.
        given()
                .spec(reqSpec)
                .body(newCountry)

                .when()
                .post("school-service/api/countries")

                .then()
                //.log().body()
                .statusCode(201)
                .extract().path("id") // id otomatik geliyor direkt extract yapabiliriz

                ;
    }

    //

    @Test
    public void countryNegativeTest(){ //dependson a gerek yok zaten bagli
        String rndCountryName = randomUretici.address().country()+randomUretici.address().countryCode();
        String rndCountryCode = randomUretici.address().countryCode();


        Map<String,String> newCountry= new HashMap<>();
        newCountry.put("name",rndCountryName);
        newCountry.put("code",rndCountryCode);
        //newCountry.put("translateName","[]");


        countryID= //bizim id tipimiz string onu donusturemez int yaparsak.
                given()
                        .spec(reqSpec)
                        .body(newCountry)

                        .when()
                        .post("school-service/api/countries")

                        .then()
                        //.log().body()
                        .statusCode(201)
                        .extract().path("id") // id otomatik geliyor direkt extract yapabiliriz

        ;
    }






}
