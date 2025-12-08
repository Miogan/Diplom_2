package ru.practicum.steps;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.example.model.User;

import static io.restassured.RestAssured.given;

public class UserSteps {
    // Создание пользователя
    public static final String USERCREATE = "/api/auth/register";
    // Логин пользователя
    public static final String USERLOGIN = "/api/auth/login";
    // Удаление пользователя
    public static final String USERDELETE = "/api/auth/user";

    @Step("Создание пользователя")
    public ValidatableResponse createUser(User user){
        return given()
                .body(user)
                .when()
                .post(USERCREATE)
                .then();
    }

    @Step("Создание пользователя")
    public ValidatableResponse loginUser(User user){
        return given()
                .body(user)
                .when()
                .post(USERLOGIN)
                .then();
    }

    @Step("Удаление пользователя")
    public ValidatableResponse deleteUser(User user){
        return given()
                .pathParams("id", user.getName())
                .when()
                .delete(USERDELETE)
                .then();
    }

}
