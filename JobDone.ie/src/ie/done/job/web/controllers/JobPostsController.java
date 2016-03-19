package ie.done.job.web.controllers;


import ie.done.job.web.dao.FormValidationGroup;
import ie.done.job.web.dao.JobPost;
import ie.done.job.web.dao.JobPostsDao;
import ie.done.job.web.dao.User;
import ie.done.job.web.models.JobPostModel;
import ie.done.job.web.web.service.JobPostsService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jboss.logging.Logger;
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

	
	private JobPostsService jobPostsService;
	
	private static Logger _logger = Logger.getLogger(JobPostsController.class);
	 
	@Autowired
	private JobPostsDao repo;
	
	@Autowired
	public void setJobPostsService(JobPostsService jobPostsService) {
		this.jobPostsService = jobPostsService;
	}

	/*ModelAndView mav = null;
	@ModelAttribute("countryList")
	public List getDomain()
	{
		List countryList = new ArrayList();
		countryList.add("India");
		countryList.add("Australia");
		countryList.add("England");
		return countryList;
	}*/
	
	@RequestMapping("/jobposts")
	public String showJobPosts(Model model) throws Exception {

		// jobPostsService.throwTestException();

		List<JobPost> jobPosts = jobPostsService.getCurrent();
		repo.indexJobPosts();
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
			BindingResult result, Principal principal,
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
			jobPostsService.saveOrUpdate(jobPost);
			return "offercreated";
		}
		else {
			jobPostsService.delete(jobPost.getId());
			return "offerdeleted";
		}
		
	}
	
	/*@RequestMapping(value = "/addJobToDB", method = RequestMethod.POST)
	   public ModelAndView addJobToDB(
	      @ModelAttribute("JobPostModel")
	      JobPostModel jobInfo
	   ) throws Exception
	   {
	      _logger.info(jobInfo.getJobPostDescription());
	      _logger.info(jobInfo.getJobPostDomain());
	      _logger.info(jobInfo.getJobPostTitle());
	      
	      repo.addJobToDB(
    		  jobInfo.getJobPostDescription(),
    		  jobInfo.getJobPostDomain(),
    		  jobInfo.getJobPostTitle()
	      );
	      
	      ModelAndView mav = new ModelAndView("done");
	      mav.addObject("message", "Add book to DB successfully");
	      return mav;
	   }
	
	@RequestMapping(value = "/addJobPost", method = RequestMethod.GET)
	public ModelAndView addBookPage()
	{
	   ModelAndView mav = new ModelAndView("addJobPost", "command", new JobPostModel());
	   return mav;
	}
	
	*/
	
	
	
	  @RequestMapping("/search")
	  public String search(String q, Model model) {
	    List<JobPost> searchResults = null;
	    try {
	      searchResults = jobPostsService.search(q);
	    }
	    catch (Exception ex) {
	      // here you should handle unexpected errors
	      // ...
	      // throw ex;
	    }
	    model.addAttribute("searchResults", searchResults);
	    return "search";
	  }

	
	/*@RequestMapping(value = "/search", method = RequestMethod.GET)
	   public ModelAndView searchPage()
	   {
	      ModelAndView mav = new ModelAndView("search");
	      return mav;
	   }
	
	
	
	
	/*@RequestMapping(value = "/dosearch", method = RequestMethod.POST)
	   public ModelAndView search(
	      @RequestParam("searchText")
	      String searchText
	   ) throws Exception
	   {
	      List<JobPost> allFound = repo.searchForJobPost(searchText);
	      List<JobPostModel> jobPostModels = new ArrayList<JobPostModel>();
	      
	      for (JobPost b : allFound)
	      {
	    	 JobPostModel jp = new JobPostModel();
	    	 jp.setJobPostDescription(b.getDescription());
	    	 jp.setJobPostDomain(b.getDomain());
	    	 jp.setJobPostTitle(b.getTitle());
	         
	         
	    	 jobPostModels.add(jp);
	      }
	      
	      ModelAndView mav = new ModelAndView("foundJobs");
	      mav.addObject("foundJobs", jobPostModels);
	      return mav;
	   }*/
	
	
	
}
