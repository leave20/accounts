package com.craig.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(
        name = "Response",
        description = "Response details"
)
@Data
@AllArgsConstructor
public class ResponseDto {

    @Schema(
            description = "status code",
            example = "200"
    )
    private String statusCode;

    @Schema(
            description = "status message",
            example = "Success"
    )
    private String statusMsg;



}
