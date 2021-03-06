package view.entrateuscite;

import grafica.componenti.alert.Alert;

import java.awt.event.ActionEvent;

import business.ControlloreSpese;
import business.ascoltatori.AscoltatoreAggiornatoreEntrate;
import business.comandi.entrate.CommandInserisciEntrata;

public class AscoltaInserisciEntrate extends AscoltatoreAggiornatoreEntrate {

	private EntrateView view;

	public AscoltaInserisciEntrate(final EntrateView view) {
		this.view = view;
	}

	@Override
	public void actionPerformedOverride(final ActionEvent e) throws Exception {
		view.aggiornaModelDaVista();

		if (view.nonEsistonoCampiNonValorizzati()) {
			if (ControlloreSpese.invocaComando(new CommandInserisciEntrata(view.getModelEntrate()))) {
				view.dispose();
			}
		} else {
			Alert.segnalazioneErroreWarning(ControlloreSpese.getSingleton().getMessaggio("fillinall"));
		}

	}

}
