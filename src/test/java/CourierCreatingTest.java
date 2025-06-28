import api.CourierAPI;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static constants.Constants.*;

import net.datafaker.Faker;
import pojo.CourierCreating;
import steps.CourierSteps;


public class CourierCreatingTest extends CourierAPI {

    Faker faker = new Faker();
    CourierCreating courierFull = new CourierCreating(faker.name().lastName(), faker.internet().password(), faker.name().firstName());
    CourierCreating courierWithoutLogin = new CourierCreating("", faker.internet().password());
    CourierCreating courierWithoutPassword = new CourierCreating(faker.name().lastName(), "");
    CourierSteps courierSteps = new CourierSteps();


    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }


    @Test
    @DisplayName("Test. Успешное создание курьера")
    @Description("При успешном создании курьеры тело ответа ok: true;")
    public void createCourier() {
        Response response = createNewCourier(courierFull);
        courierSteps.checkResponseCodeOk(response);
        courierSteps.checkResponseCode201(response);
    }


    @Test
    @DisplayName("Test. Появление ошибки при попытке создания двух одинаковых курьеров")
    @Description("Если создать курьера с логином, который уже есть, возвращается ошибка 409 и текст: \"Этот логин уже используется. Попробуйте другой.\" ")
    public void duplicateCourier() {
        createNewCourier(courierFull);
        Response response = createNewCourier(courierFull);
        courierSteps.checkErrorMessage(response);
        courierSteps.checkResponseCode409(response);
    }


    @Test
    @DisplayName("Test. Появление ошибки при создании курьера без логина")
    @Description("Создание курьера с указанием только пароля")
    public void createCourierWithoutLogin() {
        Response responseWithoutLogin = createNewCourier(courierWithoutLogin);
        courierSteps.checkErrorMessageCreateWithEmptyField(responseWithoutLogin);
        courierSteps.checkCode400WithEmptyAuthoriz(responseWithoutLogin);
    }


    @Test
    @DisplayName("Test. Проверка появления ошибки при создании курьера без пароля")
    @Description("Проверка ситуации, если одного из полей нет, запрос возвращает ошибку;")
    public void createCourierWithoutPassword() {
        Response responseWithoutPassword = createNewCourier(courierWithoutPassword);
        courierSteps.checkErrorMessageCreateWithEmptyField(responseWithoutPassword);
        courierSteps.checkCode400WithEmptyAuthoriz(responseWithoutPassword);
    }


    @After
    public void deleteCourier() {
        deleteCourierById(courierFull);
    }
}

