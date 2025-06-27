package best.fufushop.configulation;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("My fufushop API")
                        .version("1.0.0")
                        .description("API documentation for My Project")
                        .contact(new Contact()
                                .name("Panupong Sornwiriya(Best)")
                                .email("borabestz42@gmail.com")
                                .url("fufushop.best")));
    }
}

