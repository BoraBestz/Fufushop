package best.fufushop.repository;

import best.fufushop.model.User;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository  {
    User authentication(String username);
    User register(String username, String password);
    User changePassword(String username, String newPassword);

}
