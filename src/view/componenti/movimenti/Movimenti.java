package view.componenti.movimenti;

import java.awt.Container;

import view.OggettoVistaBase;

public class Movimenti extends OggettoVistaBase {

	private static final long serialVersionUID = 1L;

	private ListaMovimentiEntrate tabMovEntrate;
	private ListaMovimentiUscite  tabMovUscite;

	public Movimenti(final Container container) {
		super(container);
		initGUI();
	}

	private void initGUI() {
		try {

			this.setLayout(null);
			tabMovEntrate = new ListaMovimentiEntrate(this);
			tabMovUscite = new ListaMovimentiUscite(this);
						
			tabMovUscite.setSize(980, 420);
			tabMovEntrate.setSize(980, 420);
			tabMovUscite.posizionaSottoA(null, 0, 0);
			tabMovEntrate.posizionaSottoA(null, 0, 0);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ListaMovimentiEntrate getTabMovEntrate() {
		return tabMovEntrate;
	}

	protected void setTabMovEntrate(final ListaMovimentiEntrate tabMovEntrate) {
		this.tabMovEntrate = tabMovEntrate;
	}

	public ListaMovimentiUscite getTabMovUscite() {
		return tabMovUscite;
	}

	protected void setTabMovUscite(final ListaMovimentiUscite tabMovUscite) {
		this.tabMovUscite = tabMovUscite;
	}

}
