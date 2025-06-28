package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static api.OrderAPI.requestSpecification;
import static constants.Constants.CANCEL_ORDER;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.notNullValue;

public class OrderSteps {
    @Step("Проверка вызова списка заказов")
    public void checkBodyFromListOrder(Response response) {
        response.then().assertThat().body("orders", notNullValue());
    }


    @Step("Проверка статуса кода 200 у ранее созданного заказа")
    public void check200StatusOrder(Response response) {
        response.then().assertThat().statusCode(SC_OK); //200
    }

    @Step("Проверка статуса кода 200 у списка заказов")
    public void checkCodeFromListOrder(Response response) {
        response.then().assertThat().statusCode(SC_OK); //200
    }


    @Step("Проверка наличие трека у созданного заказа")
    public void checkTrackFromNewOrder(Response response) {
        response.then().assertThat().body("track", notNullValue());
    }


    @Step("Проверка статуса кода 201 успешно созданного заказа")
    public void orderCheckStatusCode201(Response response) {
        response.then().assertThat().statusCode(SC_CREATED);  //201
    }


    @Step("Отмена заказа")
    public void cancelOrder(Integer track) {
        requestSpecification().put(CANCEL_ORDER + track);
    }
}
