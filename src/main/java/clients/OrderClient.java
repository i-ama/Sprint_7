package clients;

import clients.Client;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import models.Order;

import static io.restassured.RestAssured.given;

public class OrderClient extends Client {
    private static final String PATH_ORDER = "/api/v1/orders";

    @Step("Создание заказа")
    public ValidatableResponse createOrder(Order order) {
        return  given()
                .spec(getSpec())
                .log().all()
                .body(order)
                .when()
                .post(PATH_ORDER)
                .then()
                .log().all();
    }

    @Step("Получение списка заказов")
    public ValidatableResponse getListOfOrders() {
        return given()
                .spec(getSpec())
                .log().all()
                .when()
                .get(PATH_ORDER)
                .then()
                .log().all();
    }
}
