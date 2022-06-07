package az.edu.arti.projct.zero.hackersuitcase.controller;

import az.edu.arti.projct.zero.hackersuitcase.dao.entity.User;
import az.edu.arti.projct.zero.hackersuitcase.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

	private final RegistrationService registrationService;
	
	@GetMapping("")
	public String viewHomePage() {
		return "index";
	}
	
	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());

		return "signup_form";
	}
	
	@PostMapping("/process_register")
	public String processRegister(User user) {
		registrationService.register(user);
		return "register_success";
	}
}
