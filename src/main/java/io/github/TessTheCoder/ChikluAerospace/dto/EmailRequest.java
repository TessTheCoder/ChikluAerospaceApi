package io.github.TessTheCoder.ChikluAerospace.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequest {

    @Email(message = "Invalid email format")
    @NotBlank(message = "Receiver email is required")
    private String receiverEmail;

    @NotBlank(message = "Message cannot be empty")
    private String message;
}
