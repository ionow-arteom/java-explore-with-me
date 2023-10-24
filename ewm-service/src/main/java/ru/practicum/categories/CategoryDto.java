package ru.practicum.categories;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

    private Long id;

    @Size(max = 50, message = "The name should have a maximum length of 50 characters.")
    @NotBlank(message = "The name field shouldn't be blank or only spaces.")
    private String name;
}