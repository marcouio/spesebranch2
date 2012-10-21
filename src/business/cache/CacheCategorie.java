package business.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import business.DBUtil;

import command.javabeancommand.AbstractOggettoEntita;

import domain.CatSpese;
import domain.wrapper.WrapCatSpese;

public class CacheCategorie extends AbstractCacheBase {

	private static CacheCategorie singleton;

	private CacheCategorie() {
		cache = new HashMap<String, AbstractOggettoEntita>();
	}

	public static CacheCategorie getSingleton() {
		if (singleton == null) {
			synchronized (CacheCategorie.class) {
				if (singleton == null) {
					singleton = new CacheCategorie();
				}
			} // if
		} // if
		return singleton;
	}

	WrapCatSpese catSpeseDAO = new WrapCatSpese();

	public CatSpese getCatSpese(final String id) {
		CatSpese categoria = (CatSpese) cache.get(id);
		if (categoria == null) {
			categoria = caricaCategoria(id);
			if (categoria != null) {
				cache.put(id, categoria);
			}
		}
		DBUtil.closeConnection();
		return (CatSpese) cache.get(id);
	}

	private CatSpese caricaCategoria(final String id) {
		final CatSpese categorie = (CatSpese) new WrapCatSpese().selectById(Integer.parseInt(id));
		return categorie;
	}

	private HashMap<String, AbstractOggettoEntita> chargeAllCategorie() {
		final ArrayList<Object> categorie = catSpeseDAO.selectAll();
		if (categorie != null && categorie.size() > 0) {
			for (int i = 0; i < categorie.size(); i++) {
				final CatSpese categoria = (CatSpese) categorie.get(i);
				final int id = categoria.getIdCategoria();
				if (cache.get(id) == null) {
					cache.put(Integer.toString(id), categoria);
				}
			}
		}
		caricata = true;
		return cache;
	}

	public HashMap<String, AbstractOggettoEntita> getAllCategorie() {
		if (!caricata) {
			cache = chargeAllCategorie();
		}
		return cache;
	}

	public Vector<CatSpese> getVettoreCategoriePerCombo(final Map<String, AbstractOggettoEntita> mappa) {
		final Vector<CatSpese> categorie = new Vector<CatSpese>();
		final Object[] lista = mappa.values().toArray();
		final CatSpese categoria = new CatSpese();
		categoria.setNome("");
		for (int i = 0; i < lista.length; i++) {
			categorie.add((CatSpese) lista[i]);
		}
		categorie.add(0, categoria);
		return categorie;
	}

	public Vector<CatSpese> getVettoreCategoriePerCombo() {
		final Vector<CatSpese> categorie = new Vector<CatSpese>();
		final Map<String, AbstractOggettoEntita> mappa = this.getAllCategorie();
		final Object[] lista = mappa.values().toArray();
		final CatSpese categoria = new CatSpese();
		categoria.setNome("");
		for (int i = 0; i < lista.length; i++) {
			categorie.add((CatSpese) lista[i]);
		}
		categorie.add(0, categoria);
		return categorie;
	}

	public Vector<CatSpese> getCategorieSenzaGruppo() {
		final Vector<CatSpese> allCategorie = getVettoreCategorie();
		final Vector<CatSpese> catSenzaGruppo = new Vector<CatSpese>();
		for (int i = 0; i < allCategorie.size(); i++) {
			final CatSpese categoria = allCategorie.get(i);
			if (categoria.getGruppi() == null ) {
				catSenzaGruppo.add(categoria);
			}
		}
		return catSenzaGruppo;
	}

	public Vector<CatSpese> getVettoreCategorie(final Map<String, CatSpese> mappa) {
		final Vector<CatSpese> categorie = new Vector<CatSpese>();
		final Object[] lista = mappa.values().toArray();
		for (int i = 0; i < lista.length; i++) {
			categorie.add((CatSpese) lista[i]);
		}
		return categorie;
	}

	public String[] getArrayCategorie(){
		final ArrayList<String> nomiCategorie = new ArrayList<String>();
		final Map<String, AbstractOggettoEntita> mappa = this.getAllCategorie();
		final Object[] lista = mappa.values().toArray();
		for (int i = 0; i < lista.length; i++) {
			nomiCategorie.add(((CatSpese) lista[i]).getNome());
		}
		return nomiCategorie.toArray(new String[nomiCategorie.size()]);
	}

	public Vector<CatSpese> getVettoreCategorie() {
		final Vector<CatSpese> categorie = new Vector<CatSpese>();
		final Map<String, AbstractOggettoEntita> mappa = this.getAllCategorie();
		final Object[] lista = mappa.values().toArray();
		for (int i = 0; i < lista.length; i++) {
			categorie.add((CatSpese) lista[i]);
		}
		return categorie;
	}

	public Object[] arrayCategorie() {
		final Map<String, AbstractOggettoEntita> mappa = this.getAllCategorie();
		return mappa.values().toArray();
	}

	public int getMaxId() {
		int maxId = 0;
		final Map<String, AbstractOggettoEntita> mappa = getAllCategorie();
		final Iterator<String> chiavi = mappa.keySet().iterator();
		if (mappa != null) {
			while (chiavi.hasNext()) {
				final CatSpese categoria = (CatSpese) mappa.get(chiavi.next());
				if (categoria != null) {
					final int idCategorie = categoria.getIdCategoria();
					if (idCategorie > maxId) {
						maxId = idCategorie;
					}
				}
			}
		}
		return maxId;
	}

}
