import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import static constants.Constants.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import io.qameta.allure.junit4.DisplayName; // импорт DisplayName
import io.qameta.allure.Description; // импорт Description
import io.qameta.allure.Step; // импорт Step
import io.qameta.allure.Issue; // импорт Issue
import io.qameta.allure.TmsLink; // импорт TmsLink



public class CourierCreatingTest{
    CourierCreating courierCreating = new CourierCreating("Stepanov", "8520", "Step");

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }
    //@DisplayName("Создание нового курьера") //имя теста
    //@Description("Проверка успешного создания нового курьера") // описание теста


    @Step("POST-Запрос на создание курьера {CREATE_COURIER}")
    public Response createNewCourier() {
        Response response = given()
                .header("Content-Type", CREATE_COURIER)
                .body(courierCreating)
                .when()
                .post("api/v1/courier");
        return response;
    }
@Step("Проверка кода ответа 201")
    public void checkResponseCode201(Response response) {
         response.then().assertThat().statusCode(201);
}

@Step("Проверка тела ответа: ok: true")
    public void checkResponseCodeOk(Response response) {
        response.then().assertThat().body("ok", equalTo(true));
}



//  //     response.then().assertThat().statusCode(equalTo(201));
//        response.then().assertThat().body("ok", equalTo(true));
//
//        System.out.println("response " + response.body().asString());


}
