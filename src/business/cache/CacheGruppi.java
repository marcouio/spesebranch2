package business.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import command.javabeancommand.AbstractOggettoEntita;

import domain.Gruppi;
import domain.wrapper.WrapGruppi;

public class CacheGruppi extends AbstractCacheBase {

	private static CacheGruppi	singleton;

	private CacheGruppi() {
		cache = new HashMap<String, AbstractOggettoEntita>();
	}

	public static CacheGruppi getSingleton() {
		synchronized (CacheGruppi.class) {
			if (singleton == null) {
				singleton = new CacheGruppi();
			}
		} // if
		return singleton;
	}

	WrapGruppi	gruppiDAO	= new WrapGruppi();

	public Gruppi getGruppo(final String id) throws Exception {
		Gruppi gruppo = (Gruppi) cache.get(id);
		if (gruppo == null) {
			gruppo = caricaGruppo(id);
			if (gruppo != null) {
				cache.put(id, gruppo);
			}
		}
		return (Gruppi) cache.get(id);
	}

	public Gruppi getGruppoPerNome(final String nome) throws Exception {
		Gruppi gruppo = (Gruppi) cache.get(nome);
		if (gruppo == null) {
			gruppo = caricaGruppoPerNome(nome);
			if (gruppo != null) {
				cache.put(Integer.toString(gruppo.getIdGruppo()), gruppo);
			}
		}
		return gruppo;
	}

	private Gruppi caricaGruppoPerNome(final String nome) throws Exception {
		return new WrapGruppi().selectByNome(nome);
	}

	private Gruppi caricaGruppo(final String id) throws Exception {
		return (Gruppi) new WrapGruppi().selectById(Integer.parseInt(id));
	}

	public Map<String, AbstractOggettoEntita> chargeAllGruppi() throws Exception {
		final ArrayList<?> gruppi = gruppiDAO.selectAll();
		if (gruppi != null && gruppi.size() > 0) {
			for (int i = 0; i < gruppi.size(); i++) {
				final Gruppi gruppo = (Gruppi) gruppi.get(i);
				final int id = gruppo.getIdGruppo();
				if (cache.get(Integer.toString(id)) == null) {
					cache.put(Integer.toString(id), gruppo);
				}
			}
			caricata = true;
		}
		return cache;
	}

	public Map<String, AbstractOggettoEntita> getAllGruppi() throws Exception {
		if (caricata) {
			return cache;
		}
		else {
			return chargeAllGruppi();
		}
	}

	public Vector<Gruppi> getVettoreGruppiSenzaZero() throws Exception {
		final Vector<Gruppi> gruppi = new Vector<Gruppi>();
		final Map<String, AbstractOggettoEntita> mappa = this.getAllGruppi();
		final Object[] lista = mappa.values().toArray();
		for (final Object element : lista) {
			final Gruppi gruppo = (Gruppi) element;
			if (gruppo != null && gruppo.getNome() != null) {
				gruppi.add((Gruppi) element);
			}
		}
		return gruppi;
	}

	public Vector<Gruppi> getVettoreGruppi() throws Exception {
		final Vector<Gruppi> gruppi = new Vector<Gruppi>();
		final Map<String, AbstractOggettoEntita> mappa = this.getAllGruppi();
		final Object[] lista = mappa.values().toArray();
		for (int i = lista.length - 1; i >= 0; i--) {
			gruppi.add((Gruppi) lista[i]);
		}
		return gruppi;
	}

	public Vector<Gruppi> getVettoreCategoriePerCombo(final Map<String, AbstractOggettoEntita> mappa) {
		final Vector<Gruppi> gruppi = new Vector<Gruppi>();
		final Object[] lista = mappa.values().toArray();
		final Gruppi gruppo = new Gruppi();
		gruppo.setNome("");
		for (final Object element : lista) {
			gruppi.add((Gruppi) element);
		}
		gruppi.add(0, gruppo);
		return gruppi;
	}

	public int getMaxId() throws Exception {
		int maxId = 0;
		final Map<String, AbstractOggettoEntita> mappa = getAllGruppi();
		if (mappa != null) {
			final Iterator<String> chiavi = mappa.keySet().iterator();
			while (chiavi.hasNext()) {
				final Gruppi gruppo = (Gruppi) mappa.get(chiavi.next());
				if (gruppo != null) {
					final int idGruppo = gruppo.getIdGruppo();
					if (idGruppo > maxId) {
						maxId = idGruppo;
					}
				}
			}
		}
		return maxId;
	}

}
