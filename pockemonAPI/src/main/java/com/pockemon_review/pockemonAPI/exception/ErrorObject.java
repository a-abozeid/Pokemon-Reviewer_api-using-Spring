package com.pockemon_review.pockemonAPI.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorObject {
    private Integer statusCode;
    private String message;
    private Date timeStamp;
}
