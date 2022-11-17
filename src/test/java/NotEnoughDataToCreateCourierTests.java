import clients.CourierClient;
import generators.CourierGeneratorData;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import models.Courier;
import models.CourierCredentials;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static junit.framework.TestCase.assertEquals;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;

@RunWith(Parameterized.class)
public class NotEnoughDataToCreateCourierTests {
    private CourierClient courierClient;
    private int id;

    private Courier courier;
    private int expectedStatusCode;
    private String expectedMessage;

    public NotEnoughDataToCreateCourierTests(Courier courier, int expectedStatusCode, String expectedMessage) {
        this.courier = courier;
        this.expectedStatusCode = expectedStatusCode;
        this.expectedMessage = expectedMessage;
    }


    @Before
    public  void setUp() {
        courierClient = new CourierClient();
    }

    @Parameterized.Parameters
    public static Object[][]  getTestData() {
        return new Object[][] {
            {CourierGeneratorData.getWithoutPassword(), SC_BAD_REQUEST, "Недостаточно данных для создания учетной записи"},
            {CourierGeneratorData.getWithoutLogin(), SC_BAD_REQUEST, "Недостаточно данных для создания учетной записи"},
            {CourierGeneratorData.getWithoutFirstName(), SC_BAD_REQUEST, "Недостаточно данных для создания учетной записи"}
        };
    }



    @Test
    @DisplayName("Параметризированный тест по созданию курьера с неполными данными")
    public void courierCanNotBeCreatedWithoutEnoughData() {
        ValidatableResponse responseCreateCourier = courierClient.createCourier(courier);
        int actualStatusCode = responseCreateCourier.extract().statusCode();
        String actualMessage = responseCreateCourier.extract().path("message");

        if (expectedStatusCode != actualStatusCode) {
            ValidatableResponse responseLoginCourier = courierClient.loginCourier(CourierCredentials.from(courier));
            id = responseLoginCourier.extract().path("id");
            courierClient.deleteCourier(id);
        }

        assertEquals(expectedStatusCode, actualStatusCode);
        assertEquals(expectedMessage, actualMessage);


    }
}
