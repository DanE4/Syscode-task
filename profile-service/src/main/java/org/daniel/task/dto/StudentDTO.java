package org.daniel.task.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
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
    @Schema(description = "Name", example = "Jane Doe")
    private String name;

    @JsonProperty("email")
    @Schema(description = "Email", example = "jane.doe@gmail.com")
    @Email(message = "Invalid email")
    private String email;
}
