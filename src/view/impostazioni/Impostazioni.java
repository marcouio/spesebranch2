package view.impostazioni;

import grafica.componenti.alert.Alert;
import grafica.componenti.button.ButtonBase;
import grafica.componenti.label.LabelBase;
import grafica.componenti.textfield.testo.TextFieldTesto;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import view.impostazioni.ascoltatori.AscoltatoreLanguage;
import view.impostazioni.ascoltatori.AscoltatoreLook;
import xml.CoreXMLManager;
import business.ControlloreSpese;
import business.aggiornatori.AggiornatoreManager;
import business.ascoltatori.AscoltatoreAggiornatoreNiente;
import business.ascoltatori.AscoltatoreAggiornatoreTutto;
import business.cache.CacheLookAndFeel;
import db.UtilDb;
import domain.Lookandfeel;
import domain.Utenti;
import domain.wrapper.WrapEntrate;
import domain.wrapper.WrapSingleSpesa;

public class Impostazioni extends JDialog {

	private static final long	serialVersionUID	= 1L;
	private static Impostazioni	singleton;
	private static String		posDatabase			= "";

	public static void main(final String[] args) {
		final Impostazioni dialog = new Impostazioni();
		dialog.pack();
		dialog.setVisible(true);
	}

	public static final Impostazioni getSingleton() {
		synchronized (Impostazioni.class) {
			if (singleton == null) {
				singleton = new Impostazioni();
			}
		} // if
		return singleton;
	} // getSingleton()

	private JTextField				dataOdierna;
	private JTextField				utente;
	private ArrayList<String>		listaLook;
	private JComboBox<Lookandfeel>	comboLook;
	private TextFieldTesto				annotextField;
	private static int				anno	= new GregorianCalendar().get(Calendar.YEAR);
	private static JTextField		caricaDatabase;

	public Impostazioni() {
		initGUI();
	}

