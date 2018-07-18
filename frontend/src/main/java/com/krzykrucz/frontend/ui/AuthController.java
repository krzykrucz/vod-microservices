package com.krzykrucz.frontend.ui;

import com.krzykrucz.frontend.infrastructure.ApiEndpoints;
import com.krzykrucz.frontend.infrastructure.ApiGatewayClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final ApiGatewayClient client;

    public AuthController(ApiGatewayClient client) {
        this.client = client;
    }

    @GetMapping("/login")
    String getLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    String login(@ModelAttribute LoginForm loginForm, Model model, RedirectAttributes redirectAttributes) {
        final ResponseEntity<Void> response = client.post(ApiEndpoints.AUTH_USER, loginForm);
        if (response.getStatusCode().equals(HttpStatus.OK)) {
            redirectAttributes.addFlashAttribute("username", loginForm.getUserIdentifier());
            return "redirect:videos";
        }
        model.addAttribute("loginError", true);
        return "login";
    }

}
