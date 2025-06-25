import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.instanceOf;

@RunWith(Parameterized.class)
public class OrderCreateTest {
    private String[] color;

    public OrderCreateTest(String[] color) {
        this.color = color;
    }

    @Parameterized.Parameters(name = "Цвет самоката - {0}")
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
        CreateOrder orderCreateRequest = new CreateOrder(color);
        OrderListParam orderSteps = new OrderListParam();

        orderSteps.orderCreate(orderCreateRequest)
                .assertThat().body("track", instanceOf(Integer.class))
                .and()
                .statusCode(201);
    }
}
