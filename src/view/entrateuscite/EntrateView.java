package view.entrateuscite;

import grafica.componenti.alert.Alert;
import grafica.componenti.button.ButtonBase;
import grafica.componenti.combo.ComboBoxBase;
import grafica.componenti.label.LabelBase;
import grafica.componenti.textarea.TextAreaBase;
import grafica.componenti.textfield.testo.TextFieldTesto;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Observable;

import javax.swing.JDialog;
import javax.swing.SwingUtilities;

import business.AltreUtil;
import business.ControlloreSpese;
import business.CorreggiTesto;
import business.ascoltatori.AscoltatoreAggiornatoreEntrate;
import business.cache.CacheEntrate;
import business.comandi.entrate.CommandDeleteEntrata;
import db.ConnectionPool;
import db.UtilDb;
import domain.Utenti;
import domain.wrapper.WrapEntrate;

public class EntrateView extends AbstractEntrateView {

	private static final long        serialVersionUID = 1L;

	static private ArrayList<String> lista = new ArrayList<String>();
	static{
		lista.add(ControlloreSpese.getSingleton().getMessaggio("variables"));
		lista.add(ControlloreSpese.getSingleton().getMessaggio("fixity"));
	}

	private final TextFieldTesto       tfNome;
	private final TextAreaBase          taDescrizione;
	private final ComboBoxBase          cbTipo;
	private final TextFieldTesto         tfData;
	private final TextFieldTesto         tfEuro;

