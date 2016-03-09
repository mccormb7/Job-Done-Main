package ie.done.job.web.controllers;


import ie.done.job.web.dao.FormValidationGroup;
import ie.done.job.web.dao.JobPost;
import ie.done.job.web.dao.User;
import ie.done.job.web.web.service.JobPostsService;

import java.security.Principal;
import java.util.ArrayList;
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

	
	private JobPostsService jobPostsService;
	 private ArrayList<String> domains;  
	 private HashMap<String, ArrayList<String>> model;  
	
	
	
	@Autowired
	public void setJobPostsService(JobPostsService jobPostsService) {
		this.jobPostsService = jobPostsService;
	}

	ModelAndView mav = null;
	@ModelAttribute("countryList")
	public List getDomain()
	{
		List countryList = new ArrayList();
		countryList.add("India");
		countryList.add("Australia");
		countryList.add("England");
		return countryList;
	}
	
	@RequestMapping("/jobposts")
	public String showJobPosts(Model model) {

		// jobPostsService.throwTestException();

		List<JobPost> jobPosts = jobPostsService.getCurrent();

		model.addAttribute("jobPosts", jobPosts);

		return "jobPosts";
	}
	
	

	/* @ModelAttribute("domain")
	 public Map<String,String> populateCreditCardTypes() {
	        Map<String,String> domains = new LinkedHashMap<String,String>();
	        domains.put("VS", "Visa");domains.put("MC", "MasterCard");
	        domains.put("AE", "American Express");
	        domains.put("DS", "Discover");domains.put("DC", "Diner's Club");                
	        return domains;
	    }*/
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

	public ArrayList<String> getDomains() {
		return domains;
	}

	public void setDomains(ArrayList<String> domains) {
		this.domains = domains;
	}

	public HashMap<String, ArrayList<String>> getModel() {
		return model;
	}

	public void setModel(HashMap<String, ArrayList<String>> model) {
		this.model = model;
	}
}
