package Steps;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.example.model.Order;

import static io.restassured.RestAssured.given;

public class OrderSteps {
    // Создание заказа
    public static final String CREATEORDER = "/api/orders";
    // Удаление заказа. Нет апи


    @Step("Создание заказа")
    public ValidatableResponse createOrder(Order order){
        return given()
                .body(order)
                .when()
                .post(CREATEORDER)
                .then();
    }

}
