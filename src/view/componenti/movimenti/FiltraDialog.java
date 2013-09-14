package view.componenti.movimenti;

import grafica.componenti.button.ButtonBase;
import grafica.componenti.label.LabelBase;
import grafica.componenti.textfield.testo.TextFieldTesto;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;

import business.AltreUtil;
import business.ControlloreSpese;

public abstract class FiltraDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private TextFieldTesto        tfDa;
	private TextFieldTesto        tfA;
	private TextFieldTesto        tfNome;
	private TextFieldTesto        tfEuro;
	protected JComboBox       comboBoxCat;

	private String            dataDa;
	private String            dataA;
	private String            nome;
	private Double            euro;
	protected String          categoria;

	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) {
		try {
			FiltraDialog dialog = new FiltraDialog() {

				private static final long serialVersionUID = 1L;

				@Override
				public String[][] getMovimenti() {
					return null;
				}
			};
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public FiltraDialog() {
		setTitle(ControlloreSpese.getSingleton().getMessaggio("filtertrans"));
		setBounds(100, 100, 663, 148);
		getContentPane().setLayout(null);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 83, 661, 35);
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane);
			{
				ButtonBase okButton = new ButtonBase("OK", this);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				okButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(final ActionEvent e) {
						try{
							getMovimenti();
						}catch (Exception eee) {
							eee.printStackTrace();
						}
						dispose();
					}
				});

				getRootPane().setDefaultButton(okButton);
			}
			{
				ButtonBase cancelButton = new ButtonBase(ControlloreSpese.getSingleton().getMessaggio("cancel"), this);
				cancelButton.setActionCommand(ControlloreSpese.getSingleton().getMessaggio("cancel"));
				buttonPane.add(cancelButton);
				cancelButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(final ActionEvent e) {
						dispose();
					}
				});
			}
		}
		{
			tfDa = new TextFieldTesto(this);
			tfDa.setColumns(10);
			tfDa.setBounds(62, 26, 89, 25);
			getContentPane().add(tfDa);
		}
		{
			LabelBase label = new LabelBase(ControlloreSpese.getSingleton().getMessaggio("from")+":",this);
			label.setBounds(17, 28, 43, 15);
			getContentPane().add(label);
		}
		{
			LabelBase label = new LabelBase(ControlloreSpese.getSingleton().getMessaggio("to")+":",this);
			label.setBounds(18, 59, 43, 15);
			getContentPane().add(label);
		}
		{
			tfA = new TextFieldTesto(this);
			tfA.setColumns(10);
			tfA.setBounds(62, 56, 89, 25);
			getContentPane().add(tfA);
		}
		{
			tfNome = new TextFieldTesto(this);
			tfNome.setColumns(10);
			tfNome.setBounds(209, 26, 89, 25);
			getContentPane().add(tfNome);
		}
		{
			LabelBase label = new LabelBase(ControlloreSpese.getSingleton().getMessaggio("name")+":", this);
			label.setBounds(163, 26, 55, 15);
			getContentPane().add(label);
		}
		{
			LabelBase label = new LabelBase(ControlloreSpese.getSingleton().getMessaggio("eur")+":", this);
			label.setBounds(307, 27, 55, 15);
			getContentPane().add(label);
		}
		{
			tfEuro = new TextFieldTesto(this);
			tfEuro.setColumns(10);
			tfEuro.setBounds(341, 26, 89, 25);
			getContentPane().add(tfEuro);
		}
		{
			LabelBase label = new LabelBase(ControlloreSpese.getSingleton().getMessaggio("category")+":", this);
			label.setBounds(443, 27, 82, 15);
			getContentPane().add(label);
		}
	}

	public abstract String[][] getMovimenti() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException;

	protected String getDataDa() {
		if (!tfDa.getText().equals("")) {
			dataDa = tfDa.getText();
		}
		return dataDa;
	}

	protected void setDataDa(final String dataDa) {
		this.dataDa = dataDa;
	}

	protected String getDataA() {
		if (!tfA.getText().equals("")) {
			dataA = tfA.getText();
		}
		return dataA;
	}

	protected void setDataA(final String dataA) {
		this.dataA = dataA;
	}

	protected String getNome() {
		if (!tfNome.getText().equals("")) {
			nome = tfNome.getText();
		}
		return nome;
	}

	protected void setNome(final String nome) {
		this.nome = nome;
	}

	protected Double getEuro() {
		if (AltreUtil.checkDouble(tfEuro.getText())) {
			euro = Double.parseDouble(tfEuro.getText());
		}
		return euro;
	}

	protected void setEuro(final double euro) {
		this.euro = euro;
	}

	protected String getCategoria() {
		return categoria;
	}

	protected void setCategoria(final String categoria) {
		this.categoria = categoria;
	}
}
