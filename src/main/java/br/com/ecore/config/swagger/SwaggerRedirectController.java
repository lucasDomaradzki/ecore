package br.com.ecore.config.swagger;

import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@Controller
public class SwaggerRedirectController {

    @ApiOperation(value = "Swagger", hidden = true)
    @RequestMapping(value = "/")
    public String index() {
        return "redirect:swagger-ui.html";
    }

}
