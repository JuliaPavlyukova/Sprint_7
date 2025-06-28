package steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.hamcrest.CoreMatchers;

import static api.OrderAPI.requestSpecification;
import static constants.Constants.CANCEL_ORDER;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;

public class OrderSteps {


    @Step("Проверка вызова списка заказов")
    public void checkBodyFromListOrder(Response response) {
        response.then().body(CoreMatchers.notNullValue()).log().all();
    }
    @Step("Проверка статуса кода 200 у списка заказов")
    public void checkCodeFromListOrder(Response response) {
        response.then().statusCode(	SC_OK).log().all();
    }





    @Step("Проверка наличие трека у созданного заказа")
    public void checkTrackFromNewOrder(Response response) {
        response.then().body("track", CoreMatchers.notNullValue()).log().all();
    }

//
//    @Step("Проверка сообщения об успешном созданого заказа")
//    public void orderCheckMessage(Response response) {
//        response.then().statusCode(SC_CREATED).log().all();  //201
//        response.then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
//    }


    @Step("Проверка статуса кода 201 успешно созданного заказа")
    public void orderCheckStatusCode201(Response response) {
        response.then().statusCode(SC_CREATED).log().all();  //201
    }




    @Step("Проверка статуса кода у ранее созданного заказа")
    public void orderCheck200StatusOldOrder(Response response) {
        response.then().statusCode(SC_OK).log().all();
    }


    @Step("Отмена заказа")
    public void cancelOrder(Integer track) {
        System.out.println("FROM DELETE_ORDER =>>>>" + track);
        requestSpecification().put(CANCEL_ORDER + track);
        // .then().statusCode(SC_OK).log().all();
    }
}
