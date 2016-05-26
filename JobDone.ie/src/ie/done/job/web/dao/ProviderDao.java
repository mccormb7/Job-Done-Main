package ie.done.job.web.dao;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.WordNetDatabase;

@Repository
@Transactional
@Component("providerDao")
public class ProviderDao {

	@Autowired
	private SessionFactory sessionFactory;

	public Session session() {
		return sessionFactory.getCurrentSession();
	}
	


	@Transactional
	public void indexProviders() throws Exception {
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
	

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Provider> searchForProvider(String searchTextPro) throws Exception {
			//allows a client search for specific tradesmen

			//String [] splitWords = splitString(searchTextPro);
			List<String> synonym = new ArrayList<String>();
			
			synonym= splitStringasList(searchTextPro);
					
			List<Provider> results = null;
			List<Provider> resultsFinal = new ArrayList<Provider>();
			Session session = sessionFactory.getCurrentSession();
			System.out.println(searchTextPro + " in the search here1");
			FullTextSession fullTextSession = Search
					.getFullTextSession(session);
			
			//System.out.println(searchText + "in the search here");
			QueryBuilder qb = fullTextSession.getSearchFactory()
					.buildQueryBuilder().forEntity(Provider.class).get();
			//title, experience,
			for(int i = 0; i<synonym.size();i++){
				org.apache.lucene.search.Query query = qb.keyword()
						.onFields("experience", "title", "domain","qualifications", "location")
						.matching(synonym.get(i)).createQuery();
	
				org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(
						query, Provider.class);
			
				results = hibQuery.list();
				if(!results.isEmpty()){
					resultsFinal.addAll(results);
				}
				//"experience", "title", "domain","qualifications", "location"
				
			}
			
			return resultsFinal;
		
	}

	


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
		Date currentDate = new Date();
		provider.setDate(currentDate);
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
