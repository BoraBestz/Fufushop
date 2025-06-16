package best.fufushop.repository;

import best.fufushop.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository{
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public User authentication(String username) {
        try {
            String sql = "SELECT * FROM users WHERE username = ? ";
            return jdbcTemplate.queryForObject(sql, new Object[]{username},new BeanPropertyRowMapper<>(User.class)
        );
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }
    @Override
    public User register(String username, String password) {
        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        int result = jdbcTemplate.update(sql, username, password, ("user"));
        if (result > 0) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setRole("USER");
            return user;
        }
        return null;
    }

    @Override
    public User changePassword(String username, String newPassword) {
        try {
            String sql = "UPDATE users SET password = ? WHERE username = ? ";
            jdbcTemplate.update(sql, newPassword, username);

            String selectSql = "SELECT * FROM users WHERE username = ?";
            return jdbcTemplate.queryForObject(
                    selectSql,
                    new Object[]{username},
                    new BeanPropertyRowMapper<>(User.class)
            );

        }catch (EmptyResultDataAccessException e){
            throw new RuntimeException("ไม่พบผู้ใช้ที่ต้องการเปลี่ยนรหัสผ่าน");
        }
    }
}
