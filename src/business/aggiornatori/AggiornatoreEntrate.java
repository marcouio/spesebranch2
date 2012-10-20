package business.aggiornatori;

import aggiornatori.AggiornatoreBase;
import domain.wrapper.WrapEntrate;

public class AggiornatoreEntrate extends AggiornatoreBase {

	@Override
	public boolean aggiorna() {
		try {
			return AggiornatoreManager.aggiornamentoGenerale(WrapEntrate.NOME_TABELLA);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
