package ie.done.job.web.test.tests;


import ie.done.job.web.dao.JobPostsDao;
import ie.done.job.web.dao.ProviderDao;
import ie.done.job.web.dao.ProviderRecommendationDao;
import ie.done.job.web.dao.UsersDao;
import ie.done.job.web.pojo.JobPost;
import ie.done.job.web.pojo.Provider;
import ie.done.job.web.pojo.User;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

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
public class SearchDaoTests {

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
	//DepartmentVO domain = 
	private User user1 = new User("blyghtest", "Blygh McCormack", "password",
			"blygh@hotmail.com", true, "ROLE_USER");
	private User user2 = new User("blyghtest1", "Blygh McCormack", "password",
			"blygh@hotmail.com", true, "ROLE_USER");
	private User user3 = new User("blyghtest2", "Blygh McCormack", "password",
			"blygh@hotmail.com", true, "ROLE_USER");
	private User user4 = new User("Tradetest2", "Blygh McCormack", "password",
			"trade@hotmail.com", true, "ROLE_TRADE");
	private User user5 = new User("blyghtest2", "Blygh McCormack", "password",
			"blygh@hotmail.com", true, "ROLE_USER");
	
	private JobPost jobPost4 = new JobPost(user1, "title", "domain", "description", "location2", 20);

	private JobPost jobPost1 = new JobPost(user1, "French", "grinds",
			"Looking for grinds at a good price with someone who has alot of experience" ,"3, Crestfield ave, Dublin Ireland", 50);
	
	
	private JobPost jobPost2 = new JobPost(user2, "Cooking need", "catering",
			"Looking for food for party of 50" ,"Sligo Ireland", 50);
	private JobPost jobPost21 = new JobPost(user2, "Cooking need", "catering",
			"Looking for food for party of 50" ,"3, Crestfield ave, Dublin Ireland", 50);
	private JobPost jobPost22 = new JobPost(user2, "Cooking need", "catering",
			"Looking for food for party of 50" ,"218 Charlemont, Dublin 9", 50);
	private JobPost jobPost23 = new JobPost(user2, "Cooking need", "catering",
			"Looking for food for party of 50" ,"Drogheda, Co. Louth", 50);
	private JobPost jobPost24 = new JobPost(user2, "Cooking need", "catering",
			"Looking for food for party of 50" ,"Dundrum Shopping Centre", 50);
	
	
	private JobPost jobPost3 = new JobPost(user3, "Handyman for general maintenance around ", "handyman",
			"Need to repair pipes and windows" ,"DCU Dublin Ireland", 50);
	private JobPost jobPost6 = new JobPost(user4, "Party of 20", "catering",
			"Looking for tapas style food for party of 20 " ,"DCU Dublin Ireland", 50);
	private JobPost jobPost5 = new JobPost(user5, "Kicthen help wanted", "catering",
			"looking for help washing and cleaning kitchen after a party." ,"DCU Dublin Ireland", 50);
	
	private Provider pro1 = new Provider( "Grinds", "Spanish and German grinds in SLigo", "Teaching", "5 years experience", "female", "Sligo ", 40);
	
	private Provider pro5 = new Provider(user1, "title", "domain", "experience", "location", 20);
	
	private Provider pro2 = new Provider( "Garderner", "have been cutting lawns for over 5 years", "handyman", "Weed killer spray course", "male", "Sligo, Ireland", 40);
	
	private Provider pro3 = new Provider(user4, "Cook for rent", "have been cooking for over 5 years", "catering", "First aid. Degree in culinary arts", "male", "3 Knights Bridge, Clontarf", 40);


	@Before
	public void init() {
		JdbcTemplate jdbc = new JdbcTemplate(dataSource);

		// jdbc.execute("delete from offers");
		jdbc.execute("delete from provider");
		jdbc.execute("delete from jobposts");

		jdbc.execute("delete from messages");
		jdbc.execute("delete from users");

	}
	
	public String[] splitStringTest(String searchText) {
		String[] splitArray = searchText.split("\\s+");
		EnglishStemmer stemmer = new EnglishStemmer();
		for(int i = 0; i< splitArray.length;i++){
			stemmer.setCurrent(splitArray[i]);
			//if the word can be stemmed it will set it
			if(stemmer.stem()){
				splitArray[i] = stemmer.getCurrent();	
			}
		}
		
		return splitArray;
		

	}
	@Test
	public void testSynsets(){
		File f=new File("C://Program Files (x86)//WordNet//2.1//dict2");
        System.setProperty("wordnet.database.dir", f.toString());
	
        WordNetDatabase database = WordNetDatabase.getFileInstance();
        List<String> uniqueListOfSyn = new ArrayList<String>();
        
        String wordForm = "fence";
        
		//  Get the synsets containing the wrod form
		String[] wordList = {"fix", "paint" };
		
		for(int k =0;k< wordList.length;k++){
			
			
			Synset[] synsets = database.getSynsets(wordList[k]);
			
			
			if (synsets.length > 0)
			{
				System.out.println("The following synsets contain '" +
						wordForm + "' or a possible base form " +
						"of that text:");
				
				for (int i = 0; i < synsets.length; i++)
				{
					System.out.println("");
					String[] wordForms = synsets[i].getWordForms();
					
					for (int j = 0; j < wordForms.length; j++)
					{
						if(!uniqueListOfSyn.contains(wordForms[j])){
							uniqueListOfSyn.add(wordForms[j]);
						}
						
						System.out.println(wordForms[j]);
					}
					//System.out.println(": " + synsets[i].getDefinition());
					System.out.println("++++++++++++++++++++++++++++++++++++++");
					
					
				}
			}
//			else
//			{
//				System.err.println("No synsets exist that contain " +
//						"the word form '" + wordForm + "'");
//			}
		}
		System.out.println("++++++++++++++++++unique list++++++++++++++++");
		for (int j = 0; j < uniqueListOfSyn.size(); j++)
		{
			System.out.println(uniqueListOfSyn.get(j));
		}
		
		System.out.println("++++++++++++++++++Stemming++++++++++++++++");
	
	}
	
