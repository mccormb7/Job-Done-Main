package ie.done.job.web.controllers;


import ie.done.job.web.dao.FormValidationGroup;
import ie.done.job.web.dao.Provider;
import ie.done.job.web.dao.JobPostModel;
import ie.done.job.web.dao.JobPostsDao;
import ie.done.job.web.dao.User;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

@Controller
public class ProviderController {

	
	private ProviderService providerService;
	
	

	private JobPostsDao jobPostsDao;

	@Autowired
	public void setJobPostsDao(JobPostsDao JobPostsDao) {
		this.jobPostsDao = JobPostsDao;
	}

	@Autowired
	public void setProviderService(ProviderService providerService) {
		this.providerService = providerService;
	}


	
	@RequestMapping("/providers")
	public String showProviders(Model model) {

		// jobPostsService.throwTestException();

		List<Provider> providers = providerService.getCurrent();

		model.addAttribute("providers", providers);

		return "providers";
	}
	
	

	@RequestMapping("/createprofile")
	public String createJobPost(Model model, Principal principal) {

		Provider provider = null;

		if (principal != null) {
			String username = principal.getName();
			// check if user has an jobPost already and use to check if user has
			// any jobPosts
			provider = providerService.getProvider(username);
		}

		if (provider == null) {
			provider = new Provider();
		}

		model.addAttribute("provider", provider);

		return "createprofile";
	}

	
	@RequestMapping(value = "/docreateprofile", method = RequestMethod.POST)
	public String doCreateProfile(Model model, @Validated(value=FormValidationGroup.class) Provider provider,
			BindingResult result, Principal principal, @RequestParam(value = "delete", required = false) String delete) {

		System.out.println(provider.toString() + " provider value ==================");
		if (result.hasErrors()) {
			return "createprofile";
		}
		
		if(delete == null) {
			// need to suppy username when jobPost is created
			String username = principal.getName();
			// used to create jobPost in DB
			provider.getUser().setUsername(username);
			System.out.println(provider.getUser() + "-----------------");
			providerService.saveOrUpdate(provider);
			//model.addAttribute("docreatejob", new JobPostModel());
			//jobPostsDao.addBookToDB(jobInfo.getJobDescription(), jobInfo.getJobDomain(), jobInfo.getJobTitle());
			
			return "offercreated";
		}
		else {
			providerService.delete(provider.getId());
			return "offerdeleted";
		}
		
	}
	
	
	

}
