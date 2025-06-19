package best.fufushop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "address")
@NoArgsConstructor
@AllArgsConstructor
public class orderProducts {
    
    @Id
    private Long orderId;

    private Long productId;

    private Integer numberOfPieces;

    private BigDecimal pricePerPiece;

    private BigDecimal sumPrice;

}