package cl.alkewallet.alkewalletm6v2.controller;


import cl.alkewallet.alkewalletm6v2.model.User;
import cl.alkewallet.alkewalletm6v2.model.Transaction;
import cl.alkewallet.alkewalletm6v2.service.TransactionService;
import cl.alkewallet.alkewalletm6v2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public String showDashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User user = userService.findByUsername(userDetails.getUsername()).orElseThrow();
        List<Transaction> transactions = transactionService.findByUserId(user.getId());
        model.addAttribute("user", user);
        model.addAttribute("transactions", transactions);
        return "dashboard";
    }

    @PostMapping("/deposit")
    public String deposit(@AuthenticationPrincipal UserDetails userDetails, @RequestParam Double amount, Model model) {
        if (amount == null || amount <= 0) {
            model.addAttribute("error", "Invalid amount");
            return showDashboard(userDetails, model);
        }

        User user = userService.findByUsername(userDetails.getUsername()).orElseThrow();
        userService.updateBalance(user, amount);

        Transaction transaction = new Transaction();
        transaction.setType("DEPOSIT");
        transaction.setAmount(amount);
        transactionService.saveTransaction(transaction, user);

        return "redirect:/dashboard";
    }

    @PostMapping("/withdraw")
    public String withdraw(@AuthenticationPrincipal UserDetails userDetails, @RequestParam Double amount, Model model) {
        if (amount == null || amount <= 0) {
            model.addAttribute("error", "Invalid amount");
            return showDashboard(userDetails, model);
        }

        User user = userService.findByUsername(userDetails.getUsername()).orElseThrow();
        userService.updateBalance(user, -amount);

        Transaction transaction = new Transaction();
        transaction.setType("WITHDRAW");
        transaction.setAmount(amount);
        transactionService.saveTransaction(transaction, user);

        return "redirect:/dashboard";
    }

    @PostMapping("/transfer")
    public String transfer(@AuthenticationPrincipal UserDetails userDetails, @RequestParam String recipientUsername, @RequestParam Double amount, Model model) {
        if (amount == null || amount <= 0) {
            model.addAttribute("error", "Invalid amount");
            return showDashboard(userDetails, model);
        }

        User sender = userService.findByUsername(userDetails.getUsername()).orElseThrow();
        Optional<User> recipientOpt = userService.findByUsername(recipientUsername);

        if (recipientOpt.isEmpty()) {
            model.addAttribute("error", "Recipient not found");
            return showDashboard(userDetails, model);
        }

        User recipient = recipientOpt.get();

        userService.updateBalance(sender, -amount);
        userService.updateBalance(recipient, amount);

        Transaction transaction = new Transaction();
        transaction.setType("TRANSFER");
        transaction.setAmount(amount);
        transactionService.saveTransaction(transaction, sender);

        transaction = new Transaction();
        transaction.setType("TRANSFER");
        transaction.setAmount(amount);
        transactionService.saveTransaction(transaction, recipient);

        return "redirect:/dashboard";
    }
}