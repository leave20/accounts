package com.craig.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema(
        name = "Customer",
        description = "Customer details"
)
@Data
public class CustomerDto {

    @Schema(
            description = "name",
            example = "Craig"
    )
    @NotEmpty(message = "Name can not be a null or empty")
    @Size(min = 5, max = 30,message = "The length of the customer name should be between 5 and 30")
    private String name;

    @Schema(
            description = "email",
            example = "asdasd@asdasd.com"
    )
    @NotEmpty(message = "Email address can not be a null or empty")
    @Email(message = "Email address should be a valid value")
    private String email;

    @Schema(
            description = "mobile number",
            example = "1234567890"
    )
    @Pattern(regexp = "(^$|\\d{10})",message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @Schema(
            description = "account details"
    )
    private AccountDto accountDto;
}
