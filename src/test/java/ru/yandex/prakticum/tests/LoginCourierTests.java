package ru.yandex.prakticum.tests;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static ru.yandex.prakticum.resources.constant.CourierTestData.*;
import static ru.yandex.prakticum.steps.CourierSteps.*;

import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class LoginCourierTests extends BaseTest {

    String testLogin;
    String testPassword;

    @Before
    @Description("Создаем курьера перед логином")
    public void courierCreation() {
        testLogin = "login_" + UUID.randomUUID().toString().substring(0, 5);
        testPassword = "password_" + UUID.randomUUID().toString().substring(0, 5);
        createCourier(testLogin, testPassword, TEST_NAME)
                .then()
                .assertThat()
                .statusCode(HTTP_CREATED)
                .and()
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Авторизация курьера")
    @Description("Проверка на успешную авторизацию клиента")
    public void checkCourierAuthenticated() {
        loginCourier(testLogin, testPassword)
                .then()
                .assertThat()
                .body("id", notNullValue())
                .and()
                .statusCode(HTTP_OK);
    }


    @After
    public void clearCourier() {
        deleteCourier(testLogin, testPassword);
    }
}
