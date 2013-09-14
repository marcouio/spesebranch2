package view.componenti.componentiPannello;

import grafica.componenti.label.LabelBase;
import grafica.componenti.textfield.testo.TextFieldTesto;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

import business.AltreUtil;
import business.ControlloreSpese;
import business.Database;

public class SottoPannelloDatiEntrate {

	private static JTextField EntrateMesePrec;
	private static JTextField EnAnCorso;
	private static JTextField EnMeCorso;

	JComponent[]              componenti = new JComponent[3];
	JLabel[]                  labels     = new JLabel[3];
	CostruttoreSottoPannello  pannello;

	public SottoPannelloDatiEntrate() throws Exception {
		super();
		pannello = new CostruttoreSottoPannello(CostruttoreSottoPannello.VERTICAL){
			
			private static final long	serialVersionUID	= 1L;

			@Override
			protected JLabel[] getLabels() {
				String msgThisYear = ControlloreSpese.getSingleton().getMessaggio("thisyear");
				JLabel label_1 = new LabelBase(msgThisYear, this);
				label_1.setBounds(164, 66, 141, 14);
				labels[2] = label_1;

				String msgLastMonth = ControlloreSpese.getSingleton().getMessaggio("lastmonth");
				JLabel label_2 = new LabelBase(msgLastMonth, this);
				label_2.setBounds(16, 67, 136, 14);
				labels[1] = label_2;

				String msgThisMonth4 = ControlloreSpese.getSingleton().getMessaggio("thismonth");
				JLabel label_3 = new LabelBase(msgThisMonth4, this);
				label_3.setBounds(317, 67, 113, 14);
				labels[0] = label_3;
				
				return labels;
			}
			
			@Override
			protected JComponent[] getComponenti() throws Exception {
				EntrateMesePrec = new TextFieldTesto(this);
				double Emensile = Database.Emensile();
				EntrateMesePrec.setText(Double.toString(AltreUtil.arrotondaDecimaliDouble(Emensile)));
				EntrateMesePrec.setBounds(16, 85, 106, 27);
				componenti[1] = EntrateMesePrec;
				EntrateMesePrec.setColumns(10);

				EnAnCorso = new TextFieldTesto(this);

				double EAnnuale = Database.EAnnuale();
				EnAnCorso.setText(Double.toString(AltreUtil.arrotondaDecimaliDouble(EAnnuale)));
				EnAnCorso.setBounds(164, 84, 106, 27);
				componenti[2] = EnAnCorso;
				EnAnCorso.setColumns(10);

				EnMeCorso = new TextFieldTesto(this);
				double EMensile = Database.EMensileInCorso();
				EnMeCorso.setText(Double.toString(AltreUtil.arrotondaDecimaliDouble(EMensile)));
				EnMeCorso.setBounds(317, 85, 106, 27);
				componenti[0] = EnMeCorso;
				EnMeCorso.setColumns(10);
				return componenti;
			}
		};
		initGUI();
	}

	private void initGUI() {
		try {
			

			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static JTextField getEntrateMesePrec() {
		return SottoPannelloDatiEntrate.EntrateMesePrec;

	}

	public static void setEntrateMesePrec(JTextField entrateMesePrec) {
		EntrateMesePrec = entrateMesePrec;
	}

	public static JTextField getEnAnCorso() {
		return EnAnCorso;
	}

	public static void setEnAnCorso(JTextField enAnCorso) {
		EnAnCorso = enAnCorso;
	}

	public static JTextField getEnMeCorso() {
		return EnMeCorso;
	}

	public static void setEnMeCorso(JTextField enMeCorso) {
		EnMeCorso = enMeCorso;
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
