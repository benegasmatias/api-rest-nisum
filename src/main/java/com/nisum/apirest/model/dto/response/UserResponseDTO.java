package com.nisum.apirest.model.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserResponseDTO {
    private String name;
    private String email;
    private String password;
    private List<PhoneResponseDTO> phones;
}
