package APITest;

import Model.Location;
import Model.Place;
import org.testng.annotations.Test;

import java.util.Locale;

import static io.restassured.RestAssured.*;

public class _03_ApiTestPOJO {
//POJO : JSON nesnesi : locationNesnesi (JSON dilini javaya ceviriyoruz)
    //kullanimi kolaylastirmak icin yapiyoruz. veriler var kalibini hazirliyoruz donusum yapip kullanacza

    //developer in yaptigi islemin tersini yapiyoruz
    @Test
    public void extractJsonAll_POJO(){
        //Ogrnci cinsinden ogr gibi Ogrenci ogr1 = new Ogrenci();
        Location locationNesnesi =
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")

                .then()
                .extract().body().as(Location.class) //Location Kalibina gore(Model paketinden)Location bilgilerini body den al yaz
                //donen body bilgisini al Location Class kalibi ile cevir (fakat donusumu saglayan bir dependencies eklemek lazim JSONi eski haline dondurmek lazim)
                //developer serializer yapip JSON a donusturmus biz de deserializer yapip java ya cevirecez


                ;

        System.out.println(" locationNesnesi.getCountry() = " + locationNesnesi.getCountry());

        System.out.println("locationNesnesi.getPlaces() = " + locationNesnesi.getPlaces());

        for(Place p : locationNesnesi.getPlaces()){

            System.out.println("p = " + p);
        }



// JSonaDonustur(locationNesnesi); developer bu şekilde dönüştürmüştü
// Json.Serialise(locationNesnesi);  bende tersine deSerialize yaptım.
// yani NESNE yi elde ettim.
    }


    //Soru: http://api.zippopotam.us/tr/01000  endpointinden dönen verilerden "Dörtağaç Köyü" ait bilgileri yazdırınız
    @Test
    public void extractJsonAll_POJO2(){
        Location locationNesnesi =
                given()
                        .when()
                        .get("http://api.zippopotam.us/tr/01000")

                        .then()
                        .extract().body().as(Location.class)

                ;

        for (Place p : locationNesnesi.getPlaces()){
            if(p.getPlacename().equalsIgnoreCase("Dörtağaç Köyü")){
                System.out.println(p);
            }
        }

    }






}
