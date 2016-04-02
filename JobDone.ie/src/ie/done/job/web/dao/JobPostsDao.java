package ie.done.job.web.dao;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
import org.apache.lucene.analysis.*;
import org.tartarus.snowball.ext.EnglishStemmer;

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
	
	//***************************
	//change find job to take list instead
	
	
	@Transactional
	public List <String> splitStringasList(String searchText) {
		List<String> items = Arrays.asList(searchText.split("\\s+"));
		//List<String> splitList = searchText.split("\\s+");
		
		return items;

	}
	@Transactional
	public String[] splitString(String searchText) {
		String[] splitArray = searchText.split("\\s+");
		
		return splitArray;
		

	}
	
	
	//splits a field for the search,ie. description taken as string and split to array
	@Transactional
	public String[] splitStringTest(String searchText) {
		String[] splitArray = searchText.split("\\s+");
		EnglishStemmer stemmer = new EnglishStemmer();
		for(int i = 0; i< splitArray.length;i++){
			stemmer.setCurrent(splitArray[i]);
			//if the word can be stemmed it will set it
			if(stemmer.stem()){
				splitArray[i] = stemmer.getCurrent();	
			}
		}
		
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
						.onFields("description", "title", "domain", "location")
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
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<JobPost> recommendJob(Provider provider) throws Exception {
			//String [] splitWords = splitString(searchText);
			
			//String [] splitWords = null;
			String [] stopWords = {"a", "an", "and", "are", "as", "at", "be", "but", "by",
			                       "for", "if", "in", "into", "is", "it",
			                       "no", "not", "of", "on", "or", "such",
			                       "that", "the", "their", "then", "there", "these",
			                       "they", "this", "to", "was", "will", "with"};
			List<JobPost> results = null;
			List<JobPost> resultsFinal = new ArrayList<JobPost>();
			List<String> profileDetails = new ArrayList<String>();
			List<String> splitDetails = new ArrayList<String>();
			List<String> holder = new ArrayList<String>();
			//takes in full sentances and must be split in order to process
			profileDetails.add(provider.getTitle());
			profileDetails.add(provider.getExperience());
			profileDetails.add(provider.getDomain());
			profileDetails.add(provider.getQualifications());
			profileDetails.add(provider.getDescription());
			//String [] splitWords = null;
			for(int i = 0; i<profileDetails.size();i++){
				//if(stopWords[i])
					holder = splitStringasList(profileDetails.get(i));
					splitDetails.addAll(holder);
					//+++++++++++++++++++++++++++++++++++++++++++++++++++
					System.out.println(splitDetails.size() + "############### ");
					
			}
			for(int j = 0; j<splitDetails.size();j++){
				System.out.println(splitDetails.get(j)+ "=====================");
				//remove all the stop words from the search
				for(int s = 0; s<stopWords.length;s++){
					if((splitDetails.get(j).equals(stopWords[s]))){
						System.out.println(stopWords[s]+ " %%%%%%%%%%%%%%%%%%%");
						splitDetails.remove(j);
					}
				}
				
				
			}
			
			
			
			
			
			Session session = sessionFactory.getCurrentSession();
			//System.out.println(searchText + " in the search here1");
			FullTextSession fullTextSession = Search
					.getFullTextSession(session);
			
			//System.out.println(searchText + "in the search here");
			QueryBuilder qb = fullTextSession.getSearchFactory()
					.buildQueryBuilder().forEntity(JobPost.class).get();
			
			for(int i = 0; i<splitDetails.size()-1;i++){
				org.apache.lucene.search.Query query = qb.keyword()
						.onFields("description", "title", "domain", "location")
						.matching(splitDetails.get(i)).createQuery();
	
				org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(
						query, JobPost.class);
			
				results = hibQuery.list();
				if(!results.isEmpty()){
					resultsFinal.addAll(results);
				}
				
				
			}
			
			return resultsFinal;
		
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
	
		return crit.list();
	}

	/*
	 * Criteria cri = session().createCriteria(User.class);
	 * cri.add(Restrictions.eq("email", email)); User user = (User)
	 * cri.uniqueResult();
	 */

	public void saveOrUpdate(JobPost JobPost) {
		Date currentDate = new Date();
		JobPost.setDate(currentDate);
		session().saveOrUpdate(JobPost);
	}
	
	
    public void updateJobPost(JobPost jobPost) {
        session().update(jobPost);
        //logger.info("Person updated successfully, Person Details="+p);
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

	public boolean delete(String username) {
		Query query = session().createQuery("delete from JobPost where username=:username");
		
		query.setString("username", username);
		return query.executeUpdate() == 1;
		// TODO Auto-generated method stub
		
	}

}
