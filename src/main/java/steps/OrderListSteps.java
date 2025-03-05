package steps;

import base.ScooterHttpClient;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static base.URL.ORDER_URL;
import static io.restassured.RestAssured.given;

public class OrderListSteps extends ScooterHttpClient {

    @Step("Получение списка заказов")
    public ValidatableResponse grtAllOrders() {
        return given()
                .spec(getBaseRequestSpec())
                .when()
                .get(ORDER_URL)
                .then();
    }
}