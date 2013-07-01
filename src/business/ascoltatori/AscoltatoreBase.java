package business.ascoltatori;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import aggiornatori.IAggiornatore;
import business.ControlloreSpese;

public abstract class AscoltatoreBase implements ActionListener {

	protected IAggiornatore aggiornatore;

	public AscoltatoreBase(final String cosaAggiornare) {
		aggiornatore = ControlloreSpese.getSingleton().getAggiornatoreManager().creaAggiornatore(cosaAggiornare);
	}

	public AscoltatoreBase(final IAggiornatore aggiornatore) {
		this.aggiornatore = aggiornatore;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		try {
			actionPerformedOverride(e);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		aggiornatore.aggiorna();
	}

	/**
	 * Questo è il metodo del listener che va implementato al posto dell'actionPerformed. 
	 * Occhio ad utilizzare questo e non l'altro
	 * 
	 * @param e
	 * @throws Exception 
	 */
	protected abstract void actionPerformedOverride(final ActionEvent e) throws Exception;

}
