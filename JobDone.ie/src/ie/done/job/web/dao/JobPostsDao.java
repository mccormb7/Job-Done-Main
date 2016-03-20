package ie.done.job.web.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
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

	public Session session() {
		return sessionFactory.getCurrentSession();
	}
	
	/*@Transactional
	   public void addBookToDB(String title, String description, String domain)
	   {
	      Session session = sessionFactory.getCurrentSession();
	      JobPost jobPost = new JobPost();
	      jobPost.setDescription(description);
	      jobPost.setTitle(title);
	      jobPost.setDomain(domain);
	      
	      
	   }*/

	@Transactional
	public void indexJobs() throws Exception {
		try {
			Session session = sessionFactory.getCurrentSession();

			FullTextSession fullTextSession = Search
					.getFullTextSession(session);
			fullTextSession.createIndexer().startAndWait();
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Transactional
	public String[] splitString(String searchText) {
		String[] splitArray = searchText.split("\\s+");
		
		return splitArray;
		

	}
	

	@Transactional
	public List<JobPost> searchForJob(String searchText) throws Exception {
			
		
			String [] splitWords = splitString(searchText);
			/*for(int i = 0; i<splitWords.length;i++){
				System.out.println(splitWords[i] + "word " + i);
			}*/

			
			
			Session session = sessionFactory.getCurrentSession();
			
			

			
			//System.out.println(searchText + "in the search here1");
			FullTextSession fullTextSession = Search
					.getFullTextSession(session);
			
			//System.out.println(searchText + "in the search here");
			QueryBuilder qb = fullTextSession.getSearchFactory()
					.buildQueryBuilder().forEntity(JobPost.class).get();
			
			org.apache.lucene.search.Query query = qb.keyword()
					.onFields("description", "title", "domain")
					.matching(searchText).createQuery();

			org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(
					query, JobPost.class);

			List<JobPost> results = hibQuery.list();
			return results;
		
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

	// ////////////////////////////Fucntiomn to perform search///////////////
	@SuppressWarnings("unchecked")
	public List<JobPost> searchForJobtemp(String text) {

		Criteria crit = session().createCriteria(JobPost.class);
		crit.add(Restrictions.eq("username", "%" + text + "%"));
		/*
		 * QueryBuilder queryBuilder =
		 * fullTextSession.getSearchFactory().buildQueryBuilder
		 * ().forEntity(JobPost.class).get(); Query luceneQuery =
		 * queryBuilder.keyword
		 * ().onFields("productTitle").matching(text).createQuery();
		 * 
		 * org.hibernate.search.FullTextQuery fullTextQuery =
		 * fullTextSession.createFullTextQuery(luceneQuery, JobPost.class);
		 * 
		 * 
		 * Criteria crit = session().createCriteria(JobPost.class);
		 * crit.add(Restrictions.eq("username", text));
		 * 
		 * /*crit.createAlias("user", "u"); Criteria criteria =
		 * session.createCriteria(StockDailyRecord.class)
		 * .add(Restrictions.eq("volume", 10000));
		 * 
		 * crit.add(Restrictions.eq("u.enabled", true));
		 * crit.add(Restrictions.eq("u.username", text));
		 */

		return crit.list();
	}

	/*
	 * Criteria cri = session().createCriteria(User.class);
	 * cri.add(Restrictions.eq("email", email)); User user = (User)
	 * cri.uniqueResult();
	 */

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

}
