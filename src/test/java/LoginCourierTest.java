import courier.CourierCreds;
import courier.CourierGenerator;
import courier.CourierModel;
import io.qameta.allure.Issue;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.CourierSteps;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginCourierTest {
    private CourierModel courier;
    private CourierSteps courierSteps;
    private int courierId;

    @Before
    public void setUp() {
        courier = CourierGenerator.getRandom();
        courierSteps = new CourierSteps();
        courierSteps.createCourier(courier);
        courierId = courierSteps.getIdCourier(CourierCreds.from(courier));
    }

    @After
    public void deleteCourier() {
        CourierSteps.deleteCourier(courierId);
    }

    @Test
    @DisplayName("Тест курьер успешно авторизовался c валидным логином и паролем")
    public void courierCanBeLoginWithValidData() {
        courierSteps.loginCourier(CourierCreds.from(courier))
                .assertThat()
                .statusCode(SC_OK)
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Тест курьер Не авторизовался c валидным логином и невалидным паролем")
    public void courierCanNotBeLoginWithIncorrectPassword() {
        courier.setPassword("incorrect");
        courierSteps.loginCourier(CourierCreds.from(courier))
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .body("message", is("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Тест курьер Не авторизовался c невалидным логином и валидным паролем")
    public void courierCanNotBeLoginWithIncorrectLogin() {
        courier.setLogin("incorrect");
        courierSteps.loginCourier(CourierCreds.from(courier))
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .body("message", is("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Тест курьер Не авторизовался c невалидным логином и паролем")
    public void courierCanNotBeLoginWithIncorrectData() {
        courier.setLogin("incorrect");
        courier.setPassword("incorrect");
        courierSteps.loginCourier(CourierCreds.from(courier))
                .assertThat()
                .statusCode(SC_NOT_FOUND)
                .body("message", is("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Тест курьер Не авторизовался без логина")
    public void courierCanNotBeLoginWithoutLogin() {
        courier.setLogin(null);
        courierSteps.loginCourier(CourierCreds.from(courier))
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Тест курьер Не авторизовался без логина")
    public void courierCanNotBeLoginWithEmptyLogin() {
        courier.setLogin("");
        courierSteps.loginCourier(CourierCreds.from(courier))
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Тест курьер Не авторизовался без пароля")
    @Issue("https://alexeyc.youtrack.cloud/issue/SPR-2/")
    public void courierCanNotBeLoginWithoutPassword() {
        courier.setPassword(null);
        courierSteps.loginCourier(CourierCreds.from(courier))
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Тест курьер Не авторизовался без пароля, пароль пустое значение")
    public void courierCanNotBeLoginWithEmptyPassword() {
        courier.setPassword("");
        courierSteps.loginCourier(CourierCreds.from(courier))
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для входа"));
    }
}
