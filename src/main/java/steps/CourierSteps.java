package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.*;

public class CourierSteps {
    @Step("Проверка кода ответа 200")
    public void checkResponseCode200(Response response) {
        response.then().assertThat().statusCode(SC_OK);
    }

    @Step("Проверка: успешный запрос возвращает id")
    public void checkId(Response response) {
        response.then().assertThat().body("id", notNullValue());
    }


    @Step("Проверка тела ответа: ok: true")
    public void checkResponseCodeOk(Response response) {
        response.then().body("ok", is(true));
    }


    @Step("Проверка ошибки авторизации курьера без обязательного поля логин или пароль")
    public void checkErrorMessageWithEmptyField(Response response) {
        response.then().assertThat().body("message", startsWith("Недостаточно данных для входа"));
    }


    //авторизация курьера статус код 400
    @Step("Проверка ошибки авторизации без логина")
    public void checkCode400WithEmptyAuthoriz(Response response) {
        response.then().statusCode(SC_BAD_REQUEST); //400
    }

    @Step("Проверка ошибки создания курьера без обязательного поля логин или пароль")
    public void checkErrorMessageCreateWithEmptyField(Response response) {
        response.then().assertThat().body("message", startsWith("Недостаточно данных для создания учетной записи"));
    }


    @Step("Проверка кода ответа 201")
    public void checkResponseCode201(Response response) {
        response.then().assertThat().statusCode(SC_CREATED); //201
    }


    @Step("Проверка ошибки авторизации при вводе неправильного пароля")
    public void checkErrorMessageWithInvalidField(Response response) {
        response.then().assertThat().body("message", startsWith("Учетная запись не найдена"));
    }


    @Step("Проверка ошибки авторизации при вводе неправильного пароля")
    public void checkCode404WithInvalidAuthoriz(Response response) {
        response.then().statusCode(SC_NOT_FOUND); //404
    }


    @Step("Проверка кода ответа 409")
    public void checkResponseCode409(Response response) {
        response.then().assertThat().statusCode(409);
    }


    @Step("Проверка сообщения об ошибке при дублировании курьера")
    public void checkErrorMessage(Response response) {
        response.then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }
}
