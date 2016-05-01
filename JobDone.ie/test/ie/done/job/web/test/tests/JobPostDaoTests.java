package ie.done.job.web.test.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import ie.done.job.web.dao.JobPost;
import ie.done.job.web.dao.JobPostsDao;
import ie.done.job.web.dao.User;
import ie.done.job.web.dao.UsersDao;

import java.io.File;
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
public class JobPostDaoTests {

	@Autowired
	private JobPostsDao jobPostsDao;

	@Autowired
	private UsersDao usersDao;

	@Autowired
	private DataSource dataSource;

	File file = new File(
			"C:\\Users\\User\\Desktop\\college 4th\\dbimage\\test.jpg"); // windows
	// File file = new File("images/extjsfirstlook.jpg");
	byte[] bFile = new byte[(int) file.length()];
	
	
	
	
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
	
	
	

	private JobPost jobPost = new JobPost(user1, "French", "grinds",
			"Can provide grinds at a good price", "DCU Dublin Ireland", 50);
	private JobPost jobPost14 = new JobPost(user1, "Cooking need", "catering",
			"Looking for food for party of 50" ,"Dundrum Shopping Centre", 50);

	
	private JobPost jobPost1 = new JobPost(user2, "Cooking need", "catering",
			"Looking for food for party of 50" ,"218 Charlemont, Dublin 9", 50);
	private JobPost jobPost12 = new JobPost(user2, "Cooking need", "catering",
			"Looking for food for party of 50" ,"Drogheda, Co. Louth", 50);
	private JobPost jobPost13 = new JobPost(user2, "Cooking need", "catering",
			"Looking for food for party of 50" ,"Dundrum Shopping Centre", 50);
	
	
	private JobPost jobPost2 = new JobPost(user3, "Handyman for general maintenance around ", "handyman",
			"Need to repair pipes and windows" ,"DCU Dublin Ireland", 50);
	
	private JobPost jobPost3 = new JobPost(user5, "Party of 20", "catering",
			"Looking for tapas style food for party of 20 " ,"DCU Dublin Ireland", 50);
	
	private JobPost jobPost4 = new JobPost(user4, "Kicthen help wanted", "catering",
			"looking for help washing and cleaning kitchen after a party." ,"DCU Dublin Ireland", 50);
	


	@Before
	public void init() {
		JdbcTemplate jdbc = new JdbcTemplate(dataSource);

		jdbc.execute("delete from provider");
		jdbc.execute("delete from jobposts");

		jdbc.execute("delete from messages");
		jdbc.execute("delete from users");
	}

	@Test
	public void testDeleteJobPost() {
		
		usersDao.create(user1);
		usersDao.create(user2);
		usersDao.create(user3);
		usersDao.create(user4);
		
		
		jobPostsDao.saveOrUpdate(jobPost);
		jobPostsDao.saveOrUpdate(jobPost1);
		jobPostsDao.saveOrUpdate(jobPost12);
		jobPostsDao.saveOrUpdate(jobPost2);
		jobPostsDao.saveOrUpdate(jobPost3);
		
	
		JobPost retrieved1 = jobPostsDao.getJobPost(jobPost.getId());
		assertNotNull("JobPost with ID " + retrieved1.getId() + " should not be null", retrieved1);

		jobPostsDao.delete(jobPost.getId());
		assertNotNull("JobPost with ID " + retrieved1.getId() + " should not be null (deleted, actual)", retrieved1);


	}

	@Test
	public void testGetJobPostById() {
		
		
		usersDao.create(user2);
		usersDao.create(user5);
		
	
		jobPostsDao.saveOrUpdate(jobPost1);	
		jobPostsDao.saveOrUpdate(jobPost3);

		
		JobPost retrieved1 = jobPostsDao.getJobPost(jobPost1.getId());
		assertEquals("Job Post should match", jobPost1, retrieved1);
		
		JobPost retrieved2 = jobPostsDao.getJobPost(jobPost3.getId());
		assertNull("JobPost is not available for disabled user", retrieved2);
	
	}

	@Test
	public void testCreateRetrieveJobPost() {
		
		usersDao.create(user1);
		usersDao.create(user2);
		usersDao.create(user3);
		usersDao.create(user4);
				
		jobPostsDao.saveOrUpdate(jobPost1);	
		jobPostsDao.saveOrUpdate(jobPost2);
		
		List<JobPost> jobpostsRet1 = jobPostsDao.getJobPosts();
		
		assertEquals("2 jobPosts in List", 2, jobpostsRet1.size());
		assertEquals("JobPosts should match", jobPost1, jobpostsRet1.get(0));
		
		jobPostsDao.saveOrUpdate(jobPost4);	
		jobPostsDao.saveOrUpdate(jobPost12);
		jobPostsDao.saveOrUpdate(jobPost13);
		
		List<JobPost> jobpostsRet2 = jobPostsDao.getJobPosts();
		
		assertEquals("Should be five jobPosts for enabled users", 5, jobpostsRet2.size());
		
	}

	@Test
	public void testEditJobPost() {
		
		usersDao.create(user1);
		usersDao.create(user2);
		usersDao.create(user3);
		usersDao.create(user4);
		usersDao.create(user6);
				
		jobPostsDao.saveOrUpdate(jobPost);	
		jobPostsDao.saveOrUpdate(jobPost14);
		jobPostsDao.saveOrUpdate(jobPost2);
		
		jobPost.setTitle("German Grinds");
		jobPost.setPrice(10);
		jobPostsDao.saveOrUpdate(jobPost);
		
		JobPost ret1 = jobPostsDao.getJobPost(jobPost.getId());
		assertEquals("Retrieved JobPots should be updated", jobPost, ret1);
		
	
	}

	@Test
	public void testGetJobPostByUsername() {
		usersDao.create(user1);
		usersDao.create(user2);
		usersDao.create(user3);
		usersDao.create(user4);
		usersDao.create(user6);
				
		jobPostsDao.saveOrUpdate(jobPost);	
		jobPostsDao.saveOrUpdate(jobPost14);
		jobPostsDao.saveOrUpdate(jobPost2);
		
		List<JobPost>jobPostsRet1 = jobPostsDao.getJobPosts(user1.getUsername());
		assertEquals("Should be two jobPost for this user", 2, jobPostsRet1.size());
		
		List<JobPost>jobPostsRet2 = jobPostsDao.getJobPosts(user6.getUsername());
		assertEquals("No JobPosts for thsi user", 0, jobPostsRet2.size());
		
		List<JobPost>jobPostsRet3 = jobPostsDao.getJobPosts(user3.getUsername());
		assertEquals("One JobPost for this user", 1, jobPostsRet3.size());
		

	}

}

