package view.grafici.dialogGraph;

import grafica.componenti.button.ButtonBase;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import view.componenti.movimenti.DialogHandler;
import business.Database;
import db.ConnectionPool;
import domain.wrapper.WrapEntrate;

public class GrEntrate1 extends JDialog implements ActionListener {


	private static final long       serialVersionUID = 1L;
	private final DefaultPieDataset dataset;
	private final double            entrateFisse     = Database.totaleEntrateAnnoCategoria(WrapEntrate.IMPORTANZA_FISSE);
	private final double            enrateVariabili  = Database.totaleEntrateAnnoCategoria(WrapEntrate.IMPORTANZA_VARIABILI);

	/**
	 * Entrate per tipo. Create the dialog.
	 * 
	 * @throws SQLException
	 * @throws IOException
	 */

	public static void main(final String[] args) {
		try {
			final GrEntrate1 dialog = new GrEntrate1(new JFrame(), "Entrate", true);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public GrEntrate1(final JFrame frame, final String title, final boolean modal) throws Exception {

		dataset = new DefaultPieDataset();
		dataset.setValue("Fisse", entrateFisse);
		dataset.setValue("Variabili", enrateVariabili);
		setBounds(100, 100, 650, 650);
		final JFreeChart chart = ChartFactory.createPieChart("Entrate", dataset,
				true, true, true);
		final GregorianCalendar data = new GregorianCalendar();

		final String dataMinuti = "" + data.get(Calendar.HOUR_OF_DAY)
				+ data.get(Calendar.MINUTE);

		ChartUtilities.saveChartAsPNG(new java.io.File("./immagini/torta"
				+ dataMinuti + ".png"), chart, 560, 530);
		getContentPane().setLayout(null);

		ConnectionPool.getSingleton().chiudiOggettiDb(null);
		final ImageIcon image = new ImageIcon("./immagini/torta" + dataMinuti
				+ ".png");
		final JLabel immagine = new JLabel();
		immagine.setIcon(image);
		getContentPane().add(immagine);
		final JButton chiudi = new ButtonBase("Chiudi", this);
		chiudi.setActionCommand("chiudi");
		immagine.setBounds(12, 22, 618, 546);
		chiudi.setBounds(269, 580, 97, 30);
		setBounds(100, 100, 650, 650);
		getContentPane().add(chiudi);
		chiudi.addActionListener(new DialogHandler(this));
		chiudi.addActionListener(this);
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		if (e.getActionCommand().equals("chiudi")) {
			if (e.getActionCommand().equals("chiudi")) {
				setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				this.dispose();
			}
		}

	}

}
