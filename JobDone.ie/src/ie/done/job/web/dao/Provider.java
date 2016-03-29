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
@Table(name="provider")
public class Provider {

	@Id
	@GeneratedValue//auto generated from the db
	private int id;
	
	@ManyToOne//mapping from the object your in to this object
	@JoinColumn(name="username")
	private User user;
	
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	@Column(name="title")//must match name in DB
	private String title;
	
	//@Column(name="domain")//must match name in DB
	//private String domain;
	
	@Size(min=5, max=255, groups={PersistenceValidationGroup.class, FormValidationGroup.class})
	@Column(name="experience")//must match name in DB
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	private String experience;
	
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	@Column(name="domain")
	private String domain;
	
	@Column(name="qualifications")
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	private String qualifications;
	
	@Lob
    @Column(name="photo", nullable=false, columnDefinition="mediumblob")
    private byte[] photo;
	
	@Column(name="gender")
	private String gender;
	
//title , experience, domain, qualifications,gender,price
	@Column(name="location")
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	private String location;
	
	@Column(name="price")
	private double price;
	
	@Column(name="rating")
	private double rating;
	
	@Column(name = "date")
	private Date date;
	


	public Provider() {
		this.user = new User();
	}
	
	public Provider(int id, User user, String title, String experience,
			String domain, String qualifications, byte[] photo, String gender,
			String location, double price, double rating) {
	
		this.id = id;
		this.user = user;
		this.title = title;
		this.experience = experience;
		this.domain = domain;
		this.qualifications = qualifications;
		this.photo = photo;
		this.gender = gender;
		this.location = location;
		this.price = price;
		this.rating = rating;
	}
	
	public Provider( User user, String title, String experience,
			String domain, String qualifications, String gender,
			String location, double price) {
	
		this.user = user;
		this.title = title;
		this.experience = experience;
		this.domain = domain;
		this.qualifications = qualifications;
		this.gender = gender;
		this.location = location;
		this.price = price;
		
	}
	
	public Provider( String title, String experience,
			String domain, String qualifications, String gender,
			String location, double price) {
	
		this.title = title;
		this.experience = experience;
		this.domain = domain;
		this.qualifications = qualifications;
		this.gender = gender;
		this.location = location;
		this.price = price;
		
	}
	
	public Provider(int id, User user, String title, String experience,
			String domain, String qualifications, byte[] photo, String gender,
			String location, double price) {
	
		this.id = id;
		this.user = user;
		this.title = title;
		this.experience = experience;
		this.domain = domain;
		this.qualifications = qualifications;
		this.photo = photo;
		this.gender = gender;
		this.location = location;
		this.price = price;
	}
	
	

	public Provider(String title, String experience, String domain) {
		this.title = title;
		this.experience = experience;
		this.domain = domain;
	}



	public Provider(int id, User user, String title, String domain,
			String experience, byte[] photo, String location, double price) {
		this.id = id;
		this.user = user;
		this.title = title;
		this.domain = domain;
		this.experience = experience;
		this.photo = photo;
		this.location = location;
		this.price = price;
	}
	
	public Provider(int id, User user, String title, String domain,
			String experience, String location, double price) {
		this.id = id;
		this.user = user;
		this.title = title;
		this.domain = domain;
		this.experience = experience;
		
		this.location = location;
		this.price = price;
	}
	
	public Provider(User user, String title, String domain,
			String experience, String location, double price) {
		
		this.user = user;
		this.title = title;
		this.domain = domain;
		this.experience = experience;	
		this.location = location;
		this.price = price;
	}
	
	public Provider( User user, String title, String domain,
			String experience, byte[] photo, String location, double price) {
		this.user = user;
		this.title = title;
		this.domain = domain;
		this.experience = experience;
		this.photo = photo;
		this.location = location;
		this.price = price;
	}



	public Provider(User user, String experience) {
		this.user = user;
		this.experience = experience;
	}

	public Provider(int id, User user, String experience) {
		this.id = id;
		this.user = user;
		this.experience = experience;
	}



	public Provider(int id, User user, String title, String experience,
			String domain, String qualifications, byte[] photo,
			String location, double price, double rating) {
		this.id = id;
		this.user = user;
		this.title = title;
		this.experience = experience;
		this.domain = domain;
		this.qualifications = qualifications;
		this.photo = photo;
		this.location = location;
		this.price = price;
		this.rating = rating;
	}



	public String getExperience() {
		return experience;
	}



	public void setExperience(String experience) {
		this.experience = experience;
	}



	public String getQualifications() {
		return qualifications;
	}



	public void setQualifications(String qualifications) {
		this.qualifications = qualifications;
	}



	public double getRating() {
		return rating;
	}



	public void setRating(double rating) {
		this.rating = rating;
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
		return experience;
	}

	public void setDescription(String experience) {
		this.experience = experience;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((domain == null) ? 0 : domain.hashCode());
		result = prime * result
				+ ((experience == null) ? 0 : experience.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + id;
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		result = prime * result + Arrays.hashCode(photo);
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((qualifications == null) ? 0 : qualifications.hashCode());
		temp = Double.doubleToLongBits(rating);
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
		Provider other = (Provider) obj;
		if (domain == null) {
			if (other.domain != null)
				return false;
		} else if (!domain.equals(other.domain))
			return false;
		if (experience == null) {
			if (other.experience != null)
				return false;
		} else if (!experience.equals(other.experience))
			return false;
		if (gender == null) {
			if (other.gender != null)
				return false;
		} else if (!gender.equals(other.gender))
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
		if (qualifications == null) {
			if (other.qualifications != null)
				return false;
		} else if (!qualifications.equals(other.qualifications))
			return false;
		if (Double.doubleToLongBits(rating) != Double
				.doubleToLongBits(other.rating))
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

	@Override
	public String toString() {
		return "Provider [id=" + id + ", user=" + user + ", title=" + title
				+ ", experience=" + experience + ", domain=" + domain
				+ ", qualifications=" + qualifications + ", photo="
				+ Arrays.toString(photo) + ", gender=" + gender + ", location="
				+ location + ", price=" + price + ", rating=" + rating + "]";
	}






	

	
	

}
