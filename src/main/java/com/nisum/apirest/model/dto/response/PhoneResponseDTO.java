package com.nisum.apirest.model.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhoneResponseDTO {
    private String number;
    private String cityCode;
    private String contryCode;
}
