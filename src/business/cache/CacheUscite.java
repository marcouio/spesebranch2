package business.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import view.impostazioni.Impostazioni;
import business.ControlloreSpese;

import command.javabeancommand.AbstractOggettoEntita;

import domain.SingleSpesa;
import domain.Utenti;
import domain.wrapper.WrapSingleSpesa;

public class CacheUscite extends AbstractCacheBase {

	private static CacheUscite	singleton;

	private CacheUscite() {
		cache = new HashMap<String, AbstractOggettoEntita>();
	}

	public static CacheUscite getSingleton() {
		synchronized (CacheUscite.class) {
			if (singleton == null) {
				singleton = new CacheUscite();
			}
		} // if
		return singleton;
	}

	WrapSingleSpesa	usciteDAO	= new WrapSingleSpesa();

	public SingleSpesa getSingleSpesa(final String id) throws Exception {
		SingleSpesa uscita = (SingleSpesa) cache.get(id);
		if (uscita == null) {
			uscita = caricaSingleSpesa(id);
			if (uscita != null) {
				cache.put(id, uscita);
			}
		}
		return (SingleSpesa) cache.get(id);
	}

	private SingleSpesa caricaSingleSpesa(final String id) throws Exception {
		return (SingleSpesa) new WrapSingleSpesa().selectById(Integer.parseInt(id));
	}

	private Map<String, AbstractOggettoEntita> chargeAllUscite() throws Exception {
		final ArrayList<SingleSpesa> uscite = usciteDAO.selectAll();
		if (uscite != null) {
			for (int i = 0; i < uscite.size(); i++) {
				final SingleSpesa uscita = (SingleSpesa) uscite.get(i);
				final int id = uscita.getIdSpesa();
				if (cache.get(Integer.toString(id)) == null) {
					cache.put(Integer.toString(id), uscita);
				}
			}
			caricata = true;
		}
		else {
			cache = new HashMap<String, AbstractOggettoEntita>();
		}
		return cache;
	}

	public Map<String, AbstractOggettoEntita> getAllUscite() throws Exception {
		if (caricata) {
			return cache;
		}
		else {
			return chargeAllUscite();
		}
	}

	public ArrayList<SingleSpesa> getAllUsciteForUtente() throws Exception {
		final ArrayList<SingleSpesa> listaUscite = new ArrayList<SingleSpesa>();
		final Map<String, AbstractOggettoEntita> mappa = getAllUscite();
		final Utenti utente = (Utenti) ControlloreSpese.getSingleton().getUtenteLogin();
		if (mappa != null && utente != null) {
			final Iterator<String> chiavi = mappa.keySet().iterator();
			while (chiavi.hasNext()) {
				final SingleSpesa uscita = (SingleSpesa) mappa.get(chiavi.next());
				if (uscita != null && uscita.getUtenti() != null) {
					if (uscita.getUtenti().getIdUtente() == utente.getIdUtente()) {
						listaUscite.add(uscita);
					}
				}
			}
		}
		return listaUscite;
	}

	public ArrayList<SingleSpesa> getAllUsciteForUtenteEAnno() throws Exception {
		final ArrayList<SingleSpesa> listaUscite = new ArrayList<SingleSpesa>();
		final Map<String, AbstractOggettoEntita> mappa = getAllUscite();
		final Utenti utente = (Utenti) ControlloreSpese.getSingleton().getUtenteLogin();
		final String annoDaText = Impostazioni.getSingleton().getAnnotextField().getText();

		if (mappa != null && utente != null) {
			final Iterator<String> chiavi = mappa.keySet().iterator();
			while (chiavi.hasNext()) {
				final SingleSpesa uscita = (SingleSpesa) mappa.get(chiavi.next());
				if (uscita != null && uscita.getUtenti() != null) {
					final String annoUscita = uscita.getData().substring(0, 4);
					if (uscita.getUtenti().getIdUtente() == utente.getIdUtente() && annoUscita.equals(annoDaText)) {
						listaUscite.add(uscita);
					}
				}
			}
		}
		return listaUscite;
	}

	public int getMaxId() throws Exception {
		int maxId = 0;
		final Map<String, AbstractOggettoEntita> mappa = getAllUscite();
		if (mappa != null) {
			final Iterator<String> chiavi = mappa.keySet().iterator();
			while (chiavi.hasNext()) {
				final SingleSpesa uscita = (SingleSpesa) mappa.get(chiavi.next());
				if (uscita != null) {
					final int idSpesa = uscita.getIdSpesa();
					if (idSpesa > maxId) {
						maxId = idSpesa;
					}
				}
			}
		}
		return maxId;
	}

}
