import io.restassured.http.ContentType;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class _02_ApiTestExtract {
    @Test
    public void extratingJsonPath(){

        String countryName =
                given()
                        .when()
                        .get("http://api.zippopotam.us/us/90210")

                        .then()
                        .extract().path("country") //path i country olan

                ;

        System.out.println("countryName = " + countryName);
        Assert.assertEquals(countryName,"United States");



    }

    // // Soru : "http://api.zippopotam.us/us/90210"  endpoint indne dönen
    //    // place dizisinin ilk elemanının state değerinin  "California"
    //    // olduğunu testNG Assertion ile doğrulayınız
    @Test
    public void extratingJsonPath2(){

        String placeState =
                given()
                        .when()
                        .get("http://api.zippopotam.us/us/90210")

                        .then()
                        .extract().path("places[0].state" ) //path i country olan

                ;

        System.out.println("placeState in ilk elemani = " + placeState);
        Assert.assertEquals(placeState,"California");



    }
    // Soru : "http://api.zippopotam.us/us/90210"  endpoint indne dönen
    // place dizisinin ilk elemanının place name değerinin  "Beverly Hills"
    // olduğunu testNG Assertion ile doğrulayınız
    @Test
    public void extratingJsonPath3(){

        String placeName =
                given()
                        .when()
                        .get("http://api.zippopotam.us/us/90210")

                        .then()
                        .extract().path("places[0].'place name'" ) //places[0]["place name"]

                ;

        System.out.println("placeState in ilk elemani place name değerinin = " + placeName);
        Assert.assertEquals(placeName,"Beverly Hills");



    }

    // Soru : "https://gorest.co.in/public/v1/users"  endpoint in den dönen
    // limit bilgisinin 10 olduğunu testNG ile doğrulayınız.

    @Test
    public void extratingJsonPath4(){
        int limit =
                given()
                        .when()
                        .get("https://gorest.co.in/public/v1/users")

                        .then()
                        .log().body()
                        .extract().path("meta.pagination.limit" ) //places[0]["place name"]

                ;

        System.out.println("Endpoint limit  = " + limit);
        Assert.assertEquals(limit,10);
        Assert.assertTrue(limit==10);



    }

    @Test
    public void extratingJsonPath5(){
        // ilk elementin id si : // coklu yapida extract islemi
        List<Integer> idler =
                given()
                        .when()
                        .get("https://gorest.co.in/public/v1/users")

                        .then()
                        .log().body()
                        .extract().path("data.id" ) //data[0].id -> ilk id

                ;

        System.out.println("Endpoint limit  = " + idler);




    }



}
