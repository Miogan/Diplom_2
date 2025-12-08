package ru.yandex.practicum.test.order;


import org.junit.After;
import ru.practicum.steps.OrderSteps;
import ru.practicum.steps.UserSteps;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;

import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.model.Order;
import org.example.model.User;

import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.test.BaseTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;

@Epic("Заказы")
@Feature("Управление заказами")
public class OrderCreateTest extends BaseTest {
    private UserSteps userSteps = new UserSteps();
    private User user;
    private OrderSteps orderSteps = new OrderSteps();
    private Order order;

    @Before
    public void setUp() {
        // готовим пользователя
        user = new User();
        user.setEmail(System.currentTimeMillis()+ "@mail.ru");
        user.setPassword("pas"  + System.currentTimeMillis());
        user.setName(RandomStringUtils.randomAlphanumeric(5));
        userSteps
                .createUser(user);

        // готовим заказ
        order = new Order();
        order.setIngredients(new String[] {
                "61c0c5a71d1f82001bdaaa6d",
                "61c0c5a71d1f82081bdaaa6d"
        });
    }

    @Test
    @DisplayName("Create order with login")
    @Feature("Создание заказа с авторизацией")
    // Создаем заказ с авторизацией ингридиентами, код 200
    public void shouldCreateOrderWithLoginTest(){
        userSteps
                .loginUser(user);
        orderSteps
                .createOrder(order)
                .statusCode(200)
                .body("success", is(true));
    }

    @Test
    @DisplayName("Create  without login")
    @Feature("Создание заказа без авторизации")
    // Создаем заказ без авторизации и ингридиентами, должна быть ошибка
    public void shouldCreateOrderWithoutLoginTest(){
                orderSteps
                .createOrder(order)
                .statusCode(200)
                .body("success", is(true));
    }

    @Test
    @DisplayName("Create order without ingredients")
    @Feature("Создание заказа без ингридиентов")
    // Создаем заказ с авторизацией, код 200
    public void shouldCreateOrderWithoutIngredientsTest(){
        userSteps
                .loginUser(user);
        order.setIngredients(new String[0]);
        orderSteps
                .createOrder(order)
                .statusCode(400)
                .body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("Create order with bad hash")
    @Feature("Создание заказа с плохим хэшем ингридиента")
    // Создаем заказ с авторизацией, код 200
    public void shouldCreateOrderBadHashTest(){
        userSteps
                .loginUser(user);
        order.setIngredients(new String[] {
                "61c0c5a71d"
        });
        orderSteps
                .createOrder(order)
                .statusCode(500);
    }

 // Удаление заказа. Не найдено апи.
    // Но мы не теряемся и удаляем пользователей.
 @After
 @DisplayName("Clean user")
 @Feature("Удаление пользователя")
 // Прибираем за собой
 public void tearDown(){
     if (user == null) {
         String nameUser = userSteps.loginUser(user)
                 .extract().body().path("name");
         if (nameUser == null) {
             user.setName(nameUser);
             userSteps.deleteUser(user);
         }
     }
 }
}




