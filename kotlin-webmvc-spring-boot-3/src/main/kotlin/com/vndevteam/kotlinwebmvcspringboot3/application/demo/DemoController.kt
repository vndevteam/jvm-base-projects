package com.vndevteam.kotlinwebmvcspringboot3.application.demo

import com.vndevteam.kotlinwebmvcspringboot3.domain.enums.MESSAGE
import com.vndevteam.kotlinwebmvcspringboot3.infrastructure.util.MsgUtils
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.stereotype.Controller
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
@Controller
@RequestMapping("/demo")
class DemoController {

    @GetMapping("/locale-message")
    fun getLocaleMessage(): String {
        val test = mapOf(
            "request_locale" to LocaleContextHolder.getLocale(),
            "msg" to MsgUtils.getMessage(MESSAGE.MSG_1)
        )

        println(test)

        return "demo/index"
    }

    @PostMapping("/custom-validation-message")
    fun customValidationMessage(
        @RequestParam("name") @NotBlank(message = "{name.not.blank}") name: String,
        @Valid @RequestBody demoReq: DemoReqDto
    ): String {
        println(name)

        return "demo/index"
    }
}
