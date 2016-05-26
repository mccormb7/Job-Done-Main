package ie.done.job.web.dao;

import java.util.Date;

public class JobPostModel {
	
	private String jobTitle;
	private String jobDomain;
	private String jobDescription;
	private String jobLocation;
	private Double jobPrice;
	private Date jobDate;
	private int jobId;
	
	
	
	public int getJobId() {
		return jobId;
	}
	public void setJobId(int jobId) {
		this.jobId = jobId;
	}
	public String getJobLocation() {
		return jobLocation;
	}
	public void setJobLocation(String jobLocation) {
		this.jobLocation = jobLocation;
		
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getJobDomain() {
		return jobDomain;
	}
	public void setJobDomain(String jobDomain) {
		this.jobDomain = jobDomain;
	}
	public String getJobDescription() {
		return jobDescription;
	}
	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}
	public Date getJobDate() {
		return jobDate;
	}
	public void setJobDate(Date jobDate) {
		this.jobDate = jobDate;
	}
	public Double getJobPrice() {
		return jobPrice;
	}
	public void setJobPrice(Double jobPrice) {
		this.jobPrice = jobPrice;
	}
	
	
	

}
