package view.impostazioni;

import grafica.componenti.button.ButtonBase;
import grafica.componenti.combo.ComboBoxBase;
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
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import view.impostazioni.ascoltatori.AscoltatoreAggiornaCategoria;
import view.impostazioni.ascoltatori.AscoltatoreCancellaCategoria;
import view.impostazioni.ascoltatori.AscoltatoreInserisciCategoria;
import business.CorreggiTesto;
import business.cache.CacheCategorie;
import business.cache.CacheGruppi;
import domain.CatSpese;
import domain.Gruppi;
import domain.wrapper.WrapCatSpese;

public class CategorieView extends AbstractCategorieView {

	private CatSpese categoria = null;
	private JTextArea taDescrizione;
	private ComboBoxBase<?> cbImportanza;
	private JTextField tfNome;
	private ComboBoxBase<?> cbCategorie;
	private Vector<CatSpese> categorieSpesa;
	private ComboBoxBase<?> cbGruppi;

	private static final long serialVersionUID = 1L;

	public static void main(final String[] args) {
		final CategorieView dialog = new CategorieView(new WrapCatSpese());
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	}

	public CategorieView(final WrapCatSpese cat) {
		super(cat);

		modelCatSpese.addObserver(this);
		initGUI();
	}

	private void initGUI() {
		try {

			setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			setModalityType(ModalityType.APPLICATION_MODAL);
			setTitle("Categorie");
			this.setPreferredSize(new Dimension(260, 556));
			setLayout(null);

			initLabel();

			// Nome Spesa
			tfNome = new TextFieldTesto(this);
			tfNome.setBounds(26, 37, 206, 26);

			// Descrizione
			taDescrizione = new TextAreaBase("Inserisci la descrizione della categoria", 50, 25, this);
			taDescrizione.setLineWrap(true);
			taDescrizione.setWrapStyleWord(true);
			taDescrizione.setBounds(26, 91, 206, 88);

			// importanza Spesa
			cbImportanza = new ComboBoxBase(this);
			cbImportanza.addItem("");
			cbImportanza.addItem("Futili");
			cbImportanza.addItem("Variabili");
			cbImportanza.addItem("Fisse");
			cbImportanza.setBounds(26, 210, 206, 25);

			// bottone invia
			final ButtonBase inserisci = new ButtonBase(this);
			inserisci.setBounds(26, 305, 206, 25);
			inserisci.setText("Inserisci Categoria");

			final Vector<Gruppi> vettoreGruppi = CacheGruppi.getSingleton().getVettoreCategoriePerCombo(CacheGruppi.getSingleton().getAllGruppi());
			// combo gruppi
			cbGruppi = new ComboBoxBase(this);
			for (int i = 0; i < vettoreGruppi.size(); i++) {
				cbGruppi.addItem(vettoreGruppi.get(i));
			}
			cbGruppi.setBounds(26, 265, 206, 25);
			getContentPane().add(cbGruppi);

			categorieSpesa = CacheCategorie.getSingleton().getVettoreCategoriePerCombo(CacheCategorie.getSingleton().getAllCategorie());
			cbCategorie = new ComboBoxBase(this,categorieSpesa);
			cbCategorie.setBounds(26, 380, 206, 25);
			cbCategorie.addItemListener(new ItemListener() {

				@Override
				public void itemStateChanged(final ItemEvent e) {

					if (cbCategorie.getSelectedIndex() != 0 && cbCategorie.getSelectedItem() != null) {
						categoria = (CatSpese) cbCategorie.getSelectedItem();
						tfNome.setText(categoria.getNome());
						taDescrizione.setText(categoria.getDescrizione());
						cbImportanza.setSelectedItem(categoria.getImportanza());
						final int numeroGruppi = cbGruppi.getModel().getSize();
						boolean trovato = false;
						for (int i = 0; i < numeroGruppi; i++) {
							final Gruppi gruppo = (Gruppi) cbGruppi.getModel().getElementAt(i);
							if (gruppo != null && gruppo.getNome()!=null &&  categoria.getGruppi()!=null) {
								if(gruppo.getNome().equals(categoria.getGruppi().getNome())){
									cbGruppi.setSelectedIndex(i);
									trovato = true;
								}
							}
						}
						if(!trovato){
							cbGruppi.setSelectedIndex(0);
						}

					}
				}
			});

			// bottone Update
			final ButtonBase aggiorna = new ButtonBase(this);
			aggiorna.setBounds(26, 421, 100, 25);
			aggiorna.setText("Aggiorna");
			aggiorna.addActionListener(new AscoltatoreAggiornaCategoria(this));

			// bottone insert
			inserisci.addActionListener(new AscoltatoreInserisciCategoria(this));

			// bottone cancella
			final ButtonBase cancella = new ButtonBase(this);
			cancella.setText("Cancella");
			cancella.setBounds(132, 421, 100, 25);
			cancella.addActionListener(new AscoltatoreCancellaCategoria(this));

			getContentPane().add(cancella);
			getContentPane().add(inserisci);
			getContentPane().add(taDescrizione);
			getContentPane().add(cbImportanza);
			getContentPane().add(tfNome);
			getContentPane().add(cbCategorie);
			getContentPane().add(aggiorna);

		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public boolean nonEsistonoCampiNonValorizzati() {
		return getcDescrizione() != null && getcImportanza() != null && getcNome() != null && getcImportanza() != null;
	}

	public void aggiornaModelDaVista(final String actionCommand) throws Exception {

		if (actionCommand.equals("Inserisci")) {
			final int idCategoria = (CacheCategorie.getSingleton().getMaxId()) + 1;
			getModelCatSpese().setIdCategoria(idCategoria);
		} else {
			final int idCategoriaDaCombo = getCategoria().getIdCategoria();
			if (idCategoriaDaCombo == 0) {
				final int idCategorie = (CacheCategorie.getSingleton().getMaxId()) + 1;
				getModelCatSpese().setIdCategoria(idCategorie);
			} else {
				getModelCatSpese().setIdCategoria(idCategoriaDaCombo);
			}
		}
		final CorreggiTesto checkTestoNome = new CorreggiTesto(tfNome.getText());
		setcNome(checkTestoNome.getTesto());

		final CorreggiTesto checkTestoDescrizione = new CorreggiTesto(taDescrizione.getText());
		setcDescrizione(checkTestoDescrizione.getTesto());
		setcImportanza((String) cbImportanza.getSelectedItem());
		setGruppo((Gruppi) cbGruppi.getSelectedItem());

	}

	public Vector<CatSpese> getCategorieSpesa() {
		return categorieSpesa;
	}

	public void setCategorieSpesa(final Vector<CatSpese> categorieSpesa) {
		this.categorieSpesa = categorieSpesa;
	}

	/**
	 * @return the comboCategorie
	 */
	public JComboBox getComboCategorie() {
		return cbCategorie;
	}

	/**
	 * @param comboCategorie
	 *            the comboCategorie to set
	 */
	public void setComboCategorie(final ComboBoxBase comboCategorie) {
		this.cbCategorie = comboCategorie;
	}

	public ComboBoxBase getComboGruppi() {
		return cbGruppi;
	}

	public void setComboGruppi(final ComboBoxBase comboGruppi) {
		this.cbGruppi = comboGruppi;
	}

	public CatSpese getCategoria() {
		return categoria;
	}

	@Override
	public void update(final Observable o, final Object arg) {
		tfNome.setText(getcNome());
		taDescrizione.setText(getcDescrizione());
		cbImportanza.setSelectedItem(getcImportanza());
		cbGruppi.setSelectedItem(getGruppo());

	}

	private void initLabel() {

		// Label nome
		final JLabel labelNome = new LabelBase(this);
		labelNome.setBounds(26, 12, 100, 25);
		labelNome.setText("Categoria");

		// Label descrizione
		final JLabel labelDescrizione = new LabelBase(this);
		labelDescrizione.setBounds(26, 65, 90, 25);
		labelDescrizione.setText("Descrizione");

		// Label Importanza
		final JLabel labelCategorie = new LabelBase(this);
		labelCategorie.setBounds(26, 184, 100, 25);
		labelCategorie.setText("Importanza");

		// Label Combo Categorie
		final JLabel labelComboCategorie = new LabelBase(this);
		labelComboCategorie.setBounds(26, 352, 100, 25);
		labelComboCategorie.setText("Lista Categorie");

		final LabelBase lbltstGruppo = new LabelBase(this);
		lbltstGruppo.setText("Gruppo");
		lbltstGruppo.setBounds(26, 239, 100, 25);

	}

}