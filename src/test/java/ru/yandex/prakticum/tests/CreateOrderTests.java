package ru.yandex.prakticum.tests;

import java.util.List;

import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.notNullValue;
import static ru.yandex.prakticum.resources.constant.OrderTestData.*;
import static ru.yandex.prakticum.steps.OrderSteps.*;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.prakticum.data.Order;

@RunWith(Parameterized.class)
public class CreateOrderTests extends BaseTest {

    private final Order order;

    public CreateOrderTests(Order order) {
        this.order = order;
    }

    @Parameterized.Parameters
    public static List<Order> CreateOrderTestData() {
        return List.of(new Order(TEST_FIRST_NAME, TEST_LAST_NAME, TEST_ADDRESS, TEST_METRO_STATION, TEST_PHONE, TEST_RENT_TIME, TEST_DELIVERY_DATE, TEST_COMMENT, List.of(TEST_COLOR_BLACK)),
                new Order(TEST_FIRST_NAME, TEST_LAST_NAME, TEST_ADDRESS, TEST_METRO_STATION, TEST_PHONE, TEST_RENT_TIME, TEST_DELIVERY_DATE, TEST_COMMENT, List.of(TEST_COLOR_GREY)),
                new Order(TEST_FIRST_NAME, TEST_LAST_NAME, TEST_ADDRESS, TEST_METRO_STATION, TEST_PHONE, TEST_RENT_TIME, TEST_DELIVERY_DATE, TEST_COMMENT, List.of(TEST_COLOR_BLACK, TEST_COLOR_GREY)),
                new Order(TEST_FIRST_NAME, TEST_LAST_NAME, TEST_ADDRESS, TEST_METRO_STATION, TEST_PHONE, TEST_RENT_TIME, TEST_DELIVERY_DATE, TEST_COMMENT));
    }

    @Test
    @DisplayName("Создаем заказ")
    @Description("Создание заказа должно быть успешным")
    public void checkOrderCreation() {
        var response = createOrder(order);
        response.then()
                .assertThat()
                .body("track", notNullValue())
                .and()
                .statusCode(HTTP_CREATED);
    }

    @After
    public void clearData() {
        cancelOrder(order.getTrack());
    }
}
