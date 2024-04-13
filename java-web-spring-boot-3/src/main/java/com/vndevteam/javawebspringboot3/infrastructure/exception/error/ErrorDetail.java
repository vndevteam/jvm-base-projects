package com.vndevteam.javawebspringboot3.infrastructure.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ErrorDetail {
    private String code;
    private String field;
    private String message;
}
