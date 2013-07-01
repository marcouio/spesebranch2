package view.tabelleMesi;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JTabbedPane;

import view.OggettoVistaBase;
import business.ControlloreSpese;

public class PerMesiF extends OggettoVistaBase {

	private static final long serialVersionUID = 1L;

	private TabellaEntrata      tabEntrate = null;
	private TabellaUscita       tabUscite  = null;
	private TabellaUscitaGruppi tabUG      = null;

	private JTabbedPane                tabGenerale;

	public PerMesiF(final Container container) {
		super(container);
		initGUI(container);
	}

	private void initGUI(final Container container) {
		try {
			tabEntrate = new TabellaEntrata(container);
			tabUscite  = new TabellaUscita(container);
			tabUG      = new TabellaUscitaGruppi(container);

			this.setPreferredSize(new Dimension(983, 500));
			this.setLayout(null);

			tabGenerale = new JTabbedPane();
			tabGenerale.setBounds(12, 65, 930, 488);
			tabGenerale.addTab(ControlloreSpese.getSingleton().getMessaggio("income"), tabEntrate);
			tabGenerale.addTab(ControlloreSpese.getSingleton().getMessaggio("withdrawal"), tabUscite);
			tabGenerale.addTab(ControlloreSpese.getSingleton().getMessaggio("groupscharge"), tabUG);

			tabUscite.getScrollPane().setSize(950, 350);
			tabEntrate.getScrollPane().setSize(950, 350);
			TabellaUscitaGruppi.getScrollPane().setSize(950, 350);
			this.add(tabGenerale);

		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the tabEntrate
	 */
	public TabellaEntrata getTabEntrate() {
		return tabEntrate;
	}

	/**
	 * @param tabEntrate
	 *            the tabEntrate to set
	 */
	public void setTabEntrate(final TabellaEntrata tabEntrate) {
		this.tabEntrate = tabEntrate;
	}

	/**
	 * @return the tabUscite
	 */
	public TabellaUscita getTabUscite() {
		return tabUscite;
	}

	/**
	 * @param tabUscite
	 *            the tabUscite to set
	 */
	public void setTabUscite(final TabellaUscita tabUscite) {
		this.tabUscite = tabUscite;
	}

}
