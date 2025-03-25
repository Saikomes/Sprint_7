package ru.yandex.prakticum.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import ru.yandex.prakticum.data.Order;

import java.util.List;

import static org.hamcrest.Matchers.notNullValue;
import static ru.yandex.prakticum.steps.OrderSteps.getOrders;

public class GetOrdersTest extends BaseTest {

    @Test
    @DisplayName("Тест получения заказов")
    @Description("Проверка что заказы не null, парсим ответ в json и проверяем orders")
    public void checkOrderList() {
        List<Order> orders = getOrders()
                .jsonPath()
                .getList("orders", Order.class);
        MatcherAssert.assertThat("Orders list should not be null", orders, notNullValue());
    }
}
