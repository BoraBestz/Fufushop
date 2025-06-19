package best.fufushop.service;

import best.fufushop.dto.auth.AuthRequest;
import best.fufushop.dto.auth.ChangePasswordRequest;
import best.fufushop.model.User;
import best.fufushop.repository.UserRepository;
import best.fufushop.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordUtil passwordUtil;

    @Override
    public User authenticate(AuthRequest request) {
        User user = userRepository.authentication(request.getUsername());
        if (user == null) {
            throw new UsernameNotFoundException("ไม่พบผู้ใช้");
        }
        if (!passwordUtil.comparePassword(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("รหัสผ่านไม่ถูกต้อง");
        }
        return user;
    }

    @Override
    public User changePassword(ChangePasswordRequest request) {
        if (request.getOldPassword().equalsIgnoreCase(request.getNewPassword())){
            throw new BadCredentialsException("รหัสผ่านใหม่ต้องไม่ตรงกับรหัสผ่านเก่า");
        }
        User user = userRepository.authentication(request.getUsername());
        if (user == null) {
            throw new UsernameNotFoundException("ไม่พบผู้ใช้");
        }
        if (!passwordUtil.comparePassword(request.getOldPassword(), user.getPassword())) {
            throw new BadCredentialsException("รหัสผ่านเก่าไม่ถูกต้อง");
        }
        String hashedNewPassword = passwordUtil.hashPasswordWithBCrypt(request.getNewPassword());
        user = userRepository.changePassword(request.getUsername(), hashedNewPassword);
        return user;
    }

    @Override
    public User register (AuthRequest request){
        String hashedPassword = passwordUtil.hashPasswordWithBCrypt(request.getPassword());
        return userRepository.register(request.getUsername(), hashedPassword);
    }

    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

}

