import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static constants.Constants.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.startsWith;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;

public class CourierAPI {

    @Step("POST-Запрос на создание курьера")
    public Response createNewCourier(CourierCreating courierCreating) {
        return given()
                .contentType(ContentType.JSON)
                .body(courierCreating)
                .when()
                .post(CREATE_COURIER);
    }

    @Step("Проверка кода ответа 201")
    public void checkResponseCode201(Response response, int statusCode) {
        response.then().assertThat().statusCode(statusCode);
    }

    @Step("Проверка тела ответа: ok: true")
    public void checkResponseCodeOk(Response response) {
        response.then().assertThat().body("ok", equalTo(true));
    }


    @Step("Проверка кода ответа 409")
    public void checkResponseCode409(Response response) {
        response.then().assertThat().statusCode(409);
    }

    @Step("Проверка сообщения об ошибке при дублировании курьера")
    public void checkErrorMessage(Response response) {
        response.then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Step("DELETE запрос на удаление курьера по id")
    public void deleteCourierById(CourierCreating courierCreating) {
        CourierCreating id = given()
                .contentType(ContentType.JSON)
                .body(courierCreating)
                .post(COURIER_LOGIN)//узнаем id
                .as(CourierCreating.class);
        given().delete(DELETE_COURIER + id.getId());
    }

    //
    @Step("POST-Запрос на вход курьера в систему COURIER_LOGIN")
    public Response loginCourier(CourierCreating courierCreating) {
        return given()
                .contentType(ContentType.JSON)
                .body(courierCreating)
                .when()
                .post(COURIER_LOGIN);
    }

    @Step("Проверка: успешный запрос возвращает id")
    public void checkId(Response response) {
        response.then().assertThat().body("id", notNullValue());
    }

    @Step("Проверка ошибки авторизации при несуществующем логине")
    public void checkErrorMessageWithInvalidLogin(Response response) {
        response.then().assertThat().body("message", startsWith("Учетная запись не найдена"));
    }

    @Step("Проверка кода ответа 404")
    public void checkResponseCodeWithInvalidLogin(Response response) {
        response.then().assertThat().statusCode(SC_NOT_FOUND);
    }


    @Step("Проверка ошибки авторизации курьера без обязательного поля логин")
    public void checkErrorMessageWithEmptyLogin(Response response) {
        response.then().assertThat().body("message", startsWith("Недостаточно данных для входа"));
    }

    @Step("Проверка ошибки авторизации курьера без обязательного поля пароль")
    public void checkErrorMessageWithEmptyPassword(Response response) {
        response.then().assertThat().body("message", startsWith("Недостаточно данных для входа"));
    }


    @Step("Проверка ошибки авторизации при вводе неправильного пароля")
    public void checkErrorMessageWithInvalidPassword(Response response) {
        response.then().assertThat().body("message", startsWith("Учетная запись не найдена"));
    }
}
