package steps;
import base.ScooterHttpClient;
import courier.CourierCreds;
import courier.CourierModel;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static base.URL.COURIER_URL;
import static io.restassured.RestAssured.given;

public class CourierSteps extends ScooterHttpClient {

    @Step("Создание курьера")
    public ValidatableResponse createCourier(CourierModel courierModel){
        return given()
                .spec(getBaseRequestSpec())
                .body(courierModel)
                .when()
                .post(COURIER_URL)
                .then();
    }

    @Step("Залогинить курьера")
    public ValidatableResponse loginCourier(CourierCreds courierCreds) {
        return given()
                .spec(getBaseRequestSpec())
                .body(courierCreds)
                .when()
                .post(COURIER_URL + "/login")
                .then();
    }

    public Integer getIdCourier(CourierCreds courierCreds) {
        return loginCourier(courierCreds).extract().path("id");
    }

    @Step("Удалить курьера")
    public static ValidatableResponse deleteCourier(Integer courierId) {
        return given()
                .spec(getBaseRequestSpec())
                .when()
                .delete(COURIER_URL + "/" + courierId)
                .then();
    }
}
