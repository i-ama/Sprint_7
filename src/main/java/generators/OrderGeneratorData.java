package generators;

import models.Order;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;

public class OrderGeneratorData {
    static final String firstName = RandomStringUtils.randomAlphabetic(7);
    static final  String lastName = RandomStringUtils.randomAlphabetic(7);
    static final  String address = RandomStringUtils.randomAlphabetic(7);
    static final  String metroStation = "4";
    static final  String phone = "+7 800 355 35 35";
    static final  int rentTime = 5;
    static final  String deliveryDate = "2020-06-06";
    static final  String comment = RandomStringUtils.randomAlphabetic(10);;
    static final List<String> color = null;

    public static Order getDefault() {
        return new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
    }
}