import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static constants.Constants.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;


public class CourierCreatingTest {
    CourierCreating courierFull = new CourierCreating("Timofeev", "0123", "Tim");
    CourierCreating courierWithoutLogin = new CourierCreating("", "0123");
    CourierCreating courierWithoutPassword = new CourierCreating("Timofeev", "");

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @Step("POST-Запрос на создание курьера")
    public Response createNewCourier(CourierCreating courierCreating) {
        return given()
                .contentType(ContentType.JSON)
                .body(courierCreating)
                .when()
                .post(CREATE_COURIER);
    }

    @Test
    @DisplayName("Test. Успешное создание курьера")
    @Description("При успешном создании курьеры тело ответа ok: true;")
    public void createCourier() {
        Response response = createNewCourier(courierFull);
        checkResponseCode201(response, 201);
        checkResponseCodeOk(response);
    }

    @Step("Проверка кода ответа 201")
    public void checkResponseCode201(Response response, int statusCode) {
        response.then().assertThat().statusCode(statusCode);
    }

    @Step("Проверка тела ответа: ok: true")
    public void checkResponseCodeOk(Response response) {
        response.then().assertThat().body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Test. Появление ошибки при попытке создания двух одинаковых курьеров")
    @Description("Если создать курьера с логином, который уже есть, возвращается ошибка 409 и текст: \"Этот логин уже используется. Попробуйте другой.\" ")
    public void duplicateCourier() {
        createNewCourier(courierFull);
        Response response = createNewCourier(courierFull);
        checkResponseCode409(response);
        checkMessage(response);
    }

    @Step("Проверка кода ответа 409")
    public void checkResponseCode409(Response response) {
        response.then().assertThat().statusCode(409);
    }

    @Step("Проверка сообщения об ошибке при дублировании курьера")
    public void checkMessage(Response response) {
        response.then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Test. Создание курьера без логина")
    @Description("Создание курьера с указанием только имени и пароля")
    public void createCourierWithoutLogin() {
        Response responseWithoutLogin = createNewCourier(courierWithoutLogin);
        responseWithoutLogin.then().assertThat().statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }


    @Test
    @DisplayName("Test. Проверка появления ошибки при создании курьера без пароля")
    @Description("Проверка ситуации, если одного из полей нет, запрос возвращает ошибку;")
    public void createCourierWithoutPassword() {
        Response responseWithoutPassword = createNewCourier(courierWithoutPassword);
        responseWithoutPassword.then().assertThat().statusCode(400)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @After
    public void deleteCourier() {
        CourierCreating id = given()
                .contentType(ContentType.JSON)
                .body(courierFull)
                .post("/api/v1/courier/login")//узнаем id
                .as(CourierCreating.class);
        given().delete(DELETE_COURIER + id.getId()); //запрос на удаление по id
    }
}

