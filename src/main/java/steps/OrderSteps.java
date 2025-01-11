package steps;
import base.ScooterHttpClient;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import order.OrderModel;
import static base.URL.ORDER_URL;
import static io.restassured.RestAssured.given;

public class OrderSteps extends ScooterHttpClient {

    @Step("Создание заказа")
    public ValidatableResponse createOrder(OrderModel order) {
        return given()
                .spec(getBaseRequestSpec())
                .body(order)
                .when()
                .post(ORDER_URL)
                .then();
    }
}