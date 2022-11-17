import clients.CourierClient;
import generators.CourierGeneratorData;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import models.Courier;
import models.CourierCredentials;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.apache.http.HttpStatus.SC_CREATED;

public class CourierCanBeCreatedAndLoginTest {

    private CourierClient courierClient;
    private Courier courier;
    private int id;



    @Before
    public  void setUp() {
        courierClient = new CourierClient();
        courier = CourierGeneratorData.getDefault();
    }

    @After
    public void cleanUp() {
        courierClient.deleteCourier(id);
    }

    @Test
    @DisplayName("Успешные создание и логин курьера")
    public void courierCanBeCreatedAndLogin() {
        ValidatableResponse responseCreateCourier = courierClient.createCourier(courier);
        ValidatableResponse responseLoginCourier = courierClient.loginCourier(CourierCredentials.from(courier));
        id = responseLoginCourier.extract().path("id");
        int actualStatusCode = responseCreateCourier.extract().statusCode();
        boolean isCourierCreated = responseCreateCourier.extract().path("ok");
        assertEquals("Incorrect status code", SC_CREATED, actualStatusCode);
        assertEquals("Incorrect ok message",true, isCourierCreated);
    }
}
