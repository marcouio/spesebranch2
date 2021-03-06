package view.impostazioni.ascoltatori;

import grafica.componenti.alert.Alert;

import java.awt.event.ActionEvent;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;

import view.impostazioni.GruppiView;
import business.ControlloreSpese;
import business.aggiornatori.AggiornatoreManager;
import business.ascoltatori.AscoltatoreAggiornatoreTutto;
import business.cache.CacheCategorie;
import business.cache.CacheGruppi;
import business.comandi.gruppi.CommandUpdateGruppo;
import domain.Gruppi;
import domain.IGruppi;
import domain.wrapper.WrapGruppi;

public class AscoltatoreAggiornaGruppo extends AscoltatoreAggiornatoreTutto {

	private GruppiView gruppiView;

	public AscoltatoreAggiornaGruppo(final GruppiView gruppiView) {
		this.gruppiView = gruppiView;
	}

	@Override
	protected void actionPerformedOverride(final ActionEvent e) throws Exception {
		super.actionPerformedOverride(e);

		final Gruppi gruppi = (Gruppi) gruppiView.getComboGruppi().getSelectedItem();
		final WrapGruppi modelGruppi = gruppiView.getModelGruppi();

		if (gruppi != null) {
			final Gruppi oldGruppo = CacheGruppi.getSingleton().getGruppo(Integer.toString(gruppi.getIdGruppo()));
			gruppiView.setGruppo("Aggiorna");
			modelGruppi.setIdGruppo(gruppi.getIdGruppo());

			try {
				if (ControlloreSpese.invocaComando(new CommandUpdateGruppo(oldGruppo, (IGruppi) modelGruppi.getEntitaPadre()))) {

					final Vector<Gruppi> vectorGruppi = CacheGruppi.getSingleton().getVettoreCategoriePerCombo(CacheGruppi.getSingleton().getAllGruppi());
					final DefaultComboBoxModel model = new DefaultComboBoxModel(vectorGruppi);
					gruppiView.getComboGruppi().setModel(model);
					AggiornatoreManager.aggiornamentoComboBox(CacheCategorie.getSingleton().getVettoreCategorie());
					modelGruppi.setChanged();
					modelGruppi.notifyObservers();
					gruppiView.dispose();
				}
			} catch (final Exception e22) {
				e22.printStackTrace();
				Alert.segnalazioneErroreGrave("Inserisci i dati correttamente: " + e22.getMessage());
			}
		} else {
			Alert.segnalazioneErroreGrave("Impossibile aggiornare un gruppo inesistente!");
		}
	}

}
