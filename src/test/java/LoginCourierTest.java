import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;

public class LoginCourierTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru";
    }

    @Step("Успешный логин курьера")
    private void loginCourierWithSuccess(Courier courier) {
        Response response = courier.doLoginCourier();
        response.then().assertThat()
                .statusCode(200)
                .and()
                .body("id", instanceOf(Integer.class))
                .and()
                .body("id", notNullValue());
    }

    @Step("Ошибка при логине, если нет логина")
    private void loginCourierWithoutLogin(Courier courier) {
        Response response = courier.doLoginCourier();
        response.then().assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Step("Ошибка при логине, если такого курьера не существует")
    private void loginCourierWithWrongData(Courier courier) {
        Response response = courier.doLoginCourier();
        response.then().assertThat()
                .statusCode(404)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Успешный логин курьера")
    public void loginCourierWithSuccessTest() {
        Courier courier = new Courier("courier11111", "zxczxc123", "Олег");
        courier.doCreateCourier();
        loginCourierWithSuccess(courier);
        courier.doDeleteCourier(courier.getCourierId(courier));
    }

    @Test
    @DisplayName("Ошибка при логине, если нет логина")
    public void loginCourierWithoutLoginTest() {
        Courier courier = new Courier("", "zxczxc123", "Олег");
        loginCourierWithoutLogin(courier);
    }

    @Test
    @DisplayName("Ошибка при логине, если такого курьера не существует")
    public void loginCourierWithWrongDataTest() {
        Courier courier = new Courier("courier83552", "zxczxc123", "Олег");
        loginCourierWithWrongData(courier);
    }
}
