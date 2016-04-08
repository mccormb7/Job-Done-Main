package ie.done.job.web.web.service;

import ie.done.job.web.dao.EmailVerification;
import ie.done.job.web.dao.EmailVerificationDao;
import ie.done.job.web.dao.Message;
import ie.done.job.web.dao.MessagesDao;
import ie.done.job.web.dao.User;
import ie.done.job.web.dao.UsersDao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

@Service("usersService")
public class UsersService {
	
	@Autowired
	private UsersDao usersDao;
	
	@Autowired
	private EmailVerificationDao emailVerificationDao;
	
	@Autowired
	private MessagesDao messagesDao;
	
	public void enabled(User user){
		usersDao.enabled(user);
	}
	
	public void createEmailVerificationToken(User user, String token) {
		EmailVerification emailToken  = new EmailVerification(token, user);
		emailVerificationDao.create(emailToken);
		//System.out.println("Token created ********" + myToken);
	}
	
	public EmailVerification getToken(String token) {
		return emailVerificationDao.getToken(token);
	}
	
	public void create(User user) {
		usersDao.create(user);
	}


	public boolean exists(String username) {
		return usersDao.exists(username);
	}
	
	public boolean existsEmail(String email) {
		return usersDao.existsEmail(email);
	}

	//wrapper for the DAo methods
	@Secured("ROLE_ADMIN")
	public List<User> getAllUsers() {
		return usersDao.getAllUsers();
	}
	
	public void sendMessage(Message message) {
		messagesDao.saveOrUpdate(message);
	}

	public void sendMessageEmail(String email){
		
	}
	
	public User getUser(String username){
		return usersDao.getUser(username);
	}
	
	public User getUserEmailOwner(String email){
		return usersDao.getUserEmailOwner(email);
	}


	public List<Message> getMessages(String username) {
		
		return messagesDao.getMessages(username);
	}
	
	public void delete(int id) {
		//deletes from DB
		messagesDao.delete(id);
	
	}

	
	


}
