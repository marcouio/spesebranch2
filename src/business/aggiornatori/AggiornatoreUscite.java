package business.aggiornatori;

import aggiornatori.AggiornatoreBase;
import domain.wrapper.WrapSingleSpesa;

public class AggiornatoreUscite extends AggiornatoreBase  {

	@Override
	public boolean aggiorna() {
		try {
			return AggiornatoreManager.aggiornamentoGenerale(WrapSingleSpesa.NOME_TABELLA);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
