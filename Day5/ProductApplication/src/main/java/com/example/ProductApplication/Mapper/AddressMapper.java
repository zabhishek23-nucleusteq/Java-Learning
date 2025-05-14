package Day5.ProductApplication.src.main.java.com.example.ProductApplication.Mapper;

import com.example.ProductApplication.DTO.AddressDto;
import com.example.ProductApplication.Entity.Address;

public class AddressMapper {
    public static AddressDto entityToDto(Address address) {
        AddressDto dto = new AddressDto();
        dto.setId(address.getId());
        dto.setCity(address.getCity());
        dto.setState(address.getState());
        return dto;
    }

    public static Address dtoToEntity(AddressDto dto) {
        Address address = new Address();
        address.setId(dto.getId());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        return address;
    }
}
