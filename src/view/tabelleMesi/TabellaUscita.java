

package view.tabelleMesi;

import grafica.componenti.ExceptionGraphics;
import grafica.componenti.contenitori.ScrollPaneBase;
import grafica.componenti.table.TableModel;
import grafica.componenti.table.table.TableBase;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JScrollPane;

import view.OggettoVistaBase;
import business.generatori.TableModelUscite;

public class TabellaUscita extends OggettoVistaBase {

	private static final long serialVersionUID = 1L;

	private static String[][] primo;
	private static ScrollPaneBase scrollPane;



	public TabellaUscita(final Container container) throws ExceptionGraphics {
		super(new GridLayout(1,0), container);
		scrollPane = new ScrollPaneBase(container);
		TableBase table = null;
		try {
			TableModelUscite model = new TableModelUscite(null);
			table = createTable(model, scrollPane);
			table.setColoreBackground(Color.LIGHT_GRAY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//Add the scroll pane to this panel.
		add(scrollPane);

	}

	public static String[][] getPrimo() {
		return primo;
	}

	public static void setPrimo(final String[][] primo) {
		TabellaUscita.primo = primo;
	}

	/**
	 * 
	 * Permette di generare una tabella
	 * 
	 * @param primo
	 * @param nomiColonne
	 * @return TableF
	 */
	public static TableBase createTable(final TableModel model, final Container container) {

		TableBase table = new TableBase(model, container);

		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);
		table.setRowHeight(27);
		return table;
	}

	public static JScrollPane getScrollPane() {
		return scrollPane;
	}

	public void setScrollPane(final ScrollPaneBase scrollPane) {
		TabellaUscita.scrollPane = scrollPane;
	}
}