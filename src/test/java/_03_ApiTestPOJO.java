import Model.Location;
import org.testng.annotations.Test;

import java.util.Locale;

import static io.restassured.RestAssured.*;

public class _03_ApiTestPOJO {
//POJO : JSON nesnesi : locationNesnesi (JSON dilini javaya ceviriyoruz)
    //kullanimi kolaylastirmak icin yapiyoruz. veriler var kalibini hazirliyoruz donusum yapip kullanacza

    @Test
    public void extractJsonAll_POJO(){
        //Ogrnci cinsinden ogr gibi Ogrenci ogr1 = new Ogrenci();
        Location location =
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .extract().body().as(Location.class) //Location Kalibina gore(Model paketinden)Location bilgilerini body den al yaz
                //donen body bilgisini al Location Class kalibi ile cevir (fakat donusumu saglayan bir dependencies eklemek lazim JSONi eski haline dondurmek lazim)
                //developer serializer yapip JSON a donusturmus biz de deserializer yapip java ya cevirecez


                ;



    }


}
