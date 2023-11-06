package Campus;
import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
public class _08_CountryTest {

    RequestSpecification reqSpec;
    Faker randomUretici = new Faker();
    String countryID="";
    String rndCountryName="";
    String rndCountryCode="";
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
         rndCountryName = randomUretici.address().country()+randomUretici.address().countryCode();
         rndCountryCode = randomUretici.address().countryCode();


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
                .extract().path("id")

                // id otomatik geliyor direkt extract yapabiliriz

                ;
    }

    //// Aynı countryName ve code gönderildiğinde kayıt yapılmadığını yani createCountryNegative testini yapınız donen sonuctan already mesajini al

    @Test(dependsOnMethods = "countryTest") //bi tane olunca otomatik calistirir. dependson bir kademe yukari gider
    public void createCountryNegative(){
        Map<String,String> newCountry= new HashMap<>();
        newCountry.put("name",rndCountryName);
        newCountry.put("code",rndCountryCode);


                given()
                        .spec(reqSpec)
                        .body(newCountry)

                        .when()
                        .post("school-service/api/countries")

                        .then()
                        .log().body() //bunu yazdik ki mesaji gorelim
                        .statusCode(400)
                        .body("message",containsString("already"))// id otomatik geliyor direkt extract yapabiliriz

        ;
    }

    // updat eCountry testini yapınız
    @Test(dependsOnMethods = "createCountryNegative")
    public void updateCountry(){
        Map<String,String> updCountry= new HashMap<>();
        String newCountryName="Updated Country" + randomUretici.number().digits(5);

        updCountry.put("id",countryID);
        updCountry.put("name",newCountryName);
        updCountry.put("code","001122334455x");

        given()
                .spec(reqSpec)
                .body(updCountry)

                .when()
                .put("school-service/api/countries")

                .then()
                .log().body()
                .statusCode(200)
                .body("name",equalTo(newCountryName))

                ;

    }

    @Test(dependsOnMethods = "updateCountry")
    public void deleteCountryPositiveTest(){


        given()
                .spec(reqSpec)

                .when()
                .delete("school-service/api/countries/"+countryID)

                .then()
                .log().body()
                .statusCode(200)

        ;

    }
    //not body kismi bizim test kismimiz
    // Delete Country testinin Negative test halini yapınız
    // dönen mesajın "Country not found" olduğunu doğrulayınız
    @Test(dependsOnMethods = "deleteCountryPositiveTest")
    public void deleteCountryNegativeTest(){


        given()
                .spec(reqSpec)

                .when()
                .delete("school-service/api/countries/"+countryID)

                .then()
                .log().body()
                .statusCode(400)
                .body("message",containsString("Country not found")) //donen kisim console daki kisim icin body yapilir

        ;
    }


    // Aşağıdaki bölüm translate göndermemiz gerektiğindeki seçeneklerimizdir.
    @Test
    public void createCountryAllParamater(){

        rndCountryName= randomUretici.address().country()+randomUretici.address().countryCode();
        rndCountryCode= randomUretici.address().countryCode();

        Object[] arr=new Object[1];
        Map<String,Object> newCountry=new HashMap<>();
        newCountry.put("name",rndCountryName);
        newCountry.put("code",rndCountryCode);
        newCountry.put("translateName", new Object[1] ); //arr

        given()
                .spec(reqSpec)
                .body(newCountry)
                //.log().all()
                .when()
                .post("school-service/api/countries")

                .then()
                .log().body()
                .statusCode(201)
                .extract().path("id");
        ;
    }

    @Test
    public void createCountryAllParamaterClass(){

        rndCountryName= randomUretici.address().country()+randomUretici.address().countryCode();
        rndCountryCode= randomUretici.address().countryCode();

        Country newCountry=new Country();
        newCountry.name=rndCountryName;
        newCountry.code=rndCountryCode;
        newCountry.translateName = new Object[1];

        given()
                .spec(reqSpec)
                .body(newCountry)
                //.log().all()
                .when()
                .post("school-service/api/countries")

                .then()
                .log().body()
                .statusCode(500)
                .extract().path("id");
        ;
    }

}
