package com.proyectoprogra3.proyectoprogra3;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/intro")

public class DocumentationController {
    @GetMapping()
    public String index() {
        return "index";
    }
}
