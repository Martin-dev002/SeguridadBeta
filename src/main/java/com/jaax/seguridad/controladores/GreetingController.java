package com.jaax.seguridad.controladores;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/greeting")
public class GreetingController {

    @GetMapping("/sayhelloPublic")
    public String sayHello(){
        return "Hello form API";
    }

    @GetMapping("/sayhelloProtected")
    public String sayHelloProtected(){
        return "Hello form API protected";
    }
}
