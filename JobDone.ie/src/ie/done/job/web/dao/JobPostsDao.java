package ie.done.job.web.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;
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
	
	  // Spring will inject here the entity manager object
	  //@PersistenceContext
	  private EntityManager entityManager;

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

		return (JobPost) crit.uniqueResult();
	}
	
	@Transactional
	   public void indexJobPosts() throws Exception
	   {
	      try
	      {
	         Session session = sessionFactory.getCurrentSession();
	      
	         FullTextSession fullTextSession = Search.getFullTextSession(session);
	         fullTextSession.createIndexer().startAndWait();
	      }
	      catch(Exception e)
	      {
	         throw e;
	      }
	   }

	
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<JobPost> search(String searchText) throws Exception {
		try {
			Session session = sessionFactory.getCurrentSession();
			//error here 
			FullTextSession fullTextSession = Search
					.getFullTextSession(session);

			QueryBuilder qb = fullTextSession.getSearchFactory()
					.buildQueryBuilder().forEntity(JobPost.class).get();
			org.apache.lucene.search.Query query = qb.keyword()
					.onFields("description", "title", "domain")
					.matching(searchText).createQuery();

			org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(
					query, JobPost.class);

			List<JobPost> results = hibQuery.list();
			return results;
		} catch (Exception e) {
			throw e;
		}
	}
	
	/*@Transactional
	 public List<JobPost> search(String text) {
		    
		
			// get the full text entity manager
		    FullTextEntityManager fullTextEntityManager =
		        org.hibernate.search.jpa.Search.
		        getFullTextEntityManager(entityManager);
		    
		    // create the query using Hibernate Search query DSL
		    QueryBuilder queryBuilder = 
		        fullTextEntityManager.getSearchFactory()
		        .buildQueryBuilder().forEntity(JobPost.class).get();
		    
		    // a very basic query by keywords
		    org.apache.lucene.search.Query query =
		        queryBuilder
		          .keyword()
		          .onFields("title", "description", "domain")
		          .matching(text)
		          .createQuery();

		    // wrap Lucene query in an Hibernate Query object
		    org.hibernate.search.jpa.FullTextQuery jpaQuery =
		        fullTextEntityManager.createFullTextQuery(query, JobPost.class);
		  
		    // execute search and return results (sorted by relevance as default)
		    @SuppressWarnings("unchecked")
		    List<JobPost> results = jpaQuery.getResultList();
		    
		    return results;
		  } // method search

	*/

	

}
