package ie.done.job.web.test.tests;

import static org.junit.Assert.*;
import ie.done.job.web.dao.EmailVerification;
import ie.done.job.web.dao.EmailVerificationDao;
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

public class EmailVerificationTests {

	@Autowired
	private UsersDao usersDao;
	
	@Autowired
	private EmailVerificationDao emailVerDao;

	@Autowired
	private DataSource dataSource;
	
	private User user1 = new User("blyghtest", "Blygh McCormack", "password",
			"blygh@hotmail.com", true, "ROLE_USER");
	private User user2 = new User("blyghtest1", "Blygh McCormack", "password",
			"blygh1@hotmail.com", true, "ROLE_USER");
	private User user3 = new User("blyghtest2", "Blygh McCormack", "password",
			"blygh2@hotmail.com", true, "ROLE_USER");
	private User user4 = new User("Tradetest2", "Blygh McCormack", "password",
			"trade3@hotmail.com", true, "ROLE_TRADE");
	private User user5 = new User("blyghtest2", "Blygh McCormack", "password",
			"blygh4@hotmail.com", false, "ROLE_USER");
	private User user6 = new User("blyghtest6", "Blygh McCormack", "password",
			"blygh6@hotmail.com", false, "ROLE_USER");

	
	private EmailVerification emailVerToken1= new EmailVerification("testtoken", user1 );
	private EmailVerification emailVerToken2= new EmailVerification("anothertesttoken", user2 );
	private EmailVerification emailVerToken3 = new EmailVerification("finaltesttoken", user3 );
	private EmailVerification emailVerToken4 = new EmailVerification("lastone", user4 );
	
		@Before
		public void init() {
			JdbcTemplate jdbc = new JdbcTemplate(dataSource);

			// jdbc.execute("delete from offers");
			jdbc.execute("delete from provider");
			jdbc.execute("delete from jobposts");

			jdbc.execute("delete from messages");
			jdbc.execute("delete from users");
			jdbc.execute("delete from emailverification");
	}
		
	@Test
	public void testCreate(){
		usersDao.create(user1);
		emailVerDao.create(emailVerToken1);
		
		String token = "testtoken";
		String compareToken = emailVerToken1.getToken();
		
		assertEquals("Token should match", token, compareToken);
		
	}
	
	@Test
	public void testGetAllCreateTokens() {
		usersDao.create(user1);
		usersDao.create(user2);
		usersDao.create(user3);
		
		emailVerDao.create(emailVerToken1);
		emailVerDao.create(emailVerToken2);
		emailVerDao.create(emailVerToken3);
		
		
		List<EmailVerification> tokenlist = emailVerDao.getAllToken();
		
		assertEquals("There should be 4 tokens", 3, tokenlist.size());
		assertEquals("This should be the first token", "testtoken" , tokenlist.get(0).getToken());
	}
	
	@Test
	public void testExists(){
		
		usersDao.create(user1);
		usersDao.create(user2);
		usersDao.create(user3);
		
		emailVerDao.create(emailVerToken1);
		emailVerDao.create(emailVerToken2);
		emailVerDao.create(emailVerToken3);

		assertTrue("This token should exist.", emailVerDao.exists(emailVerToken1.getToken()));
		assertFalse("This token should not exist.", emailVerDao.exists("notatoken"));
		
	}
	
	@Test
	public void testGetAUsersToken(){
		
		usersDao.create(user1);
		usersDao.create(user2);
		usersDao.create(user3);
		
		emailVerDao.create(emailVerToken1);
		emailVerDao.create(emailVerToken2);
		emailVerDao.create(emailVerToken3);
		
		String testToken = "testtoken";
		
		assertEquals("This token should exist.", testToken, emailVerToken1.getToken());
		assertNotEquals("This token should not exist.",testToken, emailVerToken2.getToken());
		
	}
	

}
