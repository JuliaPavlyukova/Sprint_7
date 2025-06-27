import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static constants.Constants.*;

import org.apache.http.HttpStatus;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.hamcrest.CoreMatchers.equalTo;


public class CourierCreatingTest extends CourierAPI {
    CourierCreating courierFull = new CourierCreating("Timofeev", "0123", "Tim");
    CourierCreating courierWithoutLogin = new CourierCreating("", "0123");
    CourierCreating courierWithoutPassword = new CourierCreating("Timofeev", "");
    CourierAPI courierAPI = new CourierAPI();


    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }


    @Test
    @DisplayName("Test. Успешное создание курьера")
    @Description("При успешном создании курьеры тело ответа ok: true;")
    public void createCourier() {
        Response response = createNewCourier(courierFull);
        checkResponseCode201(response, HttpStatus.SC_CREATED);
        checkResponseCodeOk(response);
    }


    @Test
    @DisplayName("Test. Появление ошибки при попытке создания двух одинаковых курьеров")
    @Description("Если создать курьера с логином, который уже есть, возвращается ошибка 409 и текст: \"Этот логин уже используется. Попробуйте другой.\" ")
    public void duplicateCourier() {
        createNewCourier(courierFull);
        Response response = createNewCourier(courierFull);
        checkResponseCode409(response);
        checkErrorMessage(response);
    }


    @Test
    @DisplayName("Test. Создание курьера без логина")
    @Description("Создание курьера с указанием только имени и пароля")
    public void createCourierWithoutLogin() {
        Response responseWithoutLogin = createNewCourier(courierWithoutLogin);
        responseWithoutLogin.then().assertThat().statusCode(SC_BAD_REQUEST)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }


    @Test
    @DisplayName("Test. Проверка появления ошибки при создании курьера без пароля")
    @Description("Проверка ситуации, если одного из полей нет, запрос возвращает ошибку;")
    public void createCourierWithoutPassword() {
        Response responseWithoutPassword = createNewCourier(courierWithoutPassword);
        responseWithoutPassword.then().assertThat().statusCode(SC_BAD_REQUEST)
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @After
    public void deleteCourier() {
        deleteCourierById(courierFull);
    }
}

