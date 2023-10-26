import io.restassured.http.ContentType;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.internal.RequestSpecificationImpl;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ZippoTest {

    @Test
    public void test1(){

        given()
                //hazirlik islemleri kodlari yaziliyor

                .when()

                //end point verilir ve method verilir.
                //endpoint (url) , method u verip istek gonderiliyor


                    .then()
                    //assertion , test,data islemleri
        ;

    }

    @Test
    public void statusCodeTest(){

        given()
                //hazirlik kismi bos

                .when()
                .get("http://api.zippopotam.us/us/90210")



                .then()
                .log().body() // donen body json data, log().All() : data disindaki her sey
                .statusCode(200); //test kismi oldugundan adderstion status code 200 mu 201 yazinca hata veriyor

    }

    @Test
    public void contentTypeTest(){

        given()
                //hazirlik kismi bos

                .when()
                .get("http://api.zippopotam.us/us/90210")



                .then()
                .log().body() // donen body json data, log().All() : data disindaki her sey
                .statusCode(200)
                .contentType(ContentType.JSON); // donen datanin tipi JSON mi
    }

    @Test
    public void test3(){
        //bir bilginin degerini
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                      .then()
                      //.log().body() //donen degerlerin tumunu json data deniliyor. posymande body=pm.response.json(); ile aliyorduk -> body.country
                      .statusCode(200) //assertion
                      .body("country",equalTo("United States")) //assertion ->kutuphane ekleyecez kendi icinde test edecez
                //body nin country degiskeni "United States" e esit mi

        ;

    }
    //Soru : http://api.zippopotam.us/us/90210 endpointten donen place dizisinin ilknelemaninin state degerlerinin  "California" oldugunu
    @Test
    public void checkStateInResponseBody(){
        //bir bilginin degerini
        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                //.log().body() //donen degerlerin tumunu json data deniliyor. posymande body=pm.response.json(); ile aliyorduk -> body.country
                .statusCode(200) //assertion
                .body("places[0].state",equalTo("California")) //assertion ->kutuphane ekleyecez kendi icinde test edecez
        //body nin country degiskeni "United States" e esit mi

        // places.state -> herhangi bir index vermezsek tumunu verir.
        ;
    }
    // Soru : "http://api.zippopotam.us/tr/01000"  endpoint in dönen
    // place dizisinin herhangi bir elemanında  "Dörtağaç Köyü" değerinin
    // olduğunu doğrulayınız
    @Test
    public void checkHasItem(){

        given()

                .when()
                .get("http://api.zippopotam.us/tr/01000")

                .then()
                //.log().body()
                .body("places.'place name'",hasItem("Dörtağaç Köyü"))
                .statusCode(200)


        ;
    }

    // Soru : "http://api.zippopotam.us/us/90210"  endpoint in dönen
    // place dizisinin dizi uzunluğunun 1 olduğunu doğrulayınız.

    @Test
    public void bodyArrayHasSizeTest2(){

        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .statusCode(200)
                .body("places",hasSize(1)) //places in item size i 1 e esit mi
                //.body("places.size()",hasSize(1))


        ;


    }

    //coklu test , biri fail olursa digerleri bundan etkilenmiyor
    @Test
    public void combiningTest(){

        given()

                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .log().body()
                .statusCode(200)
                .body("places",hasSize(1))
                .body("places[0].state",equalTo("California"))
                .body("places[0].'place name'",equalTo("Beverly Hills"))
                .body("country",equalTo("United States"))

                ;

    }

    @Test
    public void pathParamTest(){
        // / varsa path param

        given()
                //biryerden okuyup gonderme islemi
                .pathParam("ulke","us") //ulke degiskenine us ata
                .pathParam("postaKod",90210) //postakodu
                .log().uri() // request link gitmeden calismadan onceki hali

                .when()
                .get("http://api.zippopotam.us/{ulke}/{postaKod}")

                .then()
                .statusCode(200)
        ;
    }

    // .param() bir HTTP GET veya POST isteği sırasında sorgu parametreleri eklemek için kullanılır. Sorgu parametreleri, EndPointin yani URL'nin sonuna ekliyoruz ve isteğin hedef kaynağa API sunucusuna ilgili verileri iletmek için kullanılır.
    //Örneğin, .param("page", 1) ile /api/resource?page=1 gibi bir URL oluşturabiliyoruz.

    @Test
    public void queryParamTest(){
        // meta -> saklanan bilgi tum bilgi
        given()

                .param("page",1) //?page=1 seklinde linke ekleniyor //queryparam ile de kullanilabiliyor.
                .log().uri() //before request


                .when()
                .get("https://gorest.co.in/public/v1/users") //?page=1

                .then()
                .statusCode(200)
                .log().body()
                ;

    }

    @Test
    public void queryParamTest2(){
        // https://gorest.co.in/public/v1/users?page=3
        // bu linkteki 1 den 10 kadar sayfaları çağırdığınızda response daki donen page degerlerinin
        // çağrılan page nosu ile aynı olup olmadığını kontrol ediniz.
        for (int i = 1; i <10 ; i++) {

            given()

                    .param("page",i) //?page=1 seklinde linke ekleniyor //queryparam ile de kullanilabiliyor.
                    .log().uri() //before request


                    .when()
                    .get("https://gorest.co.in/public/v1/users") //?page=1

                    .then()
                    .statusCode(200)
                    .body("meta.pagination.page",equalTo(i)) //json finder dan page in path ine ulastim.
                    //.log().body()
            ;


        }
    }

    //her methodda kullanacagimiz seyleri genelliyoruz
    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;
    @BeforeClass
    public void setup(){
        baseURI = "https://gorest.co.in/public/v1";

        requestSpec= new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .log(LogDetail.URI)  // log().uri()
                .build();

        responseSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)  // statusCode(200)
                .log(LogDetail.BODY)
                .expectContentType(ContentType.JSON)
                .build();
    }

    @Test
    public void requestResponseSpecificationn(){
        given()
                .param("page",1)
                .spec(requestSpec)

                .when()
                .get("/users") // http hok ise baseUri baş tarafına gelir.

                .then()
                .spec(responseSpec)
        ;
    }

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



}
