package view.tabelleMesi;

import grafica.componenti.ExceptionGraphics;
import grafica.componenti.contenitori.ScrollPaneBase;
import grafica.componenti.table.table.TableBase;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import view.OggettoVistaBase;
import business.generatori.TableModelUsciteGruppi;

public class TabellaUscitaGruppi extends OggettoVistaBase {

	private static final long serialVersionUID = 1L;
	private static TableBase table;

	private static String[][] primo;
	private static JScrollPane scrollPane;

	public TabellaUscitaGruppi(final Container container) throws ExceptionGraphics {
		super(new GridLayout(1, 0), container);
		scrollPane = new ScrollPaneBase(container);

		try {
			getDatiPerTabella(scrollPane);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Create the scroll pane and add the table to it.

		// Add the scroll pane to this panel.
		add(scrollPane);

	}

	public TableBase getDatiPerTabella(final Container container) throws Exception {

		TableModelUsciteGruppi model = new TableModelUsciteGruppi(null);

		table = new TableBase(model, container);
		table.setRowHeight(27);
		table.setPreferredScrollableViewportSize(new Dimension(700, 300));
		table.setFillsViewportHeight(true);
		table.setBackgroundPrimaColonna(Color.GRAY);
		return table;
	}

	public static String[][] getPrimo() {
		return primo;
	}

	public static void setPrimo(final String[][] primo) {
		TabellaUscitaGruppi.primo = primo;
	}

	public static JTable getTable() {
		return table;
	}

	public static void setTable(final TableBase table) {
		TabellaUscitaGruppi.table = table;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public static void setScrollPane(final JScrollPane scrollPane) {
		TabellaUscitaGruppi.scrollPane = scrollPane;
	}
}