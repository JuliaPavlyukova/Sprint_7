import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import static constants.Constants.BASE_URL;
import static constants.Constants.CREATE_ORDER;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderGetListTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    @DisplayName("Test. Получение списка заказов")
    @Description("Получение списка заказов, проверка наличия списка")

    public void orderGetList() {
        given()
                .get(CREATE_ORDER)
                .then()
                .assertThat().body("orders", notNullValue())
                .and()
                .statusCode(200);
    }
}