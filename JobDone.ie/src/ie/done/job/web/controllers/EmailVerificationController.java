package ie.done.job.web.controllers;

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

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EmailVerificationController {
	
	
	private UsersService usersService;
	
	private MessageService messageService;
	
	@Autowired
	private JavaMailSender javaMailSender;

	public void setUsersService(UsersService usersService) {
		this.usersService = usersService;
	}
	
	

}
