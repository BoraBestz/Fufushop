package best.fufushop.controller;

import best.fufushop.dto.ApiResponse;
import best.fufushop.dto.auth.AuthRequest;
import best.fufushop.dto.auth.AuthResponse;
import best.fufushop.dto.auth.ChangePasswordRequest;
import best.fufushop.enums.Status;
import best.fufushop.mapper.AuthResponseMapper;
import best.fufushop.model.User;
import best.fufushop.service.UserService;
import best.fufushop.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthResponseMapper authResponseMapper;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody AuthRequest request) {
        ApiResponse<AuthResponse> response = new ApiResponse<>();
        try {
           User user = userService.authenticate(request);
            String token = jwtUtil.generateToken(user);
           AuthResponse authResponse = authResponseMapper.toAuthResponse(user);
            authResponse.setToken(token);
            response.setStatus(Status.SUCCESS);
            response.setMessage("ล็อกอินสำเร็จ");
            response.setData(authResponse);
            return ResponseEntity.status(HttpStatus.OK).body(response);
       } catch (UsernameNotFoundException e) {
            response.setStatus(Status.ERROR);
            response.setMessage(e.getMessage());
            response.setData(null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
       } catch (BadCredentialsException e) {
            response.setStatus(Status.ERROR);
            response.setMessage("รหัสผ่านไม่ถูกต้อง");
            response.setData(null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
       } catch (Exception e) {
            response.setStatus(Status.ERROR);
            response.setMessage("เกิดข้อผิดพลาดในการล็อกอิน");
            response.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
       }
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@RequestBody AuthRequest request) {
        try {
            User registeredUser = userService.register(request);
            AuthResponse authResponse = authResponseMapper.toAuthResponse(registeredUser);
            ApiResponse<AuthResponse> response = new ApiResponse<>();
            response.setData(authResponse);
            response.setMessage("ลงทะเบียนสำเร็จ");
            response.setStatus(Status.SUCCESS);
            response.setData(authResponse);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }catch (BadCredentialsException e){
            ApiResponse<AuthResponse> response = new ApiResponse<>();
            response.setStatus(Status.ERROR);
            response.setMessage(e.getMessage());
            response.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }catch (Exception e){
            ApiResponse<AuthResponse> response = new ApiResponse<>();
            response.setStatus(Status.ERROR);
            response.setMessage("เกิดข้อผิดพลาดในการลงทะเบียน");
            response.setData(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<ApiResponse<String>> changePassword(@RequestBody ChangePasswordRequest request) {
        ApiResponse<String> response = new ApiResponse<>();
        try {
            User user = userService.changePassword(request);
            response.setStatus(Status.SUCCESS);
            response.setMessage("เปลี่ยนรหัสผ่านสำเร็จ");
            response.setData(user.getUsername());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (UsernameNotFoundException | BadCredentialsException e) {
            response.setStatus(Status.ERROR);
            response.setMessage(e.getMessage());
            response.setData(null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        } catch (Exception e) {
            response.setStatus(Status.ERROR);
            response.setMessage("เกิดข้อผิดพลาดในการเปลี่ยนรหัสผ่าน");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
} 