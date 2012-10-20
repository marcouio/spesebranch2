package business.aggiornatori;

import aggiornatori.AggiornatoreBase;

public class AggiornatoreTotale extends AggiornatoreBase  {

	@Override
	public boolean aggiorna() {
		return AggiornatoreManager.aggiornamentoPerImpostazioni();
	}

}
