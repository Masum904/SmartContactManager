package com.smart.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.smart.dao.UserRepository;
import com.smart.entities.User;
import com.smart.helper.Message;

@Controller
public class HomeController {
@Autowired
private BCryptPasswordEncoder passworidEncoder;

@Autowired
private UserRepository userRepository;	
@GetMapping("/test")
@ResponseBody
public String test() {
	User user=new User();
	user.setName("Masum Billal");
	user.setEmail("billalmasum342@gmail.com");
	userRepository.save(user);
	return "working";
}
@RequestMapping("/")
public String home(Model model) {
	model.addAttribute("title","Home - Smart Contact Manager");
	return "home";
	
}
@RequestMapping("/about")
public String about(Model model) {
	model.addAttribute("title","About - Smart Contact Manager");
	return "about";
	
}
@RequestMapping("/signup")
public String signup(Model model) {
	model.addAttribute("title","Register - Smart Contact Manager");
	model.addAttribute("user",new User());
	return "signup";
	
}
//handler for register user
@RequestMapping(value="/do_register", method=RequestMethod.POST)
public String registerUser(@Valid @ModelAttribute("user") User user,BindingResult result1,@RequestParam(value="agreement",defaultValue="false") boolean agreement,Model model,HttpSession session) {
	
	
	
	try {
		if(!agreement) {
			System.out.println("You have not agreed the trams and coditions");
			throw new Exception("You have not agreed the trams and condition");
		}
		if(result1.hasErrors()) {
			System.out.println("Error"+ result1.toString());
			model.addAttribute("user",user);
			return "signup";
		}
		user.setRole("ROLE_USER");
		user.setEnabled(true);
		user.setImageUrl("dafault.png");
		user.setPassword(passworidEncoder.encode(user.getPassword()));
		User result=this.userRepository.save(user);
		System.out.println("USER "+user.toString());
		System.out.println("Agreement "+agreement);
		model.addAttribute("user",new User());
		session.setAttribute("message",new Message("Successfully Registered!!","alert-success"));
		return "signup";
		
	} catch (Exception e) {
		e.printStackTrace();
		model.addAttribute("user", user);
		session.setAttribute("message",new Message("something went wrong!!"+e.getMessage(),"alert-danger"));
		return "signup";
	}
	
}
@RequestMapping("/signin")
public String customLogin(Model model) {
	model.addAttribute("title","Login Page");
	return "login";
	
}

}
