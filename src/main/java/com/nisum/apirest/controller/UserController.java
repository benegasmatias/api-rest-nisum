package com.nisum.apirest.controller;

import com.nisum.apirest.exception.UserAlreadyExistsException;
import com.nisum.apirest.exception.UserNotFoundException;
import com.nisum.apirest.exception.ValidationException;
import com.nisum.apirest.model.dto.request.UserRequestDTO;
import com.nisum.apirest.model.entity.User;
import com.nisum.apirest.model.dto.response.Response;
import com.nisum.apirest.model.dto.response.AuthResponseDTO;
import com.nisum.apirest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @PostMapping
    public ResponseEntity<Response<AuthResponseDTO>> createUser(@RequestBody UserRequestDTO userRequestDTO) {
        try {

            validateUserRequest(userRequestDTO);

            AuthResponseDTO createdUser = userService.createUser(userRequestDTO);

            return new ResponseEntity<>(new Response<>(createdUser, "Usuario creado exitosamente"), HttpStatus.CREATED);
        } catch (UserAlreadyExistsException e) {
            return new ResponseEntity<>(new Response<>(null, "El usuario ya existe"), HttpStatus.CONFLICT);

        } catch (ValidationException e) {
            return new ResponseEntity<>(new Response<>(null, e.getMessage()), HttpStatus.CONFLICT);

        } catch (Exception e) {
            return new ResponseEntity<>(new Response<>(null, "Error interno del servidor"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    private void validateUserRequest(UserRequestDTO userRequestDTO) {
        if (!userRequestDTO.isValidPassword()) {
            throw  new ValidationException("Formato de contraseña incorrecto");
        }

        if (!userRequestDTO.isValidEmail()) {
            throw  new ValidationException("Formato de correo incorrecto");
        }

        if (userService.isEmailRegistered(userRequestDTO.getEmail())) {
            throw  new ValidationException("El usuario ya existe");
        }
    }
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Response<User>> searchUserById(@PathVariable("id") Long id) {
        User user = userService.getUserById(id);
        if (user != null) {
            return new ResponseEntity<>(new Response<>(user, "Usuario encontrado"), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new Response<>(null, "Usuario no encontrado"), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<Void>> deleteUserById(@PathVariable("id") Long id) {
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>(new Response<>(null, "Usuario eliminado exitosamente"), HttpStatus.NO_CONTENT);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(new Response<>(null, "Usuario no encontrado"), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(new Response<>(null, "Error interno del servidor"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}