package ie.done.job.web.test.tests;

import static org.junit.Assert.*;
import ie.done.job.web.dao.JobPost;
import ie.done.job.web.dao.Offer;
import ie.done.job.web.dao.JobPostsDao;
import ie.done.job.web.dao.User;
import ie.done.job.web.dao.UsersDao;

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
	//DepartmentVO domain = 
	private User user1 = new User("blyghtest", "Blygh McCormack", "password",
			"blygh@hotmail.com", true, "ROLE_USER");

	private JobPost jobPost = new JobPost(user1, "French", "grinds",
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
		jdbc.execute("delete from jobPosts");
		jdbc.execute("delete from offers");

		jdbc.execute("delete from messages");
		jdbc.execute("delete from users");

	}

	@Test
	public void testDelete() {
		usersDao.create(user1);
		jobPostsDao.saveOrUpdate(jobPost);
		JobPost ret1 = jobPostsDao.getJobPost(jobPost.getId());
		
		jobPostsDao.delete(jobPost.getId());
		
		assertNotNull("Offer with ID " + ret1.getId()
				+ " should not be null (deleted, actual)", ret1);

		
		
		/*
		 * usersDao.create(user2); usersDao.create(user3);
		 * usersDao.create(user4); jobPostsDao.saveOrUpdate(JobPost);
		 * jobPostsDao.saveOrUpdate(offer3); jobPostsDao.saveOrUpdate(offer4);
		 * jobPostsDao.saveOrUpdate(offer5); jobPostsDao.saveOrUpdate(offer6);
		 * jobPostsDao.saveOrUpdate(offer7);
		 */

		/*
		 * Offer retrieved1 = jobPostsDao.getOffer(offer2.getId());
		 * assertNotNull("Offer with ID " + retrieved1.getId() +
		 * " should not be null (deleted, actual)", retrieved1);
		 * 
		 * jobPostsDao.delete(offer2.getId());
		 * 
		 * Offer retrieved2 = jobPostsDao.getOffer(offer2.getId());
		 * assertNull("Offer with ID " + retrieved1.getId() +
		 * " should be null (deleted, actual)", retrieved2);
		 */

	}

	@Test
	public void testGetById() {
		usersDao.create(user1);
		jobPostsDao.saveOrUpdate(jobPost);
		
		JobPost ret1 = jobPostsDao.getJobPost(jobPost.getId());
		assertEquals("JobPost should match", jobPost, ret1);
		/*
		 * usersDao.create(user2); usersDao.create(user3);
		 * usersDao.create(user4); jobPostsDao.saveOrUpdate(offer1);
		 * jobPostsDao.saveOrUpdate(offer2); jobPostsDao.saveOrUpdate(offer3);
		 * jobPostsDao.saveOrUpdate(offer4); jobPostsDao.saveOrUpdate(offer5);
		 * jobPostsDao.saveOrUpdate(offer6); jobPostsDao.saveOrUpdate(offer7);
		 */
		//
		// Offer retrieved1 = jobPostsDao.getOffer(offer1.getId());
		// assertEquals("Offers should match", offer1, retrieved1);
		//
		// Offer retrieved2 = jobPostsDao.getOffer(offer7.getId());
		// assertNull("Should not retrieve offer for disabled user.",
		// retrieved2);
	}
	@Test
	public void testCreateRetrieve() {
		
		usersDao.create(user1);
		jobPostsDao.saveOrUpdate(jobPost);
		
		JobPost ret1 = jobPostsDao.getJobPost(jobPost.getId());
	
	/*

		List<Offer> offers1 = offersDao.getOffers();
		assertEquals("Should be one offer.", 1, offers1.size());

		assertEquals("Retrieved offer should equal inserted offer.", offer1,
				offers1.get(0));

		offersDao.saveOrUpdate(offer2);
		offersDao.saveOrUpdate(offer3);
		offersDao.saveOrUpdate(offer4);
		offersDao.saveOrUpdate(offer5);
		offersDao.saveOrUpdate(offer6);
		offersDao.saveOrUpdate(offer7);

		List<Offer> offers2 = offersDao.getOffers();
		assertEquals("Should be six offers for enabled users.", 6,
				offers2.size());
*/
	}
	
	@Test
	public void testUpdate() {
		usersDao.create(user1);
		jobPostsDao.saveOrUpdate(jobPost);
		
		JobPost ret1 = jobPostsDao.getJobPost(jobPost.getId());
		
		jobPost.setLocation("sligo");
		jobPostsDao.saveOrUpdate(jobPost);
	
		//assertEquals("Retrieved offer should be updated.", jobPost, ret1);
	}

	@Test
	public void testGetUsername() {
		usersDao.create(user1);
		jobPostsDao.saveOrUpdate(jobPost);
		
		
		JobPost ret1 = jobPostsDao.getJobPost(jobPost.getId());
		//assertEquals("Should be 1 jobpost for this user.", 1, jobPost);
		
		/*usersDao.create(user1);
		usersDao.create(user2);
		usersDao.create(user3);
		usersDao.create(user4);

		offersDao.saveOrUpdate(offer1);
		offersDao.saveOrUpdate(offer2);
		offersDao.saveOrUpdate(offer3);
		offersDao.saveOrUpdate(offer4);
		offersDao.saveOrUpdate(offer5);
		offersDao.saveOrUpdate(offer6);
		offersDao.saveOrUpdate(offer7);

		List<Offer> offers1 = offersDao.getOffers(user3.getUsername());
		assertEquals("Should be three offers for this user.", 3, 
		offers1.size());

		List<Offer> offers2 = offersDao.getOffers("sdfsfd");
		assertEquals("Should be zero offers for this user.", 0, offers2.size());

		List<Offer> offers3 = offersDao.getOffers(user2.getUsername());
		assertEquals("Should be 1 offer for this user.", 1, offers3.size());
		
		*/
	}

	
}
