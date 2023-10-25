package ru.practicum.user.dto;

import lombok.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;

    @NotBlank(message = "Name should not be blank or just whitespace.")
    @Size(min = 2, max = 250, message = "Name length should be between 2 and 250 characters.")
    private String name;

    @Email(message = "Invalid email format.")
    @NotBlank(message = "Email field should not be blank or just whitespace.")
    @Size(min = 6, max = 254, message = "Email length should be between 6 and 254 characters.")
    private String email;
}