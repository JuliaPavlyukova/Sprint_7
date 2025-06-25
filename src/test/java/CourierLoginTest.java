import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static constants.Constants.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CourierLoginTest {
    CourierCreating courierFull = new CourierCreating("Sokolov", "9876", "Sok");
    CourierCreating courierWithInvalidLogin = new CourierCreating("Orlov", "9876");
    CourierCreating courierWithInvalidPassword = new CourierCreating("Sokolov", "8888");
    CourierCreating courierWithoutLogin = new CourierCreating("", "8888");
    CourierCreating courierWithoutPassword = new CourierCreating("Sokolov", "");

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @Step("Создание курьера")
    public void createNewCourier(CourierCreating courierCreating) {
        given()
                .contentType(ContentType.JSON)
                .body(courierCreating)
                .when()
                .post(CREATE_COURIER);
    }

    @Step("POST-Запрос на вход курьера в систему COURIER_LOGIN")
    public Response loginCourier(CourierCreating courierCreating) {
        return given()
                .contentType(ContentType.JSON)
                .body(courierCreating)
                .when()
                .post(COURIER_LOGIN);
    }

    @Test
    @DisplayName("Test. Проверка успешной авторизации и наличия id в ответе")
    @Description("Курьер может авторизоваться передав обязательные для авторизации поля")
    public void loginNewCourier() {
        createNewCourier(courierFull);
        Response response = loginCourier(courierFull);
        checkId(response);
    }

    @Step("Проверка: успешный запрос возвращает id")
    public void checkId(Response response) {
        response.then().assertThat().body("id", notNullValue());
    }

    @Test
    @DisplayName("Test. Проверка появления ошибки при попытке авторизации c несуществующим логином")
    @Description("Cистема вернёт ошибку, если неправильно указать логин или пароль")
    public void loginWithInvalidLogin() {
        Response response = loginCourier(courierWithInvalidLogin);
        checkErrorWithInvalidLogin(response);
    }

    @Step("Проверка ошибки авторизации при несуществующем логине")
    public void checkErrorWithInvalidLogin(Response response) {
        response.then().assertThat().body("message", startsWith("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Test. Проверка появления ошибки при попытке авторизации c несуществующим паролем")
    @Description("Cистема вернёт ошибку, если неправильно указать логин или пароль")
    public void loginWithInvalidPassword() {
        Response response = loginCourier(courierWithInvalidPassword);
        checkErrorWithInvalidPassword(response);
    }

    @Step("Проверка ошибки авторизации при вводе неправильного пароля")
    public void checkErrorWithInvalidPassword(Response response) {
        response.then().assertThat().body("message", startsWith("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Test. Проверка появления ошибки при попытке авторизации без поля логин")
    @Description("Если какого-то поля нет, запрос возвращает ошибку. При запросе на ручку COURIER_LOGIN должны быть 2 обязательных поля: логин и пароль")
    public void courierWithoutLogin() {
        Response responseWithoutLog = loginCourier(courierWithoutLogin);
        checkErrorWithEmptyLogin(responseWithoutLog);
    }

    @Step("Проверка ошибки авторизации курьера без обязательного поля логин")
    public void checkErrorWithEmptyLogin(Response response) {
        response.then().assertThat().body("message", startsWith("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Test. Проверка появления ошибки при попытке авторизации без поля пароль")
    @Description("Если какого-то поля нет, запрос возвращает ошибку. В запросе должны быть 2 обязательных поля: логин и пароль")
    public void courierWithoutPassword() {
        Response responseWithoutPassword = loginCourier(courierWithoutPassword);
        checkErrorWithEmptyPassword(responseWithoutPassword);
    }

    @Step("Проверка ошибки авторизации курьера без обязательного поля пароль")
    public void checkErrorWithEmptyPassword(Response response) {
        response.then().assertThat().body("message", startsWith("Недостаточно данных для входа"));
    }
}
