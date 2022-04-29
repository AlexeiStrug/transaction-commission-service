package com.example.transaction.commision.common.response;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    private String message;
    private String description;
    private int statusCode;
}
