package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.CoreMatchers;
import pojo.CreateOrder;

import static constants.Constants.*;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.startsWith;

public class CourierSteps {
    @Step("Проверка: успешный запрос возвращает id")
    public void checkId(Response response) {
        response.then().assertThat().body("id", notNullValue());
    }


    @Step("Проверка кода ответа 201")
    public void checkResponseCode201(Response response) {
        response.then().assertThat().statusCode(SC_CREATED);
    }


    @Step("Проверка тела ответа: ok: true")
    public void checkResponseCodeOk(Response response) {
        response.then().assertThat().body("ok", equalTo(true));
    }


    @Step("Проверка ошибки при попытке создания курьера без логина или пароля")
    public void checkErrorMessageWithEnptyField(Response response) {
        response.then().assertThat().body("message", startsWith("Недостаточно данных для создания учетной записи"));
    }


    //авторизация курьера
    @Step("Проверка кода ответа 200")
    public void checkResponseCode200(Response response) {
        response.then().assertThat().statusCode(SC_OK);
    }

    //авторизация курьера
    @Step("Проверка ошибки авторизации при несуществующем логине")
    public void checkErrorMessageWithInvalidLogin(Response response) {
        response.then().assertThat().body("message", startsWith("Учетная запись не найдена"));
    }

    //авторизация курьера
    @Step("Проверка кода ответа 404")
    public void checkResponseCodeWithInvalidLogin(Response response) {
        response.then().assertThat().statusCode(SC_NOT_FOUND);
    }

    //авторизация курьера
    @Step("Проверка ошибки авторизации курьера без обязательного поля логин")
    public void checkErrorMessageWithEmptyLogin(Response response) {
        response.then().assertThat().body("message", startsWith("Недостаточно данных для входа"));
    }

    //Недостаточно данных для создания учетной записи

    //авторизация курьера
    @Step("Проверка ошибки авторизации курьера без обязательного поля пароль")
    public void checkErrorMessageWithEmptyPassword(Response response) {
        response.then().assertThat().body("message", startsWith("Недостаточно данных для входа"));
    }

    //авторизация курьера
    @Step("Проверка ошибки авторизации при вводе неправильного пароля")
    public void checkErrorMessageWithInvalidPassword(Response response) {
        response.then().assertThat().body("message", startsWith("Учетная запись не найдена"));
    }

    //авторизация курьера статус код 404
    @Step("Проверка ошибки авторизации при вводе неправильного пароля")
    public void checkCode404WithInvalidAuthoriz(Response response) {
        response.then().assertThat().statusCode(SC_NOT_FOUND); //404
    }

    //авторизация курьера статус код 400
    @Step("Проверка ошибки авторизации без логина")
    public void checkCode400WithInvalidAuthoriz(Response response) {
        response.then().assertThat().statusCode(SC_BAD_REQUEST); //400
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
