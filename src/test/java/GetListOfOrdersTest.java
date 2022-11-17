import clients.OrderClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.apache.http.HttpStatus.SC_OK;

public class GetListOfOrdersTest {
    private OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Получение списка заказов")
    public void getListOfOrders() {
        ValidatableResponse responseOrderClient = orderClient.getListOfOrders();
        int actualStatusCode = responseOrderClient.extract().statusCode();
        assertEquals("Incorrect status code", SC_OK, actualStatusCode);
        assertNotNull(responseOrderClient.extract().path("orders"));
    }
}
