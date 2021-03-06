package view.impostazioni.ascoltatori;

import grafica.componenti.alert.Alert;

import java.awt.event.ActionEvent;

import view.impostazioni.GruppiView;
import business.ControlloreSpese;
import business.ascoltatori.AscoltatoreAggiornatoreTutto;
import business.cache.CacheGruppi;
import business.comandi.gruppi.CommandInserisciGruppo;
import domain.Gruppi;
import domain.wrapper.WrapGruppi;

public class AscoltatoreInserisciGruppo extends AscoltatoreAggiornatoreTutto {

	private GruppiView gruppiView;

	public AscoltatoreInserisciGruppo(final GruppiView gruppiView) {
		this.gruppiView = gruppiView;
	}

	private Gruppi gruppo1;

	@Override
	protected void actionPerformedOverride(ActionEvent e) throws Exception {
		super.actionPerformedOverride(e);

		gruppiView.setGruppo("Inserisci");
		final WrapGruppi modelGruppi = gruppiView.getModelGruppi();

		if (gruppiView.nonEsistonoCampiNonValorizzati()) {

			if (ControlloreSpese.invocaComando(new CommandInserisciGruppo(modelGruppi))) {
				gruppo1 = CacheGruppi.getSingleton().getGruppo(Integer.toString(modelGruppi.getIdGruppo()));
				if (gruppo1 != null) {
					gruppiView.getComboGruppi().addItem(gruppo1);
				}

				modelGruppi.setChanged();
				modelGruppi.notifyObservers();
				gruppiView.dispose();
			}
		} else {
			final String messaggio = "E' necessario riempire tutti i campi";
			Alert.segnalazioneErroreGrave(messaggio);
		}
	}

}
