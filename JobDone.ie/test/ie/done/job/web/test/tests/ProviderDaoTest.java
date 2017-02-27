package ie.done.job.web.test.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import ie.done.job.web.dao.JobPostsDao;
import ie.done.job.web.dao.ProviderDao;
import ie.done.job.web.dao.UsersDao;
import ie.done.job.web.pojo.JobPost;
import ie.done.job.web.pojo.Provider;
import ie.done.job.web.pojo.User;

import java.io.File;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Ignore;
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
	private JobPostsDao jobPostsDao;
	
	@Autowired
	private ProviderDao providerDao;

	@Autowired
	private UsersDao usersDao;

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
	
	
	/***********************Provider Trade Users ************************/
	private User user5 = new User("blyghTrade2", "Blygh McCormack", "password",
			"blygh5@hotmail.com", false, "ROLE_TRADE");
	private User user6 = new User("blyghTrade6", "Blygh McCormack", "password",
			"blygh6@hotmail.com", true, "ROLE_TRADE");
	private User user7 = new User("blyghTrade7", "Blygh McCormack", "password",
	"blygh7@hotmail.com", true, "ROLE_TRADE");
	private User user8 = new User("blyghTrade8", "Blygh McCormack", "password",
			"blygh8@hotmail.com", true, "ROLE_TRADE");
			
	
	

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
	
	//private JobPost jobPost3 = new JobPost(user5, "Party of 20", "catering",
		//	"Looking for tapas style food for party of 20 " ,"DCU Dublin Ireland", 50);
	
	private JobPost jobPost4 = new JobPost(user4, "Kicthen help wanted", "catering",
			"looking for help washing and cleaning kitchen after a party." ,"DCU Dublin Ireland", 50);

	
	private Provider pro1 = new Provider( user5,"Garderner", "have been cutting lawns for over 5 years", "handyman", 											"Weed killer spray course", "male", "Dublin", 40);
	private Provider pro2 = new Provider( user6,"Painter", "specialize in painting houses and windows", "painter", 
			"FAS painting course", "male", "Dublin 6", 20);
	private Provider pro3 = new Provider( user7,"Builder for development", "Walls, foundations and window frames", "builder", 
			"Construction Industry Federation ", "male", "Dublin, 2", 40);
	private Provider pro4 = new Provider( user8,"Builder for development", "Walls, foundations and window frames", "builder", 
			"Construction Industry Federation ", "male", "Dublin, 2", 40);
	//private Provider pro4 = new Provider( "Garderner", "have been cutting lawns for over 5 years", "handyman", 
	//		"Weed killer spray course", "male", "Dublin", 40);
	//private Provider p = new Provider
	


	@Before
	public void init() {
		JdbcTemplate jdbc = new JdbcTemplate(dataSource);

		jdbc.execute("delete from provider");
		jdbc.execute("delete from jobposts");
		jdbc.execute("delete from messages");
		jdbc.execute("delete from users");
	}

	
	@Test
	public void testCreateRetrieveProfile() {
		
		usersDao.create(user5);
		usersDao.create(user6);
		usersDao.create(user7);
		
				
		//providerDao.saveOrUpdate(pro1);
		providerDao.saveOrUpdate(pro2);
		providerDao.saveOrUpdate(pro3);
		
		List<Provider> providerRet1 = providerDao.getProviders();
		
		assertEquals("2 Provider Profiles in List", 2, providerRet1.size());
		assertEquals("Provider should match", pro2, providerRet1.get(0));
		
	
		
	}

	
	
	@Ignore
	public void testDeleteProfile() {
		usersDao.create(user5);
		usersDao.create(user6);
		usersDao.create(user7);
		
		providerDao.saveOrUpdate(pro2);
		providerDao.saveOrUpdate(pro3);

		Provider ret1 = providerDao.getProvider(pro2.getId());
		assertNotNull("Provider with ID " + ret1.getId() + " should not be null", ret1);
		
		providerDao.delete(pro2.getId());
		assertNotNull("Provider with ID " + ret1.getId() + " should be deleted", ret1);
	}
	
	
	@Test
	public void testGetProviderById() {
		
		
		usersDao.create(user5);
		usersDao.create(user6);
		usersDao.create(user7);
		
		providerDao.saveOrUpdate(pro2);
		providerDao.saveOrUpdate(pro3);
		
		
		Provider retrieved1 = providerDao.getProvider(pro2.getId());
		assertEquals("Profile should match", pro2, retrieved1);
			
	
	}


	@Test
	public void testEditProfile() {
		
		usersDao.create(user5);
		usersDao.create(user6);
		usersDao.create(user7);
		
		providerDao.saveOrUpdate(pro2);
		providerDao.saveOrUpdate(pro3);
	
		
		Provider before = providerDao.getProvider(pro2.getId());
		
		pro2.setTitle("German Grinds");
		pro2.setPrice(10);
		providerDao.saveOrUpdate(pro2);
		
		Provider after = providerDao.getProvider(pro2.getId());
		
		assertNotEquals("Retrieved Profile should not be updated", pro2,before);
		assertEquals("Retrieved Profile should be updated", pro2, after);
		
	
	
	}

	@Test
	public void testGetProviderByUsername() {
		usersDao.create(user5);
		usersDao.create(user6);
		usersDao.create(user7);
		
		providerDao.saveOrUpdate(pro2);
		providerDao.saveOrUpdate(pro3);
						
		List<Provider> providerRet1 = providerDao.getProviders(user6.getUsername());
		assertEquals("Should be One profile", 1, providerRet1.size());
		

	}
	
	@Test
	public void testSearchForProvider() throws Exception{
		usersDao.create(user5);
		usersDao.create(user6);
		usersDao.create(user7);
		usersDao.create(user8);
		usersDao.create(user1);
		
		providerDao.saveOrUpdate(pro2);
		providerDao.saveOrUpdate(pro3);
		providerDao.saveOrUpdate(pro4);
		
		jobPostsDao.saveOrUpdate(jobPost);
		
		String searchTextPro = "Painter";
		
		List<Provider> searchResults = providerDao.searchForProvider(searchTextPro);
		assertEquals("Should be One result returned", 0, searchResults.size());
		
		searchTextPro = "Builder";
		List<Provider> searchResults2 = providerDao.searchForProvider(searchTextPro);
		assertEquals("Should be two results returned", 2, searchResults2.size());
		
	}
	

}

