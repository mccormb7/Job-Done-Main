package ie.done.job.web.controllers;


import ie.done.job.web.dao.FormValidationGroup;
import ie.done.job.web.dao.JobPost;
import ie.done.job.web.dao.JobPostModel;
import ie.done.job.web.dao.JobPostsDao;
import ie.done.job.web.dao.User;
import ie.done.job.web.test.tests.ProviderDaoTest;
import ie.done.job.web.web.service.JobPostService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;








import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

@Controller
public class JobPostsController {

	
	private JobPostService jobPostsService;

	private JobPostsDao jobPostsDao;

	@Autowired
	public void setJobPostsDao(JobPostsDao JobPostsDao) {
		this.jobPostsDao = JobPostsDao;
	}
//	private JobPostModel  _repo;
	
	@Autowired
	public void setJobPostsService(JobPostService jobPostsService) {
		this.jobPostsService = jobPostsService;
	}

//	ModelAndView mav = null;
//	@ModelAttribute("countryList")
//	public List getDomain()
//	{
//		List countryList = new ArrayList();
//		countryList.add("India");
//		countryList.add("Australia");
//		countryList.add("England");
//		return countryList;
//	}
	
	@RequestMapping("/jobposts")
	public String showJobPosts(Model model) {

		// jobPostsService.throwTestException();

		List<JobPost> jobPosts = jobPostsService.getCurrent();

		model.addAttribute("jobPosts", jobPosts);

		return "jobPosts";
	}
	
	

	@RequestMapping("/createjobpost")
	public String createJobPost(Model model, Principal principal) {

		JobPost jobPost = null;

		if (principal != null) {
			String username = principal.getName();
			// check if user has an jobPost already and use to check if user has
			// any jobPosts
			jobPost = jobPostsService.getJobPost(username);
		}

		if (jobPost == null) {
			jobPost = new JobPost();
		}

		model.addAttribute("jobpost", jobPost);

		return "createjobpost";
	}

	/*@RequestMapping(value = "/docreatejob", method = RequestMethod.POST)
	public String doCreate(Model model, JobPost jobPost){
		System.out.println(jobPost);
		return "offercreated";
		
	}*/
	
	
	
	
	@RequestMapping(value = "/docreatejob", method = RequestMethod.POST)
	public String doCreate(Model model, @Validated(value=FormValidationGroup.class) JobPost jobPost,
			BindingResult result, Principal principal, @ModelAttribute("JobPostModel") JobPostModel jobInfo,
			@RequestParam(value = "delete", required = false) String delete) {

		System.out.println(jobPost);
		if (result.hasErrors()) {
			return "createjobpost";
		}
		
		if(delete == null) {
			// need to suppy username when jobPost is created
			String username = principal.getName();
			// used to create jobPost in DB
			jobPost.getUser().setUsername(username);
			System.out.println(jobPost.getUser() + "-----------------");
			jobPostsService.saveOrUpdate(jobPost);
			//model.addAttribute("docreatejob", new JobPostModel());
			//jobPostsDao.addBookToDB(jobInfo.getJobDescription(), jobInfo.getJobDomain(), jobInfo.getJobTitle());
			
			return "offercreated";
		}
		else {
			jobPostsService.delete(jobPost.getId());
			return "offerdeleted";
		}
		
	}
	
	@RequestMapping("/search")
	public String showLSearch() {
		return "search";
	}
	@RequestMapping("/searchpro")
	public String showLSearchPro() {
		return "searchpro";
	}
	
	
	@RequestMapping("/search1")
	public String showLSearch1() {
		return "search1";
	}

	@RequestMapping("/searchnull")
	public String showSearchNull() {
		return "search1";
	}
	
	@RequestMapping("/foundjobs")
	public String showResult() {
		return "foundjobs";
	}
	
	@RequestMapping("/profile")
	public String showProfile() {
		return "profile";
	}
	
	@RequestMapping(value = "/doSearch", method = RequestMethod.POST)
	public String search(Model model,
	   @RequestParam("searchText")
	   String searchText
	) throws Exception
	{
		//check if entered text is null
	   if(searchText.equals("")){
		   return "searchnull";
	   }
	   List<JobPost> allFound = jobPostsDao.searchForJob(searchText);
	   List<JobPostModel> jobPostModels = new ArrayList<JobPostModel>();


	  // System.out.println(allFound.get(0).getDescription());
	   System.out.print(allFound.size() + " size of the array being passed");
	   
	   if(allFound.isEmpty()){
		  allFound = Collections.emptyList();
		  return "searchnull";
		  
	   }
	 // JobPost b = new JobPost();
	 //  System.out.println("test");
	  for(JobPost b : allFound){

		   JobPostModel jm = new JobPostModel();
		   System.out.println(searchText);

		  jm.setJobDescription(b.getDescription());
		  jm.setJobDomain(b.getDomain());
		  jm.setJobTitle(b.getTitle());
		  
	      

	      jobPostModels.add(jm);
	  }
	  
	  System.out.println(jobPostModels + "jbmodel");

	  model.addAttribute("search1",jobPostModels);
	   return "search1";
	}
	
	
	
	
	/*@RequestMapping(value = "/doSearch", method = RequestMethod.POST)
	   public String search(@RequestParam("searchText")String searchText, Model model) throws Exception
	   {
		System.out.print(searchText);
		
		List<JobPost> foundPosts = jobPostsService.searchForJob(searchText);
		String temp="empty";
		int i=0;
		
			temp = foundPosts.get(i).getDescription();
			System.out.println(temp);
			i++;
		
		model.addAttribute("search", foundPosts);
		return "search";

	   }*/


}
