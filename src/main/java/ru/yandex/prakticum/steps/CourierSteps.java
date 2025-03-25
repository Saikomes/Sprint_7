package ru.yandex.prakticum.steps;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import ru.yandex.prakticum.data.Courier;

import static io.restassured.RestAssured.given;
import static ru.yandex.prakticum.resources.constant.Endpoints.*;

public class CourierSteps {


    @Step("Создаем курьера, POST /api/v1/courier")
    public static Response createCourier(String login, String password, String firstName) {
        var courier = new Courier(login, password, firstName);
        return given()
                .body(courier)
                .when()
                .post(COURIER)
                .then()
                .extract().response();
    }


    @Step("Удаляем курьера, DELETE /api/v1/courier/:id")
    public static void deleteCourier(String login, String password) {
        // Сначала получаем ответ от логина курьера
        Response loginResponse = loginCourier(login, password);

        // Извлекаем ID из ответа
        String courierId = loginResponse.jsonPath().getString("id");

        if(courierId != null) {
            given()
                    .when()
                    .delete(String.format(COURIER_ID, courierId))
                    .then()
                    .extract().response();
        }
    }

    @Step("Логин курьера. POST /api/v1/courier/login")
    public static Response loginCourier(String login, String password) {
        var courier = new Courier(login, password);
        return given()
                .body(courier)
                .when()
                .post(LOGIN)
                .then()
                .extract().response();
    }


}
