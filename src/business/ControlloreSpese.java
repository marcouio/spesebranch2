package business;

import grafica.componenti.ExceptionGraphics;
import grafica.componenti.alert.Alert;
import grafica.componenti.contenitori.FrameBase;

import java.awt.Container;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import messaggi.I18NManager;
import view.GeneralFrame;
import view.MyWindowListener;
import business.aggiornatori.AggiornatoreManager;
import business.cache.CacheLookAndFeel;
import business.cache.CacheUtenti;

import command.AbstractCommand;
import command.CommandManager;

import controller.ControlloreBase;
import db.ConnectionPool;
import domain.IUtenti;
import domain.Lookandfeel;
import domain.Utenti;
import domain.wrapper.WrapLookAndFeel;
import domain.wrapper.WrapUtenti;

public class ControlloreSpese extends ControlloreBase {

	private FrameBase view;
	private static GeneralFrame pannello;
	private static IUtenti utenteLogin;
	private static CommandManager commandManager;
	private static AggiornatoreManager aggiornatoreManager;
	private InizializzatoreFinestre initFinestre;
	private static ControlloreSpese singleton;
	public static String lookUsato;

	@Override
	public void mainOverridato(final FrameBase frame) throws Exception {
		init();
		Database.DB_URL = Database.DB_URL_WORKSPACE;
		verificaPresenzaDb();
		setStartUtenteLogin();
		view = frame;
		final MyWindowListener windowListener = new MyWindowListener(view);
		view.addWindowListener(windowListener);
		view.addComponentListener(windowListener);
		view.addWindowFocusListener(windowListener);
		view.addMouseListener(windowListener);
		settaLookFeel();
		final Container contentPane = view.getContentPane();
		contentPane.add(getPannello());
		view.setResizable(false);
		view.setTitle(getMessaggio("title"));
		view.setVisible(true);
	}

	@Override
	public String getMessaggio(final String chiave) {
		return I18NManager.getSingleton().getMessaggio(chiave);
	}

	private static void settaLookFeel() {
		try {
			final CacheLookAndFeel cacheLook = CacheLookAndFeel.getSingleton();
			final java.util.Vector<Lookandfeel> vettore = cacheLook.getVettoreLooksPerCombo();
			Lookandfeel look = null;
			Lookandfeel lookDaUsare = null;
			for (int i = 0; i < vettore.size(); i++) {
				look = vettore.get(i);
				// verifico se sul database quale look era scelto
				if (look.getUsato() == 1) {
					lookDaUsare = look;
					break;
				}
			}
			if (lookDaUsare != null && lookDaUsare.getValore() != null) {
				UIManager.setLookAndFeel(lookDaUsare.getValore());
				lookUsato = lookDaUsare.getValore();
			} else {
				// se non c'era un look selezionato setto quello di sistema
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				lookUsato = UIManager.getSystemLookAndFeelClassName();
			}
			SwingUtilities.updateComponentTreeUI(ControlloreSpese.getSingleton().getView());
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(final String[] args) {
		ControlloreSpese.getSingleton().myMain(ControlloreSpese.getSingleton(), true, "myApplication");
	}

	private void verificaPresenzaDb() throws Exception {
		final Connection cn = ConnectionPool.getSingleton().getConnection();
		try {
			final String sql = "SELECT * FROM " + WrapLookAndFeel.NOME_TABELLA;
			final Statement st = cn.createStatement();
			final ResultSet rs = st.executeQuery(sql);
			getLog().info("Verificata presenza Db rs trovato:" +rs.toString());
		} catch (final SQLException e) {
			try {
				Database.getSingleton().generaDB();
				Alert.info("Database non presente: è stato rigenerato", "");
			} catch (final SQLException e1) {
				ControlloreSpese.getLog().severe("Database non creato: " + e.getMessage());
			}
		}
		ConnectionPool.getSingleton().chiudiOggettiDb(cn);
	}

	private ControlloreSpese() {
	}

	public GeneralFrame getPannello() {
		if (pannello == null) {
			try {
				pannello = GeneralFrame.getSingleton();
			} catch (final ExceptionGraphics e) {
				e.printStackTrace();
			}
		}
		return pannello;
	}

	public static boolean invocaComando(final AbstractCommand comando) throws Exception {
		return ControlloreSpese.getSingleton().getCommandManager().invocaComando(comando);
	}

	/**
	 * Controlla se esiste sul db l'utente guest, altrimenti lo crea
	 * @throws Exception 
	 */
	private static void setStartUtenteLogin() throws Exception {
		final Utenti utenteGuest = CacheUtenti.getSingleton().getUtente("1");
		if (utenteGuest == null || utenteGuest.getNome() == null) {
			final Utenti utente = new Utenti();
			utente.setIdUtente(1);
			utente.setUsername("guest");
			utente.setPassword("guest");
			utente.setNome("guest");
			utente.setCognome("guest");
			final WrapUtenti wrap = new WrapUtenti();
			wrap.insert(utente);
		}

		utenteLogin = CacheUtenti.getSingleton().getUtente("1");
	}

	public static ControlloreSpese getSingleton() {
		if (singleton == null) {
			synchronized (ControlloreSpese.class) {
				if (singleton == null) {
					singleton = new ControlloreSpese();
				}
			} // if
		} // if
		return singleton;
	}

	@Override
	public Object getUtenteLogin() {
		return utenteLogin;
	}

	public static void setUtenteLogin(final Utenti utenteLogin) {
		ControlloreSpese.utenteLogin = utenteLogin;
	}

	public AggiornatoreManager getAggiornatoreManager() {
		if (aggiornatoreManager == null) {
			aggiornatoreManager = AggiornatoreManager.getSingleton();
		}
		return aggiornatoreManager;
	}

	@Override
	public CommandManager getCommandManager() {
		if (commandManager == null) {
			commandManager = CommandManager.getIstance();
		}
		return commandManager;
	}

	public FrameBase getView() {
		return view;
	}

	@Override
	public void quit() {
		view.setVisible(false);
		view.dispose();
		System.exit(0);
	}

	public InizializzatoreFinestre getInitFinestre() {
		if (initFinestre == null) {
			initFinestre = new InizializzatoreFinestre();
		}
		return initFinestre;
	}

	public void setInitFinestre(final InizializzatoreFinestre initFinestre) {
		this.initFinestre = initFinestre;
	}

	@Override
	public String getConnectionClassName() {
		return ConnectionClass.class.getName();
	}
}