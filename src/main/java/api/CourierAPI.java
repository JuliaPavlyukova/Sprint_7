package api;

import pojo.CourierCreating;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static constants.Constants.*;
import static io.restassured.RestAssured.given;


public class CourierAPI {


    @Step("POST-Запрос на создание курьера")
    public Response createNewCourier(CourierCreating courierCreating) {
        return given()
                .contentType(ContentType.JSON)
                .body(courierCreating)
                .when()
                .post(CREATE_COURIER);
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


    @Step("POST-Запрос на вход курьера в систему COURIER_LOGIN")
    public Response loginCourier(CourierCreating courierCreating) {
        return given()
                .contentType(ContentType.JSON)
                .body(courierCreating)
                .when()
                .post(COURIER_LOGIN);
    }

}
