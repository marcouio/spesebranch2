package view.tabelleMesi;

import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JTabbedPane;

import view.OggettoVistaBase;
import business.Controllore;

public class PerMesiF extends OggettoVistaBase {

	private static final long serialVersionUID = 1L;

	private static TabellaEntrata      tabEntrate = null;
	private static TabellaUscita       tabUscite  = null;
	private static TabellaUscitaGruppi tabUG      = null;

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

			this.setPreferredSize(new Dimension(983, 545));
			this.setLayout(null);

			tabGenerale = new JTabbedPane();
			tabGenerale.setBounds(12, 65, 930, 468);
			tabGenerale.addTab(Controllore.getSingleton().getMessaggio("income"), tabEntrate);
			tabGenerale.addTab(Controllore.getSingleton().getMessaggio("withdrawal"), tabUscite);
			tabGenerale.addTab(Controllore.getSingleton().getMessaggio("groupscharge"), tabUG);

			TabellaUscita.getScrollPane().setSize(400, 400);
			TabellaEntrata.getScrollPane().setSize(400, 400);
			tabUG.getScrollPane().setSize(400, 400);
			this.add(tabGenerale);

		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the tabEntrate
	 */
	public static TabellaEntrata getTabEntrate() {
		return tabEntrate;
	}

	/**
	 * @param tabEntrate
	 *            the tabEntrate to set
	 */
	public static void setTabEntrate(final TabellaEntrata tabEntrate) {
		PerMesiF.tabEntrate = tabEntrate;
	}

	/**
	 * @return the tabUscite
	 */
	public static TabellaUscita getTabUscite() {
		return tabUscite;
	}

	/**
	 * @param tabUscite
	 *            the tabUscite to set
	 */
	public static void setTabUscite(final TabellaUscita tabUscite) {
		PerMesiF.tabUscite = tabUscite;
	}

}
