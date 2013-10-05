package business.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import command.javabeancommand.AbstractOggettoEntita;

import domain.Utenti;
import domain.wrapper.WrapUtenti;

public class CacheUtenti extends AbstractCacheBase {

	private static CacheUtenti	singleton;

	private CacheUtenti() {
		cache = new HashMap<String, AbstractOggettoEntita>();
	}

	public static CacheUtenti getSingleton() {
		synchronized (CacheUtenti.class) {
			if (singleton == null) {
				singleton = new CacheUtenti();
			}
		} // if
		return singleton;
	}

	WrapUtenti	utentiDAO	= new WrapUtenti();

	public boolean checkUtentePerUsername(String username) throws Exception {
		boolean ok = false;
		Object[] utenti = getArrayUtenti();
		for (int i = 0; i < utenti.length; i++) {
			Utenti utente = (Utenti) utenti[i];
			if (utente.getUsername().equals(username)) {
				ok = true;
			}
		}
		return ok;
	}

	public Utenti getUtente(String id) throws Exception {
		Utenti utenti = (Utenti) cache.get(id);
		if (utenti == null) {
			utenti = caricaUtenti(id);
			if (utenti != null) {
				cache.put(id, utenti);
			}
		}
		return (Utenti) cache.get(id);
	}

	private Utenti caricaUtenti(String id) throws Exception {
		return (Utenti) new WrapUtenti().selectById(Integer.parseInt(id));
	}

	public Map<String, AbstractOggettoEntita> chargeAllUtenti() throws Exception {
		ArrayList<?> utenti = utentiDAO.selectAll();
		if (utenti != null && utenti.size() > 0) {
			for (int i = 0; i < utenti.size(); i++) {
				Utenti utente = (Utenti) utenti.get(i);
				int id = utente.getIdUtente();
				if (cache.get(Integer.toString(id)) == null) {
					cache.put(Integer.toString(id), utente);
				}
			}
			caricata = true;
		}
		return cache;
	}

	public Map<String, AbstractOggettoEntita> getAllUtenti() throws Exception {
		if (caricata)
			return cache;
		else
			return chargeAllUtenti();
	}

	public Vector<Utenti> getVettoreUtenti() throws Exception {
		Vector<Utenti> utenti = new Vector<Utenti>();
		Map<String, AbstractOggettoEntita> mappa = this.getAllUtenti();
		Utenti[] lista = (Utenti[]) mappa.values().toArray(new Utenti[mappa.values().size()]);
		for (int i = 0; i < lista.length; i++) {
			utenti.add(lista[i]);
		}
		return utenti;
	}

	public Object[] getArrayUtenti() throws Exception {
		Map<String, AbstractOggettoEntita> mappa = this.getAllUtenti();
		return (Object[]) mappa.values().toArray();
	}
}
