package view.grafici.dialogGraph;

import grafica.componenti.button.ButtonBase;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import view.componenti.movimenti.DialogHandler;
import business.Database;
import business.cache.CacheCategorie;
import db.ConnectionPool;
import domain.CatSpese;

public class GrUscite1 extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

	/**
	 * Uscite per categoria Launch the application.
	 */
	public static void main(final String[] args) {
		try {
			final GrUscite1 dialog = new GrUscite1();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 * @throws Exception 
	 */
	public GrUscite1() throws Exception {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		final Vector<CatSpese> categorie = CacheCategorie.getSingleton().getVettoreCategorie();
		Connection cn = null;
		try {
			cn = ConnectionPool.getSingleton().getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Grafico a barre
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		for (int i = 0; i < categorie.size(); i++) {
			final CatSpese categoria = categorie.get(i);
			final double uscita = Database.totaleUscitaAnnoCategoria(categoria.getIdCategoria());
			dataset.setValue(uscita, "Euro", categoria.getNome());
		}

		final JFreeChart chart = ChartFactory.createBarChart("Uscite",
				"Categorie di spesa", "Euro", dataset,
				PlotOrientation.VERTICAL, true, true, true);
		final GregorianCalendar data = new GregorianCalendar();

		final String dataMinuti = "" + data.get(Calendar.HOUR_OF_DAY)
				+ data.get(Calendar.MINUTE);

		ChartUtilities.saveChartAsPNG(new java.io.File("./immagini/barUscite"
				+ dataMinuti + ".png"), chart, 550, 550);
		getContentPane().setLayout(null);
		ConnectionPool.getSingleton().chiudiOggettiDb(cn);
		final ImageIcon image = new ImageIcon("./immagini/barUscite" + dataMinuti
				+ ".png");
		final JLabel immagine = new JLabel(image);
		dispose();
		final JButton chiudi = new ButtonBase("Chiudi", this);
		chiudi.setActionCommand("chiudi");

		immagine.setBounds(12, 22, 618, 546);
		chiudi.setBounds(269, 580, 97, 30);
		setBounds(100, 100, 650, 650);

		getContentPane().add(immagine);
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
