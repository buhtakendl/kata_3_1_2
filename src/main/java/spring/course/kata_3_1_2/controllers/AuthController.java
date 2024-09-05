package spring.course.kata_3_1_2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.course.kata_3_1_2.models.Person;
import spring.course.kata_3_1_2.services.RegistrarionService;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final RegistrarionService registrarionService;

    @Autowired
    public AuthController(RegistrarionService registrarionService) {
        this.registrarionService = registrarionService;
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("person") Person person) {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("person") Person person) {
        registrarionService.register(person);
        return "redirect:process_login";
    }

}
