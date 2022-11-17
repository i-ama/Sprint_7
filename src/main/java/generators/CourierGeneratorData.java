package generators;

import models.Courier;
import org.apache.commons.lang3.RandomStringUtils;

public class CourierGeneratorData {

        static private String courierLogin = RandomStringUtils.randomAlphabetic(7);
        static private String courierPassword = RandomStringUtils.randomAlphabetic(7);
        static private String courierFirstName = RandomStringUtils.randomAlphabetic(7);
        static private String secondCourierPassword = RandomStringUtils.randomAlphabetic(7);
        static private String secondCourierFirstName = RandomStringUtils.randomAlphabetic(7);

    public static Courier getDefault() {
        return  new Courier(courierLogin, courierPassword, courierFirstName);
    }

    public static Courier getDefaultWithTheSameLogin() {
        return new Courier(courierLogin, secondCourierPassword, secondCourierFirstName);
    }

    public static Courier getWithoutPassword() {
        return  new Courier(courierLogin, null, courierFirstName);
    }

    public static Courier getWithoutLogin() {
        return  new Courier(null, courierPassword, courierFirstName);
    }

    public static Courier getWithoutFirstName() {
        return  new Courier(courierLogin, courierPassword, null);
    }
}
