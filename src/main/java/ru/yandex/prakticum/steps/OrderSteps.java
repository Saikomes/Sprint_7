package ru.yandex.prakticum.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.prakticum.data.Order;

import static io.restassured.RestAssured.given;
import static ru.yandex.prakticum.resources.constant.Endpoints.*;

public class OrderSteps {

    @Step("Создание заказа, POST /api/v1/orders")
    public static Response createOrder(Order order) {
        return given()
                .body(order)
                .when()
                .post(ORDERS)
                .then()
                .extract()
                .response();
    }

    @Step("Получить список заказов, GET /api/v1/orders")
    public static Response getOrders() {
        return given()
                .when()
                .get(ORDERS)
                .then()
                .extract()
                .response();
    }

    @Step("Отмена заказа, PUT /api/v1/orders/cancel")
    public static void cancelOrder(String track) {
        given()
                .queryParam("track", track)
                .when()
                .put(CANCEL_ORDER);
    }


}
