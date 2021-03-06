package view.impostazioni.ascoltatori;

import grafica.componenti.alert.Alert;

import java.awt.event.ActionEvent;

import view.impostazioni.CategorieView;
import business.ControlloreSpese;
import business.ascoltatori.AscoltatoreAggiornatoreTutto;
import business.cache.CacheCategorie;
import business.comandi.categorie.CommandInserisciCategoria;
import domain.CatSpese;

public class AscoltatoreInserisciCategoria extends AscoltatoreAggiornatoreTutto {

	private final CategorieView categorieView;
	private CatSpese      categoria1;

	public AscoltatoreInserisciCategoria(final CategorieView categorieView) {
		this.categorieView = categorieView;
	}

	@Override
	protected void actionPerformedOverride(final ActionEvent e) throws Exception {
		super.actionPerformedOverride(e);
		categorieView.aggiornaModelDaVista("Inserisci");
		if (categorieView.nonEsistonoCampiNonValorizzati()) {

			if (ControlloreSpese.invocaComando(new CommandInserisciCategoria(categorieView.getModelCatSpese()))) {
				categoria1 = CacheCategorie.getSingleton().getCatSpese(Integer.toString(categorieView.getModelCatSpese().getIdCategoria()));
				if (categoria1 != null) {
					categorieView.getComboCategorie().addItem(categoria1);
				}
				//				Alert.operazioniSegnalazioneInfo("Inserita correttamente categoria: " + categorieView.getModelCatSpese().getNome());
				categorieView.getModelCatSpese().setChanged();
				categorieView.getModelCatSpese().notifyObservers();
				categorieView.dispose();
			}
		} else {
			Alert.segnalazioneErroreGrave("E' necessario riempire tutti i campi");
		}
	}
}
