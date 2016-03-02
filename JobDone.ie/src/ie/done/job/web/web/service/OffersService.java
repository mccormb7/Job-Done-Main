package ie.done.job.web.web.service;

import ie.done.job.web.dao.Offer;
import ie.done.job.web.dao.OffersDao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

@Service("offersService")
public class OffersService {

	private OffersDao offersDao;

	@Autowired
	public void setOffersDao(OffersDao offersDao) {
		this.offersDao = offersDao;
	}

	public List<Offer> getCurrent() {
		return offersDao.getOffers();
	}

	@Secured({ "ROLE_USER", "ROLE_ADMIN" })
	public void create(Offer offer) {
		offersDao.saveOrUpdate(offer);
	}
	//an offer exists for that user alreaddy and eligiable for edit or delete of an offer
	public boolean hasOffer(String name) {

		if (name == null) {
			return false;
		}

		List<Offer> offers = offersDao.getOffers(name);

		if (offers.size() == 0) {
			return false;
		}

		return true;
	}

	public Offer getOffer(String username) {

		if (username == null) {
			return null;
		}

		List<Offer> offers = offersDao.getOffers(username);
		//stops trying to return first item of an empty list
		if (offers.size() == 0) {
			return null;
		}

		return offers.get(0);
	}

	public void saveOrUpdate(Offer offer) {
		//hibernate looks at primary key and checks is it in db or not
		offersDao.saveOrUpdate(offer);
	}

	public void delete(int id) {
		//deletes from DB
		offersDao.delete(id);
	}
}
