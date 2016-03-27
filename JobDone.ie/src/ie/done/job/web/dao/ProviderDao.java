package ie.done.job.web.dao;

import java.util.ArrayList;
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
@Component("providerDao")
public class ProviderDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session session() {
		return sessionFactory.getCurrentSession();
	}
	

/*
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
			
			
			List<JobPost> results = null;
			List<JobPost> resultsFinal = new ArrayList<JobPost>();
			Session session = sessionFactory.getCurrentSession();
			System.out.println(searchText + " in the search here1");
			FullTextSession fullTextSession = Search
					.getFullTextSession(session);
			
			//System.out.println(searchText + "in the search here");
			QueryBuilder qb = fullTextSession.getSearchFactory()
					.buildQueryBuilder().forEntity(JobPost.class).get();
			
			for(int i = 0; i<splitWords.length;i++){
				org.apache.lucene.search.Query query = qb.keyword()
						.onFields("description", "title", "domain")
						.matching(splitWords[i]).createQuery();
	
				org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(
						query, JobPost.class);
			
				results = hibQuery.list();
				if(!results.isEmpty()){
					resultsFinal.addAll(results);
				}
				
				
			}
			
			return resultsFinal;
		
	}
	
	*/

	@SuppressWarnings("unchecked")
	public List<Provider> getProviders() {
		Criteria crit = session().createCriteria(Provider.class);
		crit.createAlias("user", "u").add(Restrictions.eq("u.enabled", true));
		return crit.list();
	}

	@SuppressWarnings("unchecked")
	public List<Provider> getProviders(String username) {
		Criteria crit = session().createCriteria(Provider.class);
		crit.createAlias("user", "u");
		crit.add(Restrictions.eq("u.enabled", true));
		crit.add(Restrictions.eq("u.username", username));

		return crit.list();
	}

	// ////////////////////////////Fucntiomn to perform search///////////////


	public void saveOrUpdate(Provider provider) {
		session().saveOrUpdate(provider);
	}

	public boolean delete(int id) {
		Query query = session().createQuery("delete from provider where id=:id");
		query.setLong("id", id);
		return query.executeUpdate() == 1;
	}

	public Provider getProvider(int id) {
		Criteria crit = session().createCriteria(Provider.class);

		crit.createAlias("user", "u");

		crit.add(Restrictions.eq("u.enabled", true));
		crit.add(Restrictions.idEq(id));

		return (Provider) crit.uniqueResult();
	}
	
	public Provider getProvider(String username) {
		Criteria crit = session().createCriteria(Provider.class);

		crit.createAlias("user", "u");

		crit.add(Restrictions.eq("u.enabled", true));
		crit.add(Restrictions.idEq(username));

		return (Provider) crit.uniqueResult();
	}

}
