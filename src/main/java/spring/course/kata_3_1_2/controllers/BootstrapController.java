package spring.course.kata_3_1_2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.course.kata_3_1_2.models.Person;
import spring.course.kata_3_1_2.userDAO.PersonDAO;

@Controller
@RequestMapping("/boots")
public class BootstrapController {

    private final PersonDAO personDAO;  // <1>

    @Autowired
    public BootstrapController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping("/index")
    public String showUserInfo(@AuthenticationPrincipal Person person, Model model) {
        model.addAttribute("persons", personDAO.getAllUsers());
        model.addAttribute("person", person);
        return "boots/index";
    }

        @GetMapping("/add")
    public String newUserPage(@AuthenticationPrincipal Person person, Model model) {
        model.addAttribute("person", person);
        return "boots/newPerson";
    }

    @PostMapping("/new")
    public String addUser(@ModelAttribute("user") Person person) {
        personDAO.addUser(person);
        return "redirect:boots/index";
    }

    @PutMapping("/{id}/update")
    public String update(@ModelAttribute("user") Person person, Model model) {
        personDAO.updateUserPerson(person);
        return "redirect:/boots/index";
    }

        @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personDAO.deleteUser(id);
        return "redirect:/boots/index";
    }
}
