import api.OrderAPI;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import pojo.CreateOrder;
import steps.OrderSteps;


@RunWith(Parameterized.class)
public class OrderCreateTest {
    private Integer track; // Поле для хранения идентификатора заказа
    private OrderSteps orderSteps;
    private String[] color;

    public OrderCreateTest(String[] color) {
        this.color = color;
    }

    CreateOrder orderCreateRequest = new CreateOrder(color);
    OrderAPI orderAPI = new OrderAPI();

    @Before
    public void setUp() {
        orderSteps = new OrderSteps();
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
    @DisplayName("Test. Проверка  созданного заказа")
    @Description("Создание заказа с самокатами разных цветов через параметризованный тест")
    public void orderNewCreate() {
        Response response = orderAPI.orderCreate(orderCreateRequest);
        track = response.body().jsonPath().getInt("track");
        orderSteps.checkTrackFromNewOrder(response);
        orderSteps.orderCheckStatusCode201(response);
    }


    @Test
    @DisplayName("Test. Проверка статуса кода у ранее созданного заказа")
    @Description("Создание заказа с самокатами разных цветов через параметризованный тест")
    public void checkStatusCodeFromOldOrder() {
        Response response = orderAPI.orderCreate(orderCreateRequest);
        track = response.body().jsonPath().getInt("track");
        System.out.println(track + " track");
        Response response2 = orderAPI.checkOrderCreate(track);
        orderSteps.check200StatusOrder(response2);
    }
}
