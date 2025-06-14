package com.tauhoa.train.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class CommonResponse {
    private HttpStatus status;
    private String message;
    private Object data;
}
