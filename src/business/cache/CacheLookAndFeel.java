package business.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import command.javabeancommand.AbstractOggettoEntita;

import domain.Lookandfeel;
import domain.wrapper.WrapLookAndFeel;

public class CacheLookAndFeel extends AbstractCacheBase {

	private static CacheLookAndFeel	singleton;
	private WrapLookAndFeel			lookDAO	= new WrapLookAndFeel();

	private CacheLookAndFeel() {
		cache = new HashMap<String, AbstractOggettoEntita>();
	}

	public static CacheLookAndFeel getSingleton() {
		synchronized (CacheLookAndFeel.class) {
			if (singleton == null) {
				singleton = new CacheLookAndFeel();
			}

		} // if
		return singleton;
	}

	public Map<String, AbstractOggettoEntita> chargeAllLook() throws Exception {
		ArrayList<?> looks = lookDAO.selectAll();
		if (looks != null && looks.size() > 0) {
			for (int i = 0; i < looks.size(); i++) {
				Lookandfeel lookFeel = (Lookandfeel) looks.get(i);
				int id = lookFeel.getIdLook();
				if (cache.get(Integer.toString(id)) == null) {
					cache.put(Integer.toString(id), lookFeel);
				}
			}
		}
		caricata = true;
		return cache;
	}

	public Map<String, AbstractOggettoEntita> getAllLooks() throws Exception {
		if (caricata)
			return cache;
		else
			return chargeAllLook();
	}

	public Vector<Lookandfeel> getVettoreLooksPerCombo() throws Exception {
		Vector<Lookandfeel> looks = new Vector<Lookandfeel>();
		Map<String, AbstractOggettoEntita> mappa = this.getAllLooks();
		Object[] lista = mappa.values().toArray();
		Lookandfeel look = new Lookandfeel();
		look.setNome("");
		looks.add(look);
		for (int i = 0; i < lista.length; i++) {
			looks.add((Lookandfeel) lista[i]);
		}
		return looks;
	}
}
