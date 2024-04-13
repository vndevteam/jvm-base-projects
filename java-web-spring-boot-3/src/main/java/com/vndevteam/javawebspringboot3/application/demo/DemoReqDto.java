package com.vndevteam.javawebspringboot3.application.demo;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DemoReqDto {
    @NotBlank(message = "{email.not.blank}")
    @JsonProperty("email")
    private String email;

    @Size(
            min = 5,
            max = 20,
            message = "The text1 '${validatedValue}' must be between {min} and {max} characters long"
    )
    @JsonProperty("text1")
    private String text1;

    @Size(min = 5, max = 20, message = "{text2.length.between.min.max}")
    @JsonProperty("text2")
    private String text2;
}
