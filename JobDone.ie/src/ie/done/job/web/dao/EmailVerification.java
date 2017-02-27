package ie.done.job.web.dao;

import ie.done.job.web.pojo.User;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
@Table(name="emailVerification")
public class EmailVerification {

	   private static final int EXPIRATION = 60 * 24;

	   //@Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private Long idnum;

	    @Id
	    private String token;

	    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	    @JoinColumn(nullable = false, name = "username") //change this to email ***********
	    private User user;

	 /*   private Date expiryDate;
	    
	    private Date calculateExpiryDate(final int expiryTimeInMinutes) {
	        final Calendar cal = Calendar.getInstance();
	        cal.setTimeInMillis(new Date().getTime());
	        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
	        return new Date(cal.getTime().getTime());
	    }
	    */
	    public EmailVerification() {
	    	
	    }

//		public EmailVerification(String token) {
//			
//			this.token = token;
//			this.expiryDate = calculateExpiryDate(EXPIRATION);
//		}
	    
		public EmailVerification(String token, User user) {
			
			this.token = token;
			this.user = user;
			//this.expiryDate = calculateExpiryDate(EXPIRATION);
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

	

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((token == null) ? 0 : token.hashCode());
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
			EmailVerification other = (EmailVerification) obj;
			if (token == null) {
				if (other.token != null)
					return false;
			} else if (!token.equals(other.token))
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
			return "EmailVerification [idnum=" + idnum + ", token=" + token
					+ ", user=" + user + "]";
		}

	/*	public Date getExpiryDate() {
			return expiryDate;
		}

		public void setExpiryDate(Date expiryDate) {
			this.expiryDate = expiryDate;
		}
		
		public void updateToken(final String token) {
	        this.token = token;
	        this.expiryDate = calculateExpiryDate(EXPIRATION);
	    }
*/
	


	

	
	

}
