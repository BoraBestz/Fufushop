package best.fufushop.controller;

import best.fufushop.dto.ApiResponse;
import best.fufushop.dto.ChangePasswordRequest;
import best.fufushop.dto.LoginRequest;
import best.fufushop.dto.RegisterRequest;
import best.fufushop.model.Product;
import best.fufushop.model.User;
import best.fufushop.service.ProductService;
import best.fufushop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest request) {
        ApiResponse<User> response = new ApiResponse<>();
        try {
           User user = userService.authenticate(request);
            response.setStatus("success");
            response.setMessage("ล็อกอินสำเร็จ");
            response.setData(user);
            return ResponseEntity.ok(response);
       } catch (UsernameNotFoundException e) {
            response.setStatus("error");
            response.setMessage(e.getMessage());
            response.setData(null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
       } catch (BadCredentialsException e) {
            response.setStatus("error");
            response.setMessage("รหัสผ่านไม่ถูกต้อง");
            response.setData(null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
       } catch (Exception e) {
            response.setStatus("error");
            response.setMessage("เกิดข้อผิดพลาดในการล็อกอิน");
            response.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
       }
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> register(@RequestBody RegisterRequest request) {
        User registeredUser = userService.register(request);
        ApiResponse<User> response = new ApiResponse<>();

        if (registeredUser == null) {
            response.setStatus("error");
            response.setMessage("การลงทะเบียนล้มเหลว");
            response.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        response.setStatus("success");
        response.setMessage("ลงทะเบียนสำเร็จ");
        response.setData(registeredUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/change-password")
    public ResponseEntity<ApiResponse<String>> changePassword(@RequestBody ChangePasswordRequest request) {
        ApiResponse<String> response = new ApiResponse<>();
        try {
            User user = userService.changePassword(request);
            response.setStatus("success");
            response.setMessage("เปลี่ยนรหัสผ่านสำเร็จ");
            response.setData(user.getUsername());
            return ResponseEntity.ok(response);
        } catch (UsernameNotFoundException | BadCredentialsException e) {
            response.setStatus("error");
            response.setMessage(e.getMessage());
            response.setData(null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } catch (Exception e) {
            response.setStatus("error");
            response.setMessage("เกิดข้อผิดพลาดในการเปลี่ยนรหัสผ่าน");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }
} 