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

@Epic("Пользователи")
@Feature("Управление пользователями. Авторизация")
public class AuthorizationUserTest extends BaseTest {
    private UserSteps userSteps = new UserSteps();
    private User user;

    @Before
    public void setUp(){
        user = new User();
        user.setEmail(System.currentTimeMillis()+ "@mail.ru");
        user.setPassword("pas"  + System.currentTimeMillis());
        user.setName(RandomStringUtils.randomAlphanumeric(5));
        userSteps
                .createUser(user);
    }

    @Test
    @DisplayName("Create user & authorization")
    @Feature("Авторизация пользователя")
    // Создаем и авторизуемся, код 200
    public void shouldLoginUserTest(){
        userSteps
                .createUser(user);
        userSteps
                .loginUser(user)
                .statusCode(200)
                .body("success", is(true));
    }

    @Test
    @DisplayName("Create user & authorization incorrect login")
    @Feature("Авторизация пользователя с некорректным паролем")
    // Создаем и авторизуемся, код 401
    public void shouldLoginUserIncorrectPasswordTest(){
        user.setPassword(RandomStringUtils.randomAlphanumeric(2)  + System.currentTimeMillis());
        userSteps
                .loginUser(user)
                .statusCode(401)
                .body("success", is(false));
    }

    @Test
    @DisplayName("Create user & authorization incorrect email")
    @Feature("Авторизация пользователя с некорректным email")
    // Создаем и авторизуемся, код 401
    public void shouldLoginUserIncorrectLoginTest(){
        user.setEmail(System.currentTimeMillis()+ "@mail.ru");
        userSteps
                .loginUser(user)
                .statusCode(401)
                .body("success", is(false));
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
