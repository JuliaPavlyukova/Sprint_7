import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static constants.Constants.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderListParam {


    @Step("Before создания нового заказа")
    public static RequestSpecification requestSpecification() {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL);
    }

    @Step("Создание нового заказа")
    public ValidatableResponse orderCreate(CreateOrder orderCreateRequest) {
        return requestSpecification()
                .body(orderCreateRequest)
                .post(CREATE_ORDER)
                .then()
                .body("track", notNullValue());

    }

    @Step("Получение списка заказа")
    public void orderGetList() {
        requestSpecification()
                .get(CREATE_ORDER)
                .then()
                .assertThat().body("orders", notNullValue())
                .and()
                .statusCode(200);
    }

    @Step("Проверка созданного заказа")
    public void checkOrderCreate(Integer track) {
        requestSpecification()
                .get(GET_ORDER + track)
                .then()
                .assertThat().body("order.track", equalTo(track))
                .and()
                .statusCode(200);
    }

    @Step("Отмена заказа")
    public void cancelOrder(Integer track) {
        requestSpecification().delete(DELETE_ORDER + track);
    }
}