	public static void main(final String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				final EntrateView dialog = new EntrateView(new WrapEntrate());
				dialog.setLocationRelativeTo(null);
				dialog.setBounds(0, 0, 347, 318);
				dialog.setVisible(true);
			}
		});
	}

	/**
	 * Create the panel.
	 */
	public EntrateView(final WrapEntrate entrate) {
		super(entrate);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle(ControlloreSpese.getSingleton().getMessaggio("insertentry"));
		modelEntrate.addObserver(this);
		getContentPane().setLayout(null);

		initLabel();

		String insertDescr = ControlloreSpese.getSingleton().getMessaggio("insertheredescrentry");
		taDescrizione = new TextAreaBase(insertDescr, this);
		taDescrizione.setBounds(13, 89, 318, 75);

		// specifica se �true� di andare a capo automaticamente a fine riga
		taDescrizione.setLineWrap(true);
		// va a capo con la parola se �true� o col singolo carattere se �false�
		taDescrizione.setWrapStyleWord(true);
		taDescrizione.setAutoscrolls(true);

		tfNome = new TextFieldTesto(this);
		tfNome.setBounds(12, 38, 150, 27);
		tfNome.setColumns(10);

		// array per Categoria
		ArrayList<String> listaCombo = new ArrayList<String>();
		listaCombo.add("");
		for(int i = 0; i<lista.size(); i++){
			listaCombo.add(lista.get(i));
		}

		cbTipo = new ComboBoxBase(this,lista.toArray());
		cbTipo.setBounds(181, 38, 150, 27);
		getContentPane().add(cbTipo);

		final GregorianCalendar gc = new GregorianCalendar();
		String oggi = UtilDb.dataToString(gc.getTime(), "yyyy/MM/dd");
		tfData = new TextFieldTesto(oggi, this);
		tfData.setColumns(10);
		tfData.setBounds(13, 191, 150, 27);

		tfEuro = new TextFieldTesto("0.0", this);
		tfEuro.setColumns(10);
		tfEuro.setBounds(182, 191, 150, 27);

		final ButtonBase inserisci = new ButtonBase(this);
		inserisci.setText(ControlloreSpese.getSingleton().getMessaggio("insert"));
		inserisci.setBounds(13, 238, 149, 27);

		final ButtonBase eliminaUltima = new ButtonBase(this);
		eliminaUltima.setText(ControlloreSpese.getSingleton().getMessaggio("deletelast"));
		eliminaUltima.setBounds(184, 238, 144, 27);

		eliminaUltima.addActionListener(new AscoltatoreAggiornatoreEntrate() {

			@Override
			protected void actionPerformedOverride(final ActionEvent e) throws Exception {
				super.actionPerformedOverride(e);

				try {
					aggiornaModelDaVista();
					if (ControlloreSpese.invocaComando(new CommandDeleteEntrata(modelEntrate))) {
						String msg = ControlloreSpese.getSingleton().getMessaggio("okentrata")+" " + modelEntrate.getNome() + " "+ ControlloreSpese.getSingleton().getMessaggio("correctlydeleted");
						Alert.segnalazioneInfo(msg);
					}
				} catch (final Exception e2) {
					e2.printStackTrace();
					Alert.segnalazioneErroreGrave(e2.getMessage());
					ConnectionPool.getSingleton().chiudiOggettiDb(null);
				}
			}

		});

		inserisci.addActionListener(new AscoltaInserisciEntrate(this));

	}

	public boolean nonEsistonoCampiNonValorizzati() {
		return getcNome() != null && getcDescrizione() != null && getcData() != null && getDataIns() != null
				&& getFisseOVar() != null && getdEuro() != 0.0 && getUtenti() != null;
	}

	private void initLabel() {
		final LabelBase lblNomeEntrata = new LabelBase("Nome Entrata", this);
		lblNomeEntrata.setText(ControlloreSpese.getSingleton().getMessaggio("name"));
		lblNomeEntrata.setBounds(13, 12, 97, 27);
		getContentPane().add(lblNomeEntrata);

		String msgEuro = ControlloreSpese.getSingleton().getMessaggio("eur");
		final LabelBase lblEuro = new LabelBase(msgEuro, this);
		lblEuro.setBounds(184, 165, 77, 27);
		getContentPane().add(lblEuro);

		final LabelBase lblCategorie = new LabelBase("Categorie", this);
		String msgType = ControlloreSpese.getSingleton().getMessaggio("type");
		lblCategorie.setText(msgType);
		lblCategorie.setBounds(181, 12, 77, 27);

		final LabelBase lblData = new LabelBase("Data", this);
		lblData.setBounds(13, 165, 77, 27);

		final LabelBase lblDescrizione = new LabelBase("Descrizione Spesa", this);
		lblDescrizione.setText(ControlloreSpese.getSingleton().getMessaggio("descr"));
		lblDescrizione.setBounds(14, 64, 123, 25);
	}

	/**
	 * @return the lista
	 */
	public static ArrayList<String> getLista() {
		return EntrateView.lista;
	}

	public void aggiornaModelDaVista() {
		final int idEntrate = (CacheEntrate.getSingleton().getMaxId()) + 1;
		getModelEntrate().setIdEntrate(idEntrate);

		final CorreggiTesto checkTesto = new CorreggiTesto(tfNome.getText());

		final String nome = checkTesto.getTesto();
		setcNome(nome);

		checkTesto.setTesto(taDescrizione.getText());
		final String descri = checkTesto.getTesto();
		setcDescrizione(descri);

		setFisseOVar((String) cbTipo.getSelectedItem());
		if (AltreUtil.checkData(tfData.getText())) {
			setcData(tfData.getText());
		} else {
			final String messaggio = ControlloreSpese.getSingleton().getMessaggio("datainformat");
			Alert.segnalazioneErroreGrave(messaggio);
		}
		if (AltreUtil.checkDouble(tfEuro.getText())) {
			final Double euro = Double.parseDouble(tfEuro.getText());
			setdEuro(AltreUtil.arrotondaDecimaliDouble(euro));
		} else {
			final String messaggio = ControlloreSpese.getSingleton().getMessaggio("valorenotcorrect");
			Alert.segnalazioneErroreGrave(messaggio);
		}
		setUtenti((Utenti) ControlloreSpese.getSingleton().getUtenteLogin());
		setDataIns(UtilDb.dataToString(new Date(), "yyyy/MM/dd"));
	}

	/**
	 * @param lista
	 *            the lista to set
	 */
	public static void setLista(final ArrayList<String> lista) {
		EntrateView.lista = lista;
	}

	@Override
	public void update(final Observable o, final Object arg) {
		tfNome.setText(getcNome());
		taDescrizione.setText(getcDescrizione());
		cbTipo.setSelectedItem(getFisseOVar());
		tfData.setText(getcData());
		tfEuro.setText(getdEuro().toString());

	}

}