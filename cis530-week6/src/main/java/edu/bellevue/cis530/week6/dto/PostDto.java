/*
Natarajan, C. (2026). CIS 530 Server-Side Development. Bellevue University.
Class structure supplied in the CIS 530 Assignment 6.2 instructions. Bellevue University.
*/
package edu.bellevue.cis530.week6.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object that holds one post returned by JSONPlaceholder.
 * Lombok generates the getters, setters, toString, equals, hashCode, and both constructors.
 * The field names match the JSON keys so Jackson can map them without extra configuration.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {

    @NotNull(message = "userId is required")
    @Min(value = 1, message = "userId must be at least 1")
    private Long userId;

    private Long id;

    @NotBlank(message = "title is required")
    private String title;

    @NotBlank(message = "body is required")
    private String body;
} // end PostDto
