package ie.done.job.web.test.tests;

import static org.junit.Assert.*;
import ie.done.job.web.dao.JobPost;
import ie.done.job.web.dao.JobPostModel;
import ie.done.job.web.dao.Offer;
import ie.done.job.web.dao.JobPostsDao;
import ie.done.job.web.dao.Provider;
import ie.done.job.web.dao.ProviderDao;
import ie.done.job.web.dao.User;
import ie.done.job.web.dao.UsersDao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mvel2.ast.AssertNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

@ActiveProfiles("dev")
@ContextConfiguration(locations = {
		"classpath:ie/done/job/web/web/configurations/dao-context.xml",
		"classpath:ie/done/job/web/web/configurations/security-context.xml",
		"classpath:ie/done/job/web/test/config/datasource.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class SearchDaoTests {

	@Autowired
	private JobPostsDao jobPostsDao;

	@Autowired
	private UsersDao usersDao;
	
	@Autowired
	private ProviderDao providerDao;

	@Autowired
	private DataSource dataSource;

	File file = new File(
			"C:\\Users\\User\\Desktop\\college 4th\\dbimage\\test.jpg"); // windows
	// File file = new File("images/extjsfirstlook.jpg");
	byte[] bFile = new byte[(int) file.length()];
	//DepartmentVO domain = 
	private User user1 = new User("blyghtest", "Blygh McCormack", "password",
			"blygh@hotmail.com", true, "ROLE_USER");
	private User user2 = new User("blyghtest1", "Blygh McCormack", "password",
			"blygh@hotmail.com", true, "ROLE_USER");
	private User user3 = new User("blyghtest2", "Blygh McCormack", "password",
			"blygh@hotmail.com", true, "ROLE_USER");
	
	private JobPost jobPost4 = new JobPost(user1, "title", "domain", "description", "location2", 20);

	private JobPost jobPost1 = new JobPost(user1, "French", "grinds",
			"Can provide grinds at a good price" ,"DCU Dublin Ireland", 50);
	private JobPost jobPost2 = new JobPost(user2, "German", "grinds",
			"Can provide grinds at a good price" ,"DCU Dublin Ireland", 50);
	private JobPost jobPost3 = new JobPost(user3, "Spanish", "grinds",
			"Can provide grinds at a good price" ,"DCU Dublin Ireland", 50);

	/*
	 * private User user2 = new User("richardhannay", "Richard Hannay",
	 * "the39steps", "richard@caveofprogramming.com", true, "ROLE_ADMIN");
	 * private User user3 = new User("suetheviolinist", "Sue Black",
	 * "iloveviolins", "sue@caveofprogramming.com", true, "ROLE_USER"); private
	 * User user4 = new User("rogerblake", "Rog Blake", "liberator",
	 * "rog@caveofprogramming.com", false, "user");
	 */

	/*
	 * private Offer offer1 = new Offer(user1, "This is a test offer."); private
	 * Offer offer2 = new Offer(user1, "This is another test offer."); private
	 * Offer offer3 = new Offer(user2, "This is yet another test offer.");
	 * private Offer offer4 = new Offer(user3,
	 * "This is a test offer once again."); private Offer offer5 = new
	 * Offer(user3, "Here is an interesting offer of some kind."); private Offer
	 * offer6 = new Offer(user3, "This is just a test offer."); private Offer
	 * offer7 = new Offer(user4,
	 * "This is a test offer for a user that is not enabled.");
	 */

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
	public void testSearch() throws Exception {
		usersDao.create(user1);
		usersDao.create(user2);
		usersDao.create(user3);
		jobPostsDao.saveOrUpdate(jobPost3);
		jobPostsDao.saveOrUpdate(jobPost4);
		jobPostsDao.saveOrUpdate(jobPost2);

		List<Provider> results = new ArrayList<Provider>();
		
		//JobPost ret1 = jobPostsDao.getJobPost(jobPost.getId());
		String testText = "German";
		List<JobPost> allFound = jobPostsDao.searchForJob(testText);
		//jobPostsDao.searchForJob(searchText)
		
		for(int i=0;i<allFound.size();i++){
			System.out.println("DESCRITPION -> " + allFound.get(i).getDescription());
			System.out.println("Title ->" + allFound.get(i).getTitle());
			System.out.println("User -> " + allFound.get(i).getUser());
		}
	}
		
	
	
	
}
