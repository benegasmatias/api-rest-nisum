package com.nisum.apirest.controller;

import com.nisum.apirest.exception.AuthenticationFailedException;
import com.nisum.apirest.exception.UserNotFoundException;
import com.nisum.apirest.model.dto.request.LoginRequestDTO;
import com.nisum.apirest.model.dto.response.AuthResponseDTO;
import com.nisum.apirest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
        try {
            // Llama al servicio para autenticar al usuario
            AuthResponseDTO authenticatedUser = userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());

            // Si la autenticación es exitosa, devuelve el usuario autenticado y un código 200 (OK)
            return new ResponseEntity<>(authenticatedUser, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            // Maneja el caso en el que el usuario no se encuentra y devuelve un código 404 (NOT FOUND)
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        } catch (AuthenticationFailedException e) {
            // Maneja el caso en el que la autenticación falla y devuelve un código 401 (UNAUTHORIZED)
            return new ResponseEntity<>("Autenticación fallida", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            // Maneja otros errores y devuelve un código 500 (INTERNAL SERVER ERROR)
            return new ResponseEntity<>("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
