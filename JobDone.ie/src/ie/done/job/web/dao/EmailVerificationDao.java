package ie.done.job.web.dao;



import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@Component("EmailVerificationDao")
public class EmailVerificationDao {

	@Autowired
	private SessionFactory sessionFactory;
	

	public Session session() {
		return sessionFactory.getCurrentSession();
	}
	

	@Transactional
	public void create(EmailVerification token){
		session().save(token);
	}
	
	@SuppressWarnings("unchecked")
	public List<EmailVerification> getAllToken (String email) {
		return session().createQuery("from VerificationToken").list();
	}

	public boolean exists(String token){
		EmailVerification emailVerification = getToken(token);
		return emailVerification != null;
	}
	
	public EmailVerification getToken(String token) {
		
		Criteria crit = session().createCriteria(EmailVerification.class);
		crit.add(Restrictions.idEq(token));
		return (EmailVerification)crit.uniqueResult();
	}

}
