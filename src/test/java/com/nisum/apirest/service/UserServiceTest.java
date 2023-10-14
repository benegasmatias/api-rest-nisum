package com.nisum.apirest.service;

import com.nisum.apirest.model.dto.request.UserRequestDTO;
import com.nisum.apirest.model.dto.response.AuthResponseDTO;
import com.nisum.apirest.model.entity.User;
import com.nisum.apirest.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateUser() {
        // Datos de prueba
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setName("John Doe");
        userRequestDTO.setEmail("john.doe@example.com");
        userRequestDTO.setPassword("password123");

        User userToSave = new User();
        userToSave.setId(1L);
        userToSave.setFirstName(userRequestDTO.getName());
        userToSave.setEmail(userRequestDTO.getEmail());
        userToSave.setPassword("hashedPassword"); // Contraseña ya hasheada
        userToSave.setIsactive(true);
        userToSave.setToken("token123");

        // Configuración de comportamiento de Mockito
        Mockito.when(passwordEncoder.encode(any())).thenReturn("hashedPassword"); // Simular la contraseña hasheada
        Mockito.when(userRepository.save(any(User.class))).thenReturn(userToSave);

        // Llamada al método que se está probando
        AuthResponseDTO createdUser = userService.createUser(userRequestDTO);

        // Verificar que el usuario de respuesta se creó correctamente
        assertEquals(1L, createdUser.getId());
        assertEquals(userRequestDTO.getName(), createdUser.getUser().getName());
        assertEquals(userRequestDTO.getEmail(), createdUser.getUser().getEmail());
        assertEquals("token123", createdUser.getToken());
        // Otras verificaciones...
    }
}
