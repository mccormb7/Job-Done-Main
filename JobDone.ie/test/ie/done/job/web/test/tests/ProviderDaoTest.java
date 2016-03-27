package ie.done.job.web.test.tests;

import ie.done.job.web.dao.Provider;
import ie.done.job.web.dao.ProviderDao;
import ie.done.job.web.dao.User;
import ie.done.job.web.dao.UsersDao;
import ie.done.job.web.web.service.ProviderService;
import ie.done.job.web.web.service.UsersService;

import java.io.File;

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
public class ProviderDaoTest {

	@Autowired
	private ProviderDao providerDao;

	
	private ProviderService providerService;
	private UsersService usersService;
	
	@Autowired
	private UsersDao usersDao;

	@Autowired
	private DataSource dataSource;

	File file = new File(
			"C:\\Users\\User\\Desktop\\college 4th\\dbimage\\test.jpg"); // windows
	// File file = new File("images/extjsfirstlook.jpg");
	byte[] bFile = new byte[(int) file.length()];
	//DepartmentVO domain = 
	private User user1 = new User("blyghtrade", "Blygh McCormack", "password",
			"blygh@hotmail.com", true, "ROLE_TRADE");

	//private Tradesman tradesman = new Tradesman(user1, "French", "grinds",
		//	"Can provide grinds at a good price" ,"DCU Dublin Ireland", 50);
	

	private Provider pro1 = new Provider( "Garderner", "have been cutting lawns for over 5 years", "handyman", "Weed killer spray course", "male", "Dublin", 40);
	

	@Before
	public void init() {
		JdbcTemplate jdbc = new JdbcTemplate(dataSource);

		// jdbc.execute("delete from offers");
		jdbc.execute("delete from provider");
		jdbc.execute("delete from offers");

		jdbc.execute("delete from messages");
		jdbc.execute("delete from users");

	}

	@Test
	public void testDelete() {
		usersDao.create(user1);
		String username = user1.getUsername();
		//pro1.setUser(user);
		
		//pro1.getUser().setUsername(username);
		System.out.println(" pro1 and its details ================"  + pro1);
		providerDao.saveOrUpdate(pro1);
		//tradesmanDao.saveOrUpdate(tradesman1);
		// trade = tradesmanDao.getProfile(tradesman1.getId());
		
		
		//tradesmanDao.delete(trade.getId());
		
		//assertNotNull("Profile with ID " + tradesman1.getId()
			//	+ " should not be null (deleted, actual)", trade);

		
		
		/*
		 * usersDao.create(user2); usersDao.create(user3);
		 * usersDao.create(user4); tradesmanDao.saveOrUpdate(JobPost);
		 * tradesmanDao.saveOrUpdate(offer3); tradesmanDao.saveOrUpdate(offer4);
		 * tradesmanDao.saveOrUpdate(offer5); tradesmanDao.saveOrUpdate(offer6);
		 * tradesmanDao.saveOrUpdate(offer7);
		 */

		/*
		 * Offer retrieved1 = tradesmanDao.getOffer(offer2.getId());
		 * assertNotNull("Offer with ID " + retrieved1.getId() +
		 * " should not be null (deleted, actual)", retrieved1);
		 * 
		 * tradesmanDao.delete(offer2.getId());
		 * 
		 * Offer retrieved2 = tradesmanDao.getOffer(offer2.getId());
		 * assertNull("Offer with ID " + retrieved1.getId() +
		 * " should be null (deleted, actual)", retrieved2);
		 */

	}

/*	@Test
	public void testGetById() {
		usersDao.create(user1);
		tradesman1.setUser(user1);
		tradesmanDao.saveOrUpdate(tradesman1);
		System.out.println(tradesman1.toString() + " test version ");
		
		Tradesman ret1 = tradesmanDao.getProfile(tradesman1.getId());
		System.out.println(ret1.toString() + " test version part 2");
		assertEquals("Profile should match", tradesman1, ret1);
		/*
		 * usersDao.create(user2); usersDao.create(user3);
		 * usersDao.create(user4); tradesmanDao.saveOrUpdate(offer1);
		 * tradesmanDao.saveOrUpdate(offer2); tradesmanDao.saveOrUpdate(offer3);
		 * tradesmanDao.saveOrUpdate(offer4); tradesmanDao.saveOrUpdate(offer5);
		 * tradesmanDao.saveOrUpdate(offer6); tradesmanDao.saveOrUpdate(offer7);
		 */
		//
		// Offer retrieved1 = tradesmanDao.getOffer(offer1.getId());
		// assertEquals("Offers should match", offer1, retrieved1);
		//
		// Offer retrieved2 = tradesmanDao.getOffer(offer7.getId());
		// assertNull("Should not retrieve offer for disabled user.",
		// retrieved2);

/*	@Test
	public void testCreateRetrieve() {
		
		usersDao.create(user1);
		tradesman1.setUser(user1);
		tradesmanDao.saveOrUpdate(tradesman1);
		
		Tradesman ret1 = tradesmanDao.getProfile(tradesman1.getId());
		tradesmanDao.saveOrUpdate(ret1);
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
	
	/*@Test
	public void testUpdate() {
		usersDao.create(user1);
		tradesmanDao.saveOrUpdate(jobPost);
		
		JobPost ret1 = tradesmanDao.getJobPost(jobPost.getId());
		
		jobPost.setLocation("sligo");
		tradesmanDao.saveOrUpdate(jobPost);
	
		//assertEquals("Retrieved offer should be updated.", jobPost, ret1);
	}
*/
	/*@Test
	public void testGetUsername() {
		usersDao.create(user1);
		//tradesmanDao.saveOrUpdate(jobPost);
		
		//User user2 = usersService.getUser(user1.getUsername());
		//System.out.println(user2.toString()+" user 2  ===================================");
		//Tradesman ret1 = tradesmanService.getProfile(user1.getUsername());
		//List<Tradesman> listt = tradesmanDao.getProfile(user1.getUsername());
		//System.out.println(listt.get(0) + ".........................................................................");
		
	}*/
	
		
		
		
	

