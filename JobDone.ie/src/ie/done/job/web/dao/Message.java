package ie.done.job.web.dao;

//import ie.done.job.web.validation.Validfromuser;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="messages")
public class Message implements Serializable {

	private static final long serialVersionUID = 8063111826064761657L;
//testgits
	@Id
	@GeneratedValue
	private int id;
	
	@NotBlank
	@Size(min=5, max=100)
	private String subject;
	
	@NotBlank
	@Size(min=8, max=1000)
	private String content;

	// Name of user sending message
	@NotBlank
	@Size(min=8, max=60)
	private String name;

	// Sender's fromuser address
	//@Validfromuser
	private String fromuser;

	// Send message TO this user.
	//name of user the fromuser will go to.
	private String username;
	
	private String usernameto;

	public Message() {

	}

	public Message(String subject, String content, String name, String fromuser,
			String username) {
		this.subject = subject;
		this.content = content;
		this.name = name;
		this.fromuser = fromuser;
		this.username = username;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getFromuser() {
		return fromuser;
	}

	public void setFromuser(String fromuser) {
		this.fromuser = fromuser;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsernameto() {
		return usernameto;
	}

	public void setUsernameto(String usernameto) {
		this.usernameto = usernameto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((fromuser == null) ? 0 : fromuser.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
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
		Message other = (Message) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (fromuser == null) {
			if (other.fromuser != null)
				return false;
		} else if (!fromuser.equals(other.fromuser))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", subject=" + subject + ", content="
				+ content + ", name=" + name + ", fromuser=" + fromuser
				+ ", username=" + username + "]";
	}

	
}
