package best.fufushop.mapper;

import best.fufushop.dto.product.ProductCreateRequest;
import best.fufushop.dto.product.ProductResponse;
import best.fufushop.dto.product.ProductUpdateRequest;
import best.fufushop.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductRequestMapper {
    Product toProductRequest(ProductCreateRequest product);

    Product updateShop(@MappingTarget Product product, ProductUpdateRequest request);
}
