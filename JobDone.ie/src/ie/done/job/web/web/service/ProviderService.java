package ie.done.job.web.web.service;

import ie.done.job.web.dao.Provider;
import ie.done.job.web.dao.OffersDao;
import ie.done.job.web.dao.ProviderDao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

@Service("providerService")
public class ProviderService {

	private ProviderDao providerDao;

	@Autowired
	public void setProviderDao(ProviderDao providerDao) {
		this.providerDao = providerDao;
	}

	public List<Provider> getCurrent() {
		return providerDao.getProviders();
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN", "ROLE_TRADE" })
	public void create(Provider provider) {
		providerDao.saveOrUpdate(provider);
	}
	//an offer exists for that user alreaddy and eligiable for edit or delete of an offer
	public boolean hasProvider(String name) {

		if (name == null) {
			return false;
		}

		List<Provider> offers = providerDao.getProviders(name);

		if (offers.size() == 0) {
			return false;
		}

		return true;
	}

	public Provider getProvider(String username) {

		if (username == null) {
			return null;
		}

		List<Provider> provider = providerDao.getProviders(username);
		//stops trying to return first item of an empty list
		if (provider.size() == 0) {
			return null;
		}
		

		return provider.get(0);
	}

	public void saveOrUpdate(Provider provider) {
		//hibernate looks at primary key and checks is it in db or not
		providerDao.saveOrUpdate(provider);
	}

	public void delete(int id) {
		//deletes from DB
		providerDao.delete(id);
	}
}
