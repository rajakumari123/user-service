package com.ibm.cs.user.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class LocationServiceTest {
    @Autowired
    private LocationService locationService;

    @Test
    public void getLocationInfo(){
        String canadaIpAddress = "24.67.0.1";
        assertEquals("CA", locationService.getLocationInfo(canadaIpAddress).getCountryCode());
    }
}
