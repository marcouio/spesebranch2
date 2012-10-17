package view.entrateuscite;

import java.util.Observer;

import javax.swing.JDialog;

import business.CorreggiTesto;
import domain.CatSpese;
import domain.Utenti;
import domain.wrapper.WrapSingleSpesa;

public abstract class AbstractUsciteView extends JDialog implements Observer {

	public static final long serialVersionUID = 1L;
	public WrapSingleSpesa modelUscita = null;

	public WrapSingleSpesa getModelUscita() {
		return modelUscita;
	}

	public void setModelUscita(final WrapSingleSpesa modelUscita) {
		this.modelUscita = modelUscita;
	}

	public AbstractUsciteView(final WrapSingleSpesa modelUscita) {
		this.modelUscita = modelUscita;
	}

	protected String getcNome() {
		return modelUscita.getNome();
	}

	protected void setcNome(final String cNome) {
		final CorreggiTesto correttoreTesto = new CorreggiTesto(cNome);
		modelUscita.setNome(correttoreTesto.getTesto());
	}

	protected String getcData() {
		return modelUscita.getData();
	}

	protected void setcData(final String cData) {
		modelUscita.setData(cData);
	}

	protected Double getdEuro() {
		return modelUscita.getInEuro();
	}

	protected void setdEuro(final Double dEuro) {
		modelUscita.setInEuro(dEuro);
	}

	public String getcDescrizione() {
		return modelUscita.getDescrizione();
	}

	public void setcDescrizione(final String cDescrizione) {
		final CorreggiTesto correttoreTesto = new CorreggiTesto(cDescrizione);
		modelUscita.setDescrizione(correttoreTesto.getTesto());
	}

	public Utenti getUtenti() {
		return modelUscita.getUtenti();
	}

	public void setUtenti(final Utenti utente) {
		modelUscita.setUtenti(utente);
	}

	public CatSpese getCategoria() {
		return modelUscita.getCatSpese();
	}

	public void setCategoria(final CatSpese catSpese) {
		modelUscita.setCatSpese(catSpese);
	}

	public String getDataIns() {
		return modelUscita.getDataIns();
	}

	public void setDataIns(final String date) {
		modelUscita.setDataIns(date);
	}

}
