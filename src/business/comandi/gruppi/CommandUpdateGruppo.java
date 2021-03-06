package business.comandi.gruppi;

import grafica.componenti.alert.Alert;

import java.util.HashMap;

import business.cache.CacheGruppi;

import command.javabeancommand.AbstractCommandForJavaBean;
import command.javabeancommand.AbstractOggettoEntita;

import domain.Gruppi;
import domain.IGruppi;
import domain.wrapper.WrapGruppi;

public class CommandUpdateGruppo extends AbstractCommandForJavaBean {

	final private Gruppi                                 newEntita;
	final private Gruppi                                 oldEntita;

	public CommandUpdateGruppo(final Gruppi oldEntita, final IGruppi newEntita) {
		this.newEntita = (Gruppi) newEntita;
		this.oldEntita = oldEntita;
		this.wrap = new WrapGruppi();
		final CacheGruppi cache = CacheGruppi.getSingleton();
		mappaCache = (HashMap<String, AbstractOggettoEntita>) cache.getCache();
	}

	@Override
	public boolean execute() throws Exception {
		if (newEntita instanceof Gruppi) {
			if (wrap.update(newEntita)) {
				mappaCache.put(Integer.toString(newEntita.getIdGruppo()), newEntita);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean unExecute() throws Exception {
		if (oldEntita instanceof Gruppi) {
			if (wrap.update(oldEntita)) {
				mappaCache.put(Integer.toString(oldEntita.getIdGruppo()), oldEntita);
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "Modificata Gruppo " + (newEntita).getNome();
	}

	@Override
	public void scriviLogExecute(boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Aggiornato correttamente gruppo " + newEntita.getNome());
		}

	}

	@Override
	public void scriviLogUnExecute(boolean isComandoEseguito) {
		if (isComandoEseguito) {
			Alert.segnalazioneInfo("Ripristinato gruppo " + oldEntita.getNome() + " precedentemente aggiornato");
		}
	}
}
