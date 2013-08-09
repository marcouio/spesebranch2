package view.componenti.movimenti;

import grafica.componenti.button.ButtonBase;
import grafica.componenti.contenitori.ScrollPaneBase;
import grafica.componenti.table.table.TableBase;
import grafica.componenti.textfield.testo.TextFieldTesto;

import java.awt.Container;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public abstract class AbstractListaMov extends view.OggettoVistaBase {
	private static final long serialVersionUID = 1L;
	
	TableBase table;
	private ScrollPaneBase scrollPane;
	String[][] movimenti;
	protected JDialog dialog;
	protected ButtonBase updateButton;
	protected ButtonBase deleteButton;

	private AscoltatoreBottoniEntrata ascoltatore;
	private PanFiltraMovimenti	panFiltraMov;

	public PanFiltraMovimenti getPanFiltraMov() {
		return panFiltraMov;
	}

	protected void setMovimenti(final String[][] movimenti) {
		this.movimenti = movimenti;
	}

	public AbstractListaMov(final Container container) {
		super(container);
		initGUI();
	}

	private void initGUI() {
		try {
			
			panFiltraMov = new PanFiltraMovimenti(this);
			panFiltraMov.posizionaSopraA(null, 0, 0);
			
			final String[] nomiColonne = createNomiColonne();

			movimenti = createMovimenti();

			scrollPane = new ScrollPaneBase(this);
			table = new TableBase(movimenti, nomiColonne, scrollPane);
			scrollPane.setViewportView(table);

			impostaTable(table);
			if (this instanceof ListaMovimentiEntrate) {
				table.addMouseListener(new AscoltatoreBottoniEntrata(this.getTable()));
			} else if (this instanceof ListaMovimentiUscite) {
				table.addMouseListener(new AscoltatoreBottoniUscita(this.getTable()));
			}

			scrollPane.setBounds(21, 38, 948, 370);

		} catch (final Exception e) {
			e.printStackTrace();
		}

	}

	protected abstract String getTipo();

	public abstract ActionListener getListener();

	private void impostaTable(final JTable table2) {
		table2.setFillsViewportHeight(true);
		table2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table2.setRowHeight(26);
		table2.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table2.addMouseListener(ascoltatore);
		impostaTableSpecifico();
	}

	public abstract void impostaTableSpecifico();

	public abstract String[][] createMovimenti();

	public abstract String[] createNomiColonne();

	public int getNumEntry() {
		TextFieldTesto campo = getPanFiltraMov().getCampo();
		return Integer.parseInt(campo.getText());
	}

	public void setNumEntry(final int numEntry) {
		TextFieldTesto campo = getPanFiltraMov().getCampo();
		campo.setText(Integer.toString(numEntry));
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public void setScrollPane(final ScrollPaneBase scrollPane) {
		this.scrollPane = scrollPane;
	}

	public TableBase getTable() {
		return table;
	}

	public void setTable(final TableBase table) {
		this.table = table;
	}

}