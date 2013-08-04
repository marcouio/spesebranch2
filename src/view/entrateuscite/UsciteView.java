package view.entrateuscite;

import grafica.componenti.alert.Alert;
import grafica.componenti.button.ButtonBase;
import grafica.componenti.combo.ComboBoxBase;
import grafica.componenti.contenitori.FrameBase;
import grafica.componenti.label.LabelBase;
import grafica.componenti.textarea.TextAreaBase;
import grafica.componenti.textfield.testo.TextFieldTesto;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Observable;
import java.util.Vector;

import view.font.LabelListaGruppi;
import view.font.TextAreaF;
import view.font.TextFieldF;
import business.AltreUtil;
import business.ControlloreSpese;
import business.CorreggiTesto;
import business.ascoltatori.AscoltatoreAggiornatoreUscite;
import business.cache.CacheCategorie;
import business.cache.CacheUscite;
import business.comandi.singlespese.CommandDeleteSpesa;
import db.UtilDb;
import domain.CatSpese;
import domain.Utenti;
import domain.wrapper.WrapSingleSpesa;

public class UsciteView extends AbstractUsciteView {

	private static final long serialVersionUID = 1L;
	private final TextFieldTesto  tfNome;
	private final TextFieldTesto  tfData;
	private final TextFieldTesto  tfEuro;
	private final TextAreaBase   taDescrizione;
	private ComboBoxBase  cCategorie;

