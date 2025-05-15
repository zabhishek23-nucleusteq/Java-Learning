package com.example.UserProductApplication.Controller;

import com.example.UserProductApplication.DTO.AddressDto;
import com.example.UserProductApplication.DTO.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class UserControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/user";
    }

    @Test
    public void testAddAndGetUser() {
        AddressDto address = AddressDto.builder().city("Pune").state("MH").build();
        UserDto userDto = UserDto.builder().name("Integration User").addresses(Collections.singletonList(address)).build();

        // POST: Add user
        ResponseEntity<String> postResponse = restTemplate.postForEntity(getBaseUrl(), userDto, String.class);
        assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());
        assertEquals("User Added Successfully", postResponse.getBody());

        // GET: Fetch all users
        ResponseEntity<UserDto[]> getResponse = restTemplate.getForEntity(getBaseUrl(), UserDto[].class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        assertNotNull(getResponse.getBody());
        assertTrue(getResponse.getBody().length >= 1);
    }

    @Test
    public void testUpdateUser() {
        // First, create a user
        AddressDto address = AddressDto.builder().city("Test City").state("Test State").build();
        UserDto userDto = UserDto.builder().name("Before Update").addresses(Collections.singletonList(address)).build();

        ResponseEntity<String> response = restTemplate.postForEntity(getBaseUrl(), userDto, String.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Get user ID (assuming first one)
        ResponseEntity<UserDto[]> allUsers = restTemplate.getForEntity(getBaseUrl(), UserDto[].class);
        int id = Objects.requireNonNull(allUsers.getBody())[0].getId();

        // Update user
        UserDto updatedUser = UserDto.builder().name("After Update")
                .addresses(Collections.singletonList(AddressDto.builder().city("New City").state("New State").build()))
                .build();

        HttpEntity<UserDto> requestEntity = new HttpEntity<>(updatedUser);
        ResponseEntity<UserDto> updateResponse = restTemplate.exchange(getBaseUrl() + "/" + id, HttpMethod.PUT, requestEntity, UserDto.class);

        assertEquals("After Update", Objects.requireNonNull(updateResponse.getBody()).getName());
    }

    @Test
    public void testDeleteUser() {
        AddressDto address = AddressDto.builder().city("ToDelete").state("TS").build();
        UserDto userDto = UserDto.builder().name("ToDelete").addresses(Collections.singletonList(address)).build();

        ResponseEntity<String> response = restTemplate.postForEntity(getBaseUrl(), userDto, String.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        ResponseEntity<UserDto[]> users = restTemplate.getForEntity(getBaseUrl(), UserDto[].class);
        int id = Objects.requireNonNull(users.getBody())[0].getId();

        restTemplate.delete(getBaseUrl() + "/" + id);

        ResponseEntity<UserDto> deletedUser = restTemplate.getForEntity(getBaseUrl() + "/" + id, UserDto.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, deletedUser.getStatusCode()); // because UserNotFoundException
    }
}
