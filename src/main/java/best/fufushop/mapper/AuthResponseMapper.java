package best.fufushop.mapper;

import best.fufushop.dto.auth.AuthResponse;
import best.fufushop.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthResponseMapper {

    AuthResponse toAuthResponse(User user);
}
