import io.qameta.allure.junit4.DisplayName;
import order.OrderModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import order.OrderGenerator;
import steps.OrderSteps;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderParametrizedTest {

    private final String[] color;
    public CreateOrderParametrizedTest(String[] color){
        this.color  = color;
    }

    @Parameterized.Parameters(name = "Цвет: {index} - {0}")
    public static Object[][] getColorData() {
        return new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK", "GREY"}},
                {new String[]{}}
        };
    }

    @Test
    @DisplayName("Создание заказа:")
    public void createOrder() {
        OrderSteps orderSteps = new OrderSteps();
        OrderModel order = OrderGenerator.getRandomWithoutColor(color);

        orderSteps.createOrder(order)
                .assertThat()
                .statusCode(SC_CREATED)
                .body("track", notNullValue());
    }
}
