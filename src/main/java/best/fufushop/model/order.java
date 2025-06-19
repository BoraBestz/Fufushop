package best.fufushop.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "address")
@NoArgsConstructor
@AllArgsConstructor
public class order {
    
    @Id
    private Long orderId;

    private Long userId;

    private BigDecimal totalPrice;

    private String orderStatus;

    private LocalDateTime orderDate;

}