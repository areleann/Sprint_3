import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class CreateCourierTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Step("Успешное создание курьера")
    private void createCourier(Courier courier) {
        Response response = courier.doCreateCourier();
        response.then().assertThat()
                .statusCode(201)
                .and()
                .body("ok", equalTo(true));
    }

    @Step("Создание курьера с тем же логином")
    private void createCourierWithExistingLogin(Courier courier) {
        Response response = courier.doCreateCourier();
        response.then().assertThat()
                .statusCode(409)
                .and()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Step("Создание курьера без логина")
    private void createCourierWithoutLogin(Courier courier) {
        Response response = courier.doCreateCourier();
        response.then().assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Успешное создание курьера")
    public void createCourierPositiveTest() {
        Courier courier = new Courier("courier11111", "zxczxc123", "Олег");
        createCourier(courier);
        courier.doDeleteCourier(courier.getCourierId(courier));
    }

    @Test
    @DisplayName("Ошибка при создании курьера с существующим логином")
    public void createCourierWithExistingLoginTest() {
        Courier courier = new Courier("courier11111", "zxczxc123", "Олег");
        Courier courier2 = new Courier("courier11111", "zxczxc321", "Денис");
        createCourier(courier);
        createCourierWithExistingLogin(courier2);
        courier.doDeleteCourier(courier.getCourierId(courier));
    }

    @Test
    @DisplayName("Ошибка при создании курьера без логина")
    public void createCourierWithoutLoginTest() {
        Courier courier = new Courier("", "zxczxc123", "Олег");
        createCourierWithoutLogin(courier);
    }
}
