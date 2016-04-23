package ie.done.job.web.dao;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Store;


@Indexed
@Entity
@Table(name="jobPosts")
public class JobPost extends Post implements Comparable<JobPost>  {

	@Id
	@GeneratedValue//auto generated from the db
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	private int id;
	
	@ManyToOne//mapping from the object your in to this object
	@JoinColumn(name="username")
	private User user;
	
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	@Column(name="title")//must match name in DB
	private String title;
	
	//@Column(name="domain")//must match name in DB
	//private String domain;
	//title, domain, description, photo, location. price
	@Size(min=5, max=255, groups={PersistenceValidationGroup.class, FormValidationGroup.class})
	@Column(name="description")//must match name in DB
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	private String description;
	
	@Lob
    @Column(name="photo", nullable=false, columnDefinition="mediumblob")
    private byte[] photo;
	
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	@Column(name="location")
	private String location;
	
	@Column(name="price")
	private double price;
	
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	@Column(name="domain")
	private String domain;
	
	@Column(name = "date")
	private Date date;
	
	//holds distance between job location and provider
	@Column(name = "distance")
	private Double distance;

	@Column(name="internetpic")
	private String internetpic;



	public JobPost() {
		this.user = new User();
	}
	
	

	public JobPost(String title, String description, String domain) {
		this.title = title;
		this.description = description;
		this.domain = domain;
	}



	public JobPost(int id, User user, String title, String domain,
			String description, byte[] photo, String location, double price) {
		this.id = id;
		this.user = user;
		this.title = title;
		this.domain = domain;
		this.description = description;
		this.photo = photo;
		this.location = location;
		this.price = price;
	}
	
	public JobPost(int id, User user, String title, String domain,
			String description, String location, double price) {
		this.id = id;
		this.user = user;
		this.title = title;
		this.domain = domain;
		this.description = description;
		
		this.location = location;
		this.price = price;
	}
	
	public JobPost(User user, String title, String domain,
			String description, String location, double price) {
		
		this.user = user;
		this.title = title;
		this.domain = domain;
		this.description = description;	
		this.location = location;
		this.price = price;
	}
	
	public JobPost( User user, String title, String domain,
			String description, byte[] photo, String location, double price) {
		this.user = user;
		this.title = title;
		this.domain = domain;
		this.description = description;
		this.photo = photo;
		this.location = location;
		this.price = price;
	}



	public JobPost(User user, String description) {
		this.user = user;
		this.description = description;
	}

	public JobPost(int id, User user, String description) {
		this.id = id;
		this.user = user;
		this.description = description;
	}



	public String getDomain() {
		return domain;
	}



	public void setDomain(String domain) {
		this.domain = domain;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/*public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}*/

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}



	public String getInternetpic() {
		return internetpic;
	}



	public void setInternetpic(String internetpic) {
		this.internetpic = internetpic;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((domain == null) ? 0 : domain.hashCode());
		result = prime * result + id;
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		result = prime * result + Arrays.hashCode(photo);
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JobPost other = (JobPost) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (domain == null) {
			if (other.domain != null)
				return false;
		} else if (!domain.equals(other.domain))
			return false;
		if (id != other.id)
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (!Arrays.equals(photo, other.photo))
			return false;
		if (Double.doubleToLongBits(price) != Double
				.doubleToLongBits(other.price))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}



	public Date getDate() {
		return date;
	}



	public void setDate(Date date) {
		this.date = date;
	}



	public Double getDistance() {
		return distance;
	}



	public void setDistance(Double distance) {
		this.distance = distance;
	}



	@Override
	public int compareTo(JobPost o) {
		// TODO Auto-generated method stub
		return 0;
	}



	

	
	

}
