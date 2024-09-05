package spring.course.kata_3_1_2.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import spring.course.kata_3_1_2.models.Person;
import spring.course.kata_3_1_2.userDAO.PersonDAO;

@Controller
@RequestMapping("/admins")
public class AdminController {

    private final PersonDAO personDAO;

    @Autowired
    public AdminController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String getAllUsers(Model model) {
        model.addAttribute("users", personDAO.getAllUsers());
        return "admins/users";
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", personDAO.getUserById(id));
        return "admins/user";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") Person person) {
        return "admins/new";
    }

    @PostMapping("")
    public String addUser(@ModelAttribute("user") Person person) {
        personDAO.addUser(person);
        return "redirect:admins";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", personDAO.getUserById(id));
        return "/admins/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") Person person, @PathVariable("id") int id) {
        personDAO.updateUser(id, person);
        return "redirect:/admins";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personDAO.deleteUser(id);
        return "redirect:/admins";
    }
}
