package best.fufushop.dto;

import best.fufushop.enums.Status;
import best.fufushop.model.Product;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private Status status;
    private T data;
    private String message;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp = LocalDateTime.now();

    public ApiResponse(Status status, List<Product> products, String message) {
        this.status = status;
        this.data = (T) products;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }


}

