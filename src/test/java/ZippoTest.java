import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

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

}
