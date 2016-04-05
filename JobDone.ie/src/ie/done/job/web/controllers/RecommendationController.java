package ie.done.job.web.controllers;


import ie.done.job.web.dao.FormValidationGroup;
import ie.done.job.web.dao.JobPost;
import ie.done.job.web.dao.Provider;
import ie.done.job.web.dao.JobPostModel;
import ie.done.job.web.dao.JobPostsDao;
import ie.done.job.web.dao.ProviderDao;
import ie.done.job.web.dao.ProviderModel;
import ie.done.job.web.dao.ProviderRecommendationDao;
import ie.done.job.web.dao.User;
import ie.done.job.web.test.tests.ProviderDaoTest;
import ie.done.job.web.web.service.JobPostService;
import ie.done.job.web.web.service.ProviderService;
import ie.done.job.web.validation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;




























import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

@Controller
public class RecommendationController {

	@Autowired
	private ProviderDao providerDao;

	
	
	@Autowired
	private ProviderRecommendationDao recommend;


	@Autowired
	private JobPostsDao jobPostsDao;
	
	@Autowired
	private ProviderService providerService;
	
	
	
	
	
	
	@Autowired
	public void setProviderDao(ProviderDao providerDao) {
		this.providerDao = providerDao;
	}




	@Autowired
	public void setProviderService(ProviderService providerService) {
		this.providerService = providerService;
	}


/*	@RequestMapping("/recommendationpro")
	public String showRecommendations(Model model, Principal principal) {
		
		return "recommendationpro";
	}
	*/
	@RequestMapping("/searchnullrecommend")
	public String showSearchNullRecommend() {
		return "searchnullrecommend";
	}
	
	
	public static void printMap(Map<JobPost, Integer> map){

		for (Map.Entry<JobPost, Integer> entry : map.entrySet()) {
			System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
		}
			
	}
	/*
	private static Map<JobPost, Integer> sortByComparator(Map<JobPost, Integer> unsortMap) {

		// Convert Map to List
		List<Map.Entry<JobPost, Integer>> list = 
			new LinkedList<Map.Entry<JobPost, Integer>>(unsortMap.entrySet());

		// Sort list with comparator, to compare the Map values
		Collections.sort(list, new Comparator<Map.Entry<JobPost, Integer>>() {
			public int compare(Map.Entry<JobPost, Integer> o1,
                                           Map.Entry<JobPost, Integer> o2) {
				return (o1.getValue()).compareTo(o2.getValue());
			}
		});

		// Convert sorted map back to a Map
		Map<JobPost, Integer> sortedMap = new LinkedHashMap<JobPost, Integer>();
		for (Iterator<Map.Entry<JobPost, Integer>> it = list.iterator(); it.hasNext();) {
			Map.Entry<JobPost, Integer> entry = it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}
	
	*/

	
	
	
	@RequestMapping("/recommendationpro")
	public String createJobPost(Model model, Principal principal) throws Exception {
		
		Provider provider = null;
		Double radius = 10.0;
		
		if (principal != null) {
			String username = principal.getName();
			provider = providerService.getProvider(username);
			//check if they have a profile
			
		}
	
		List<JobPost> recommendedList = recommend.recommendJob(provider);
		//List<JobPost> uniqueList = new ArrayList<JobPost>();
		List<JobPost> uniqueListDistance = new ArrayList<JobPost>();
		int numberOfMatchs = 0;
		
		
		List<String> list = new ArrayList<String>();
		list.add("aaa");
		list.add("bbb");
		list.add("aaa");
		
		System.out.println(" ________________________------occurences1 ------------_____________");

		
		//System.out.println("\nExample 3 - Count all with Map===========================");
		//MyLinkedMap<JobPost, Integer> map1 = new MyLinkedMap<JobPost, Integer>();
		Map<JobPost, Integer> map = new HashMap<JobPost, Integer>();

		for (JobPost temp : recommendedList) {
			Integer count = map.get(temp);
			map.put(temp, (count == null) ? 1 : count + 1);
		}
		
		printMap(map);
		
		System.out.println("\nSorted Linked list to be passed----------------------------");
		
		Set<JobPost> keySet = map.keySet();
		List<JobPost> uniqueList = new ArrayList<JobPost>(keySet); 
		
		Collections.reverse(uniqueList);
		for(int i = 0; i < uniqueList.size();i++){
			System.out.println(uniqueList.get(i).toString());
		}
		
	
		
		
		System.out.println(" ________________________------occurences2 ------------_____________");
		
	/*	//ensures no duplicate results in Arraylist
		for(int i=0;i<recommendedList.size();i++) {
			
	        if(!uniqueList.contains(recommendedList.get(i)))
	            uniqueList.add(recommendedList.get(i));
	            
	    }
	*/	
		
		//used to check the distance
		String postcode=provider.getLocation();
		System.setProperty("java.net.useSystemProxies", "true");
		String latLongs[] = new String[5];
		String latLongs2[] = new String[5];
		
		
		
		for(int i=0;i<uniqueList.size();i++){
			String postcode2=uniqueList.get(i).getLocation();
			latLongs = recommend.getLatLongPositions(postcode);
		    latLongs2 = recommend.getLatLongPositions(postcode2);
		    Double lat1 = Double.parseDouble(latLongs[0]);
		    Double long1 = Double.parseDouble(latLongs[1]);
			Double lat2 = Double.parseDouble(latLongs2[0]);
			Double long2 = Double.parseDouble(latLongs2[1]);
			
			//adds jobPosts that are within the desired distance
			if(recommend.distance(lat1,long1, lat2,long2, "K") < radius){
				uniqueListDistance.add(uniqueList.get(i));
				//for showing the distance details 
				Double journeyKM = recommend.distance(lat1,long1, lat2,long2, "K");
				
				uniqueList.get(i).setDistance(journeyKM);
				uniqueList.get(i).getDate();
				
			}
		}
		
		
		//******************************test output for distance ******************
		for(int i=0;i<uniqueListDistance.size();i++){
			System.out.println("User -> " + uniqueListDistance.get(i).getUser());
			System.out.println("Title ->" + uniqueListDistance.get(i).getTitle());
			System.out.println("DESCRITPION -> " + uniqueListDistance.get(i).getDescription());
			System.out.println("Location -> " + uniqueListDistance.get(i).getLocation());
		}
		boolean hasRecommendation = true;
		if(uniqueListDistance.size()<1){
			hasRecommendation = false;
		}
		
		model.addAttribute("hasRecommendation", hasRecommendation);
		model.addAttribute("recommend", uniqueListDistance);

		return "recommendationpro";
	}
	
	
	
