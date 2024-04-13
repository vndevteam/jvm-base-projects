package com.vndevteam.javawebspringboot3.application.demo;

import com.vndevteam.javawebspringboot3.domain.enums.Message;
import com.vndevteam.javawebspringboot3.infrastructure.util.MsgUtils;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/demo")
public class DemoRestController {
    @GetMapping("/locale-message")
    public Map<String, Object> getLocaleMessage() {
        Map<String, Object> map = new HashMap<>();
        map.put("request_locale", LocaleContextHolder.getLocale());
        map.put("msg", MsgUtils.getMessage(Message.MSG_1));
        return map;
    }

    @PostMapping("/custom-validation-message")
    public String customValidationMessage(
            @RequestParam("name") @NotBlank(message = "{name.not.blank}") String name,
            @Valid @RequestBody DemoReqDto demoReq) {
        return name;
    }
}
