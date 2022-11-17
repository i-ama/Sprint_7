package generators;

import models.Courier;
import org.apache.commons.lang3.RandomStringUtils;

public class CourierGeneratorData {

        static final String courierLogin = RandomStringUtils.randomAlphabetic(7);
        static final String courierPassword = RandomStringUtils.randomAlphabetic(7);
        static final String courierFirstName = RandomStringUtils.randomAlphabetic(7);

    public static Courier getDefault() {
        return  new Courier(courierLogin, courierPassword, courierFirstName);
    }

    public static Courier getWithoutPassword() {
        return  new Courier(courierLogin, null, courierFirstName);
    }

    public static Object getWithoutLogin() {
        return  new Courier(null, courierPassword, courierFirstName);
    }

    public static Object getWithoutFirstName() {
        return  new Courier(courierLogin, courierPassword, null);
    }
}
