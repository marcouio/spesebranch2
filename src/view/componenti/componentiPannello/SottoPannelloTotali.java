package view.componenti.componentiPannello;

import grafica.componenti.label.LabelBase;
import grafica.componenti.textfield.testo.TextFieldTesto;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;

import business.AltreUtil;
import business.ControlloreSpese;
import business.Database;
import domain.wrapper.WrapCatSpese;

public class SottoPannelloTotali {

	
	private JLabel            jLabel2;
	private JLabel            jLabel3;
	private JLabel            jLabel4;
	private static JTextField percentoVariabili;
	private static JTextField percentoFutili;
	private static JTextField avanzo;

	JComponent[]              componenti = new JComponent[3];
	JLabel[]                  labels     = new JLabel[3];
	CostruttoreSottoPannello  pannello;

	public SottoPannelloTotali() throws Exception {
		super();
		pannello = new CostruttoreSottoPannello(CostruttoreSottoPannello.VERTICAL){

			private static final long	serialVersionUID	= 1L;

			@Override
			protected JComponent[] getComponenti() throws Exception {
				avanzo = new TextFieldTesto(this);
				componenti[2] = avanzo;
				avanzo.setColumns(10);

				double differenza = AltreUtil.arrotondaDecimaliDouble((Database.EAnnuale()) - (Database.Annuale()));
				avanzo.setText(Double.toString(AltreUtil.arrotondaDecimaliDouble(differenza)));
				avanzo.setBounds(16, 85, 106, 27);
				avanzo.setSize(92, 27);

				double percVariabili = Database.percentoUscite(WrapCatSpese.IMPORTANZA_VARIABILE);

				double percFutili = Database.percentoUscite(WrapCatSpese.IMPORTANZA_FUTILE);

				percentoVariabili = new TextFieldTesto(this);
				componenti[1] = percentoVariabili;
				percentoVariabili.setColumns(10);
				percentoVariabili.setText(Double.toString(percVariabili));
				percentoVariabili.setBounds(164, 84, 106, 27);
				percentoVariabili.setSize(92, 27);

				percentoFutili = new TextFieldTesto(this);
				componenti[0] = percentoFutili;
				percentoFutili.setColumns(10);
				percentoFutili.setText(Double.toString(percFutili));
				percentoFutili.setBounds(317, 85, 106, 27);
				
				return componenti;
			}
			
			@Override
			protected JLabel[] getLabels() {

				jLabel2 = new LabelBase(this);
				labels[0] = jLabel2;
				jLabel2.setText("% " + ControlloreSpese.getSingleton().getMessaggio("spesefut"));
				jLabel2.setBounds(317, 67, 106, 14);
				jLabel2.setOpaque(true);

				jLabel3 = new LabelBase(this);
				labels[1] = jLabel3;
				jLabel3.setText("% " + ControlloreSpese.getSingleton().getMessaggio("spesevar"));
				jLabel3.setBounds(164, 66, 141, 15);

				jLabel4 = new LabelBase(this);
				labels[2] = jLabel4;
				jLabel4.setText(ControlloreSpese.getSingleton().getMessaggio("avanzo")+"/"+ControlloreSpese.getSingleton().getMessaggio("disavanzo"));
				jLabel4.setBounds(16, 67, 128, 14);
				
				return labels;
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

	public static JTextField getPercentoVariabili() {
		return percentoVariabili;
	}

	public static void setPercentoVariabili(JTextField percentoVariabili) {
		SottoPannelloTotali.percentoVariabili = percentoVariabili;
	}

	public static JTextField getPercentoFutili() {
		return percentoFutili;
	}

	public static void setPercentoFutili(JTextField percentoFutili) {
		SottoPannelloTotali.percentoFutili = percentoFutili;
	}

	public static JTextField getAvanzo() {
		return avanzo;
	}

	public static void setAvanzo(JTextField avanzo) {
		SottoPannelloTotali.avanzo = avanzo;
	}

}
