package best.fufushop;

import best.fufushop.dto.auth.AuthRequest;
import best.fufushop.model.User;
import best.fufushop.repository.UserRepository;
import best.fufushop.service.UserServiceImpl;
import best.fufushop.util.PasswordUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordUtil passwordUtil;

    @InjectMocks
    private UserServiceImpl userServiceImpl;


    @Test
    void testLoginSuccess() {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername("user1");
        authRequest.setPassword("myPassword123");

        String hashedPassword = passwordUtil.hashPasswordWithBCrypt(authRequest.getPassword());
        User user = new User();
        user.setUsername("user1");
        user.setPassword(hashedPassword);


        when(userRepository.authentication("user1")).thenReturn(user);
        when(passwordUtil.comparePassword("myPassword123", hashedPassword)).thenReturn(true);

        User result = userServiceImpl.authenticate(authRequest);

        assertNotNull(result);
        assertEquals("user1", result.getUsername());
    }


}
