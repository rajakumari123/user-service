package com.ibm.cs.user.controller;

import com.ibm.cs.user.dto.RegistrationRequestDto;
import com.ibm.cs.user.dto.RegistrationResponseDto;
import com.ibm.cs.user.service.RegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/registration")
@Slf4j
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping
    public ResponseEntity<RegistrationResponseDto> registerUser(@Valid @RequestBody RegistrationRequestDto registrationRequestDto) {
        return new ResponseEntity<>(registrationService.registerUser(registrationRequestDto), HttpStatus.CREATED);
    }


}
