import io.restassured.http.ContentType;
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

    @Test
    public void queryParamTest(){
        // meta -> saklanan bilgi tum bilgi
        given()

                .param("page",1)
                .log().uri() //before request


                .when()
                .get("https://gorest.co.in/public/v1/users") //?page=1

                .then()
                .statusCode(200)
                .log().body()


                ;





    }




}
