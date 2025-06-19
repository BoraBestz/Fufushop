package best.fufushop.util;


import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private final String secret = "mySecretKey";
    private final long expirationMs = 86400000; // 1 วัน


}

