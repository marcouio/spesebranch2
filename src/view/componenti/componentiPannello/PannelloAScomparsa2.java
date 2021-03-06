package view.componenti.componentiPannello;

import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import business.ControlloreSpese;

public class PannelloAScomparsa2 extends JFrame implements ItemListener {

	private static final long serialVersionUID = 1L;

	public static void main(final String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame inst = null;
				try {
					inst = new PannelloAScomparsa2();
					inst.setBounds(0, 0, 250, 425);
					inst.setPreferredSize(new Dimension(250, 425));
					inst.setLocationRelativeTo(null);
					inst.setVisible(true);
					inst.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				} catch (final Throwable e) {
					e.printStackTrace();
				}
			}
		});
	}

	private final ArrayList<JPanel> pannelli = new ArrayList<JPanel>();
	private JComboBox combo;
	private SottoPannelloDatiSpese pannelloSpese;
	private SottoPannelloDatiEntrate pannelloEntrate;
	private SottoPannelloMesi pannelloMesi;
	private SottoPannelloCategorie pannelloCategorie;
	private SottoPannelloTotali pannelloTotali;
	CostruttoreSottoPannello[] arrayPannelli;

	public PannelloAScomparsa2() throws Exception {
		initGui();
	}

	private void initGui() throws Exception {

		this.setLayout(null);
		this.setTitle(ControlloreSpese.getSingleton().getMessaggio("datapanel"));
		this.setSize(250, 425);
		pannelloSpese = new SottoPannelloDatiSpese();
		pannelloEntrate = new SottoPannelloDatiEntrate();
		pannelloMesi = new SottoPannelloMesi();
		pannelloCategorie = new SottoPannelloCategorie();
		pannelloTotali = new SottoPannelloTotali();

		initArrayPannello();

		combo = new JComboBox();
		this.add(combo);
		combo.setBounds(65, 50, 120, 40);
		combo.addItem("");
		combo.addItem("1 - " + ControlloreSpese.getSingleton().getMessaggio("withdrawal"));
		combo.addItem("2 - " + ControlloreSpese.getSingleton().getMessaggio("categories"));
		combo.addItem("3 - " + ControlloreSpese.getSingleton().getMessaggio("entries"));
		combo.addItem("4 - " + ControlloreSpese.getSingleton().getMessaggio("months"));
		combo.addItem("5 - " + ControlloreSpese.getSingleton().getMessaggio("totals"));
		combo.setSelectedIndex(0);
		combo.addItemListener(this);
	}

	@Override
	public void itemStateChanged(final ItemEvent e) {

		final JPanel p = new JPanel();

		for (final JPanel pannello : pannelli) {
			pannello.setVisible(false);
			this.remove(pannello);
		}
		pannelli.clear();
		CostruttoreSottoPannello sottoPannello = null;
		if (combo.getSelectedIndex() != 0 && e.getStateChange() == ItemEvent.SELECTED) {
			sottoPannello = arrayPannelli[combo.getSelectedIndex()];
			mostra(p, sottoPannello);
		}
		this.validate();
		repaint();

	}

	private void mostra(final JPanel p, final CostruttoreSottoPannello sottoPannello) {
		this.add(p);
		pannelli.add(p);
		p.add(sottoPannello);
		p.setVisible(true);
		p.setBounds(50, 90, sottoPannello.getPreferredSize().width, sottoPannello.getPreferredSize().height);
	}

	private void initArrayPannello() {
		arrayPannelli = new CostruttoreSottoPannello[] {
				new CostruttoreSottoPannello(),
				pannelloSpese.getPannello(),
				pannelloCategorie.getPannello(),
				pannelloEntrate.getPannello(),
				pannelloMesi.getPannello(),
				pannelloTotali.getPannello()
		};
	}

}