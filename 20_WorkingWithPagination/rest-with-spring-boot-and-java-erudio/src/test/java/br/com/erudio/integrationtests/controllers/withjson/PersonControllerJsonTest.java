package br.com.erudio.integrationtests.controllers.withjson;

import br.com.erudio.config.TestConfigs;
//import br.com.erudio.data.dto.PersonDTO;
import br.com.erudio.integrationtests.dto.wrappers.json.WrapperPersonDTO;
import br.com.erudio.integrationtests.testcontainers.AbstractIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import br.com.erudio.integrationtests.dto.PersonDTO;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.util.List;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PersonControllerJsonTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;

    private static ObjectMapper objectMapper;

    private static PersonDTO person;

    @BeforeAll
    static void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        person = new PersonDTO();
    }

    @Test
    @Order(1)
    void createTest() throws JsonProcessingException {
        mockPerson();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_ERUDIO)
                .setBasePath("/api/person/v1")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(person)
                .when()
                .post()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
        person = createdPerson;

        Assertions.assertNotNull(createdPerson.getId());
        Assertions.assertTrue(createdPerson.getId() > 0);

        Assertions.assertEquals("Linus", createdPerson.getFirstName());
        Assertions.assertEquals("Torvalds", createdPerson.getLastName());
        Assertions.assertEquals("Helsinki - Finland", createdPerson.getAddress());
        Assertions.assertEquals("Male", createdPerson.getGender());
        Assertions.assertTrue((createdPerson.getEnabled()));
    }

    @Test
    @Order(2)
    void updateTest() throws JsonProcessingException {
        person.setLastName("Benedict Torvalds");

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(person)
                .when()
                .put()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
        person = createdPerson;

        Assertions.assertNotNull(createdPerson.getId());
        Assertions.assertTrue(createdPerson.getId() > 0);

        Assertions.assertEquals("Linus", createdPerson.getFirstName());
        Assertions.assertEquals("Benedict Torvalds", createdPerson.getLastName());
        Assertions.assertEquals("Helsinki - Finland", createdPerson.getAddress());
        Assertions.assertEquals("Male", createdPerson.getGender());
        Assertions.assertTrue((createdPerson.getEnabled()));
    }

    @Test
    @Order(3)
    void findByIdTest() throws JsonProcessingException {

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", person.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
        person = createdPerson;

        Assertions.assertNotNull(createdPerson.getId());
        Assertions.assertTrue(createdPerson.getId() > 0);

        Assertions.assertEquals("Linus", createdPerson.getFirstName());
        Assertions.assertEquals("Benedict Torvalds", createdPerson.getLastName());
        Assertions.assertEquals("Helsinki - Finland", createdPerson.getAddress());
        Assertions.assertEquals("Male", createdPerson.getGender());
        Assertions.assertTrue((createdPerson.getEnabled()));
    }

    @Test
    @Order(4)
    void disableTest() throws JsonProcessingException {

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", person.getId())
                .when()
                .patch("{id}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
        person = createdPerson;

        Assertions.assertNotNull(createdPerson.getId());
        Assertions.assertTrue(createdPerson.getId() > 0);

        Assertions.assertEquals("Linus", createdPerson.getFirstName());
        Assertions.assertEquals("Benedict Torvalds", createdPerson.getLastName());
        Assertions.assertEquals("Helsinki - Finland", createdPerson.getAddress());
        Assertions.assertEquals("Male", createdPerson.getGender());
        Assertions.assertFalse((createdPerson.getEnabled()));
    }

    @Test
    @Order(5)
    void deleteTest() throws JsonProcessingException {

        given(specification)
                .pathParam("id", person.getId())
                .when()
                .delete("{id}")
                .then()
                .statusCode(204);
    }

    @Test
    @Order(6)
    void findAllTest() throws JsonProcessingException {

        var content = given(specification)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .queryParam("page", 3, "size", 12, "direction", "asc")
                .when()
                .get()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        WrapperPersonDTO wrapper = objectMapper.readValue(content, WrapperPersonDTO.class);
        List<PersonDTO> people = wrapper.getEmbedded().getPeople();

        PersonDTO personOne = people.get(0);

        Assertions.assertNotNull(personOne.getId());
        Assertions.assertTrue(personOne.getId() > 0);

        Assertions.assertEquals("Amalia", personOne.getFirstName());
        Assertions.assertEquals("Kembery", personOne.getLastName());
        Assertions.assertEquals("10th Floor", personOne.getAddress());
        Assertions.assertEquals("Female", personOne.getGender());
        Assertions.assertTrue((personOne.getEnabled()));

        PersonDTO personTwo = people.get(1);

        Assertions.assertEquals("Amata", personTwo.getFirstName());
        Assertions.assertEquals("Hadingham", personTwo.getLastName());
        Assertions.assertEquals("Suite 55", personTwo.getAddress());
        Assertions.assertEquals("Female", personTwo.getGender());
        Assertions.assertFalse((personTwo.getEnabled()));
    }

    @Test
    @Order(7)
    void findByNameTest() throws JsonProcessingException {

        var content = given(specification)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("firstName", "and")
                .queryParams("page", 0, "size", 12, "direction", "asc")
                .when()
                .get("findPeopleByName/{firstName}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        WrapperPersonDTO wrapper = objectMapper.readValue(content, WrapperPersonDTO.class);
        List<PersonDTO> people = wrapper.getEmbedded().getPeople();

        PersonDTO personOne = people.get(0);

        Assertions.assertNotNull(personOne.getId());
        Assertions.assertTrue(personOne.getId() > 0);

        Assertions.assertEquals("Alexandra", personOne.getFirstName());
        Assertions.assertEquals("Ganning", personOne.getLastName());
        Assertions.assertEquals("14th Floor", personOne.getAddress());
        Assertions.assertEquals("Female", personOne.getGender());
        Assertions.assertFalse(personOne.getEnabled());

        PersonDTO personTwo = people.get(1);

        Assertions.assertNotNull(personTwo.getId());
        Assertions.assertTrue(personTwo.getId() > 0);

        Assertions.assertEquals("Alexandros", personTwo.getFirstName());
        Assertions.assertEquals("Heikkinen", personTwo.getLastName());
        Assertions.assertEquals("PO Box 70116", personTwo.getAddress());
        Assertions.assertEquals("Male", personTwo.getGender());
        Assertions.assertTrue(personTwo.getEnabled());
    }

    private void mockPerson() {
        person.setFirstName("Linus");
        person.setLastName("Torvalds");
        person.setAddress("Helsinki - Finland");
        person.setGender("Male");
        person.setEnabled(true);
    }
}