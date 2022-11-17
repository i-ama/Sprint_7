package clients;

import clients.Client;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import models.Courier;
import models.CourierCredentials;


import static io.restassured.RestAssured.given;

public class CourierClient extends Client {
    private  static  final String PATH_CREATE_COURIER = "/api/v1/courier";
    private  static  final String PATH_LOGIN_COURIER = "/api/v1/courier/login";
    private  static  final String PATH_DELETE_COURIER = "/api/v1/courier/";

    @Step("Создание курьера")
    public ValidatableResponse createCourier(Courier courier) {
        return given()
                .spec(getSpec())
                .log().all()
                .body(courier)
                .when()
                .post(PATH_CREATE_COURIER)
                .then()
                .log().all();
    }

    @Step("Логин курьера в системе")
    public ValidatableResponse loginCourier(CourierCredentials credentials) {
        return given()
                .spec(getSpec())
                .log().all()
                .body(credentials)
                .when()
                .post(PATH_LOGIN_COURIER)
                .then()
                .log().all();
    }

    @Step("Удаление курьера")
    public  ValidatableResponse deleteCourier(int id) {
        return given()
                .spec(getSpec())
                .log().all()
                .when()
                .delete(PATH_DELETE_COURIER + id)
                .then()
                .log().all();
    }
}
