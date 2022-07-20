package com.ibm.cs.user.service;

import com.ibm.cs.user.dto.LocationInfoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class LocationService {
    private final RestTemplate restTemplate;
    private final String locationServiceUrl;

    public LocationService(RestTemplate restTemplate,
                           @Value("${location.service.url}")String locationServiceUrl) {
        this.restTemplate = restTemplate;
        this.locationServiceUrl = locationServiceUrl;
    }

    public LocationInfoDto getLocationInfo(String ipAddress){
        log.info("Calling External API to get location info for ipAddress: {}", ipAddress);
        LocationInfoDto locationInfoDto = restTemplate.getForObject(locationServiceUrl, LocationInfoDto.class, ipAddress);
        log.info("Response received for ipAddress: {} and response: {}", ipAddress, locationInfoDto);
        return locationInfoDto;
    }
}
