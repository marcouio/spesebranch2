package view.componenti.movimenti;

import grafica.componenti.alert.Alert;
import grafica.componenti.button.ButtonBase;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import view.GeneralFrame;
import view.entrateuscite.AbstractUsciteView;
import view.font.LabelListaGruppi;
import view.font.TextFieldF;
import business.AltreUtil;
import business.Controllore;
import business.aggiornatori.AggiornatoreManager;
import business.cache.CacheUscite;
import business.comandi.singlespese.CommandDeleteSpesa;
import business.comandi.singlespese.CommandUpdateSpesa;
import domain.CatSpese;
import domain.ISingleSpesa;
import domain.SingleSpesa;
import domain.Utenti;
import domain.wrapper.Model;
import domain.wrapper.WrapSingleSpesa;

public class DialogUsciteMov extends AbstractUsciteView {

	private static final long serialVersionUID = 1L;
	private final JLabel labelEuro = new LabelListaGruppi(Controllore.getSingleton().getMessaggio("eur"));
	private final JLabel labelData = new LabelListaGruppi(Controllore.getSingleton().getMessaggio("date"));
	private final JLabel labelCategoria = new LabelListaGruppi(Controllore.getSingleton().getMessaggio("category"));
	private final JLabel labelDescrizione = new LabelListaGruppi(Controllore.getSingleton().getMessaggio("descr"));
	private final JLabel labelNome = new LabelListaGruppi(Controllore.getSingleton().getMessaggio("name"));
	private final JLabel labelDataIns = new LabelListaGruppi(Controllore.getSingleton().getMessaggio("insertdate"));
	private final JLabel labelIdSpesa = new LabelListaGruppi(Controllore.getSingleton().getMessaggio("key"));

	private JTextField tfEuro = new TextFieldF();
	private JTextField tfData = new TextFieldF();
	private JComboBox cbCategorie;
	private JTextField taDescrizione = new TextFieldF();
	private JTextField tfNome = new TextFieldF();
	private final JTextField tfDataIns = new TextFieldF();
	private JTextField idSpesa = new TextFieldF();
	private final JButton update = new ButtonBase(Controllore.getSingleton().getMessaggio("update"), this);
	private final JButton delete = new ButtonBase(Controllore.getSingleton().getMessaggio("delete"), this);

	/**
	 * Auto-generated main method to display this JDialog
	 */

