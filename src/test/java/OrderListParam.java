import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static constants.Constants.BASE_URL;
import static constants.Constants.CREATE_ORDER;
import static io.restassured.RestAssured.given;

public class OrderListParam {

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
                .then();
    }
}