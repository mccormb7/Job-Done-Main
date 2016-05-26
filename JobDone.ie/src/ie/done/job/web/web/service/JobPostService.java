package ie.done.job.web.web.service;

import ie.done.job.web.dao.JobPost;
import ie.done.job.web.dao.JobPostsDao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

@Service("jobPostsService")
public class JobPostService {

	private JobPostsDao JobPostsDao;

	@Autowired
	public void setJobPostsDao(JobPostsDao JobPostsDao) {
		this.JobPostsDao = JobPostsDao;
	}

	public List<JobPost> getCurrent() {
		return JobPostsDao.getJobPosts();
	}

	//add role client
	@Secured({ "ROLE_USER", "ROLE_ADMIN" , "ROLE_TRADE"})
	public void create(JobPost offer) {
		JobPostsDao.saveOrUpdate(offer);
	}
	
	//an offer exists for that user alreaddy and eligiable for edit or delete of an offer
	public boolean hasJobPost(String name) {

		if (name == null) {
			return false;
		}

		List<JobPost> jobPosts = JobPostsDao.getJobPosts(name);

		if (jobPosts.size() == 0) {
			return false;
		}

		return true;
	}

	public JobPost getJobPost(String username) {

		if (username == null) {
			return null;
		}

		List<JobPost> jobPosts = JobPostsDao.getJobPosts(username);
		//stops trying to return first item of an empty list
		if (jobPosts.size() == 0) {
			return null;
		}

		return jobPosts.get(0);
	}

	
	public List<JobPost> searchForJob(String text) {

		if (text == null) {
			return null;
		}

		List<JobPost> jobPosts = JobPostsDao.getJobPosts(text);
		System.out.println(jobPosts + "jobpost value");
		
		//stops trying to return first item of an empty list
		if (jobPosts.size() == 0) {
			return null;
		}

		return jobPosts;
	}

	public void saveOrUpdate(JobPost offer) {
		//hibernate looks at primary key and checks is it in db or not
		JobPostsDao.saveOrUpdate(offer);
	}
	
	public void updateJobPost(JobPost jobPost){
		JobPostsDao.updateJobPost(jobPost);
	}
	
	public void getJobPostById(int id){
		JobPostsDao.getJobPost(id);
	}
	public JobPost getJobPostByIdEdit(int id){
		return JobPostsDao.getJobPost(id);
	}

	public void delete(int id) {
		//deletes from DB
		JobPostsDao.delete(id);
	}
	
	

	public void indexJobs() throws Exception {
		JobPostsDao.indexJobs();
		
	}

	public void delete(String username) {
		JobPostsDao.delete(username);
		// TODO Auto-generated method stub
		
	}
}
