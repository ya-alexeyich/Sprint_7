import io.qameta.allure.Issue;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import steps.OrderListSteps;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.notNullValue;

public class GetOrdersTest {
    private OrderListSteps orderListSteps;

    @Before
    public void setUp() {
        orderListSteps = new OrderListSteps();
    }

    @Test
    @DisplayName("Тест получить список заказов")
    @Issue("https://alexeyc.youtrack.cloud/issue/SPR-1/")
    public void getAllOrdersArrayWithOrdersNotEmpty() {
        orderListSteps.grtAllOrders()
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .assertThat()
                .body("orders", notNullValue());
    }
}
