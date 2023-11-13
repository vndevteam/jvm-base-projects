package com.vndevteam.kotlinwebmvcspringboot3.application.demo

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

class DemoReqDto {
    @NotBlank(message = "{email.not.blank}") @JsonProperty("email") var email: String? = null

    @Size(
        min = 5,
        max = 20,
        message = "The text1 '\${validatedValue}' must be between {min} and {max} characters long"
    )
    @JsonProperty("text1")
    var text1: String? = null

    @Size(min = 5, max = 20, message = "{text2.length.between.min.max}")
    @JsonProperty("text2")
    var text2: String? = null
}
