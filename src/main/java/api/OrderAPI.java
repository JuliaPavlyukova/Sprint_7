package api;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.CreateOrder;

import static constants.Constants.*;
import static io.restassured.RestAssured.given;

public class OrderAPI {
    @Step("Before создания нового заказа")
    public static RequestSpecification requestSpecification() {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL);
    }


    @Step("Создание нового заказа")
    public Response orderCreate(CreateOrder orderCreateRequest) {
        return requestSpecification()
                .body(orderCreateRequest)
                .log().all()
                .post(CREATE_ORDER);
    }

    @Step("Получение списка ранее созданного заказа по track {track}")
    public Response checkOrderCreate(Integer track) {
        return  requestSpecification().get(GET_ORDER + track);

    }

    @Step("Получение списка заказа")
    public Response orderGetList() {
        return  requestSpecification().get(CREATE_ORDER);
    }

}
