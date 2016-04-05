package ie.done.job.web.dao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;

@Repository
@Transactional
@Component("providerRecommendationDao")
public class ProviderRecommendationDao {

	@Autowired
	private SessionFactory sessionFactory;
	private ProviderRecommendationDao recommend;
	
	


	public Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	
	@Transactional
	public static String[] splitString(String searchText) {
		String[] splitArray = searchText.split("\\s+");
		
		return splitArray;
		

	}
	
	@Transactional
	public List <String> splitStringasList(String searchText) {
		List<String> items = Arrays.asList(searchText.split("\\s+"));
		//List<String> splitList = searchText.split("\\s+");
		
		return items;

	}

	/*@SuppressWarnings("unchecked")
	@Transactional
	public List<JobPost> recommendJobDistance(Provider provider, Double distance) throws Exception {
		
		recommend = null;
		List<JobPost> recommendedList = recommend.recommendJob(provider);
		List<JobPost> uniqueList = new ArrayList<JobPost>();
		List<JobPost> uniqueListDistance = new ArrayList<JobPost>();
		
		
		for(int i=0;i<recommendedList.size();i++) {
	        if(!uniqueList.contains(recommendedList.get(i)))
	            uniqueList.add(recommendedList.get(i));
	            
	    }
		
		String postcode=provider.getLocation();
		System.setProperty("java.net.useSystemProxies", "true");
		
		for(int i=0;i<uniqueList.size();i++){
			String postcode2=uniqueList.get(i).getLocation();// job post user location
			String latLongs[] = getLatLongPositions(postcode);
		    String latLongs2[] = getLatLongPositions(postcode2);

		    Double lat1 = Double.parseDouble(latLongs[0]);
		    Double long1 = Double.parseDouble(latLongs[1]);
			Double lat2 = Double.parseDouble(latLongs2[0]);
			Double long2 = Double.parseDouble(latLongs2[1]);
			
			//adds jobPosts that are within the desired distance
			if(distance(lat1,long1, lat2,long2, "K") < distance){
				uniqueListDistance.add(uniqueList.get(i));
			}
		}
		
		return uniqueListDistance;
		
	}
	
	*/
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<JobPost> recommendJob(Provider provider) throws Exception {
			//String [] splitWords = splitString(searchText);
			
			//String [] splitWords = null;
			String [] stopWords = { "an", "and","a", "are", "as", "at", "be", "but", "by",
			                       "for", "if", "in", "into", "is", "it",
			                       "no", "not", "of", "on", "or", "such",
			                       "that", "the", "their", "then", "there", "these",
			                       "they", "this", "to", "was", "will", "with"};
			List<JobPost> results = null;
			List<JobPost> resultsFinal = new ArrayList<JobPost>();
			List<String> profileDetails = new ArrayList<String>();
			List<String> splitDetails = new ArrayList<String>();
			List<String> holder = new ArrayList<String>();
			//takes in full sentances and must be split in order to process
			if(provider.getTitle().isEmpty() || provider.getExperience().isEmpty()){
				return Collections.EMPTY_LIST;
			}
			System.out.println(provider.toString() + "++++++++++++++++++++++++++++++++++++++++");
			profileDetails.add(provider.getTitle());
			profileDetails.add(provider.getExperience());
			profileDetails.add(provider.getDomain());
			profileDetails.add(provider.getQualifications());
			profileDetails.add(provider.getDescription());
			//String [] splitWords = null;
			for(int i = 0; i<profileDetails.size();i++){
				//if(stopWords[i])
					holder = splitStringasList(profileDetails.get(i));
					splitDetails.addAll(holder);
					//+++++++++++++++++++++++++++++++++++++++++++++++++++
					System.out.println(splitDetails.size() + "############### ");
					
			}
			for(int j = 0; j<splitDetails.size();j++){
				System.out.println(splitDetails.get(j)+ "=====================");
				//remove all the stop words from the search
				for(int s = 0; s<stopWords.length;s++){
					
					if((splitDetails.get(j).equals(stopWords[s]))|| splitDetails.get(j).equals("a")){
						System.out.println(stopWords[s]+ " %%%%%%%%%%%%%%%%%%%");
						splitDetails.remove(j);
					}
				}
				
				
			}

			Session session = sessionFactory.getCurrentSession();
			//System.out.println(searchText + " in the search here1");
			FullTextSession fullTextSession = Search
					.getFullTextSession(session);
			
			//System.out.println(searchText + "in the search here");
			QueryBuilder qb = fullTextSession.getSearchFactory()
					.buildQueryBuilder().forEntity(JobPost.class).get();
			
			for(int i = 0; i<splitDetails.size()-1;i++){
				org.apache.lucene.search.Query query = qb.keyword()
						.onFields("description", "title", "domain", "location")
						.matching(splitDetails.get(i)).createQuery();
	
				org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(
						query, JobPost.class);
			
				results = hibQuery.list();
				if(!results.isEmpty()){
					resultsFinal.addAll(results);
				}
				
				
			}
			
			return resultsFinal;
		
	}
	
	public double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	
	public String[] getLatLongPositions(String address) throws Exception
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
	
	
	//round the distance to certain decimal places
	public double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
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

		return round(dist,2);
	}

	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	/*::	This function converts decimal degrees to radians						 :*/
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	/*::	This function converts radians to decimal degrees						 :*/
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	private static double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}
	




}
