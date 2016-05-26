package ie.done.job.web.dao;



import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.WordNetDatabase;

@Repository
@Transactional
@Component("jobPostsDao")
public class JobPostsDao {

	@Autowired
	private SessionFactory sessionFactory;
	

	public Session session() {
		return sessionFactory.getCurrentSession();
	}
	

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
	public List <String> splitStringasList(String searchText) {
		List<String> items = Arrays.asList(searchText.split("\\s+"));
		items = addSynonymToSearch(items);		
		System.out.println(items);
		return items;

	}

	public boolean isStopWord(String word){
		
		String lowerWord = word.toLowerCase();
		boolean stopWord= true;
		String [] stopWords = { "an","looking", "my", "in", "and","a", "are", "as", "at", "be", "but", "by",
	            "for", "if", "in", "into", "is", "it",
	            "no", "not", "of", "on", "or", "such",
	            "that", "the", "their", "then", "there", "these",
	            "they", "this", "to", "was", "will", "with"};
		
		if(Arrays.asList(stopWords).contains(lowerWord)){
			stopWord=true;
		}
		else{
			return false;
		}
		
		return stopWord;
		
	}
	
	public boolean hasSpecialChar(String word){
		Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(word);
		boolean specialCharContained = false;
		boolean b = m.find();

		if (b){
			specialCharContained = true;
		}else{
			return specialCharContained;
		}
		return specialCharContained;
	   
	}
	
	@Transactional
	public List <String> addSynonymToSearch(List<String> splitDetails) {
		
		
		File f=new File("C://Program Files (x86)//WordNet//2.1//dict2");
        System.setProperty("wordnet.database.dir", f.toString());
	
        WordNetDatabase database = WordNetDatabase.getFileInstance();
        List<String> uniqueListOfSyn = new ArrayList<String>();

		//  Get the synsets containing the wrod form
        for(int k =0;k< splitDetails.size();k++){
	        Synset[] synsets = database.getSynsets(splitDetails.get(k));
			
			//finds all possible synonyms of each line of split details
			if (synsets.length > 0){
				
				for (int i = 0; i < synsets.length; i++){
					String[] wordForms = synsets[i].getWordForms();
					
					for (int j = 0; j < wordForms.length; j++){
						if(!uniqueListOfSyn.contains(wordForms[j])){
							if(!wordForms[j].contains(" ")&& (!isStopWord(wordForms[j])) &&(!hasSpecialChar(wordForms[j]))){
								uniqueListOfSyn.add(wordForms[j]);
							}
						}
						
						//System.out.println(wordForms[j]);
					}
				

				}
			}
			
        }
        return uniqueListOfSyn;	

	}

	@Transactional
	public List<JobPost> searchForJob(String searchText) throws Exception {
			
			List<String>  splitWords = splitStringasList(searchText);
			
			List<JobPost> results = null;
			List<JobPost> resultsFinal = new ArrayList<JobPost>();
			Session session = sessionFactory.getCurrentSession();
			FullTextSession fullTextSession = Search
					.getFullTextSession(session);
			
			
			//System.out.println(searchText + "in the search here");
			QueryBuilder qb = fullTextSession.getSearchFactory()
					.buildQueryBuilder().forEntity(JobPost.class).get();
			
			for(int i = 0; i<splitWords.size();i++){
				org.apache.lucene.search.Query query = qb.keyword()
						.onFields("description", "title", "domain", "location")
						.matching(splitWords.get(i)).createQuery();
	
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
