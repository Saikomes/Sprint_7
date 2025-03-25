package ru.yandex.prakticum.tests;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static ru.yandex.prakticum.steps.CourierSteps.*;


import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class LoginCourierParameterizedTests extends BaseTest {

    private final String login;
    private final String password;
    private final String expectedMessage;
    private final int expectedStatusCode;

    // Конструктор для класса с параметризованными тестами
    public LoginCourierParameterizedTests(String login, String password, String expectedMessage, int expectedStatusCode) {
        this.login = login;
        this.password = password;
        this.expectedMessage = expectedMessage;
        this.expectedStatusCode = expectedStatusCode;
    }

    @Parameterized.Parameters(name = "Тест: логин={0}, пароль={1}, ожидаемое сообщение={2}, статус={3}")
    public static Collection<Object[]> createOrderTestData() {
        return Arrays.asList(new Object[][] {
                {null, "password", "Недостаточно данных для входа", HTTP_BAD_REQUEST},
                {"login", null, "Недостаточно данных для входа", HTTP_BAD_REQUEST},
                {"nonexistent_user", "wrongpass", "Учетная запись не найдена", HTTP_NOT_FOUND},
                {"", "password", "Недостаточно данных для входа", HTTP_BAD_REQUEST},
                {"login", "", "Недостаточно данных для входа", HTTP_BAD_REQUEST}
        });
    }

    @Test
    @DisplayName("Проверка авторизации с разными наборами данных")
    @Description("Тестирование различных сценариев входа с невалидными данными")
    public void testLoginWithInvalidCredentials() {
        loginCourier(login, password)
                .then()
                .statusCode(expectedStatusCode)
                .body("message", equalTo(expectedMessage));
    }

}