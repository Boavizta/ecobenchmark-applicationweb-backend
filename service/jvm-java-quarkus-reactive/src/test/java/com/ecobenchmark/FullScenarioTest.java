package com.ecobenchmark;

import com.ecobenchmark.controllers.addaccount.AccountRequest;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class FullScenarioTest {

    @Test
    public void testScenarioTodo() {
        AccountRequest accountRequest= new AccountRequest();
        accountRequest.setLogin("user1");
        given()
                .when()
                .body(accountRequest)
                .contentType(ContentType.JSON)
                .post("/api/accounts")
                .then()
                .statusCode(200);
    }
}
