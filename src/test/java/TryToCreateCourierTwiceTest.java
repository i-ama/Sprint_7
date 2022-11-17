import clients.CourierClient;
import generators.CourierGeneratorData;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import models.Courier;
import models.CourierCredentials;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.apache.http.HttpStatus.SC_CONFLICT;

public class TryToCreateCourierTwiceTest {


    private CourierClient courierClient;
    private Courier courier;
    private int id;
    private String expectedMessage = "Этот логин уже используется";



    @Before
    public  void setUp() {
        courierClient = new CourierClient();
        courier = CourierGeneratorData.getDefault();
    }


    @Test
    @DisplayName("Проверка, что нельзя создать двух одинаковых курьеров")
    public void courierCanNotBeCreatedTwice() {
        courierClient.createCourier(courier);
        ValidatableResponse responseCreateCourierSecond = courierClient.createCourier(courier);
        int actualStatusCode = responseCreateCourierSecond.extract().statusCode();
        String actualMessage = responseCreateCourierSecond.extract().path("message");

        if (SC_CONFLICT != actualStatusCode) {
            ValidatableResponse responseLoginCourier = courierClient.loginCourier(CourierCredentials.from(courier));
            id = responseLoginCourier.extract().path("id");
            courierClient.deleteCourier(id);
        }

        assertEquals("Incorrect status code", SC_CONFLICT, actualStatusCode);
        assertEquals("Incorrect ok message", expectedMessage, actualMessage);

    }
}
