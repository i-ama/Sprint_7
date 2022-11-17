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

public class CourierCanNotBeCreateWithExistDataTests {


    private CourierClient courierClient;
    private Courier courier;
    private Courier courierSecond;
    private String expectedMessage = "Этот логин уже используется";



    @Before
    public  void setUp() {
        courierClient = new CourierClient();
        courier = CourierGeneratorData.getDefault();
        courierClient.createCourier(courier);
    }


    @Test
    @DisplayName("Проверка, что нельзя создать двух одинаковых курьеров")
    public void courierCanNotBeCreatedTwice() {
        ValidatableResponse responseLoginCourierFirst = courierClient.loginCourier(CourierCredentials.from(courier));
        ValidatableResponse responseCreateCourierSecond = courierClient.createCourier(courier);
        int actualStatusCode = responseCreateCourierSecond.extract().statusCode();
        String actualMessage = responseCreateCourierSecond.extract().path("message");

        if (SC_CONFLICT != actualStatusCode) {
            ValidatableResponse responseLoginCourierSecond = courierClient.loginCourier(CourierCredentials.from(courierSecond));
            int id2 = responseLoginCourierSecond.extract().path("id");
            courierClient.deleteCourier(id2);
        }


        int id =responseLoginCourierFirst.extract().path("id");
        courierClient.deleteCourier(id);

        assertEquals("Incorrect status code", SC_CONFLICT, actualStatusCode);
        assertEquals("Incorrect ok message", expectedMessage, actualMessage);

    }

    @Test
    @DisplayName("Проверка, что нельзя создать пользователя с логином, который уже используется")
    public void courierCanNotBeCreatedWithExistLogin() {
        ValidatableResponse responseLoginCourierFirst = courierClient.loginCourier(CourierCredentials.from(courier));
        courierSecond = CourierGeneratorData.getDefaultWithTheSameLogin();
        ValidatableResponse responseCreateCourierSecond = courierClient.createCourier(courierSecond);
        int actualStatusCode = responseCreateCourierSecond.extract().statusCode();
        String actualMessage = responseCreateCourierSecond.extract().path("message");

        if (SC_CONFLICT != actualStatusCode) {
            ValidatableResponse responseLoginCourierSecond = courierClient.loginCourier(CourierCredentials.from(courierSecond));
            int id2 = responseLoginCourierSecond.extract().path("id");
            courierClient.deleteCourier(id2);
        }


        int id =responseLoginCourierFirst.extract().path("id");
        courierClient.deleteCourier(id);

        assertEquals("Incorrect status code", SC_CONFLICT, actualStatusCode);
        assertEquals("Incorrect ok message", expectedMessage, actualMessage);
    }
}
