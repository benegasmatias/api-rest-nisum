package com.nisum.apirest.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

@NoArgsConstructor
public class Response<T> {
    private T data;
    private String message;


    public Response(T data, String message) {
        this.data = data;
        this.message = message;
    }
}