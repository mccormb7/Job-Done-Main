package ie.done.job.web.test.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import ie.done.job.web.dao.JobPost;
import ie.done.job.web.dao.Provider;
import ie.done.job.web.dao.JobPostsDao;
import ie.done.job.web.dao.ProviderDao;
import ie.done.job.web.dao.ProviderRecommendationDao;
import ie.done.job.web.dao.User;
import ie.done.job.web.dao.UsersDao;

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
public class ProviderRecommendationDaoTest {

	@Autowired
	private JobPostsDao jobPostsDao;
	
	@Autowired
	private ProviderDao providerDao;

	@Autowired
	private UsersDao usersDao;

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private ProviderRecommendationDao providerRecommendationDao;


	
	
	
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
	public void testAddSynonymAndRemoveStopWords(){
		
		String testSentance = " Building and painter for a house";
		String testSentance2 = " Handyman in the area";
		
		List<String> splitList = providerRecommendationDao.splitStringasList(testSentance);
		List<String> splitList2 = providerRecommendationDao.splitStringasList(testSentance2);
		List<String> synList = providerRecommendationDao.addSynonymToSearch(splitList);
		List<String> synList2 = providerRecommendationDao.addSynonymToSearch(splitList2);
		
		assertEquals("List should have 29 available synonym's in List", 29, synList.size());
		assertEquals("List should have 15 available synonym's in List", 15, synList2.size());

				
		
	}
	
	@Test
	public void testLongLatPosition() throws Exception{
		String address = "8 Crestfield Ave, Whitehall, Dublin 9";
		String address2 = "53 Belmont Ave, Dublin 4";
		
		String [] location  = providerRecommendationDao.getLatLongPositions(address);
		String [] location2  = providerRecommendationDao.getLatLongPositions(address2);

		assertEquals("Should be the same value returned", "53.3860672", location[0]);
		assertEquals("Should be the same value returned", "-6.2512838", location[1]);
		
		assertEquals("Should be the same value returned", "53.3213100", location2[0]);
		assertEquals("Should be the same value returned", "-6.2401419", location2[1]);
		System.out.println(location.length + "----length");
		
	}

	@Test 
	public void testDistance(){
		
		
		Double lat1 = Double.parseDouble("53.3860672");
		Double long1 = Double.parseDouble("-6.2512838");
		Double lat2 = Double.parseDouble("53.3213100");
		Double long2 = Double.parseDouble("-6.2401419");
		Double knownDistance = 4.5;
		Double distance =providerRecommendationDao.distance(lat1, long1, lat2, long2, "k");
		assertEquals("Should be the same value returned", knownDistance, distance);
	}
}

