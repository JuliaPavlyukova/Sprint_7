import api.OrderAPI;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import steps.OrderSteps;

import static constants.Constants.BASE_URL;


public class OrderGetListTest {

    OrderSteps orderSteps = new OrderSteps();
    OrderAPI orderAPI = new OrderAPI();

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    @DisplayName("Test. Получение списка заказов")
    @Description("Получение списка заказов, проверка наличия списка")
    public void getOrderList() {
        Response response = orderAPI.orderGetList();
        orderSteps.checkBodyFromListOrder(response);
        orderSteps.check200StatusOrder(response);
    }
}