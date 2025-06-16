package best.fufushop.service;

import best.fufushop.dto.ChangePasswordRequest;
import best.fufushop.dto.LoginRequest;
import best.fufushop.dto.RegisterRequest;
import best.fufushop.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User authenticate(LoginRequest request);
    User register(RegisterRequest request);
    User changePassword(ChangePasswordRequest request);
    User findById(Long id);
    User update(User user);
    void deleteById(Long id);
}
