package org.example;

import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;

import static org.hamcrest.Matchers.equalTo;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

public class Testator
{
    String token = " fe24c8ba85a4dd4d0bbab9cbf6164d2e634767ebec52d73cc0cee0839332be12";

    String name = "\"name\"" ;
    String gender = "\"gender\"" ;
    String email = "\"email\"" ;
    String status = "\"status\"" ;

    String male = "\"male\"" ;
    String female = "\"female\"" ;

    String active = "\"active\"" ;
    String innactive = "\"innactive\"" ;

    String URL = "https://gorest.co.in/public/v2/users/" ;

    String uniqueEmail = "papa.test" + System.currentTimeMillis() + "@example.com";

    String mail = "\"" + uniqueEmail + "\"" ;


    int id = 0 ;


    @Test
    @Order(2)
    public void get_rest()
    {
        given()
                .header("Authorization", "Bearer" + token).
        when()
                .get("http://gorest.co.in/public/v2/users").
        then()
                .log().body()
                .statusCode(200);

    }
    @Test
    @Order(1)
    public void post_rest()
    {

        id = given()
                .header("Authorization", "Bearer" + token)
                .header("Content-Type", "application/json")
                .body("{ " +
                        "\"name\" : \"Papa Ndiasse\", " +
                        "\"gender\" : \"female\", " +
                        "\"email\": "+ mail + ", " +
                        "\"status\": \"active\" " +
                        "}").
        when()
                .post("https://gorest.co.in/public/v2/users").
        then()
                .statusCode(201) // Vérifié le code retour
                .body("name", equalTo("Papa Ndiasse")) // Vérifie le contenu de ce qui à été envoyé
                .body("email", equalTo(uniqueEmail)) // Vérifie le contenu de ce qui à été envoyé
                .extract().path("id") ;
    }

    @Test
    @Order(3)
    public void put_rest()
    {
        given()
                .header("Authorization", "Bearer" + token)
                .header("Content-Type", "application/json")
                .body("{ " +
                        "\"name\" : \"Papa Ndiasse\", " +
                        "\"gender\" : \"male\", " +
                        "\"email\":" + mail + " , " +
                        "\"status\": \"active\" " +
                        "}").
        when()
                .put("https://gorest.co.in/public/v2/users/" + id).
        then()
                .log().body()
                .statusCode(200)
                .body("gender", equalTo("male"));

    }

    @Test
    @Order(4)
    public void patch_rest()
    {
        given()
                .header("Authorization", "Bearer" + token)
                .header("Content-Type", "application/json")
                .body("{" + gender + ":" + female + "}").
        when()
                .patch(URL + id).
        then()
                .log().body()
                .statusCode(200)
                .body("gender", equalTo("female")) ;
    }

    @Test
    @Order(5)
    public void delete_rest()
    {
        given()
                .header("Authorization", "Bearer" + token)
                .header("Content-Type", "application/json").
        when()
                .delete(URL + id).
        then()
                .log().body()
                .statusCode(204) ;


    }

}