package ie.done.job.web.controllers;

import ie.done.job.web.dao.FormValidationGroup;
import ie.done.job.web.dao.Message;
import ie.done.job.web.dao.User;
import ie.done.job.web.web.service.UsersService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {
	
	
	private UsersService usersService;
	
	@Autowired
	private MailSender mailSender;
	//bean autowired from offer-servlet
	
	@Autowired
	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
	}

	@RequestMapping("/login")
	public String showLogin() {
		return "login";
	}
	
	@RequestMapping("/messages")
	public String showMessages() {
		return "messages";
	}
	
	@RequestMapping("/denied")
	public String showDenied() {
		return "denied";
	}
	
	//redirect user when method security invoked
	@RequestMapping("/admin")
	public String showAdmin(Model model) {
		
		List<User> users = usersService.getAllUsers();
		
		model.addAttribute("users", users);
		
		return "admin";
	}
	
	@RequestMapping("/loggedout")
	public String showLoggedOut() {
		return "loggedout";
	}
	
	
	
	@RequestMapping("/newaccount")
	public String showNewAccount(Model model) {
		
		model.addAttribute("user", new User());
		return "newaccount";
	}
	

	/*@RequestMapping(value="/createaccount", method=RequestMethod.POST)
	public String createAccount(@Validated(FormValidationGroup.class) User user, BindingResult result) {
		
		if(result.hasErrors()) {
			return "newaccount";
		}
		
		user.setAuthority("ROLE_USER");
		user.setEnabled(true);
		
		if(usersService.exists(user.getUsername())) {
			result.rejectValue("username", "DuplicateKey.user.username");
			return "newaccount";
		}
		
		try {
			usersService.create(user);
		} catch (DuplicateKeyException e) {
			result.rejectValue("username", "DuplicateKey.user.username");
			return "newaccount";
		}

		return "accountcreated";
	}*/
	@RequestMapping(value="/createaccount", method=RequestMethod.POST)
	public String createAccount(@Validated(FormValidationGroup.class) User user, BindingResult result) {
		
		if(result.hasErrors()) {
			return "newaccount";
		}
		
		user.setAuthority("ROLE_USER");
		user.setEnabled(true);
		
		if(usersService.exists(user.getUsername())||usersService.existsEmail(user.getEmail())) {
			if(usersService.exists(user.getUsername())){
					result.rejectValue("username", "DuplicateKey.user.username");
					return "newaccount";
			}
			else{
				result.rejectValue("email", "DuplicateKey.user.email");
				return "newaccount";
			}
		}
		
		//checks duplicate usernames & will check duplicate email addresses
		try {
			usersService.create(user);
		} catch (DuplicateKeyException e) {
			result.rejectValue("username", "DuplicateKey.user.username", "This username already exists!");
			return "newaccount";
		}
		
		
		return "accountcreated";
	}
	
	//gets amount of messsages current logged in user has
	
	@RequestMapping(value = "/getmessages", method=RequestMethod.GET, produces = "application/json")
	@ResponseBody//causes spring to look at data return and try to return in approipate format from "produces"
	public Map<String, Object> getMessages(Principal prince){
		
		List<Message> messages = null;
		
		if(prince == null){
			messages = new ArrayList<Message>();
		}
		else{
			String username = prince.getName();
			messages = usersService.getMessages(username);
		}
		//lists of maps (json 12.34)
		Map<String, Object> info = new HashMap<String, Object>();
		info.put("messages", messages);
		info.put("number", messages.size());
		
		return info;
		
	}
	
	/*@RequestMapping(value = "/sendmessage", method=RequestMethod.POST, produces = "application/json")
	@ResponseBody//causes spring to look at data return and try to return in approipate format from "produces"
	public Map<String, Object> sendMessages(Principal prince, @RequestBody Map<String, Object>info){
		
		String text = (String) info.get("text");//taken from messages.jsp
		String name = (String) info.get("name");
		String email = (String) info.get("email");
		Integer target =(Integer)info.get("target");;
		
		Map<String, Object> data = new HashMap<String, Object>();
		System.out.println(name + " " + email + " " + text );
		//gets JSON and put into map and is index of message
		data.put("success", true);
		data.put("target", target);
		
		return data;
		
	}*/
	
	@RequestMapping(value="/sendmessage", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String, Object> sendMessage(Principal principal, @RequestBody Map<String, Object> data) {
		
		
		String text = (String)data.get("text");
		String name = (String)data.get("name");
		String email = (String)data.get("email");
		Integer target = (Integer)data.get("target");
		

		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setFrom("jobdoneire@gmail.com");
		mail.setTo(email);
		mail.setSubject("Re: " + name + ", your message");
		mail.setText(text);
		
		try {
			mailSender.send(mail);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Can't send message");
		}

		Map<String, Object> rval = new HashMap<String, Object>();
		rval.put("success", true);
		rval.put("target", target);
		
		return rval;
	}
}
