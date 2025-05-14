package Day5.ProductApplication.src.main.java.com.example.ProductApplication.Service;

import com.example.ProductApplication.DTO.UserDto;

import java.util.List;

public interface IUserService {
    public String addUser(UserDto userDto);
    public List<UserDto> getAllUsers();
    public UserDto getUserById(int id);
    public UserDto updateUser(int id , UserDto userDto);
    public String deleteUser(int id);
}
