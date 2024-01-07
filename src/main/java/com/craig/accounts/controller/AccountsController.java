package com.craig.accounts.controller;

import com.craig.accounts.constants.AccountsConstants;
import com.craig.accounts.dto.CustomerDto;
import com.craig.accounts.dto.ErrorResponseDto;
import com.craig.accounts.dto.ResponseDto;
import com.craig.accounts.service.IAccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.craig.accounts.constants.AccountsConstants.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.OK;

@Tag(
        name = "Accounts API v1.0 for Make a Miracle",
        description = "Accounts API v1.0 - Create, Fetch, Update and Delete Accounts"
)
@RestController
@RequestMapping(path = "/api/accounts", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class AccountsController {

    private final IAccountsService iAccountsService;

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    public AccountsController(IAccountsService iAccountsService) {
        this.iAccountsService = iAccountsService;
    }

    @Operation(
            summary = "Create an account",
            description = "Create an account"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Account created successfully"
    )
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(
            @Valid
            @RequestBody CustomerDto customerDto
    ) {
        iAccountsService.createAccount(customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201));
    }

    @Operation(
            summary = "Fetch an account",
            description = "Fetch an account"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Account fetched successfully"
    )
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccount(
            @RequestParam
            @Pattern(
                    regexp = "(^$|\\d{10})",
                    message = "Mobile number must be 10 digits"
            )
            String mobileNumber
    ) {
        CustomerDto customerDto = iAccountsService.fetchAccount(mobileNumber);
        return ResponseEntity.status(OK).body(customerDto);
    }

    @Operation(
            summary = "Update an account",
            description = "Update an account"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Account updated successfully"
                    ),
                    @ApiResponse(
                            responseCode = "417",
                            description = "Expectation failed"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Update failed",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ErrorResponseDto.class
                                    )
                            )
                    )
            }
    )
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(
            @Valid
            @RequestBody CustomerDto customerDto
    ) {
        boolean isUpdated = iAccountsService.updateAccount(customerDto);
        if (isUpdated) {
            return ResponseEntity
                    .status(OK)
                    .body(new ResponseDto(STATUS_200, MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(STATUS_500, MESSAGE_500));
        }
    }

    @Operation(
            summary = "Delete an account",
            description = "Delete an account"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Account deleted successfully"
                    ),
                    @ApiResponse(
                            responseCode = "417",
                            description = "Expectation failed"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Account deletion failed",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(
                                            implementation = ErrorResponseDto.class
                                    )
                            )
                    )
            }
    )
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetails(
            @RequestParam
            @Pattern(regexp = "(^$|\\d{10})",
                    message = "Mobile number must be 10 digits")
            String mobileNumber
    ) {
        boolean isDeleted = iAccountsService.deleteAccount(mobileNumber);
        if (isDeleted) {
            return ResponseEntity
                    .status(OK)
                    .body(new ResponseDto(STATUS_200, MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(STATUS_500, MESSAGE_500));
        }
    }

    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo() {
        return ResponseEntity.status(OK).body(buildVersion);
    }

}
