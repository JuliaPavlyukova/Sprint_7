import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class OrderCreateTest {
    private Integer track; // Поле для хранения идентификатора заказа
    private OrderListParam orderSteps;
    private String[] color;

    public OrderCreateTest(String[] color) {
        this.color = color;
    }

    CreateOrder orderCreateRequest = new CreateOrder(color);

    @Before
    public void setUp() {
        orderSteps = new OrderListParam();
    }

    @After
    public void tearDown() {
        if (track != null) {
            orderSteps.cancelOrder(track); // Удаление созданного заказа
        }
    }

    @Parameterized.Parameters(name = "Набор цветов {index}")
    public static Object[][] dataGen() {
        return new Object[][]{
                {new String[]{"BLACK", "GREY"}},
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{}}
        };
    }

    @Test
    @DisplayName("Test. Создание заказа")
    @Description("Создание заказа с самокатами разных цветов через параметризованный тест")
    public void orderCreate() {
        System.out.println("create order");
        ValidatableResponse response = orderSteps.orderCreate(orderCreateRequest);
        track = response.extract().body().jsonPath().getInt("track");
    }

    @Test
    public void checkOrder() {
        System.out.println("check order");
        ValidatableResponse response = orderSteps.orderCreate(orderCreateRequest);
        track = response.extract().body().jsonPath().getInt("track");
        orderSteps.checkOrderCreate(track);
    }
}
