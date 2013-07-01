package view.componenti.movimenti;

import java.awt.Container;

import javax.swing.JTabbedPane;

import view.OggettoVistaBase;
import business.ControlloreSpese;

public class Movimenti extends OggettoVistaBase {

	private static final long serialVersionUID = 1L;

	private JTabbedPane           tabGenerale;
	private ListaMovimentiEntrate tabMovEntrate;
	private ListaMovimentiUscite  tabMovUscite;

	public Movimenti(final Container container) {
		super(container);
		initGUI(container);
	}

	private void initGUI(final Container container) {
		try {
			this.setPreferredSize(new java.awt.Dimension(900, 650));
			this.setLayout(null);

			tabMovEntrate = new ListaMovimentiEntrate(container);

			tabGenerale = new JTabbedPane();
			tabGenerale.setBounds(65, 65, 800, 500);
			tabGenerale.addTab(ControlloreSpese.getSingleton().getMessaggio("income")+" "+ControlloreSpese.getSingleton().getMessaggio("transactions"), tabMovEntrate);

			tabMovEntrate.setBounds(20, 10, 700, 500);
			tabMovUscite = new ListaMovimentiUscite(container);
			tabGenerale.addTab(ControlloreSpese.getSingleton().getMessaggio("withdrawal")+" "+ControlloreSpese.getSingleton().getMessaggio("transactions"), tabMovUscite);
			tabMovUscite.setBounds(20, 10, 700, 500);

			this.add(tabGenerale);
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