	private void initGUI() {
		try {
			this.setModalityType(ModalityType.APPLICATION_MODAL);
			this.setTitle("Setting");
			this.setPreferredSize(new Dimension(626, 250));
			getContentPane().setLayout(null);

			final JLabel calendario = new LabelBase("Data Odierna", this);
			calendario.setBounds(22, 86, 87, 14);
			dataOdierna = new TextFieldTesto(this);
			dataOdierna.setBounds(140, 82, 113, 27);
			dataOdierna.setEditable(false);
			final GregorianCalendar gc = new GregorianCalendar();
			dataOdierna.setText(UtilDb.dataToString(gc.getTime(), "dd-MM-yyyy"));
			getContentPane().add(dataOdierna);
			getContentPane().add(calendario);

			utente = new TextFieldTesto(this);
			Utenti utenteLogin = (Utenti) ControlloreSpese.getSingleton().getUtenteLogin();
			utente.setText(utenteLogin.getUsername());
			utente.setBounds(140, 126, 113, 27);
			getContentPane().add(utente);

			final JLabel lblImpostaAnno = new LabelBase("Imposta anno", this);
			lblImpostaAnno.setBounds(278, 79, 97, 27);
			getContentPane().add(lblImpostaAnno);

			final ButtonBase btnChange = new ButtonBase(this);
			btnChange.setText("Cambia");
			btnChange.setBounds(504, 78, 91, 27);
			btnChange.addActionListener(new AscoltatoreAggiornatoreTutto() {

				@Override
				public void actionPerformedOverride(final ActionEvent e) {
					try {
						anno = Integer.parseInt(annotextField.getText());
					}
					catch (final Exception e1) {
						e1.printStackTrace();
					}
				}
			});
			getContentPane().add(btnChange);

			final JLabel lblCaricaDatabase = new LabelBase("Carica Database", this);
			lblCaricaDatabase.setBounds(22, 183, 113, 14);
			getContentPane().add(lblCaricaDatabase);

			final ButtonBase btnCarica = new ButtonBase(this);
			btnCarica.setText("Carica");
			btnCarica.setBounds(333, 179, 91, 27);
			getContentPane().add(btnCarica);
			btnCarica.addActionListener(new AscoltatoreAggiornatoreTutto() {

				@Override
				public void actionPerformedOverride(final ActionEvent e) {
					AggiornatoreManager.aggiornamentoPerImpostazioni();
				}
			});

			caricaDatabase = new TextFieldTesto(this);
			caricaDatabase.setBounds(140, 179, 149, 27);
			caricaDatabase.setText(posDatabase);
			getContentPane().add(caricaDatabase);

			final ButtonBase button = new ButtonBase(this);
			button.setText("...");
			button.setBounds(287, 179, 29, 27);
			getContentPane().add(button);

			final ButtonBase elimina = new ButtonBase(this);
			elimina.setText("Elimina");
			elimina.addActionListener(new AscoltatoreAggiornatoreTutto() {
				@Override
				public void actionPerformedOverride(final ActionEvent arg0) {
					if (new WrapEntrate().deleteAll() && new WrapSingleSpesa().deleteAll()) {
						// TODO creare comando per eliminare tutto
						Alert.segnalazioneInfo("Ok, tutti i dati sono stati cancellati: puoi ripartire!");
						try {
							AggiornatoreManager.aggiornamentoGenerale(WrapEntrate.NOME_TABELLA);
							AggiornatoreManager.aggiornamentoGenerale(WrapSingleSpesa.NOME_TABELLA);
						}
						catch (final Exception e) {
							e.getMessage();
						}
					}
				}
			});

			elimina.setBounds(504, 125, 91, 27);
			getContentPane().add(elimina);

			final JDesktopPane desktopPane = new JDesktopPane();
			desktopPane.setBounds(94, 138, 1, 1);
			getContentPane().add(desktopPane);

			final LabelBase lbltstEliminaTuttiLe = new LabelBase("Carica Database", this);
			lbltstEliminaTuttiLe.setText("Elimina dati per entrate e uscite");
			lbltstEliminaTuttiLe.setBounds(278, 126, 232, 27);
			getContentPane().add(lbltstEliminaTuttiLe);

			final LabelBase lbltstUtente = new LabelBase("Data Odierna", this);
			lbltstUtente.setText("Utente");
			lbltstUtente.setBounds(22, 130, 87, 14);
			getContentPane().add(lbltstUtente);

			annotextField = new TextFieldTesto(this);
			annotextField.setText(Integer.toString(gc.get(Calendar.YEAR)));
			annotextField.setBounds(375, 78, 113, 27);
			getContentPane().add(annotextField);

			final CacheLookAndFeel cacheLook = CacheLookAndFeel.getSingleton();
			final Vector<Lookandfeel> vettore = cacheLook.getVettoreLooksPerCombo();

			Lookandfeel look = null;
			comboLook = new JComboBox<Lookandfeel>(vettore);
			Lookandfeel system = new Lookandfeel();
			system.setNome("System");
			system.setValore(UIManager.getSystemLookAndFeelClassName());
			system.setUsato(0);
			comboLook.addItem(system);

			comboLook.setSelectedItem("System");
			for (int i = 0; i < vettore.size(); i++) {
				look = vettore.get(i);
				if (look.getUsato() == 1) {
					comboLook.setSelectedIndex(i);
				}
			}

			comboLook.setBounds(140, 24, 115, 24);
			getContentPane().add(comboLook);
			comboLook.addActionListener(new AscoltatoreLook(comboLook, vettore));

			final JLabel labelLook = new JLabel("Look");
			labelLook.setBounds(22, 29, 70, 15);
			getContentPane().add(labelLook);

			JLabel lblLang = new JLabel("Language");
			lblLang.setBounds(278, 29, 113, 15);
			getContentPane().add(lblLang);

			String[] languages = new String[] { "it", "en" };
			JComboBox<String> comboLanguage = new JComboBox<String>(languages);

			comboLanguage.addActionListener(new AscoltatoreLanguage(comboLanguage));
			comboLanguage.setBounds(396, 24, 115, 24);

			for (int i = 0; i < languages.length; i++) {
				String lingua = CoreXMLManager.getSingleton().getLanguage();
				if (languages[i].equals(lingua)) {
					// comboLanguage.setSelectedIndex(i);
					// break;
				}
			}

			getContentPane().add(comboLanguage);

			button.addActionListener(new AscoltatoreAggiornatoreNiente() {

				@Override
				public void actionPerformedOverride(final ActionEvent arg0) {
					final JFileChooser fileopen = new JFileChooser();
					final FileFilter filter = new FileNameExtensionFilter("db files", "db", "sqlite");
					fileopen.addChoosableFileFilter(filter);

					final int ret = fileopen.showDialog(null, "Open file");

					if (ret == JFileChooser.APPROVE_OPTION) {
						final File file = fileopen.getSelectedFile();
						posDatabase = file.getAbsolutePath();
						caricaDatabase.setText(posDatabase);
					}

				}
			});

		}
		catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public TextFieldTesto getAnnotextField() {
		return annotextField;
	}

	public void setAnnotextField(final TextFieldTesto annotextField) {
		this.annotextField = annotextField;
	}

	/**
	 * @return the dataOdierna
	 */
	public JTextField getDataOdierna() {
		return dataOdierna;
	}

	/**
	 * @param dataOdierna
	 *            the dataOdierna to set
	 */
	public void setDataOdierna(final JTextField dataOdierna) {
		this.dataOdierna = dataOdierna;
	}

	/**
	 * @return the listaLook
	 */
	public ArrayList<String> getListaLook() {
		return listaLook;
	}

	/**
	 * @param listaLook
	 *            the listaLook to set
	 */
	public void setListaLook(final ArrayList<String> listaLook) {
		this.listaLook = listaLook;
	}

	public static int getAnno() {
		return anno;
	}

	public static void setAnno(final int anno) {
		Impostazioni.anno = anno;
	}

	public static JTextField getCaricaDatabase() {
		return caricaDatabase;
	}

	public static void setCaricaDatabase(final JTextField caricaDatabase) {
		Impostazioni.caricaDatabase = caricaDatabase;
	}

	/**
	 * @return the utente
	 */
	public JTextField getUtente() {
		return utente;
	}

	/**
	 * @param utente
	 *            the utente to set
	 */
	public void setUtente(final JTextField utente) {
		this.utente = utente;
	}

	/**
	 * @return the comboLook
	 */
	public JComboBox<Lookandfeel> getComboLook() {
		return comboLook;
	}

	/**
	 * @param comboLook
	 *            the comboLook to set
	 */
	public void setComboLook(final JComboBox<Lookandfeel> comboLook) {
		this.comboLook = comboLook;
	}

	public static String getPosDatabase() {
		return posDatabase;
	}

	public static void setPosDatabase(final String posDatabase) {
		Impostazioni.posDatabase = posDatabase;
	}
}
