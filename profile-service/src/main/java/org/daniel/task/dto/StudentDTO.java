package org.daniel.task.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {
    @JsonProperty("name")
    @NotBlank(message = "Name is required")
    @Schema(description = "Name", example = "Jane Doe")
    private String name;

    @JsonProperty("email")
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email")
    @Schema(description = "Email", example = "jane.doe@gmail.com")
    private String email;
}
