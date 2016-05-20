package ie.done.job.web.test.tests;

import ie.done.job.web.dao.JobPost;
import ie.done.job.web.dao.JobPostsDao;
import ie.done.job.web.dao.Provider;
import ie.done.job.web.dao.ProviderDao;
import ie.done.job.web.dao.ProviderRecommendationDao;
import ie.done.job.web.dao.User;
import ie.done.job.web.dao.UsersDao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.tartarus.snowball.ext.EnglishStemmer;
import org.tartarus.snowball.ext.PorterStemmer;
import org.w3c.dom.Document;

import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.WordNetDatabase;

@ActiveProfiles("dev")
@ContextConfiguration(locations = {
		"classpath:ie/done/job/web/web/configurations/dao-context.xml",
		"classpath:ie/done/job/web/web/configurations/security-context.xml",
		"classpath:ie/done/job/web/test/config/datasource.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class GenerateTestData {

	@Autowired
	private JobPostsDao jobPostsDao;

	@Autowired
	private UsersDao usersDao;

	@Autowired
	private ProviderDao providerDao;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private ProviderRecommendationDao recommend;

	File file = new File(
			"C:\\Users\\User\\Desktop\\college 4th\\dbimage\\test.jpg"); // windows
	// File file = new File("images/extjsfirstlook.jpg");
	byte[] bFile = new byte[(int) file.length()];
	// DepartmentVO domain =
	//private User user1 = new User("blyghtest", "Blygh McCormack", "password",
		//	"blygh@hotmail.com", true, "ROLE_USER");
	private User user2 = new User("blyghtest1", "Blygh McCormack", "password",
			"blygh@hotmail.com", true, "ROLE_USER");
	private User user3 = new User("blyghtest2", "Blygh McCormack", "password",
			"blygh@hotmail.com", true, "ROLE_USER");
	private User user4 = new User("Tradetest2", "Blygh McCormack", "password",
			"trade@hotmail.com", true, "ROLE_TRADE");
	
	private User user5 = new User("blyghtest2", "Blygh McCormack", "password",
			"blygh@hotmail.com", true, "ROLE_USER");

	// private JobPost jobPost4 = new JobPost(user1, "title", "domain",
	// "description", "location2", 20);

	
	private JobPost jobPost1 = new JobPost(
			user2,
			"French",
			"grinds",
			"Looking for grinds at a good price with someone who has alot of experience",
			"3, Crestfield ave, Dublin Ireland", 50);

	private JobPost jobPost2 = new JobPost(23,user2, "Cooking need", "catering",
			"Looking for food for party of 50", "Sligo Ireland", 50);
	private JobPost jobPost21 = new JobPost(user2, "Cooking need", "catering",
			"Looking for food for party of 50",
			"3, Crestfield ave, Dublin Ireland", 50);
	private JobPost jobPost22 = new JobPost(user2, "Cooking need", "catering",
			"Looking for food for party of 50", "218 Charlemont, Dublin 9", 50);
	private JobPost jobPost23 = new JobPost(user2, "Cooking need", "catering",
			"Looking for food for party of 50", "Drogheda, Co. Louth", 50);
	private JobPost jobPost24 = new JobPost(user2, "Cooking need", "catering",
			"Looking for food for party of 50", "Dundrum Shopping Centre", 50);

	private JobPost jobPost3 = new JobPost(user3,
			"Handyman for general maintenance around ", "handyman",
			"Need to repair pipes and windows", "DCU Dublin Ireland", 50);
	/*
	 * private JobPost jobPost6 = new JobPost(user4, "Party of 20", "catering",
	 * "Looking for tapas style food for party of 20 " ,"DCU Dublin Ireland",
	 * 50); private JobPost jobPost5 = new JobPost(user5, "Kicthen help wanted",
	 * "catering",
	 * "looking for help washing and cleaning kitchen after a party."
	 * ,"DCU Dublin Ireland", 50);
	 */

	private Provider pro1 = new Provider("Grinds",
			"Spanish and German grinds in SLigo", "Teaching",
			"5 years experience", "female", "Sligo ", 40);

	private Provider pro5 = new Provider(user2, "title", "domain",
			"experience", "location", 20);

	private Provider pro2 = new Provider("Garderner",
			"have been cutting lawns for over 5 years", "handyman",
			"Weed killer spray course", "male", "Sligo, Ireland", 40);
	
	private User userMain = new User("tradeBill", "Bill fallow", "password",
			"bill@hotmail.com", true, "ROLE_TRADE");


	private Provider providerMain = new Provider(userMain, "Experienced Painter",
			"Spent sixteen years in the decorating trade. large projects restoration.", "painter",
			"Completed an NVQ level 3 in Painting and Decorating", "male",
			"3 Crestfield Road, Whitehall, Ireland", 40);
	
	

	@Before
	public void init() {
		JdbcTemplate jdbc = new JdbcTemplate(dataSource);

		// jdbc.execute("delete from offers");
		jdbc.execute("delete from provider");
		jdbc.execute("delete from jobposts");

		jdbc.execute("delete from messages");
		jdbc.execute("delete from users");

	}

	private static String[] Beginning = { "Kr", "Ca", "Ra", "Mrok", "Cru",
			"Ray", "Bre", "Zed", "Drak", "Mor", "Jag", "Mer", "Jar", "Mjol",
			"Zork", "Mad", "Cry", "Zur", "Creo", "Azak", "Azur", "Rei", "Cro",
			"Mar", "Luk" };
	private static String[] Middle = { "air", "ir", "mi", "sor", "mee", "clo",
			"red", "cra", "ark", "arc", "miri", "lori", "cres", "mur", "zer",
			"marac", "zoir", "slamar", "salmar", "urak" };
	private static String[] End = { "d", "ed", "ark", "arc", "es", "er", "der",
			"tron", "med", "ure", "zur", "cred", "mur" };

	private static Random rand = new Random();

	public static String generateName() {

		return Beginning[rand.nextInt(Beginning.length)]
				+ Middle[rand.nextInt(Middle.length)]
				+ End[rand.nextInt(End.length)];

	}
	
	public static String replaceString(double value){
	    NumberFormat formatter = NumberFormat.getCurrencyInstance();
	    String currencySymbol = formatter.getCurrency().getSymbol();
	    String moneyString = formatter.format(value);
	    return moneyString.replace(currencySymbol, "");
	}


	@Test
	public void fillDatabase() throws IOException {

		
		List<String> details = Files.readAllLines(Paths.get("C:\\Users\\User\\Desktop\\college 4th\\ProjectTestData\\tempTest.txt"), StandardCharsets.UTF_8);
//		
//		String [] domains = {"painter","catering","gardening","handyman"};
//		String [] tasks = {"assignment","duty","task","job","gig","business","work", "project"};
//		Random randNum = new Random();
	
		//user1, "title", "domain",
		// "description", "location2", 20);
		
		usersDao.create(user2);
		usersDao.create(userMain);
		providerDao.saveOrUpdate(providerMain);
		for(int j= 0;j <= details.size()-5;j=j+5){
			//str.replace(/#|_/g,'');
			String costValue = details.get(j+3).replaceAll("€", "").replaceAll(",", "");
			
			Double cost = Double.parseDouble(costValue);
			String title = details.get(j);
			String domain = details.get(j+2).split("\\/", 2)[0];
			String description = details.get(j+1);
			String location = details.get(j+4);
			
			System.out.println( domain  + "---------------------");
			JobPost jobPost = new JobPost(user2,title, domain,description, location, cost);
			System.out.println(jobPost.toString() + "----------jobpost-----------");
			jobPostsDao.saveOrUpdate(jobPost);
		}
		
		providerDao.saveOrUpdate(providerMain);
	
	}
		
		
		
		
		/*for(int i = 0;i< 100;i ++){
			String username = generateName();
			String usertemp = "usertempadded ";
			/*int idD = new Random().nextInt(domains.length-1);
			int idT = new Random().nextInt(tasks.length-1);
			String randomDomain = (domains[idD]);
			String randomTask = (domains[idT]);
			
			  int randomDomain = (int) (Math.random() * domains.length);
			  int randomTask = (int) (Math.random() * tasks.length);
	          System.out.print(domains[randomDomain]);
			
			int address = randNum.nextInt(18);
			int cost = randNum.nextInt(100);
			
			String name = "user" + i;
			//User user = new User(usertemp, usertemp, "password","tom@hotmail.com", true, "ROLE_USER");
			User user1 = new User("userTemptest" + i, username + " userTest", "password",
					username + i + "@hotmail.com", true, "ROLE_USER");
			
			//User user1 = new User(username, name, password, email, enabled, authority)
			//usersDao.create(user1);
			//System.out.println(randomDomain + " domain ----------------");
			
			JobPost jobPost = new JobPost(user1,
					domains[randomDomain] + " needed as soon as possible", domains[randomDomain],
					"Need a " + domains[randomDomain] + " for help with a " + tasks[randomTask] + " in my area", "Dublin " + address, cost);
			//JobPosts temp =new Jo
			//jobPostsDao.saveOrUpdate(jobPost);
			//System.out.println(jobPost.toString());
		} 
		*/


}
