package cl.alkewallet.alkewalletm6v2.controller;

import cl.alkewallet.alkewalletm6v2.model.User;
import cl.alkewallet.alkewalletm6v2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MenuController {

    @Autowired
    private UserService userService;

    @GetMapping("/menu")
    public String showMenu(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByUsername(userDetails.getUsername()).orElseThrow();
        model.addAttribute("balance", user.getBalance());
        return "menu";
    }
}

