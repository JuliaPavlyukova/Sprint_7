import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static constants.Constants.*;

import net.datafaker.Faker;

public class CourierLoginTest {

    Faker faker = new Faker();
    CourierCreating courierFull = new CourierCreating(faker.name().lastName(), faker.internet().password(), faker.name().firstName());
    CourierCreating courierWithInvalidLogin = new CourierCreating(faker.name().lastName(), faker.internet().password());
    CourierCreating courierWithInvalidPassword = new CourierCreating(faker.name().lastName(), faker.internet().password());
    CourierCreating courierWithoutLogin = new CourierCreating("", faker.internet().password());
    CourierCreating courierWithoutPassword = new CourierCreating(faker.name().lastName(), "");
    CourierAPI courierAPI = new CourierAPI();


    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
        courierAPI.createNewCourier(courierFull);
    }

    @Test
    @DisplayName("Test. Проверка успешной авторизации и наличия id в ответе")
    @Description("Курьер может авторизоваться передав обязательные для авторизации поля")
    public void loginNewCourier() {
        Response response = courierAPI.loginCourier(courierFull);
        courierAPI.checkId(response);
    }

    @Test
    @DisplayName("Test. Проверка появления ошибки при попытке авторизации c несуществующим логином")
    @Description("Cистема вернёт ошибку, если неправильно указать логин или пароль")
    public void loginWithInvalidLogin() {
        Response response = courierAPI.loginCourier(courierWithInvalidLogin);
        courierAPI.checkErrorMessageWithInvalidLogin(response);
        courierAPI.checkResponseCodeWithInvalidLogin(response);

    }

    @Test
    @DisplayName("Test. Проверка появления ошибки при попытке авторизации c несуществующим паролем")
    @Description("Система вернёт ошибку, если неправильно указать логин или пароль")
    public void loginWithInvalidPassword() {
        Response response = courierAPI.loginCourier(courierWithInvalidPassword);
        courierAPI.checkErrorMessageWithInvalidPassword(response);
    }

    @Test
    @DisplayName("Test. Проверка появления ошибки при попытке авторизации без поля логин")
    @Description("Если какого-то поля нет, запрос возвращает ошибку. При запросе на ручку COURIER_LOGIN должны быть 2 обязательных поля: логин и пароль")
    public void courierWithoutLogin() {
        Response responseWithoutLog = courierAPI.loginCourier(courierWithoutLogin);
        courierAPI.checkErrorMessageWithEmptyLogin(responseWithoutLog);
    }

    @Test
    @DisplayName("Test. Проверка появления ошибки при попытке авторизации без поля пароль")
    @Description("Если какого-то поля нет, запрос возвращает ошибку. В запросе должны быть 2 обязательных поля: логин и пароль")
    public void courierWithoutPassword() {
        Response responseWithoutPassword = courierAPI.loginCourier(courierWithoutPassword);
        courierAPI.checkErrorMessageWithEmptyPassword(responseWithoutPassword);
    }
}
