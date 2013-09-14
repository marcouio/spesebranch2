package view.componenti.componentiPannello;

import grafica.componenti.label.LabelBase;
import grafica.componenti.textfield.testo.TextFieldTesto;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

import business.AltreUtil;
import business.ControlloreSpese;
import business.Database;

public class SottoPannelloDatiSpese {


	private static JTextField meseInCors;
	private static JTextField speseAnnuali;
	private static double     annuale;
	private static JTextField mesePrecUsc;
	private static double     mensile;
	private static double     mensile2;

	JComponent[]              componenti = new JComponent[3];
	JLabel[]                  labels     = new JLabel[3];
	CostruttoreSottoPannello  pannello;

	private void initGUI() {
		try {

			annuale = Database.Annuale();
			speseAnnuali.setText(Double.toString(AltreUtil.arrotondaDecimaliDouble(annuale)));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static JTextField getMeseInCors() {
		return meseInCors;
	}

	public static void setMeseInCors(JTextField meseInCors) {
		SottoPannelloDatiSpese.meseInCors = meseInCors;
	}

	public static JTextField getSpeseAnnuali() {
		return speseAnnuali;
	}

	public static void setSpeseAnnuali(JTextField speseAnnuali) {
		SottoPannelloDatiSpese.speseAnnuali = speseAnnuali;
	}

	public static double getAnnuale() {
		return annuale;
	}

	public static void setAnnuale(double annuale) {
		SottoPannelloDatiSpese.annuale = annuale;
	}

	public static JTextField getMesePrecUsc() {
		return mesePrecUsc;
	}

	public static void setMesePrecUsc(JTextField mesePrecUsc) {
		SottoPannelloDatiSpese.mesePrecUsc = mesePrecUsc;
	}

	public static double getMensile() {
		return mensile;
	}

	public static void setMensile(double mensile) {
		SottoPannelloDatiSpese.mensile = mensile;
	}

	public SottoPannelloDatiSpese() throws Exception {
		super();
		pannello = new CostruttoreSottoPannello(CostruttoreSottoPannello.VERTICAL){
			@Override
			protected JComponent[] getComponenti() throws Exception {
				meseInCors = new TextFieldTesto(this);
				mensile2 = Database.MensileInCorso();
				meseInCors.setText(Double.toString(AltreUtil.arrotondaDecimaliDouble(mensile2)));
				componenti[0] = meseInCors;
				meseInCors.setBounds(16, 85, 106, 27);

				mensile = Database.Mensile();
				mesePrecUsc = new TextFieldTesto(this);
				mesePrecUsc.setText(Double.toString(AltreUtil.arrotondaDecimaliDouble(mensile)));
				mesePrecUsc.setBounds(317, 85, 106, 27);
				componenti[1] = mesePrecUsc;

				speseAnnuali = new TextFieldTesto(this);
				componenti[2] = speseAnnuali;
				speseAnnuali.setBounds(164, 84, 106, 27);
				speseAnnuali.setColumns(8);

				return componenti;
			}
			
			@Override
			protected JLabel[] getLabels() {
				String msgThisYear = ControlloreSpese.getSingleton().getMessaggio("thisyear");
				JLabel meseincorso = new LabelBase(msgThisYear, this);
				labels[2] = meseincorso;
				meseincorso.setBounds(164, 66, 141, 14);

				String msgLastMonth = ControlloreSpese.getSingleton().getMessaggio("lastmonth");
				JLabel label = new LabelBase(msgLastMonth, this);
				label.setBounds(317, 67, 123, 14);
				labels[1] = label;


				String msgThisMonth = ControlloreSpese.getSingleton().getMessaggio("thismonth");
				JLabel label2 = new LabelBase(msgThisMonth, this);
				label2.setBounds(16, 67, 136, 13);
				labels[0] = label2;

				return labels;
			}
		};
		initGUI();
	}

	protected JComponent[] getComponenti() {
		return componenti;
	}

	protected void setComponenti(JComponent[] componenti) {
		this.componenti = componenti;
	}

	protected JLabel[] getLabels() {
		return labels;
	}

	protected void setLabels(JLabel[] labels) {
		this.labels = labels;
	}

	protected CostruttoreSottoPannello getPannello() {
		return pannello;
	}

	protected void setPannello(CostruttoreSottoPannello pannello) {
		this.pannello = pannello;
	}

}
