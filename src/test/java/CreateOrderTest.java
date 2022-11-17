import clients.OrderClient;
import generators.OrderGeneratorData;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import models.Order;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.apache.http.HttpStatus.SC_CREATED;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private OrderClient orderClient;
    private Order order;
    private String testColor1;
    private String testColor2;

    public CreateOrderTest(String testColor1, String testColor2) {
        this.testColor1 = testColor1;
        this.testColor2 = testColor2;
    }

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        order = OrderGeneratorData.getDefault();
        order.setColor(List.of(testColor1, testColor2));
    }

    @Parameterized.Parameters
    public static Object[][]  getTestData() {
        return new Object[][] {
                {"BLACK", ""},
                {"GREY", ""},
                {"BLACK", "GREY"},
                {"", ""}
        };
    }

    @Test
    @DisplayName("Проверка создания заказа")
    public void checkCreateOrderTest() {
        ValidatableResponse responseCreateOrder = orderClient.createOrder(order);
        int actualStatusCode = responseCreateOrder.extract().statusCode();
        assertEquals("Incorrect status code", SC_CREATED, actualStatusCode);
        assertNotNull(responseCreateOrder.extract().path("track"));
    }
}
