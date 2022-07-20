package com.ibm.cs.user.service;

import com.ibm.cs.user.dto.LocationInfoDto;
import com.ibm.cs.user.dto.RegistrationRequestDto;
import com.ibm.cs.user.dto.RegistrationResponseDto;
import com.ibm.cs.user.exception.UserNotEligibleException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RegistrationService {
    private final LocationService locationService;

    public RegistrationService(LocationService locationService) {
        this.locationService = locationService;
    }

    public RegistrationResponseDto registerUser(RegistrationRequestDto registrationRequestDto) {
        LocationInfoDto locationInfoDto = locationService.getLocationInfo(registrationRequestDto.getIpAddress());
        if (locationInfoDto != null && "CA".equals(locationInfoDto.getCountryCode())) {
            return RegistrationResponseDto.builder()
                    .uuid( UUID.randomUUID())
                    .welcomeMessage("Welcome " + registrationRequestDto.getUserName() + " from " + locationInfoDto.getCity())
                    .build();
        }
        throw new UserNotEligibleException("User is not eligible for register");
    }
}
