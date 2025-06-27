import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static constants.Constants.BASE_URL;
import static constants.Constants.CREATE_ORDER;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderGetListTest {

    OrderListParam orderListParam = new OrderListParam();

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    @DisplayName("Test. Получение списка заказов")
    @Description("Получение списка заказов, проверка наличия списка")
    public void getOrderList() {
        orderListParam.orderGetList();
    }
}