	@Test
	public void testSearch() throws Exception {
		usersDao.create(user1);
		usersDao.create(user2);
		usersDao.create(user3);
		jobPostsDao.saveOrUpdate(jobPost3);
		jobPostsDao.saveOrUpdate(jobPost4);
		jobPostsDao.saveOrUpdate(jobPost2);
		jobPostsDao.saveOrUpdate(jobPost21);
		jobPostsDao.saveOrUpdate(jobPost22);
		jobPostsDao.saveOrUpdate(jobPost23);
		jobPostsDao.saveOrUpdate(jobPost24);
		
		/*providerDao.saveOrUpdate(pro1);
		providerDao.saveOrUpdate(pro2);
		providerDao.saveOrUpdate(pro3);*/

		System.out.println("...........................................................");
		List<JobPost> resultsFinal = recommend.recommendJob(pro3);
		List<JobPost> uniqueList = new ArrayList<JobPost>();
		
		
		for(int i=0;i<resultsFinal.size();i++) {
	        if(!uniqueList.contains(resultsFinal.get(i)))
	            uniqueList.add(resultsFinal.get(i));
	            
	    }
		System.out.println("...........................................................");
		System.out.println(pro3);
		System.out.println("...........................................................");
		
		String postcode=pro3.getLocation();
		System.setProperty("java.net.useSystemProxies", "true");
		
		//recommendation list
		for(int i=0;i<uniqueList.size();i++){
	  
			System.out.println("Id -> " + uniqueList.get(i).getId());
			System.out.println("User -> " + uniqueList.get(i).getUser());
			System.out.println("Title ->" + uniqueList.get(i).getTitle());
			System.out.println("DESCRITPION -> " + uniqueList.get(i).getDescription());
			System.out.println("Location -> " + uniqueList.get(i).getLocation());
		
		      System.out.println("...........................................................");
	
		}
	}
	
	
	@Ignore
	public void testRecommendation() throws Exception{
		usersDao.create(user1);
		usersDao.create(user2);
		//usersDao.create(user3);
		
		jobPostsDao.saveOrUpdate(jobPost3);
		jobPostsDao.saveOrUpdate(jobPost4);
		jobPostsDao.saveOrUpdate(jobPost2);
		System.out.println("...........................................................");
		List<JobPost> resultsFinal = recommend.recommendJob(pro1);
		for(int i=0;i<resultsFinal.size();i++){
			System.out.println("DESCRITPION -> " + resultsFinal.get(i).getDescription());
			System.out.println("Title ->" + resultsFinal.get(i).getTitle());
			System.out.println("User -> " + resultsFinal.get(i).getUser());
		}
		
		
		
	}
	

	
	
	
	//##########################################################################################
	//####################Test distance###################################################


	
	@Ignore
	public static String[] getLatLongPositions(String address) throws Exception
	  {
	    int responseCode = 0;
	    String api = "http://maps.googleapis.com/maps/api/geocode/xml?address=" + URLEncoder.encode(address, "UTF-8") + "&sensor=true";
	    System.out.println("URL : "+api);
	    URL url = new URL(api);
	    HttpURLConnection httpConnection = (HttpURLConnection)url.openConnection();
	    httpConnection.connect();
	    responseCode = httpConnection.getResponseCode();
	    if(responseCode == 200)
	    {
	      DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();;
	      Document document = builder.parse(httpConnection.getInputStream());
	      XPathFactory xPathfactory = XPathFactory.newInstance();
	      XPath xpath = xPathfactory.newXPath();
	      XPathExpression expr = xpath.compile("/GeocodeResponse/status");
	      String status = (String)expr.evaluate(document, XPathConstants.STRING);
	      if(status.equals("OK"))
	      {
	         expr = xpath.compile("//geometry/location/lat");
	         String latitude = (String)expr.evaluate(document, XPathConstants.STRING);
	         expr = xpath.compile("//geometry/location/lng");
	         String longitude = (String)expr.evaluate(document, XPathConstants.STRING);
	         return new String[] {latitude, longitude};
	      }
	      else
	      {
	         throw new Exception("Error from the API - response status: "+status);
	      }
	    }
	    return null;
	  }
	
	
	@Ignore
	private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		if (unit == "K") {
			dist = dist * 1.609344;
		} else if (unit == "N") {
			dist = dist * 0.8684;
		}

		return (dist);
	}

	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	/*::	This function converts decimal degrees to radians						 :*/
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	@Ignore
	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	/*::	This function converts radians to decimal degrees						 :*/
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	@Ignore
	private static double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}
		
	
	
	
}
