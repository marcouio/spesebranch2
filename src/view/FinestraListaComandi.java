package view;

import grafica.componenti.contenitori.ScrollPaneBase;
import grafica.componenti.table.table.TableBase;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import business.Controllore;

public class FinestraListaComandi extends JFrame {

	private static final long serialVersionUID = 1L;
	private TableBase            table;
	private ScrollPaneBase       scrollPane;

	public FinestraListaComandi() {
		setResizable(false);
		this.setSize(250, 425);
		getContentPane().setLayout(null);
		String lista = Controllore.getSingleton().getMessaggio("lista");
		this.setTitle(lista);

		scrollPane = new ScrollPaneBase(this);
		table = new TableBase(scrollPane);

		scrollPane.setViewportView(table);
		Object[][] dati = generaDati();
		table.setModel(new DefaultTableModel(dati, new String[] { lista }));

		table.setBounds(12, 12, 254, 61);


		// Add the scroll pane to this panel.
		getContentPane().add(scrollPane);
		scrollPane.setBounds(21, 23, 214, 337);

	}

	public Object[][] generaDati() {
		return Controllore.getSingleton().getCommandManager().generaDati();
	}

	public TableBase getTable() {
		return table;
	}

	public void setTable(final TableBase table) {
		this.table = table;
	}

	public static void main(final String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				JFrame f = new JFrame();
				FinestraListaComandi fe = new FinestraListaComandi();
				f.getContentPane().add(fe);
				f.setVisible(true);
				f.setSize(280, 500);
			}
		});
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public void setScrollPane(final ScrollPaneBase scrollPane) {
		this.scrollPane = scrollPane;
	}
}
