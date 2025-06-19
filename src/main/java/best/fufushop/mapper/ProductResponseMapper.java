package best.fufushop.mapper;

import best.fufushop.dto.product.ProductResponse;
import best.fufushop.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductResponseMapper {
    //@Mapping(target = "id", source = "product.id") ใช้สำหรับกำหนดการแมพปิ้งระหว่างฟิลด์ที่มีชื่อไม่ตรงกัน
    //@Mapping(target = "productAny", ignore = true) // ใช้สำหรับข้ามฟิลด์ที่ไม่ต้องการแมพ
    //@Mapping(target = "productName", expression = "java(\"TH\")" // ใช้สำหรับการเซ็ตค่าแบบกำหนดเอง
    ProductResponse toProductResponse(Product product);
}
