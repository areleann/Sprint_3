import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {

    private final List<String> checkedColor;

    public CreateOrderTest(List<String> checkedColor) {
        this.checkedColor = checkedColor;
    }

    @Parameterized.Parameters
    public static Object[][] getColorData() {
        return new Object[][] {
                {Arrays.asList("BLACK")},
                {Arrays.asList("GREY")},
                {Arrays.asList("BLACK", "GREY")},
                {Arrays.asList()}
        };
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Step("Переданные значения цветов самоката: {checkedColor}")
    public List<String> outputColorsForAllure(List<String> checkedColor) {
        return checkedColor;
    }

    @Test
    @DisplayName("Создание заказа с разными цветами и комбинациями самоката")
    public void createOrderWithDifferentScooterColorsTest() {
        outputColorsForAllure(checkedColor);
        Order newOrder = new Order(
                "Egor",
                "Ivanov",
                "Москва Планетарная дом 5",
                "2",
                "+79095557778",
                6,
                "2022-06-01",
                "privet",
                checkedColor
        );
        given()
                .header("Content-type", "application/json")
                .body(newOrder)
                .when()
                .post("/api/v1/orders")
                .then().assertThat()
                .statusCode(201)
                .and()
                .body("track", instanceOf(Integer.class))
                .and()
                .body("track", notNullValue());
    }
}
