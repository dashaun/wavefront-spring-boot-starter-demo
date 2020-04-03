package io.codeinthecloud.wavefrontbootiful;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloWorldController {

    @Value("${wavefront.url}")
    private String url;

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("url", url);

        return "helloworld";
    }

}