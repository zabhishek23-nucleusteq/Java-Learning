package Day5.ProductApplication.src.main.java.com.example.ProductApplication.DTO;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private int id;
    private String name;
    private List<AddressDto> addresses;
}
