package br.com.erudio.controllers.docs;

import br.com.erudio.data.dto.BookDTO;
import br.com.erudio.data.dto.PersonDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface BookControllerDocs {

    @Operation(summary = "Find All Books",
            description = "Finds All Books",
            tags = {"Book"},
            responses = {
                    @ApiResponse(description = "Sucess", responseCode = "200", content = {
                            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = PersonDTO.class)))
                    }),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
            })
    ResponseEntity<PagedModel<EntityModel<BookDTO>>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    );

//    @Operation(summary = "Finds a Person",
//            description = "Finds a specific person by your ID",
//            tags = {"People"},
//            responses = {
//                    @ApiResponse(description = "Sucess", responseCode = "200", content =
//                    @Content(schema = @Schema(implementation = PersonDTO.class))
//                    ),
//                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
//                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
//                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
//                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
//                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
//            })
//    PersonDTO findById(@PathVariable("id") Long id);
//
//    @Operation(summary = "Adds a new Person",
//            description = "Finds a new Person by passing in a JSON, XML, or YML representation of the person.",
//            tags = {"People"},
//            responses = {
//                    @ApiResponse(description = "Sucess", responseCode = "200", content =
//                    @Content(schema = @Schema(implementation = PersonDTO.class))
//                    ),
//                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
//                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
//                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
//            })
//    PersonDTO create(@RequestBody PersonDTO person);
//
//    @Operation(summary = "Updates a person's information",
//            description = "Updates a person's information by passing in a JSON, XML, or YML representation of the person.",
//            tags = {"People"},
//            responses = {
//                    @ApiResponse(description = "Sucess", responseCode = "200", content =
//                    @Content(schema = @Schema(implementation = PersonDTO.class))
//                    ),
//                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
//                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
//                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
//            })
//    PersonDTO update(@RequestBody PersonDTO person);
//
//    @Operation(summary = "Disabled a Person",
//            description = "Disabled a specific person by your ID",
//            tags = {"People"},
//            responses = {
//                    @ApiResponse(description = "Sucess", responseCode = "200", content =
//                    @Content(schema = @Schema(implementation = PersonDTO.class))
//                    ),
//                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
//                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
//                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
//                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
//                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
//            })
//    PersonDTO disablePerson(@PathVariable("id") Long id);
//
//    @Operation(summary = "Deletes a Person",
//            description = "Deletes a specific person by their ID",
//            tags = {"People"},
//            responses = {
//                    @ApiResponse(description = "Sucess", responseCode = "200", content =
//                    @Content(schema = @Schema(implementation = PersonDTO.class))
//                    ),
//                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
//                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
//                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
//                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
//                    @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content)
//            })
//    ResponseEntity<?> delete(@PathVariable Long id);
}
