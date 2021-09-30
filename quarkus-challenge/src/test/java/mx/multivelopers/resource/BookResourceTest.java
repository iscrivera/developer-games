package mx.multivelopers.resource;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;


public class BookResourceTest {

    
    public void testHelloEndpoint() {
        given()
          .when().get("/books")
          .then()
             .statusCode(200)
             .body(is("Hello RESTEasy"));
    }

}