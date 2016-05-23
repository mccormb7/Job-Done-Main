package ie.done.job.web.controllers;

import ie.done.job.web.dao.EmailVerification;
import ie.done.job.web.dao.FormValidationGroup;
import ie.done.job.web.dao.JobPost;
import ie.done.job.web.dao.Message;
import ie.done.job.web.dao.Provider;
import ie.done.job.web.dao.User;
import ie.done.job.web.web.service.MessageService;
import ie.done.job.web.web.service.UsersService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

@Controller
public class LoginController {
	
	
	private UsersService usersService;
	private MessageService messageService;
	
	@Autowired
	private MailSender mailSender;
	//bean autowired from jobpost-servlet
	
	@Autowired
	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
	}

	
	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}


	@RequestMapping("/login")
	public String showLogin() {
		return "login";
	}
	@RequestMapping("/emailform")
	public String showEmailForm() {
		return "emailform";
	}
	
	
	
	@RequestMapping("/settings")
	public String showSetiings() {
		return "settings";
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
	
	@RequestMapping("/mailsent")
	public String showMailSent() {
		return "mailsent";
	}
	
	/*****************************Registration verification***********************************/
	
	@RequestMapping("/newaccount")
	public String showNewAccount(Model model) {
		
		model.addAttribute("user", new User());
		return "newaccount";
	}
	

	
	@RequestMapping(value="/createaccount", method=RequestMethod.POST)
	public String createAccount(@Validated(FormValidationGroup.class) User user, BindingResult result, HttpServletRequest request) {
		
		if(result.hasErrors()) {
			return "newaccount";
		}
		
		
		//user.setAuthority("ROLE_USER");
		user.setEnabled(false);
		
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
			result.rejectValue("username", "DuplicateKey.user.username");
			return "newaccount";
		}
		
		
		/*Email Comfirmation*/
		
		// the users emaill address
		String email = user.getUsername();
		
		//final User userRegistered = usersService.getUser(email);
		
		// ******************************************************************************
				//                         send email from controller
				
		String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		String randomToken = UUID.randomUUID().toString();
		usersService.createEmailVerificationToken(user, randomToken);
		
		String userEmail = user.getEmail();
		String subject = "Registration Confirmation";
		String confirmationUrl = appUrl + "/confirmedregister?token=" + randomToken;
		String message = "Account has been registed, To start using Task Tackler, confirm your email address";
		SimpleMailMessage mailReceiver = new SimpleMailMessage();
		
		mailReceiver.setFrom("tasktacklerire@gmail.com");
		mailReceiver.setTo(userEmail);
		mailReceiver.setSubject(subject);
		mailReceiver.setText(message + " \r\n" + confirmationUrl);
		mailSender.send(mailReceiver);
		
		
		//return "accountcreated";
		
		return "accountsent";
	}
	
	@RequestMapping("/accountsent")
	public String showAccountSent() {
		return "accountsent";
	}
	
	@RequestMapping("/confirmationfailed")
	public String showConfirmationFailure() {
		return "confirmationfailed";
	}
	
	
	@RequestMapping(value="/confirmedregister", method=RequestMethod.GET)
	public String confirmComplete(WebRequest request, Model model, @RequestParam("token") String token){
		
		EmailVerification emailVerification = usersService.getToken(token);
		System.out.println(emailVerification.toString());
		
		if(emailVerification == null){
			return "searchnull";
		}
		System.out.println(" test 2");
		User user = emailVerification.getUser();
		System.out.println(user.toString());
		
	/*	Calendar cal = Calendar.getInstance();
		System.out.println(verificationToken.getExpiryDate().getTime()- cal.getTime().getTime());
		if((verificationToken.getExpiryDate().getTime()- cal.getTime().getTime()) <= 0){
			return "confirmfailed";
		}
		*/
		usersService.enabled(user);
		System.out.println("after " + user.toString());
		
		
		
		return "confirmedregister";
	}
	
	
	
	/************************************************************message related *******************************/
	
	
	@RequestMapping("/deletemessage/{id}")
    public String removeJobPost(@PathVariable("id") int id){
		usersService.delete(id);
        
        return "redirect:/messageinbox";
    }
	
	@RequestMapping("/messageinbox")
	public String showMessageInbox(Model model, Principal principal) throws Exception {

		//List<Offer> offers = offersService.getCurrent();
		
		List<Message> messages = null;
				
		
		if(principal == null){
			messages = new ArrayList<Message>();
		}
		else{
			String username = principal.getName();
			messages = usersService.getMessages(username);
		}
		
		model.addAttribute("messages1", messages);

		return "messageinbox";
	}
	
	
	
	@RequestMapping(value = "/getmessages", method=RequestMethod.GET, produces = "application/json")
	@ResponseBody//causes spring to look at data returned and try to return it in the appropriate format from "produces"
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
	
	
	
	@RequestMapping(value="/sendmail",method = RequestMethod.POST)
    public String doSendMail(HttpServletRequest request, Principal principal) {
        // takes input from e-mail form
		System.out.println(" in mails");
		User user = null;
		
		if(principal!=null){
			String username = principal.getName();
			user = usersService.getUser(username);
		}
        String recipientAddress = request.getParameter("recipient");
        String subject = request.getParameter("subject");
        String message = request.getParameter("message");
         System.out.println(user.toString());
         
        // prints debug info
        System.out.println("To: " + recipientAddress);
        System.out.println("Subject: " + subject);
        System.out.println("Message: " + message);
         
        // creates a simple e-mail object
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("tasktacklerire@gmail.com");
        email.setTo(recipientAddress);
        email.setSubject("From user " + user.getEmail() + " "+ subject);
        email.setText("This message has been sent on Task Tackler from " + user.getName() + "\n"  + message);
         
        // sends the e-mail
        mailSender.send(email);
         
        // forwards to the view named "Result"
        return "mailsent";
    }
	
	@RequestMapping(value="/sendmessage", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public Map<String, Object> sendMessage(Principal principal, @RequestBody Map<String, Object> data) {
		

		Integer target = (Integer)data.get("target");
		
		Map<String, Object> rval = new HashMap<String, Object>();
		rval.put("success", true);
		rval.put("target", target);
		
		return rval;
	}
}
