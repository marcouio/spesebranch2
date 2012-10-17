package view.impostazioni;

import java.util.Observer;

import javax.swing.JDialog;

import domain.wrapper.WrapGruppi;

public abstract class AbstractGruppiView extends JDialog implements Observer {

	private static final long serialVersionUID = 1L;
	protected WrapGruppi modelGruppi = null;

	public AbstractGruppiView(final WrapGruppi modelGruppi) {
		this.modelGruppi = modelGruppi;
	}

	public String getNome() {
		return modelGruppi.getNome();
	}

	public String getDescrizione() {
		return modelGruppi.getDescrizione();
	}

	public void setNome(final String nome) {
		modelGruppi.setNome(nome);
	}

	public void setDescrizione(final String descrizione) {
		modelGruppi.setDescrizione(descrizione);
	}

	public WrapGruppi getModelGruppi() {
		return modelGruppi;
	}

	public void setModelGruppi(final WrapGruppi modelGruppi) {
		this.modelGruppi = modelGruppi;
	}

}
