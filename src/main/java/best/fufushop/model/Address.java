package best.fufushop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "address")
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    
    @Id
    private Long userId;

    private String houseNo;

    private String subDistrict;

    private String district;

    private String city;

    private String postCode;
} 