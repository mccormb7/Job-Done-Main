package ie.done.job.web.controllers;

import ie.done.job.web.dao.JobPost;
import ie.done.job.web.dao.JobPostsDao;
import ie.done.job.web.dao.Offer;
import ie.done.job.web.dao.ProviderDao;
import ie.done.job.web.web.service.JobPostService;
import ie.done.job.web.web.service.OffersService;

import java.security.Principal;
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
	private OffersService offersService;
	
	@Autowired
	private JobPostService jobPostService;
	
	@Autowired
	private JobPostsDao jobDao;
	
	@Autowired
	private ProviderDao providerDao;

	//principal allows you to get the current username of user logged in
	@RequestMapping("/")
	public String showHome(Model model, Principal principal) throws Exception {

		//List<Offer> offers = offersService.getCurrent();
		List<JobPost> jobposts = jobPostService.getCurrent();
		model.addAttribute("jobposts1", jobposts);
		//only allow one offer
		
		//jobDao.indexJobs();
		providerDao.indexProviders();
		
		boolean hasJobPost = false;
		if(principal != null) {
			//hasJobPost = offersService.hasOffer(principal.getName());
			hasJobPost = jobPostService.hasJobPost(principal.getName());
		}
		
		model.addAttribute("hasJobPost", hasJobPost);
		//logger.debug("show home page");
		return "home";
	}

}
