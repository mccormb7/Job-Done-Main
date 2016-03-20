package ie.done.job.web.dao;

import java.util.List;

import javax.sql.DataSource;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
//translates hibernate exceptions to spring exception messages
@Transactional
//makes userDao run all its code in its methods in a transaction
@Component("usersDao")
public class UsersDao {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	// convieance method, to access current session of session factory
	public Session session() {
		return sessionFactory.getCurrentSession();
	}

	@Transactional
	public void create(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		//gets current session
		session().save(user);
	}
	
	public boolean exists(String username) {
		//getUser(username)!=null
		Criteria crit = session().createCriteria(User.class);
		//returns all user objects in DB, queries on primary Key
		crit.add(Restrictions.idEq(username));
		User user = (User)crit.uniqueResult();
		return user != null;
	}
	
	public boolean existsEmail(String email) {
		// return
		// jdbc.queryForObject("select count(*) from users where email=:email",
		// new MapSqlParameterSource("email", email), Integer.class) > 0;
		Criteria cri = session().createCriteria(User.class);
		cri.add(Restrictions.eq("email", email));
		User user = (User) cri.uniqueResult();
		
		return user != null;
	}

	@SuppressWarnings("unchecked")
	public List<User> getAllUsers() {
		// hibernate
		// not name of table in db
		// name of bean you want to use to work with that data
		return session().createQuery("from User").list();
	}

	public User getUser(String username) {
		Criteria crit = session().createCriteria(User.class);
		crit.add(Restrictions.idEq(username));
		return (User)crit.uniqueResult();
		
	}
	

}
