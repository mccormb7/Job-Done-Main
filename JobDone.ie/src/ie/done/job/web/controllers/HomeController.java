package ie.done.job.web.controllers;

import ie.done.job.web.dao.JobPost;
import ie.done.job.web.dao.JobPostsDao;
import ie.done.job.web.dao.Provider;
import ie.done.job.web.dao.ProviderDao;
import ie.done.job.web.dao.User;
import ie.done.job.web.dao.UsersDao;
import ie.done.job.web.web.service.JobPostService;
import ie.done.job.web.web.service.ProviderService;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	
	private static Logger logger = Logger.getLogger(HomeController.class);
	
	@Autowired
	private ProviderService providerService;
	
	@Autowired
	private JobPostService jobPostService;
	
	@Autowired
	private JobPostsDao jobDao;
	
	@Autowired
	private ProviderDao providerDao;
	
	@Autowired
	private UsersDao usersDao;

	//principal allows you to get the current username of user logged in
	@RequestMapping("/")
	public String showHome(Model model, Principal principal) throws Exception {

		//List<Offer> offers = offersService.getCurrent();
		List<JobPost> jobposts = jobPostService.getCurrent();
		for (int i = 0; i < jobposts.size(); i++) {
			System.out.println(jobposts.get(i).toString()+ "---------------------------------");
			//domainList.add(allJobPosts.get(i).getDomain());
			 
		}
		Collections.reverse(jobposts);
		
		model.addAttribute("jobposts1", jobposts);
		//only allow one offer
		
		
		
		List<Provider> providers = providerService.getCurrent();
		model.addAttribute("providers1", providers);
		

		
		//jobDao.indexJobs();
		providerDao.indexProviders();
		
		boolean hasJobPost = false;
		boolean hasProfile = false;
		if(principal != null) {
			//hasJobPost = offersService.hasOffer(principal.getName());
			hasJobPost = jobPostService.hasJobPost(principal.getName());
			hasProfile = providerService.hasProvider(principal.getName());
		}
		
		model.addAttribute("hasJobPost", hasJobPost);
		model.addAttribute("hasProfile", hasProfile);
		//logger.debug("show home page");
		return "home";
	}
	
	@RequestMapping("/FAQ")
	public String showFAQ() {
		return "FAQ";
	}

}