	@RequestMapping(value = "/journey", method = { RequestMethod.GET, RequestMethod.POST })
	public String search(Model model, Principal principal,@RequestParam("journeyString")String journeyString) throws Exception
	{
		//ensure its a vaild input
		String decimalPattern = "([0-9]*)\\.([0-9]*)"; 
		String intPattern = "[0-9]+";
		boolean vaildDouble = Pattern.matches(decimalPattern, journeyString);
		boolean vaildInt =  Pattern.matches(intPattern, journeyString);
		if(!vaildDouble && !vaildInt){
			System.out.println("-----------------------not vaild input");
			return "searchnullrecommend";
		}
		
		//check if entered text is null
	   if(journeyString.equals("")){
		   return "searchnullrecommend";
	   }
	   
	   
	   Double radius =Double.parseDouble(journeyString);
	   Provider provider = null;
	   
		if (principal != null) {
			String username = principal.getName();
			provider = providerService.getProvider(username);
			//check if they have a profile
			
		}
	
		List<JobPost> recommendedList = recommend.recommendJob(provider);
		List<JobPost> uniqueList = new ArrayList<JobPost>();
		List<JobPost> uniqueListDistance = new ArrayList<JobPost>();
		
		//ensures no duplicate results in Arraylist
		for(int i=0;i<recommendedList.size();i++) {
	        if(!uniqueList.contains(recommendedList.get(i)))
	            uniqueList.add(recommendedList.get(i));
	            
	    }
		
		//used to check the distance
		String postcode=provider.getLocation();
		System.setProperty("java.net.useSystemProxies", "true");
		String latLongs[] = new String[5];
		String latLongs2[] = new String[5];
		
		for(int i=0;i<uniqueList.size();i++){
			String postcode2=uniqueList.get(i).getLocation();// job post user location
			
			latLongs = recommend.getLatLongPositions(postcode);
		    latLongs2 = recommend.getLatLongPositions(postcode2);

		    Double lat1 = Double.parseDouble(latLongs[0]);
		    Double long1 = Double.parseDouble(latLongs[1]);
			Double lat2 = Double.parseDouble(latLongs2[0]);
			Double long2 = Double.parseDouble(latLongs2[1]);
			
			//adds jobPosts that are within the desired distance
			if(recommend.distance(lat1,long1, lat2,long2, "K") < radius){
				uniqueListDistance.add(uniqueList.get(i));
				//for showing the distance details 
				Double journeyKM = recommend.distance(lat1,long1, lat2,long2, "K");
				uniqueList.get(i).setDistance(journeyKM);
				uniqueList.get(i).getDate();
				
			}
		}
		
		boolean hasRecommendation = true;
		if(uniqueListDistance.size()<1){
			hasRecommendation = false;
		}
		
		model.addAttribute("hasRecommendation", hasRecommendation);

		model.addAttribute("recommend", uniqueListDistance);

		return "recommendationpro";
	}


}
