package com.example.UserProductApplication.Service;

import com.example.UserProductApplication.DTO.AddressDto;
import com.example.UserProductApplication.DTO.UserDto;
import com.example.UserProductApplication.Entity.Address;
import com.example.UserProductApplication.Entity.User;
import com.example.UserProductApplication.Exception.UserNotFoundException;
import com.example.UserProductApplication.Mapper.UserMapper;
import com.example.UserProductApplication.Repository.UserRepository;
import com.example.UserProductApplication.Service.Impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.*;


import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addTestUser(){
        AddressDto addressDto = AddressDto.builder().city("Pune").state("MH").build();
        UserDto dto = UserDto.
                builder().
                name("Abhishek")
                .addresses(Collections.singletonList(addressDto)).build();
        User user = UserMapper.toEntity(dto);
        when(userRepository.save((any(User.class)))).thenReturn(user);
        String result = userService.addUser(dto);
        assertEquals("User Added Successfully", result);
        verify(userRepository,times(1)).save(any(User.class));
    }

    public void estGetAllUsers(){
        Address address = Address.builder().city("Delhi").state("DL").build();
        User user = User.builder().name("Ram").address(Collections.singletonList(address)).build();
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
        assertEquals(1,userService.getAllUsers().size() );
    }

    @Test
    public void testUpdateUser() {
        Address oldAddress = Address.builder().city("Old City").state("Old State").build();
        User user = User.builder().id(1).name("Chris").address(Collections.singletonList(oldAddress)).build();

        AddressDto newAddressDto = AddressDto.builder().city("New City").state("New State").build();
        UserDto updatedUserDto = UserDto.builder().name("Chris Updated").addresses(Collections.singletonList(newAddressDto)).build();

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDto result = userService.updateUser(1, updatedUserDto);
        assertEquals("Chris Updated", result.getName());
        assertEquals(1, result.getAddresses().size());
        assertEquals("New City", result.getAddresses().get(0).getCity());
    }

    @Test
    public void testDeleteUser() {
        User user = User.builder().id(1).name("Delete Me").build();
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        String result = userService.deleteUser(1);
        assertEquals("User Deleted Successfully", result);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    public void testUserNotFoundThrowsException() {
        when(userRepository.findById(99)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(99));
    }


}