	/**
	 * Create the panel.
	 */
	public UsciteView(FrameBase frame, final WrapSingleSpesa spesa) {
		super(frame, spesa);

		modelUscita.addObserver(this);
		setTitle(ControlloreSpese.getSingleton().getMessaggio("insertcharge"));

		String msgLblName = ControlloreSpese.getSingleton().getMessaggio("name");
		final LabelBase lblNomeSpesa = new LabelBase(msgLblName, this);
		lblNomeSpesa.posizionaSottoA(null, 12, 12);
		
		tfNome = new TextFieldTesto(this);
		tfNome.posizionaSottoA(lblNomeSpesa, 0, 2);
		tfNome.setSize(150, 27);

		String msgLblCat = ControlloreSpese.getSingleton().getMessaggio("categories");
		final LabelBase lblCategorie = new LabelBase(msgLblCat, this);
		lblCategorie.posizionaADestraDi(null, 182, 12);
		
		Vector<CatSpese> vettoreCategorie = CacheCategorie.getSingleton().getVettoreCategoriePerCombo();
		cCategorie = new ComboBoxBase(this, vettoreCategorie.toArray());
		cCategorie.posizionaSottoA(lblCategorie, 0, 2);
		cCategorie.setSize(150, 27);
		
		initLabel();

		String msgLblDescr = ControlloreSpese.getSingleton().getMessaggio("descr");
		final LabelBase lblDescrizione = new LabelBase(msgLblDescr, this);
		lblDescrizione.posizionaSottoA(tfNome, 0, 7);
		lblDescrizione.setSize(150, 27);
		
		String mstTextArea = ControlloreSpese.getSingleton().getMessaggio("insertheredescr");
		taDescrizione = new TextAreaBase(mstTextArea, this);
		taDescrizione.posizionaSottoA(lblDescrizione, 0, 2);
		taDescrizione.setSize(318, 75);

		String msgLblData = ControlloreSpese.getSingleton().getMessaggio("date");
		final LabelBase lblData = new LabelBase(msgLblData, this);
		lblData.posizionaSottoA(taDescrizione, 0, 7);
		lblData.setSize(150, 27);
		
		tfData = new TextFieldTesto("0.0", this);
		final GregorianCalendar gc = new GregorianCalendar();
		tfData.setText(UtilDb.dataToString(gc.getTime(), "yyyy/MM/dd"));
		tfData.posizionaSottoA(lblData, 0, 2);
		tfData.setSize(150, 27);
		
		String msgLblEuro = ControlloreSpese.getSingleton().getMessaggio("eur");
		final LabelBase lblEuro = new LabelBase(msgLblEuro, this);
		lblEuro.posizionaADestraDi(lblData, 20, 0);
		lblEuro.setSize(150, 27);
		
		tfEuro = new TextFieldTesto(this);
		tfEuro.setColumns(10);
		tfEuro.posizionaSottoA(lblEuro, 0, 2);
		tfEuro.setSize(150, 27);
		
		final LabelListaGruppi lblDescrizione_1 = new LabelListaGruppi(ControlloreSpese.getSingleton().getMessaggio("descr")+" "+ControlloreSpese.getSingleton().getMessaggio("category"));
		lblDescrizione_1.setBounds(13, 216, 232, 27);
		getContentPane().add(lblDescrizione_1);
		
		final TextAreaBase descCateg = new TextAreaBase(this);
		descCateg.setText(ControlloreSpese.getSingleton().getMessaggio("heredesc"));
		descCateg.setBounds(13, 242, 318, 75);

		
		cCategorie.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(final ItemEvent e) {
				CatSpese spese = null;
				if (cCategorie.getSelectedIndex() != 0) {
					spese = (CatSpese) cCategorie.getSelectedItem();
					// int indice = categorie.getSelectedIndex();
					// il campo sotto serve per inserire la descrizione nel
					// caso
					// si selezioni
					// una categoria e si vogliono maggiori info

					descCateg.setText(spese != null ? spese.getDescrizione() : "");
				}

			}
		});



		// Bottone Elimina
		final ButtonBase eliminaUltima = new ButtonBase(this);
		eliminaUltima.addActionListener(new AscoltatoreAggiornatoreUscite() {

			@Override
			protected void actionPerformedOverride(final ActionEvent e) throws Exception {
				super.actionPerformedOverride(e);
				try {
					ControlloreSpese.invocaComando(new CommandDeleteSpesa(modelUscita));
					// TODO verificare se necessario ripristinare l'update
					// update(modelUscita, null);
				} catch (final Exception e1) {
					Alert.segnalazioneErroreGrave("Cancellazione della spesa " + modelUscita.getNome() + " non riuscita: " + e1.getMessage());
					e1.printStackTrace();
				}
			}
		});

		eliminaUltima.setText("Elimina Ultima");
		eliminaUltima.setBounds(184, 325, 147, 27);
		getContentPane().add(eliminaUltima);

		final ButtonBase inserisci = new ButtonBase(this);
		inserisci.setText("Inserisci");
		inserisci.setBounds(13, 325, 150, 27);
		getContentPane().add(inserisci);

		inserisci.addActionListener(new AscoltaInserisciUscite(this));

	}

	public boolean nonEsistonoCampiNonValorizzati() {
		return getcNome() != null && getcDescrizione() != null && getcData() != null && getDataIns() != null && getCategoria() != null && getdEuro() != 0.0 && getUtenti() != null;
	}

	private void initLabel() {

	}

	public void aggiornaModelDaVista() throws Exception {
		final int idSpesa = (CacheUscite.getSingleton().getMaxId()) + 1;
		getModelUscita().setIdSpesa(idSpesa);

		final CorreggiTesto checkTesto = new CorreggiTesto(tfNome.getText());
		final String nomeCheckato = checkTesto.getTesto();
		setcNome(nomeCheckato);

		checkTesto.setTesto(taDescrizione.getText());
		final String descrizioneCheckato = checkTesto.getTesto();
		setcDescrizione(descrizioneCheckato);

		setCategoria((CatSpese) cCategorie.getSelectedItem());
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
	 * @return the categorie
	 */
	public ComboBoxBase getComboCategorie() {
		return cCategorie;
	}

	/**
	 * @param categorie
	 *            the categorie to set
	 */
	public void setComboCategorie(final ComboBoxBase categorie) {
		this.cCategorie = categorie;
	}

	@Override
	public void update(final Observable o, final Object arg) {
		tfNome.setText(getcNome());
		taDescrizione.setText(getcDescrizione());
		cCategorie.setSelectedItem(getCategoria());
		tfData.setText(getcData());
		tfEuro.setText(getdEuro().toString());
	}
}
