package com.smart.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.domain.JpaSort.Path;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smart.dao.ContactRepository;
import com.smart.dao.UserRepository;
import com.smart.entities.Contact;
import com.smart.entities.User;
import com.smart.helper.Message;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ContactRepository contactRepository;
	@ModelAttribute
	public void addCommanData(Model model, Principal principal) {
		String userName=principal.getName();
		System.out.println("username: "+userName);
		User user=userRepository.getUserByUserName(userName);
		System.out.println("User: "+user);
		model.addAttribute("user",user);
	}
	//
@RequestMapping("/index")
public String deshboard(Model model,Principal principal) {
	model.addAttribute("title","User DeshBoard");
	return "normal/user_deshboard";
}
//open add form handler
@GetMapping("/add-contact")
public String openAddContactForm(Model model) {
	model.addAttribute("title","Add Contact");
	model.addAttribute("contact", new Contact());
	return "normal/add_contact_form";
}
//processing add contact form
@RequestMapping("/process-contant")
public String processContact(@ModelAttribute Contact contact, Principal principal ,HttpSession session) {
	
	try {
	String name=principal.getName();
	User user=this.userRepository.getUserByUserName(name);
	// processing and uploading file 
//	if(file.isEmpty()) {
//		System.out.println("file empty");
//	}else {
//		contact.setImage(file.getOriginalFilename());
//		
//		File saveFile=new ClassPathResource("static/img").getFile();
//		Path path=(Path)Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
//		
//		
//		System.out.println("update the file");
//		
//	}
	user.getContacts().add(contact);
	contact.setUser(user);
	this.userRepository.save(user);
	System.out.println("Data"+contact);
	System.out.println("Added to data base");
	//success message
	session.setAttribute("message", new Message("Your contact is added!! add more..","success"));
	
	
}catch(Exception e) {
		System.out.println("Errpr: "+e);
		e.printStackTrace();
		session.setAttribute("message", new Message("something went wrong!!","danger"));
	}
	return "normal/add_contact_form";
}
@GetMapping("/show-contacts")
public String showContacts(Model m, Principal principal) {
	m.addAttribute("title","Show User Contacts");
	//contacts list
	String userName=principal.getName();
	User user=this.userRepository.getUserByUserName(userName);
	List<Contact> contacts= this.contactRepository.findContactsByUser(user.getId());
	m.addAttribute("contacts",contacts);
	 
	
	return "normal/show_contacts";
}
//showing particulat contct  details 
@RequestMapping("/{id}/contact")
public String showContactDetail(@PathVariable("id") Integer id,Model model) {
	System.out.println(id);

	Optional<Contact>  contactOptional= this.contactRepository.findById(id);
	Contact contact=contactOptional.get();
	model.addAttribute("contact",contact);
	return "normal/contact_detail";
}
//delete contact handler
@GetMapping("/delete/{id}")
public String deleteContact(@PathVariable("id")Integer id,Model model,HttpSession session) {
	Optional<Contact> contactOptional=this.contactRepository.findById(id);
	Contact contact=this.contactRepository.findById(id).get(); 
	contact.setUser(null);
	//check assignment
	this.contactRepository.delete(contact);
	return "redirect:/user/show-contacts/";
}
//open update form handler
@PostMapping("/update-contact/{id}")
public String updateForm(@PathVariable("id")Integer id, Model m) {
	m.addAttribute("title","Update form");
	Contact contact= this.contactRepository.findById(id).get();
	m.addAttribute("contact",contact);
	return "normal/update_form";
}
//update form
@RequestMapping(value = "/process-update", method =RequestMethod.GET)
 public String updateHandler(@ModelAttribute Contact contact,Model m,HttpSession session,Principal principal) {
	
	try {
		User user= this.userRepository.getUserByUserName(principal.getName());
		contact.setUser(user);
		this.contactRepository.save(contact);
		session.setAttribute("message",new Message("Your contact is updated","success"));
	} catch (Exception e) {
		// TODO: handle exception
	}
	System.out.println("contact name: "+contact.getName());
	System.out.println("contact name: "+contact.getId());

	 return "redirect:/user/"+contact.getId()+"/contact";
 }

}




