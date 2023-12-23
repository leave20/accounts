package com.craig.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Schema(
        name = "ErrorResponse",
        description = "Error Response details"
)
@Data
@AllArgsConstructor
public class ErrorResponseDto {

    @Schema(
            description = "API path",
            example = "/api/v1/customer"
    )
    private String apiPath;

    @Schema(
            description = "Error code",
            example = "400"
    )
    private HttpStatus errorCode;

    @Schema(
            description = "Error message",
            example = "Bad Request"
    )
    private String errorMessage;

    @Schema(
            description = "Error time",
            example = "2021-08-01T12:12:12"
    )
    private LocalDateTime errorTime;
}
