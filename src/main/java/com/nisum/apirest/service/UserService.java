package com.nisum.apirest.service;

import com.nisum.apirest.exception.AuthenticationFailedException;
import com.nisum.apirest.exception.UserNotFoundException;
import com.nisum.apirest.model.dto.request.UserRequestDTO;
import com.nisum.apirest.model.dto.response.UserResponseDTO;
import com.nisum.apirest.model.entity.User;
import com.nisum.apirest.model.dto.response.AuthResponseDTO;
import com.nisum.apirest.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PhoneService phoneService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserService() {
    }

    @Transactional
    public AuthResponseDTO createUser(UserRequestDTO userRequestDTO){

        User createUser =  User.builder()
                .email(userRequestDTO.getEmail())
                .firstName(userRequestDTO.getName())
                .password(passwordEncoder.encode(userRequestDTO.getPassword()))
                .isactive(true)
                .token(generateToken()).build();
        createUser = userRepository.save(createUser);

        phoneService.savePhonesForUser(userRequestDTO.getPhones(),createUser);

        AuthResponseDTO userResponseDTO = new AuthResponseDTO();
        userResponseDTO.setId(createUser.getId());
        userResponseDTO.setCreated(createUser.getCreated());
        userResponseDTO.setModified(createUser.getModified());
        userResponseDTO.setToken(createUser.getToken());
        userResponseDTO.setActive(createUser.getIsactive());
        userResponseDTO.setUser(convertToUserResponseDTO(createUser));
        return userResponseDTO;
    }

    private UserResponseDTO convertToUserResponseDTO(User user) {
        UserResponseDTO userResponseDTO= new UserResponseDTO();
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setName(user.getFirstName());
        userResponseDTO.setPhones(phoneService.findPhonesByUserId(user.getId()));
        return userResponseDTO;
    }
    @Transactional
    public AuthResponseDTO loginUser(String email, String password) {
        // Buscar el usuario por dirección de correo electrónico
        User user = userRepository.findByEmail(email).orElseThrow(() ->  new UserNotFoundException("Usuario no encontrado"));




        // Verificar la contraseña
        if (!passwordEncoder.matches(password, user.getPassword())) {
            // La contraseña no coincide, maneja el error
            throw new AuthenticationFailedException("Autenticación fallida");
        }

        // Si la autenticación es exitosa, puedes generar un token
        String token = generateToken();

        // Crear un objeto UserResponseDTO con la información del usuario
        AuthResponseDTO userResponseDTO = new AuthResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setCreated(user.getCreated());
        userResponseDTO.setModified(user.getModified());
        userResponseDTO.setToken(token);
        userResponseDTO.setActive(user.getIsactive());

        return userResponseDTO;
    }

    public static String generateToken() {
        // Genera un token UUID
        return UUID.randomUUID().toString();
    }
    public User getUserById(Long id){
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.get();
    }
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    public boolean isEmailRegistered(String email) {
        return userRepository.existsByEmail(email);
    }
}
