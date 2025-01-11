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

public class NegativeCreateCourierTest {
    private CourierModel courier;
    private CourierSteps courierSteps;
    private int courierId;

    @Before
    public void setUp() {
        courier = CourierGenerator.getRandom();
        courierSteps = new CourierSteps();

    }

    @After
    public void deleteCourier() {
        CourierSteps.deleteCourier(courierId);
    }

    @Test
    @DisplayName("Тест курьер не может быть создан без логина")
    public void courierCanNotBeCreateWithoutLoginTest() {
        courier.setLogin(null);
        courierSteps.createCourier(courier)
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Тест курьер не может быть создан без пароля")
    public void courierCanNotBeCreateWithoutPasswordTest() {
        courier.setPassword(null);

        courierSteps.createCourier(courier)
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Тест курьер не может быть создан без логина и пароля")
    public void courierCanNotBeCreateWithoutLoginAndPasswordTest() {
        courier.setLogin(null);
        courier.setPassword(null);

        courierSteps.createCourier(courier)
                .assertThat()
                .statusCode(SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Тест нельзя создать двух одинаковых курьеров")
    @Issue("https://alexeyc.youtrack.cloud/issue/SPR-3/")
    public void twoIdenticalCouriersCanNotBeCreatedTest() {

        courierSteps.createCourier(courier)
                .assertThat()
                .statusCode(SC_CREATED)
                .body("ok", is(true));
        courierId = courierSteps.getIdCourier(CourierCreds.from(courier));
        courierSteps.createCourier(courier)
                .assertThat()
                .statusCode(SC_CONFLICT)
                .body("message", is("Этот логин уже используется"));
    }

    @Test
    @DisplayName("Тест если создать пользователя с логином, который уже есть, возвращается ошибка")
    @Issue("https://alexeyc.youtrack.cloud/issue/SPR-3/")//правки в пачке
    public void twoIdenticalCouriersCanNotBeCreatedTest2() {

        courierSteps.createCourier(courier)
                .assertThat()
                .statusCode(SC_CREATED)
                .body("ok", is(true));
        courierId = courierSteps.getIdCourier(CourierCreds.from(courier));
        courier.setPassword("incorrect");
        courierSteps.createCourier(courier)
                .assertThat()
                .statusCode(SC_CONFLICT)
                .body("message", is("Этот логин уже используется"));

    }
}
