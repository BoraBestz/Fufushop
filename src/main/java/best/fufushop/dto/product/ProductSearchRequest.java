package best.fufushop.dto.product;

import lombok.Data;

@Data
public class ProductSearchRequest {
    private String keyword;
    private Integer pageNumber;
    private Integer pageSize;
    private String sortBy;
    private String sortDirection;

}
