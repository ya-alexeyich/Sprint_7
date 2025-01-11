package courier;
import net.datafaker.Faker;
import org.jetbrains.annotations.NotNull;

public class CourierGenerator {
    private static final Faker faker = new Faker();

    public static @NotNull CourierModel getRandom() {
        String login = faker.name().username();
        String password = faker.internet().password();
        String firstName = faker.name().firstName();
        return new CourierModel(login, password, firstName);
    }
}