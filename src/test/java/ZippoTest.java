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
                      .log().body() //donen degerlerin tumunu json data deniliyor. posymande body=pm.response.json(); ile aliyorduk -> body.country
                .statusCode(200) //assertion
                .body("country",equalTo("United States")) //assertion ->kutuphane ekleyecez kendi icinde test edecez

        ;

    }
}
