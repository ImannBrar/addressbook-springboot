package ca.carleton.s4806.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        // if you always want to show book 1:
        return "redirect:/addressbooks/1";
        // or to the API list: return "redirect:/api/addressbooks";
    }
}
