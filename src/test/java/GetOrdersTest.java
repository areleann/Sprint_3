import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;

public class GetOrdersTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Получение списка заказов")
    @Step("Получение списка заказов в формате массива")
    public void getOrdersTest() {
        String json = "{\"limit\": \"5\"}";
        Response response = given()
                .header("Content-type", "application/json")
                .body(json)
                .get("/api/v1/orders");
                response.then().assertThat()
                .statusCode(200)
                .and()
                .body("orders", instanceOf(List.class))
                .and()
                .body("orders", notNullValue());
    }
}
