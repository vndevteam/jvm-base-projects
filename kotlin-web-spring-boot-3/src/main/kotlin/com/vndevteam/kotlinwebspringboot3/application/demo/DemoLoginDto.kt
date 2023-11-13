package com.vndevteam.kotlinwebspringboot3.application.demo

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

class DemoLoginDto {
    @NotBlank(message = "{email.not.blank}")
    @JsonProperty("email")
    var email: String? = null

    @Size(
        min = 5,
        max = 20,
        message = "Password '\${validatedValue}' must be between {min} and {max} characters long",
    )
    @JsonProperty("password")
    var password: String? = null
}
