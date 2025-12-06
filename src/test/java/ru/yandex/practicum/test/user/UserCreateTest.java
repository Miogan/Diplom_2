package ru.yandex.practicum.test.user;

import ru.practicum.steps.UserSteps;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.test.BaseTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;

@Epic("Пользователи")
@Feature("Управление пользователями. Создание")
public class UserCreateTest extends BaseTest {
        private UserSteps userSteps = new UserSteps();
        private User user;

        @Before
        public void setUp(){
            user = new User();
            user.setEmail(System.currentTimeMillis()+ "@mail.ru");
            user.setPassword("pas"  + System.currentTimeMillis());
            user.setName(RandomStringUtils.randomAlphanumeric(5));
        }

        @Test
        @DisplayName("Create user")
        @Feature("Создание пользователя")
        // Создаем пользователя, код 200
        public void shouldCreateUserTest(){
            userSteps
                    .createUser(user)
                    .statusCode(200)
                    .body("success", is(true));
        }

    @Test
    @DisplayName("Сreate a user who is already registered")
    @Feature("Создание пользователя, который уже зарегистрирован")
    // Создаем двух пользователей c одним логином, код 403
    public void shouldCreateDoubleUserTest(){
        userSteps
                .createUser(user);
        userSteps
                .createUser(user)
                .statusCode(403)
                .body("message", equalTo("User already exists"));
    }

    @Test
    @DisplayName("Create user without name")
    @Feature("Создание пользователя без имени")
    // Создаем пользователя без имени
    public void shouldCreateUserTestWithoutName(){
        user.setName("");
        userSteps
                .createUser(user)
                .statusCode(403)
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Create user without email")
    @Feature("Создание пользователя без почты")
    // Создаем пользователя без почты
    public void shouldCreateUserTestWithoutEmail(){
        user.setEmail("");
        userSteps
                .createUser(user)
                .statusCode(403)
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Create user without password")
    @Feature("Создание пользователя без пароля")
    // Создаем пользователя без пароля
    public void shouldCreateUserTestWithoutPassword(){
        user.setPassword("");
        userSteps
                .createUser(user)
                .statusCode(403)
                .body("message", equalTo("Email, password and name are required fields"));
    }

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
