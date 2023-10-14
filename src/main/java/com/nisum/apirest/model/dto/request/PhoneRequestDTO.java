package com.nisum.apirest.model.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhoneRequestDTO {
    private String number;
    private String cityCode;
    private String countryCode;
}
