package ie.done.job.web.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@Component("jobPostsDao")
public class JobPostsDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session session() {
		return sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	public List<JobPost> getJobPosts() {
		Criteria crit = session().createCriteria(JobPost.class);
		crit.createAlias("user", "u").add(Restrictions.eq("u.enabled", true));
		return crit.list();
	}

	@SuppressWarnings("unchecked")
	public List<JobPost> getJobPosts(String username) {
		Criteria crit = session().createCriteria(JobPost.class);
		crit.createAlias("user", "u");
		crit.add(Restrictions.eq("u.enabled", true));
		crit.add(Restrictions.eq("u.username", username));

		return crit.list();
	}

	public void saveOrUpdate(JobPost JobPost) {
		session().saveOrUpdate(JobPost);
	}

	public boolean delete(int id) {
		Query query = session().createQuery("delete from JobPost where id=:id");
		query.setLong("id", id);
		return query.executeUpdate() == 1;
	}

	public JobPost getJobPost(int id) {
		Criteria crit = session().createCriteria(JobPost.class);

		crit.createAlias("user", "u");
		
		crit.add(Restrictions.eq("u.enabled", true));
		crit.add(Restrictions.idEq(id));

		return (JobPost)crit.uniqueResult();
	}

}
