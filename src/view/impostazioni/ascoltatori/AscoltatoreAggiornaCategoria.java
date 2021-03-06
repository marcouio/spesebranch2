package view.impostazioni.ascoltatori;

import grafica.componenti.alert.Alert;

import java.awt.event.ActionEvent;

import view.impostazioni.CategorieView;
import business.ControlloreSpese;
import business.aggiornatori.AggiornatoreManager;
import business.ascoltatori.AscoltatoreAggiornatoreTutto;
import business.cache.CacheCategorie;
import business.comandi.categorie.CommandUpdateCategoria;
import domain.CatSpese;
import domain.ICatSpese;

public class AscoltatoreAggiornaCategoria extends AscoltatoreAggiornatoreTutto {

	CategorieView categorieView;

	public AscoltatoreAggiornaCategoria(final CategorieView categorieView) {
		this.categorieView = categorieView;
	}

	@Override
	protected void actionPerformedOverride(final ActionEvent e) throws Exception {
		super.actionPerformedOverride(e);
		final CatSpese oldCategoria = CacheCategorie.getSingleton().getCatSpese(Integer.toString(categorieView.getCategoria().getIdCategoria()));

		if (categorieView.getComboCategorie().getSelectedItem() != null) {
			categorieView.aggiornaModelDaVista("Aggiorna");
			if (categorieView.getCategoria() != null) {
				categorieView.getModelCatSpese().setIdCategoria(categorieView.getCategoria().getIdCategoria());
			}
			try {
				if (ControlloreSpese.invocaComando(new CommandUpdateCategoria(oldCategoria, (ICatSpese) categorieView.getModelCatSpese().getEntitaPadre()))) {
					AggiornatoreManager.aggiornaCategorie((CatSpese) categorieView.getModelCatSpese().getEntitaPadre(), categorieView.getComboCategorie());
					categorieView.getModelCatSpese().setChanged();
					categorieView.getModelCatSpese().notifyObservers();
					categorieView.dispose();
				}
			} catch (final Exception e22) {
				e22.printStackTrace();
				Alert.segnalazioneErroreGrave("Inserisci i dati correttamente: " + e22.getMessage());
			}
		} else {
			Alert.segnalazioneErroreGrave("Impossibile aggiornare una categoria inesistente!");
		}
	}

}
