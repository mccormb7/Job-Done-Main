package ie.done.job.web.test.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import ie.done.job.web.dao.Message;
import ie.done.job.web.dao.MessagesDao;

import ie.done.job.web.dao.User;
import ie.done.job.web.dao.UsersDao;

import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ActiveProfiles("dev")
@ContextConfiguration(locations = {
		"classpath:ie/done/job/web/web/configurations/dao-context.xml",
		"classpath:ie/done/job/web/web/configurations/security-context.xml",
		"classpath:ie/done/job/web/test/config/datasource.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class MessageDaoTests {

	//@Autowired
	//private OffersDao offersDao;

	@Autowired
	private UsersDao usersDao;
	
	@Autowired
	private MessagesDao messagesDao;

	@Autowired
	private DataSource dataSource;

	private User user1 = new User("johnwpurcell", "John Purcell", "hellothere",
			"john@caveofprogramming.com", true, "ROLE_USER");
	private User user4 = new User("richardhannay", "Richard Hannay",
			"the39steps", "richard@caveofprogramming.com", true, "ROLE_ADMIN");
	private User user2 = new User("blyghtest1", "Blygh McCormack", "password",
			"blygh@hotmail.com", true, "ROLE_USER");
	private User user3 = new User("blyghtest2", "Blygh McCormack", "password",
			"blygh@hotmail.com", true, "ROLE_USER");
	
	private Message me = new Message("subject", "content", "name","blyghtest1", "johnwpurcell");
	private Message message1 = new Message("Test Subject 1", "Test content 1", "Isaac Newton", user2.getUsername(), user1.getUsername());
	private Message message2 = new Message("Test Subject 2", "Test content 2", "Isaac Newton",  user1.getUsername(),user2.getUsername());
	private Message message3 = new Message("Test Subject 3", "Test content 3", "Isaac Newton",  user1.getUsername(), user2.getUsername());

	

	@Before
	public void init() {
		JdbcTemplate jdbc = new JdbcTemplate(dataSource);


		// jdbc.execute("delete from offers");
		jdbc.execute("delete from provider");
		jdbc.execute("delete from jobposts");

		jdbc.execute("delete from messages");
		jdbc.execute("delete from users");
	}
	
	@Test
	public void testSaveRetrieve() {
		usersDao.create(user1);
		usersDao.create(user2);
		usersDao.create(user3);
		
		messagesDao.saveOrUpdate(me);
		messagesDao.saveOrUpdate(message2);
		messagesDao.saveOrUpdate(message3);
		
		List<Message> messages = messagesDao.getMessages(user1.getUsername());
		assertEquals(1, messages.size());
		
		messages = messagesDao.getMessages(user2.getUsername());
		assertEquals(2, messages.size());
	}
	
	@Test
	public void testRetrieveByID() {
		usersDao.create(user1);
		usersDao.create(user2);
		messagesDao.saveOrUpdate(message1);
	}
	
	@Test
	public void testAllMessages() {
		usersDao.create(user1);
		usersDao.create(user2);
		
		messagesDao.saveOrUpdate(message1);
		messagesDao.saveOrUpdate(message2);
		messagesDao.saveOrUpdate(message3);
		
		List<Message> messages = messagesDao.getMessages();
		
		assertEquals("Should be three messages for user", 3, messages.size());
	}
	
	@Test
	public void testRetrieveByUserName() {
		usersDao.create(user1);
		usersDao.create(user2);
		
		messagesDao.saveOrUpdate(message1);
		messagesDao.saveOrUpdate(message2);
		messagesDao.saveOrUpdate(message3);
		
		List<Message> messages = messagesDao.getMessages(user2.getUsername());
		
		for(Message message: messages) {
			Message retrieved = messagesDao.getMessage(message.getId());
			assertEquals(message, retrieved);
		}
	}
	
	@Test
	public void testDelete() {
		usersDao.create(user1);
		usersDao.create(user2);
		
		messagesDao.saveOrUpdate(message1);
		messagesDao.saveOrUpdate(message2);
		messagesDao.saveOrUpdate(message3);
		
		List<Message> messages = messagesDao.getMessages(user2.getUsername());
		
		for(Message message: messages) {
			Message retrieved = messagesDao.getMessage(message.getId());
			assertEquals(message, retrieved);
		}
		
		Message toDelete = messages.get(1);
		
		assertNotNull("This message not deleted yet.", messagesDao.getMessage(toDelete.getId()));
		
		messagesDao.delete(toDelete.getId());
		
		assertNull("This message was deleted.", messagesDao.getMessage(toDelete.getId()));
	}
	
}
