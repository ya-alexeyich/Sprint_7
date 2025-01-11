package order;
import net.datafaker.Faker;
import org.jetbrains.annotations.NotNull;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class OrderGenerator {
    private static final Faker faker = new Faker();

    public static @NotNull OrderModel getRandomWithoutColor(String[] color) {
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String address = faker.address().streetAddress();
        String metroStation = faker.number().digit();
        String phone = faker.phoneNumber().phoneNumber();
        Integer rentTime = 7;
        String deliveryDate = new SimpleDateFormat("yyyy-MM-dd").format(faker.date().future(30, TimeUnit.DAYS));
        String comment = faker.lorem().sentence();
        return new OrderModel(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
    }
}
