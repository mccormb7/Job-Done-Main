package ie.done.job.web.web.service;

import ie.done.job.web.dao.JobPost;
import ie.done.job.web.dao.JobPostsDao;
import ie.done.job.web.dao.Message;
import ie.done.job.web.dao.MessagesDao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

@Service("messagesService")
public class MessageService {

	private JobPostsDao JobPostsDao;
	
	

	private MessagesDao messagesDao;

	@Autowired
	public void setMessageDao(MessagesDao messagesDao) {
		this.messagesDao = messagesDao;
	}

	
	public List<Message> getInbox(String username) {
		return messagesDao.getMessages(username);
		
	}
	
	

	
}
