package br.com.sime.api.config.swagger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SwaggerRedirectController {

    @GetMapping("/doc")
    public String redirectToCustomSwagger() {
        return "redirect:/custom-swagger-ui/index.html"; // Caminho do index.html customizado
    }
}
