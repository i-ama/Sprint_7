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
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;

public class CourierCanNotBeLoginWithIncorrectCredentialsTests {

    private CourierClient courierClient;
    private Courier courier;
    private int id;
    private String oldCredentialParam;
    private String newCredentialParam = "Test";
    private String expectedMessage;



    @Before
    public  void setUp() {
        courierClient = new CourierClient();
        courier = CourierGeneratorData.getDefault();
        courierClient.createCourier(courier);
    }

    @After
    public void cleanUp() {
        ValidatableResponse responseLoginCourier = courierClient.loginCourier(CourierCredentials.from(courier));
        id = responseLoginCourier.extract().path("id");
        courierClient.deleteCourier(id);
    }


    @Test
    @DisplayName("Логин курьера с неверным логином")
    public void courierLoginWithExistLogin() {
        expectedMessage = "Учетная запись не найдена";
        oldCredentialParam = courier.getLogin();
        courier.setLogin(newCredentialParam);
        ValidatableResponse responseLoginCourier = courierClient.loginCourier(CourierCredentials.from(courier));
        int actualStatusCode = responseLoginCourier.extract().statusCode();
        String actualMessage = responseLoginCourier.extract().path("message");
        assertEquals(SC_NOT_FOUND, actualStatusCode);
        assertEquals(expectedMessage, actualMessage);
        courier.setLogin(oldCredentialParam);
    }

    @Test
    @DisplayName("Логин курьера с неверным паролем")
    public void courierLoginWithExistPassword() {
        expectedMessage = "Учетная запись не найдена";
        oldCredentialParam = courier.getPassword();
        courier.setPassword(newCredentialParam);
        ValidatableResponse responseLoginCourier = courierClient.loginCourier(CourierCredentials.from(courier));
        int actualStatusCode = responseLoginCourier.extract().statusCode();
        String actualMessage = responseLoginCourier.extract().path("message");
        assertEquals(SC_NOT_FOUND, actualStatusCode);
        assertEquals(expectedMessage, actualMessage);
        courier.setPassword(oldCredentialParam);
    }

    @Test
    @DisplayName("Логин курьера с пустым логином")
    public void courierLoginWithEmptyLogin() {
        expectedMessage = "Недостаточно данных для входа";
        oldCredentialParam = courier.getLogin();
        courier.setLogin(null);
        ValidatableResponse responseLoginCourier = courierClient.loginCourier(CourierCredentials.from(courier));
        int actualStatusCode = responseLoginCourier.extract().statusCode();
        String actualMessage = responseLoginCourier.extract().path("message");
        assertEquals(SC_BAD_REQUEST, actualStatusCode);
        assertEquals(expectedMessage, actualMessage);
        courier.setLogin(oldCredentialParam);
    }

    @Test
    @DisplayName("Логин курьера с пустым паролем")
    public void courierLoginWithEmptyPassword() {
        expectedMessage = "Недостаточно данных для входа";
        oldCredentialParam = courier.getPassword();
        courier.setPassword(null);
        ValidatableResponse responseLoginCourier = courierClient.loginCourier(CourierCredentials.from(courier));
        int actualStatusCode = responseLoginCourier.extract().statusCode();
        String actualMessage = responseLoginCourier.extract().path("message");
        assertEquals(SC_BAD_REQUEST, actualStatusCode);
        assertEquals(expectedMessage, actualMessage);
        courier.setPassword(oldCredentialParam);
    }
}
