package business.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import command.javabeancommand.AbstractOggettoEntita;

import domain.Budget;
import domain.wrapper.WrapBudget;

public class CacheBudget extends AbstractCacheBase {

	private static CacheBudget	singleton;
	WrapBudget					budgetDAO	= new WrapBudget();

	private CacheBudget() {
		cache = new HashMap<String, AbstractOggettoEntita>();
	}

	public static CacheBudget getSingleton() {
		if (singleton == null) {
			synchronized (CacheBudget.class) {
				singleton = new CacheBudget();
			}
		} // if
		return singleton;
	}

	/**
	 * Viene cercato il budget nella cache, se non lo trova lo carica dal
	 * database. Se non presente viene inserito nella cache
	 * 
	 * @param id
	 * @return budget
	 */
	public Budget getBudget(String id) {
		try {
			Budget budget = (Budget) cache.get(id);
			if (budget == null) {
				budget = caricaBudget(id);
				if (budget != null) {
					cache.put(id, budget);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return (Budget) cache.get(id);
	}

	/**
	 * Carica il budget dal database
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 * @throws NumberFormatException
	 */
	private Budget caricaBudget(String id) throws Exception {
		return (Budget) new WrapBudget().selectById(Integer.parseInt(id));
	}

	/**
	 * Carica tutti i budget dal database e poi li inserisce nella cache
	 * 
	 * @return
	 */
	public Map<String, AbstractOggettoEntita> chargeAllBudget() {
		try {
			Vector<Object> budgets = budgetDAO.selectAll();
			if (budgets != null && budgets.size() > 0) {
				for (int i = 0; i < budgets.size(); i++) {
					Budget budget = (Budget) budgets.get(i);
					int id = budget.getIdBudget();
					if (cache.get(Integer.toString(id)) == null) {
						cache.put(Integer.toString(id), budget);
					}
				}
				caricata = true;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return cache;
	}

	public Map<String, AbstractOggettoEntita> getAllBudget() {
		if (caricata)
			return cache;
		else
			return chargeAllBudget();
	}

}
