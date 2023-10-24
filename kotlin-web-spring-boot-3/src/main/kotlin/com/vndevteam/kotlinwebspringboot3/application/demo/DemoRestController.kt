package com.vndevteam.kotlinwebspringboot3.application.demo

import com.vndevteam.kotlinwebspringboot3.infrastructure.util.MESSAGE
import com.vndevteam.kotlinwebspringboot3.infrastructure.util.MsgUtils
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
@RestController
@RequestMapping("/demo")
class DemoRestController {

    @GetMapping("/locale-message")
    fun getLocaleMessage(): Map<String, Any> {
        return mapOf(
            "request_locale" to LocaleContextHolder.getLocale(),
            "msg" to MsgUtils.getMessage(MESSAGE.MSG_1)
        )
    }

    @PostMapping("/custom-validation-message")
    fun customValidationMessage(
        @RequestParam("name") @NotBlank(message = "{name.not.blank}") name: String,
        @Valid @RequestBody demoReq: DemoReqDto
    ): String {
        return name
    }
}
