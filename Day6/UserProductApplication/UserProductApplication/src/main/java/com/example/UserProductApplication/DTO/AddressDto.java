package com.example.UserProductApplication.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDto {
    private int id;
    private String city;
    private String state;
}

