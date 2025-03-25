package ru.yandex.prakticum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;
import java.util.UUID;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static ru.yandex.prakticum.resources.constant.CourierTestData.*;
import static ru.yandex.prakticum.steps.CourierSteps.*;

@RunWith(Parameterized.class)
public class CreateCourierTests extends BaseTest {

    private final String login;

    private final String password;

    private final String name;

    public CreateCourierTests(String login, String password, String name) {
        this.login = login;
        this.password = password;
        this.name = name;
    }
    @Parameterized.Parameters
    public static List<String[]> CreateCourierTestData() {
        return List.of(
                new String[] {"login_" + UUID.randomUUID().toString().substring(0, 5), "password_" + UUID.randomUUID().toString().substring(0, 5), TEST_NAME},
                new String[] {"login_" + UUID.randomUUID().toString().substring(0, 6), "password_" + UUID.randomUUID().toString().substring(0, 5), TEST_NAME}
        );
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Курьера можно создать с корректными данными")
    public void checkCourierCreation() {
        createCourier(login, password, name)
                .then()
                .assertThat()
                .statusCode(HTTP_CREATED)
                .and()
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Два одинаковых курьера")
    @Description("Два одинаковых курьера должно быть запрещено создавать")
    public void checkCanNotCreateTwoTheSameCouriers() {
        createCourier(login, password, name)
                .then()
                .assertThat()
                .statusCode(HTTP_CREATED)
                .and()
                .body("ok", equalTo(true));

        createCourier(login, password, name)
                .then()
                .assertThat()
                .statusCode(HTTP_CONFLICT)
                .and()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Создание курьера с отстутствующим именем")
    @Description("Создание курьера с отстутствующим именем разрешено")
    public void checkCourierCreatedWithMandatoryFields() {
        createCourier(login, password, null)
                .then()
                .assertThat()
                .statusCode(HTTP_CREATED)
                .and()
                .body("ok", equalTo(true));
    }

    @Test
    @DisplayName("Создание курьера без пароля")
    @Description("В случае отсутствия пароля получаем ошибку")
    public void checkErrorIfBodyHasNoPssField() {
        createCourier(login, null, name)
                .then()
                .assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без логина")
    @Description("В случае отсутствия логина получаем ошибку")
    public void checkErrorIfBodyHasNoLoginField() {
        createCourier(null, password, name)
                .then()
                .assertThat()
                .statusCode(HTTP_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера с существующим логином")
    @Description("При попытке создать курьера с существующим логином получаем ошибку")
    public void checkErrorForCourierCreationWithExistingLogin() {
        createCourier(login, password, name)
                .then()
                .assertThat()
                .statusCode(HTTP_CREATED)
                .and()
                .body("ok", equalTo(true));

        createCourier(login, password + "test", TEST_NAME)
                .then()
                .assertThat()
                .statusCode(HTTP_CONFLICT)
                .and()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @After
    public void clearData() {
        deleteCourier(login, password);
    }
}
