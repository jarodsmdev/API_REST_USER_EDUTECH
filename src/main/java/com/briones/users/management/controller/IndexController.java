package com.briones.users.management.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    @Operation(
            summary = "Check application status",
            description = "Returns a simple message indicating that the application is running.",
            tags = {"Health Check"} // Optional: You can categorize this under a "Health Check" tag in Swagger UI
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Application is running successfully",
                    content = @Content(
                            mediaType = MediaType.TEXT_PLAIN_VALUE, // Explicitly declare text/plain
                            schema = @Schema(type = "string", example = "Aplication is running") // Describe the string response
                    )
            )
    })
    @GetMapping("/")
    public String index() {
        return "Aplication is running";
    }
}
