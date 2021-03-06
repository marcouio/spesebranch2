package view.tabelleMesi;

import grafica.componenti.ExceptionGraphics;
import grafica.componenti.contenitori.ScrollPaneBase;
import grafica.componenti.table.table.TableBase;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;

import view.OggettoVistaBase;
import business.ControlloreSpese;
import business.generatori.TableModelEntrate;

public class TabellaEntrata extends OggettoVistaBase {

	private static final long serialVersionUID = 1L;

	private static String[][] primo;
	private static String[] nomiColonne = { ControlloreSpese.getSingleton().getMessaggio("fixity"), ControlloreSpese.getSingleton().getMessaggio("variables") };

	public static String[] getNomiColonne() {
		return nomiColonne;
	}

	public static void setNomiColonne(final String[] nomiColonne) {
		TabellaEntrata.nomiColonne = nomiColonne;
	}

	private ScrollPaneBase scrollPane;

	public TabellaEntrata(final Container container) throws ExceptionGraphics {
		super(new GridLayout(1, 0), container);
		setScrollPane(new ScrollPaneBase(container));
		TableBase table = null;
		try {
			final TableModelEntrate model = new TableModelEntrate(null);
			table = createTable(model, scrollPane);
			table.setBackgroundPrimaColonna(Color.GRAY);
		} catch (final Exception e) {
			e.printStackTrace();
		}

		// Create the scroll pane and add the table to it.

		// Add the scroll pane to this panel.
		add(scrollPane);
	}

	/**
	 * 
	 * Permette di generare una tabella
	 * 
	 * @param primo
	 * @param nomiColonne
	 * @return TableF
	 */
	public static TableBase createTable(final TableModelEntrate model, final Container container) {
		final TableBase table = new TableBase(model, container);

		table.setFillsViewportHeight(true);
		table.setRowHeight(27);
		return table;
	}

	public static String[][] getPrimo() {
		return primo;
	}

	public static void setPrimo(final String[][] primo) {
		TabellaEntrata.primo = primo;
	}

	public ScrollPaneBase getScrollPane() {
		return scrollPane;
	}

	protected void setScrollPane(final ScrollPaneBase scrollPane) {
		this.scrollPane = scrollPane;
	}
}