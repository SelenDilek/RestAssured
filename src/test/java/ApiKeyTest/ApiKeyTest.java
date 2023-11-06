package ApiKeyTest;

import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;


//https://l9njuzrhf3.execute-api.eu-west-1.amazonaws.com/prod/user
//Barear Token yerine
//Api Key -> key , value
//key: x-api-key
//value:GwMco9Tpstd5vbzBzlzW9I7hr6E1D7w2zEIrhOra

public class ApiKeyTest {

    @Test
    public void test1(){

        given()
                .header("x-api-key","GwMco9Tpstd5vbzBzlzW9I7hr6E1D7w2zEIrhOra")
                .when()
                .get("https://l9njuzrhf3.execute-api.eu-west-1.amazonaws.com/prod/user")

                .then()
                .log().body()

                ;
        // google developer weather api key
        // https://developers.google.com/maps/documentation/geocoding/get-api-key
        // postmande query parameter istirosa Api key seçildikten sonra Eklenme türü seçilebilir.
        // header veya query parameter hali
        // hangisi isteniyorsa ona göre yapılıyor


    }
}
