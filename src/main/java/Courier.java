import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class Courier {
    public String login;
    public String password;
    public String firstName;

    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public Courier() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Step("Создание курьера")
    public Response doCreateCourier() {
        return given()
                .header("Content-type", "application/json")
                .body(this)
                .when()
                .post("/api/v1/courier");
    }

    @Step("Логин курьера")
    public Response doLoginCourier() {
        return given()
                .header("Content-type", "application/json")
                .body(this)
                .post("/api/v1/courier/login");
    }

    @Step("Получение id созданного курьера для последующего удаления")
    public int getCourierId(Courier courier) {
        CourierId courierId = given()
                .header("Content-type", "application/json")
                .body(courier)
                .post("/api/v1/courier/login")
                .body()
                .as(CourierId.class);
        return courierId.getId();
    }

    @Step("Удаление курьера")
    public Response doDeleteCourier(int loginCourier) {
        return given()
                .header("Content-type", "application/json")
                .delete("/api/v1/courier/" + loginCourier);
    }

    @Override
    public String toString() {
        return "Courier{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                '}';
    }
}
