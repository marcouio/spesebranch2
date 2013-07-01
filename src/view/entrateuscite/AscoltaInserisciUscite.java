package view.entrateuscite;

import grafica.componenti.alert.Alert;

import java.awt.event.ActionEvent;

import business.ControlloreSpese;
import business.ascoltatori.AscoltatoreAggiornatoreUscite;
import business.comandi.singlespese.CommandInserisciSpesa;

public class AscoltaInserisciUscite extends AscoltatoreAggiornatoreUscite {

	private UsciteView view;

	public AscoltaInserisciUscite(final UsciteView view) {
		this.view = view;
	}

	@Override
	protected void actionPerformedOverride(ActionEvent e) throws Exception {
		super.actionPerformedOverride(e);
		view.aggiornaModelDaVista();
		if (view.nonEsistonoCampiNonValorizzati()) {
			if (!ControlloreSpese.invocaComando(new CommandInserisciSpesa(view.getModelUscita()))) {
				String msg = ControlloreSpese.getSingleton().getMessaggio("insertcharges")+" "+ view.getModelUscita().getNome() + " "+ControlloreSpese.getSingleton().getMessaggio("failed");
				Alert.segnalazioneErroreGrave(msg);
			}
		} else {
			Alert.info(ControlloreSpese.getSingleton().getMessaggio("fillinall"), Alert.TITLE_ERROR);
		}
	}

}
