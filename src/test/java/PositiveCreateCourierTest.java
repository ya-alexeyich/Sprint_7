import courier.CourierCreds;
import courier.CourierModel;
import courier.CourierGenerator;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import steps.CourierSteps;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertTrue;

public class PositiveCreateCourierTest {
    private CourierModel courier;
    private CourierSteps courierSteps;

    @Before
    public void setUp() {
        courier = CourierGenerator.getRandom();
        courierSteps = new CourierSteps();
    }

    @After
    public void deleteCourier() {
        int courierId = courierSteps.getIdCourier(CourierCreds.from(courier));
        CourierSteps.deleteCourier(courierId);
    }

    @Test
    @DisplayName("Тест курьера можно создать")
    public void courierCanBeCreateWithValidDataTest() {
        courierSteps.createCourier(courier)
                .assertThat()
                .statusCode(SC_CREATED)
                .body("ok", is(true));
    }

    @Test
    @DisplayName("Тест курьера можно создать без имени")
    public void courierCanBeCreateWithoutFirstNameTest() {
        courier.setFirstName(null);

        courierSteps.createCourier(courier)
                .assertThat()
                .statusCode(SC_CREATED)
                .body("ok", is(true));
    }
}
