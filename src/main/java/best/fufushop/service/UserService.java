package best.fufushop.service;

import best.fufushop.dto.auth.AuthRequest;
import best.fufushop.dto.auth.ChangePasswordRequest;
import best.fufushop.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User authenticate(AuthRequest request);
    User register(AuthRequest request);
    User changePassword(ChangePasswordRequest request);
    User findById(Long id);
    User update(User user);
    void deleteById(Long id);
}
