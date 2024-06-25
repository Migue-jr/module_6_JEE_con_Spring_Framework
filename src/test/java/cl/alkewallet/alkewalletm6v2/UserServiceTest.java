package cl.alkewallet.alkewalletm6v2;


import cl.alkewallet.alkewalletm6v2.model.User;
import cl.alkewallet.alkewalletm6v2.repository.UserRepository;
import cl.alkewallet.alkewalletm6v2.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void testRegisterUser() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("testpass");

        when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedpass");
        when(userRepository.save(user)).thenReturn(user);

        User registeredUser = userService.register(user);

        assertNotNull(registeredUser);
        assertEquals("encodedpass", registeredUser.getPassword());
        verify(userRepository, times(1)).save(user);
    }
}
