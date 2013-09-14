package view.impostazioni;

import grafica.componenti.button.ButtonBase;
import grafica.componenti.label.LabelBase;
import grafica.componenti.textarea.TextAreaBase;
import grafica.componenti.textfield.testo.TextFieldTesto;

import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Observable;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JDialog;

import view.impostazioni.ascoltatori.AscoltatoreAggiornaGruppo;
import view.impostazioni.ascoltatori.AscoltatoreEliminaGruppo;
import view.impostazioni.ascoltatori.AscoltatoreInserisciGruppo;
import business.cache.CacheGruppi;
import domain.Gruppi;
import domain.wrapper.WrapGruppi;

public class GruppiView extends AbstractGruppiView {

	private Gruppi gruppi = null;
	private JComboBox comboGruppi;
	private TextFieldTesto nome;
	private TextAreaBase descrizione;

	public GruppiView(final WrapGruppi gruppo) {
		super(gruppo);
		modelGruppi.addObserver(this);
		initGUI();
	}

	private void initGUI() {

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Gruppi");
		getContentPane().setLayout(null);

		initLabel();
		this.setPreferredSize(new Dimension(260, 405));
		this.setModalityType(ModalityType.APPLICATION_MODAL);

		nome = new TextFieldTesto(this);
		nome.setBounds(25, 49, 206, 26);
		getContentPane().add(nome);

		descrizione = new TextAreaBase("Inserisci la descrizione della spesa", 50, 25, this);
		descrizione.setWrapStyleWord(true);
		descrizione.setLineWrap(true);
		descrizione.setAutoscrolls(true);
		descrizione.setBounds(25, 103, 206, 88);
		getContentPane().add(descrizione);

		final ButtonBase inserisci = new ButtonBase(this);
		inserisci.setText("Inserisci");
		inserisci.setBounds(26, 214, 206, 25);
		getContentPane().add(inserisci);

		inserisci.addActionListener(new AscoltatoreInserisciGruppo(this));

		final Vector<Gruppi> vettoreGruppi = CacheGruppi.getSingleton().getVettoreGruppi();
		comboGruppi = new JComboBox();
		comboGruppi.addItem("");

		for (int i = 0; i < vettoreGruppi.size(); i++) {
			comboGruppi.addItem(vettoreGruppi.get(i));
		}

		comboGruppi.setBounds(25, 279, 206, 25);
		getContentPane().add(comboGruppi);
		comboGruppi.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(final ItemEvent e) {
				if (comboGruppi.getSelectedIndex() != 0 && comboGruppi.getSelectedItem() != null) {
					gruppi = (Gruppi) comboGruppi.getSelectedItem();
					nome.setText(gruppi.getNome());
					descrizione.setText(gruppi.getDescrizione());
				}
			}
		});

		final ButtonBase aggiorna = new ButtonBase(this);
		aggiorna.setText("Aggiorna");
		aggiorna.setBounds(25, 320, 100, 25);
		getContentPane().add(aggiorna);

		final ButtonBase cancella = new ButtonBase(this);
		cancella.setText("Cancella");
		cancella.setBounds(131, 320, 100, 25);
		getContentPane().add(cancella);

		aggiorna.addActionListener(new AscoltatoreAggiornaGruppo(this));

		cancella.addActionListener(new AscoltatoreEliminaGruppo(this));

	}

	@Override
	public void update(final Observable o, final Object arg) {
		nome.setText(getNome());
		descrizione.setText(getDescrizione());

	}

	public boolean nonEsistonoCampiNonValorizzati() {
		return getDescrizione() != null && getNome() != null;
	}

	private void initLabel() {
		final LabelBase lbltstGruppo = new LabelBase(this);
		lbltstGruppo.setText("Gruppo");
		lbltstGruppo.setBounds(25, 24, 100, 25);
		getContentPane().add(lbltstGruppo);

		final LabelBase lbltstListaGruppi = new LabelBase(this);
		lbltstListaGruppi.setText("Lista Gruppi");
		lbltstListaGruppi.setBounds(25, 251, 100, 25);
		getContentPane().add(lbltstListaGruppi);

		final LabelBase labelDescrizione = new LabelBase(this);
		labelDescrizione.setText("Descrizione");
		labelDescrizione.setBounds(25, 77, 90, 25);
		getContentPane().add(labelDescrizione);

	}

	public void setGruppo(final String actionCommand) {
		if (actionCommand.equals("Inserisci")) {
			final int idGruppo = (CacheGruppi.getSingleton().getMaxId()) + 1;
			getModelGruppi().setIdGruppo(idGruppo);
		} else {
			int idGruppoDaCombo = 0;
			if (gruppi != null) {
				// prendo l'id del gruppo selezionato dalla combo
				idGruppoDaCombo = gruppi.getIdGruppo();
			}
			// se non ha un id gli assegno prendendo il massimo degli id
			// presenti
			if (idGruppoDaCombo == 0) {
				final int idGruppo = (CacheGruppi.getSingleton().getMaxId()) + 1;
				getModelGruppi().setIdGruppo(idGruppo);

				// altrimenti gli setto il suo
			} else {
				getModelGruppi().setIdGruppo(idGruppoDaCombo);
			}
		}

		setNome(nome.getText());
		setDescrizione(descrizione.getText());

	}

	private static final long serialVersionUID = 1L;

	public static void main(final String[] args) {
		final GruppiView dialog = new GruppiView(new WrapGruppi());
		dialog.pack();
		dialog.setBounds(10, 10, 500, 500);
		dialog.setVisible(true);
	}

	public JComboBox getComboGruppi() {
		return comboGruppi;
	}

	public void setComboGruppi(final JComboBox comboGruppi) {
		this.comboGruppi = comboGruppi;
	}
}
