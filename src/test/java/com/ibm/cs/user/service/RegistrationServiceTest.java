package com.ibm.cs.user.service;

import com.ibm.cs.user.dto.LocationInfoDto;
import com.ibm.cs.user.dto.RegistrationRequestDto;
import com.ibm.cs.user.dto.RegistrationResponseDto;
import com.ibm.cs.user.exception.UserNotEligibleException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTest {
    @InjectMocks
    private RegistrationService registrationService;

    @Mock
    private LocationService locationService;

    @Test
    public void test_RegisterUser_Valid_Country() {
        RegistrationRequestDto registrationRequestDto = buildUserRequestDto();

        LocationInfoDto locationInfoDto = LocationInfoDto.builder()
                .city("Toronto")
                .countryCode("CA")
                .build();
        when(locationService.getLocationInfo(registrationRequestDto.getIpAddress())).thenReturn(locationInfoDto);

        RegistrationResponseDto registrationResponseDto = registrationService.registerUser(registrationRequestDto);
        assertEquals("Welcome testUserName from Toronto", registrationResponseDto.getWelcomeMessage());
        assertNotNull(registrationResponseDto.getUuid());
    }

    private RegistrationRequestDto buildUserRequestDto() {
        RegistrationRequestDto registrationRequestDto = RegistrationRequestDto.builder()
                .userName("testUserName")
                .password("Somepas$w0rd")
                .ipAddress("some-ip-address")
                .build();
        return registrationRequestDto;
    }

    @Test
    public void test_RegisterUser_Invalid_Country() {
        RegistrationRequestDto registrationRequestDto = buildUserRequestDto();

        LocationInfoDto locationInfoDto = LocationInfoDto.builder()
                .city("New York")
                .countryCode("US")
                .build();
        when(locationService.getLocationInfo(registrationRequestDto.getIpAddress())).thenReturn(locationInfoDto);

        assertThrows(UserNotEligibleException.class, () -> registrationService.registerUser(registrationRequestDto));
    }

    @Test
    public void test_RegisterUser_No_Location_Info() {
        RegistrationRequestDto registrationRequestDto = buildUserRequestDto();

        assertThrows(UserNotEligibleException.class, () -> registrationService.registerUser(registrationRequestDto));
    }

}
