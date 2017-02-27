package ie.done.job.web.controllers;


import ie.done.job.web.dao.FormValidationGroup;
import ie.done.job.web.dao.JobPostModel;
import ie.done.job.web.dao.JobPostsDao;
import ie.done.job.web.pojo.JobPost;
import ie.done.job.web.pojo.Provider;
import ie.done.job.web.pojo.User;
import ie.done.job.web.test.tests.ProviderDaoTest;
import ie.done.job.web.web.service.JobPostService;
import ie.done.job.web.web.service.ProviderService;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

@Controller
public class JobPostsController {

	
	private JobPostService jobPostsService;

	private ProviderService providerService;
	
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
	
	@Autowired
	public void ProviderService(ProviderService providerService) {
		this.providerService = providerService;
	}


	//adds only the jobs that the current logged in user made
	@RequestMapping("/jobposts")
	public String showUsersJobPosts(Model model, Principal principal) throws Exception {

		
		List<JobPost> jobposts = jobPostsService.getCurrent();
		List<JobPost> currentPosts = new ArrayList<JobPost>();
		String username = principal.getName();

		for(int i = 0;i < jobposts.size();i++){
			if(jobposts.get(i).getUser().getUsername().equals(username)){
				
				currentPosts.add(jobposts.get(i));
				
			}
		
		}
		
		boolean hasPostedJob = false;
		
		if(currentPosts.size()>0) {
			//hasJobPost = offersService.hasOffer(principal.getName());
			hasPostedJob = true;
		}
		
		model.addAttribute("hasPostedJob", hasPostedJob);
		model.addAttribute("jobposts2", currentPosts);
		return "jobposts";
	}

	
	@RequestMapping("/jobpostdetails")
	public String showJobPostDetails(Model model, Principal principal) {
		//boolean hasProfile = false;
		
		JobPost jobpost = null;
	
		if(principal != null) {
			String username = principal.getName();
			jobpost = jobPostsService.getJobPost(username);
			
			//check if they have a profile
			//hasProfile = providerService.hasProvider(username);
		}
		
		boolean hasPic = false;
		if(jobpost.getInternetpic()!=null){
			hasPic=true;
		}
		
		model.addAttribute("hasPic", hasPic);
		
		model.addAttribute("jobpost", jobpost);
		//model.addAttribute("hasProfile", hasProfile);
		
		return "jobpostdetails";
	
	}
	
	@RequestMapping("/viewjobpost/{id}")
	public String viewProfile(Model model, Principal principal, @PathVariable("id") int id) {

		JobPost jobpost = jobPostsService.getJobPostByIdEdit(id);
				//jobPostsDao.getJobPost(id);
		
		boolean hasPic = false;
		if(jobpost.getInternetpic()!=null){
			hasPic=true;
		}
		
		model.addAttribute("hasPic", hasPic);
		

		model.addAttribute("jobpost", jobpost);

		return "jobpostdetails";
	}
	
	
	
	

	@RequestMapping("/createjobpost")
	public String createJobPost(Model model, Principal principal) {

		JobPost jobPost = new JobPost();
	
		List<String> domainList = new ArrayList<String>();
		List<String> unique = new ArrayList<String>();
		List<JobPost> allJobPosts = jobPostsService.getCurrent();
		List<Provider> allProviders = providerService.getCurrent();
		
		for (int i = 0; i < allJobPosts.size(); i++) {		
			domainList.add(allJobPosts.get(i).getDomain());
			if(!unique.contains(domainList.get(i))){
				unique.add(domainList.get(i));
			}	 
		}
		for (int i = 0; i <allProviders.size(); i++) {		
			domainList.add(allProviders.get(i).getDomain());
			if(!unique.contains(domainList.get(i))){
				System.out.println(domainList.get(i));
				unique.add(domainList.get(i));
			}	 
		}
		Collections.sort(unique);
		model.addAttribute("unique", unique);
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
			String domain = jobPost.getDomain().replaceAll("[-+.^:,]","");
			jobPost.setDomain(domain);
			jobPostsService.saveOrUpdate(jobPost);
	
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
	
	@RequestMapping("/remove/{id}")
    public String removeJobPost(@PathVariable("id") int id){
		
        jobPostsService.delete(id);
        return "redirect:/jobposts";
    }
	

	@RequestMapping("/editjobpost/{id}")
	public String editJobPost(Model model, Principal principal, @PathVariable("id") int id) {

		JobPost jobPost = null;

		if (principal != null) {
			String username = principal.getName();
			// check if user has an jobPost already and use to check if user has
			// any jobPosts
			//jobPost = jobPostsService.getJobPost(username);
			jobPost = jobPostsService.getJobPostByIdEdit(id);
		}

		model.addAttribute("jobpost", jobPost);

		return "createjobpost";
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
	   List<JobPost> allFound = jobPostsDao.searchForJob(searchText);//cast list to JobPost
	   List<JobPostModel> jobPostModels = new ArrayList<JobPostModel>();


	   System.out.print(allFound.size() + " size of the array being passed");
	   
	   if(allFound.isEmpty()){
		  allFound = Collections.emptyList();
		  return "searchnull";
		  
	   }
	  for(JobPost b : allFound){

		   JobPostModel jm = new JobPostModel();
		   System.out.println(searchText);

		  jm.setJobDescription(b.getDescription());
		  jm.setJobDomain(b.getDomain());
		  jm.setJobTitle(b.getTitle());
		  jm.setJobLocation(b.getLocation());
		  jm.setJobId(b.getId());
		  jm.setJobDate(b.getDate());
		  jm.setJobPrice(b.getPrice());
		  
	      

	      jobPostModels.add(jm);
	  }
	  
	  System.out.println(jobPostModels + "jbmodel");

	  model.addAttribute("search1",jobPostModels);
	   return "search1";
	}
	
	

}
