package view.componenti.componentiPannello;

import grafica.componenti.label.LabelBase;
import grafica.componenti.textfield.testo.TextFieldTesto;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

import business.ControlloreSpese;
import business.Database;
import business.cache.CacheCategorie;
import domain.CatSpese;

public class SottoPannelloCategorie {

	private JLabel jLabel5;

	private JLabel jLabel11;
	private JLabel jLabel6;

	private static JComboBox categorieCombo;
	private static JTextField totaleMeseCategoria;
	private static JTextField totaleAnnualeCateg;

	JComponent[] componenti = new JComponent[3];
	JLabel[] labels = new JLabel[3];
	CostruttoreSottoPannello pannello;

	public SottoPannelloCategorie() throws Exception {
		super();
		pannello = new CostruttoreSottoPannello(CostruttoreSottoPannello.VERTICAL){
			@Override
			protected JLabel[] getLabels() {

				jLabel5 = new LabelBase(this);
				jLabel5.setText(ControlloreSpese.getSingleton().getMessaggio("categories"));
				jLabel5.setBounds(177, 25, 90, 19);
				labels[0] = jLabel5;

				jLabel11 = new LabelBase(this);
				jLabel11.setText(ControlloreSpese.getSingleton().getMessaggio("annualtotal"));
				jLabel11.setBounds(135, 67, 78, 14);
				labels[1] = jLabel11;

				jLabel6 = new LabelBase(this);
				jLabel6.setText(ControlloreSpese.getSingleton().getMessaggio("monthlytotal"));
				jLabel6.setBounds(253, 67, 106, 14);
				labels[2] = jLabel6;
				return labels;
			}
			
			@Override
			protected JComponent[] getComponenti() throws Exception {
				totaleAnnualeCateg = new TextFieldTesto(this);
				totaleAnnualeCateg.setColumns(10);
				totaleAnnualeCateg.setText("0.0");
				totaleAnnualeCateg.setBounds(135, 83, 106, 27);
				componenti[1] = totaleAnnualeCateg;

				totaleMeseCategoria = new TextFieldTesto(this);
				totaleMeseCategoria.setColumns(10);
				totaleMeseCategoria.setText("0.0");
				totaleMeseCategoria.setBounds(253, 83, 106, 27);
				componenti[2] = totaleMeseCategoria;

				// CategoriaSpese
				categorieCombo = new JComboBox(CacheCategorie.getSingleton().getVettoreCategoriePerCombo());

				categorieCombo.setBounds(16, 85, 106, 27);

				categorieCombo.setSelectedIndex(0);
				categorieCombo.addItemListener(new ItemListener() {

					@Override
					public void itemStateChanged(final ItemEvent e) {
						CatSpese spese = null;
						if (categorieCombo.getSelectedIndex() != 0) {
							spese = (CatSpese) categorieCombo.getSelectedItem();
							final int mese = new GregorianCalendar().get(Calendar.MONTH) + 1;
							double spesa = 0;
							try {
								spesa = Database.speseMeseCategoria(mese, spese.getIdCategoria());
							} catch (final Exception e1) {
								e1.printStackTrace();
							}

							try {
								totaleAnnualeCateg.setText(Double.toString(Database.totaleUscitaAnnoCategoria(spese.getIdCategoria())));
							}
							catch (Exception e1) {
								e1.printStackTrace();
							}
							totaleMeseCategoria.setText(Double.toString(spesa));
						}
					}
				});
				componenti[0] = categorieCombo;
				return componenti;
			}
		};
	}

	public static JComboBox getCategorieCombo() {
		return categorieCombo;
	}

	public static void setCategorieCombo(final JComboBox categorieCombo) {
		SottoPannelloCategorie.categorieCombo = categorieCombo;
	}

	public static JTextField getTotaleMeseCategoria() {
		return totaleMeseCategoria;
	}

	public static void setTotaleMeseCategoria(final JTextField totaleMeseCategoria) {
		SottoPannelloCategorie.totaleMeseCategoria = totaleMeseCategoria;
	}

	public static JTextField getTotaleAnnualeCateg() {
		return totaleAnnualeCateg;
	}

	public static void setTotaleAnnualeCateg(final JTextField totaleAnnualeCateg) {
		SottoPannelloCategorie.totaleAnnualeCateg = totaleAnnualeCateg;
	}

	public static void azzeraCampi() {
		if (categorieCombo != null && totaleAnnualeCateg != null && totaleMeseCategoria != null) {
			categorieCombo.setSelectedIndex(0);
			totaleAnnualeCateg.setText("0.0");
			totaleMeseCategoria.setText("0.0");
		}
	}

	protected CostruttoreSottoPannello getPannello() {
		return pannello;
	}

	protected void setPannello(final CostruttoreSottoPannello pannello) {
		this.pannello = pannello;
	}

	protected JComponent[] getComponenti() {
		return componenti;
	}

	protected void setComponenti(final JComponent[] componenti) {
		this.componenti = componenti;
	}

	protected JLabel[] getLabels() {
		return labels;
	}

	protected void setLabels(final JLabel[] labels) {
		this.labels = labels;
	}

}
