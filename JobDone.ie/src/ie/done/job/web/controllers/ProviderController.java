package ie.done.job.web.controllers;




import ie.done.job.web.dao.FormValidationGroup;
import ie.done.job.web.dao.ProviderDao;
import ie.done.job.web.dao.ProviderModel;
import ie.done.job.web.pojo.JobPost;
import ie.done.job.web.pojo.Provider;
import ie.done.job.web.web.service.JobPostService;
import ie.done.job.web.web.service.ProviderService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProviderController {

	
	private ProviderService providerService;
	
	private ProviderDao providerDao;
	
	@Autowired
	private JobPostService jobPostService;
	
	
	
	@Autowired
	public void setProviderDao(ProviderDao providerDao) {
		this.providerDao = providerDao;
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
	
	@RequestMapping("/profile")
	public String showProfile(Model model, Principal principal) {
		
		Provider provider = null;
		
		boolean hasProfile = false;
		
		if (principal != null) {
			String username = principal.getName();
			provider = providerService.getProvider(username);
			//check if they have a profile
		
			
			hasProfile = providerService.hasProvider(username);
		}

		model.addAttribute("provider", provider);
		model.addAttribute("hasProfile", hasProfile);
		
		return "profile";
	
	}
	
	//view profile- allows clients view the providers profile
	@RequestMapping("/viewprofile/{id}")
	public String viewProfile(Model model, Principal principal, @PathVariable("id") int id) {


		Provider provider = providerDao.getProvider(id);

		boolean hasPic = false;
		if(provider.getInternetpic()!=null){
			hasPic=true;
		}
		
		model.addAttribute("hasPic", hasPic);
		model.addAttribute("provider", provider);

		return "profile";
	}
	
/*	
	@RequestMapping("/leaveReview/{username}")
	public String leaveReview(Model model, Principal principal, @PathVariable("username") String username) {


		Provider provider = providerDao.getProvider(username);
		//provider.setCompletedJob(
		double completedJob =Provider.getCompletedJob()+1;
		provider.setRating(provider.getRating());
		

		model.addAttribute("provider", provider);

		return "profile";
	}
	
*/
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
		List<String> domainList = new ArrayList<>();
		List<String> unique = new ArrayList<>();
		List<JobPost> allJobPosts = jobPostService.getCurrent();
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
		
		model.addAttribute("provider", provider);

		return "createprofile";
		
	}
	
	@RequestMapping("/searchprovider")
	public String showLSearchprovider() {
		return "searchprovider";
	}

	
	@RequestMapping(value = "/docreateprofile", method = RequestMethod.POST)
	public String doCreateProfile(Model model, @Validated(value=FormValidationGroup.class) Provider provider,
			BindingResult result, Principal principal, @RequestParam(value = "delete", required = false) String delete) {

		if (result.hasErrors()) {
			return "createprofile";
		}
		
		if(delete == null) {
			// need to suppy username when jobPost is created
			String username = principal.getName();
			// used to create jobPost in DB
			provider.getUser().setUsername(username);

			String domain = provider.getDomain().replaceAll("[-+.^:,]","");
			provider.setDomain(domain);

			providerService.saveOrUpdate(provider);
		
		
			return "profile";
		}
		else {
			providerService.delete(provider.getId());
			return "offerdeleted";
		}
		
	}
	
	@RequestMapping(value = "/doSearchProvider", method = RequestMethod.POST)
	public String search(Model model,
	   @RequestParam("searchTextPro")
	   String searchTextPro
	) throws Exception
	{
		//check if entered text is null
	   if(searchTextPro.equals("")){
		   return "searchnull";
	   }
	  
	   List<Provider> allFound = providerDao.searchForProvider(searchTextPro);//cast as post
	   List<ProviderModel> providerModels = new ArrayList<ProviderModel>();
	   

	  // System.out.println(allFound.get(0).getDescription());
	   System.out.print(allFound.size() + " size of the array being passed");
	   
	   if(allFound.isEmpty()){
		  allFound = Collections.emptyList();
		  return "searchnull";
		  
	   }
	 // JobPost b = new JobPost();
	 //  System.out.println("test");
	  for(Provider b : allFound){

		  ProviderModel pro = new ProviderModel();
		   System.out.println(searchTextPro);

		  pro.setProviderDomain(b.getDomain());
		  pro.setProviderExperience(b.getExperience());
		  pro.setProviderQualifications(b.getQualifications());
		  pro.setProviderTitle(b.getTitle());
		  pro.setProviderLocation(b.getLocation());
		  pro.setProviderId(b.getId());

	      providerModels.add(pro);
	  }
	  
	  System.out.println(providerModels + "promodel");

	  model.addAttribute("searchprovider",providerModels);
	   return "searchprovider";
	}
	
	
	

}
