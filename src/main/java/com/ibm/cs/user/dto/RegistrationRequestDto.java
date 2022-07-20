package com.ibm.cs.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationRequestDto {

    @NotNull(message = "User Name cannot be null")
    @NotEmpty(message = "User Name cannot be empty")
    private String userName;

    @NotNull(message = "Password cannot be null")
    @NotEmpty(message = "Password cannot be empty")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[_ #$%.])(?=\\S+$).{8,}$", message = "Password need to be greater than 8 characters, containing at least 1 number, 1 Capitalized letter, 1 special character in this set “_ # $ % .” ")
    private String password;

    @NotNull(message = "Ip Address cannot be null")
    @NotEmpty(message = "Ip Address cannot be empty")
    private String ipAddress;
}