	public void main(final String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				final DialogUsciteMov inst = new DialogUsciteMov(new WrapSingleSpesa());
				inst.setVisible(true);
			}
		});
	}

	public DialogUsciteMov(final WrapSingleSpesa uscita) {
		super(uscita);
		initGUI();
	}

	private void initGUI() {
		try {
			// questo permette di mantenere il focus sulla dialog
			this.setModalityType(ModalityType.APPLICATION_MODAL);
			cbCategorie = new JComboBox(Model.getSingleton().getCategorieCombo(false));
			idSpesa.setEditable(false);
			this.setLayout(new GridLayout(0, 2));
			update.setSize(60, 40);
			delete.setSize(60, 40);
			labelData.setSize(100, 40);
			labelDescrizione.setSize(100, 40);
			labelEuro.setSize(100, 40);
			labelIdSpesa.setSize(100, 40);
			labelNome.setSize(100, 40);
			labelCategoria.setSize(100, 40);
			labelDataIns.setSize(100, 40);

			update.addActionListener(new AscoltatoreDialogUscite(this));
			delete.addActionListener(new AscoltatoreDialogUscite(this));

			this.add(labelIdSpesa);
			this.add(idSpesa);
			this.add(labelNome);
			this.add(tfNome);
			this.add(labelDescrizione);
			this.add(taDescrizione);
			this.add(labelData);
			this.add(tfData);
			this.add(labelCategoria);
			this.add(cbCategorie);
			this.add(labelEuro);
			this.add(tfEuro);
			this.add(labelDataIns);
			this.add(tfDataIns);
			this.add(update);
			this.add(delete);
			setSize(300, 500);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public boolean nonEsistonoCampiNonValorizzati() {
		return getcNome() != null && getcDescrizione() != null && getcData() != null && getDataIns() != null && getCategoria() != null && getdEuro() != 0.0 && getUtenti() != null;
	}

	public void aggiornaModelDaVista() {
		if (AltreUtil.checkInteger(idSpesa.getText())) {
			getModelUscita().setIdSpesa(idSpesa.getText() != "" ? Integer.parseInt(idSpesa.getText()) : 0);
		} else {
			final String messaggio = Controllore.getSingleton().getMessaggio("idintero");
			Alert.segnalazioneErroreGrave(messaggio);
		}
		setcNome(tfNome.getText());
		setcDescrizione(taDescrizione.getText());
		setCategoria((CatSpese) cbCategorie.getSelectedItem());
		if (AltreUtil.checkData(tfData.getText())) {
			setcData(tfData.getText());
		} else {
			final String messaggio = Controllore.getSingleton().getMessaggio("datainformat");
			Alert.errore(messaggio, Alert.TITLE_ERROR);
		}
		if (AltreUtil.checkDouble(tfEuro.getText())) {
			final Double euro = Double.parseDouble(tfEuro.getText());
			setdEuro(AltreUtil.arrotondaDecimaliDouble(euro));
		} else {
			final String messaggio = Controllore.getSingleton().getMessaggio("valorenotcorrect");
			Alert.errore(messaggio, Alert.TITLE_ERROR);
		}
		setUtenti((Utenti) Controllore.getSingleton().getUtenteLogin());
		setDataIns(tfDataIns.getText());
	}

	public void setEuro(final JTextField euro) {
		this.tfEuro = euro;
	}

	public JTextField getEuro() {
		return tfEuro;
	}

	public void setData(final JTextField data) {
		this.tfData = data;
	}

	public JTextField getData() {
		return tfData;
	}

	public void setDescrizione(final JTextField descrizione) {
		this.taDescrizione = descrizione;
	}

	public JTextField getDescrizione() {
		return taDescrizione;
	}

	public void setCategoria(final JComboBox categoria) {
		this.cbCategorie = categoria;
	}

	public JComboBox getComboCategoria() {
		return cbCategorie;
	}

	public void setNome(final JTextField nome) {
		this.tfNome = nome;
	}

	public JTextField getNome() {
		return tfNome;
	}

	public void setidSpesa(final JTextField idSpesa) {
		this.idSpesa = idSpesa;
	}

	public JTextField getIdSpesa() {
		return idSpesa;
	}

	public JTextField getTfDataIns() {
		return tfDataIns;
	}

	@Override
	public void update(final Observable o, final Object arg) {

	}

	public class AscoltatoreDialogUscite implements ActionListener {

		DialogUsciteMov dialog;

		public AscoltatoreDialogUscite(final DialogUsciteMov dialog) {
			this.dialog = dialog;
		}

		@Override
		public void actionPerformed(final ActionEvent e) {
			if (e.getActionCommand().equals(Controllore.getSingleton().getMessaggio("update"))) {
				aggiornaModelDaVista();
				String[] nomiColonne = null;
				try {
					nomiColonne = (String[]) AltreUtil.generaNomiColonne(WrapSingleSpesa.NOME_TABELLA);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				final JTextField campo = ((GeneralFrame) Controllore.getSingleton().getView().getContentPane()).getTabMovimenti().getTabMovUscite().getCampo();
				final SingleSpesa oldSpesa = CacheUscite.getSingleton().getSingleSpesa(idSpesa.getText());

				if (dialog.nonEsistonoCampiNonValorizzati()) {
					try {
						if (!Controllore.invocaComando(new CommandUpdateSpesa(oldSpesa, (ISingleSpesa) modelUscita.getEntitaPadre()))) {
							Alert.segnalazioneErroreGrave("Spesa " + oldSpesa.getNome() + " non aggiornata");
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					AggiornatoreManager.aggiornaMovimentiUsciteDaEsterno(nomiColonne, Integer.parseInt(campo.getText()));

					// chiude la dialog e rilascia le risorse
					dispose();
				} else {
					final String msg = Controllore.getSingleton().getMessaggio("charge")+ oldSpesa.getNome() + " non aggiornata: tutti i dati devono essere valorizzati";
					Alert.segnalazioneErroreGrave(msg);
				}

			} else if (e.getActionCommand().equals("Cancella")) {
				aggiornaModelDaVista();
				try {
					if (!Controllore.invocaComando(new CommandDeleteSpesa(modelUscita))) {
						final String msg = Controllore.getSingleton().getMessaggio("charge")+ modelUscita.getNome() + " non aggiornata: tutti i dati devono essere valorizzati";
						Alert.segnalazioneErroreGrave(msg);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				// chiude la dialog e rilascia le risorse
				dispose();
			}
		}
	}

}
