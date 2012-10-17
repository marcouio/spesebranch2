package business;

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
import view.mymenu.MyMenu;
import business.aggiornatori.AggiornatoreManager;
import business.cache.CacheLookAndFeel;
import business.cache.CacheUtenti;

import command.AbstractCommand;
import command.CommandManager;

import controller.ControlloreBase;
import db.dao.GenericDAO;
import domain.IUtenti;
import domain.Lookandfeel;
import domain.Utenti;
import domain.wrapper.WrapLookAndFeel;
import domain.wrapper.WrapUtenti;

public class Controllore extends ControlloreBase{

	private static FrameBase view;
	private static GeneralFrame pannello;
	private static IUtenti utenteLogin;
	private static CommandManager commandManager;
	private static AggiornatoreManager aggiornatoreManager;
	private InizializzatoreFinestre initFinestre;
	private static Controllore singleton;
	public static String lookUsato;

	@Override
	public void mainOverridato(FrameBase frame) throws Exception {
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
		Container contentPane = view.getContentPane();
		contentPane.add(getPannello());
		view.setResizable(false);
		view.setTitle(getMessaggio("title"));
		view.setVisible(true);
		
		
		
	}
	
	public String getMessaggio(final String chiave) {
		return I18NManager.getSingleton().getMessaggio(chiave);
	}
	
	/**
	 * Launch the application.
	 */
//	public static void main(final String[] args) {
//		Database.DB_URL = Database.DB_URL_WORKSPACE;
//		verificaPresenzaDb();
//
//		settaLookFeel();
//
//		SwingUtilities.invokeLater(new Runnable() {
//
//			@Override
//			public void run() {
//				DBUtil.closeConnection();
//				Controllore.getSingleton();
//				view = GeneralFrame.getSingleton();
//				view.setResizable(false);
//				setStartUtenteLogin();
//				view.setTitle(Controllore.getSingleton().getMessaggio("title"));
//				view.setLocationByPlatform(true);
//				view.setVisible(true);
//
//			}
//		});
//	}

	private static void settaLookFeel() {
		try {
			final CacheLookAndFeel cacheLook = CacheLookAndFeel.getSingleton();
			final java.util.Vector<Lookandfeel> vettore = cacheLook.getVettoreLooksPerCombo();
			Lookandfeel look = null;
			Lookandfeel lookDaUsare = null;
			for (int i = 0; i < vettore.size(); i++) {
				look = vettore.get(i);
				//verifico se sul database quale look era scelto
				if (look.getUsato() == 1) {
					lookDaUsare = look;
					break;
				}
			}
			if (lookDaUsare != null && lookDaUsare.getValore() != null) {
				UIManager.setLookAndFeel(lookDaUsare.getValore());
				lookUsato = lookDaUsare.getValore();
			} else {
				//se non c'era un look selezionato setto quello di sistema
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				lookUsato = UIManager.getSystemLookAndFeelClassName();
			}
			SwingUtilities.updateComponentTreeUI(view);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(final String[] args) {
		Controllore.getSingleton().myMain(Controllore.getSingleton(), true, "myApplication");
	}

	private static void verificaPresenzaDb() throws Exception {
		try {
			final Connection cn = DBUtil.getConnection();
			final String sql = "SELECT * FROM " + WrapLookAndFeel.NOME_TABELLA;
			final Statement st = cn.createStatement();
			@SuppressWarnings("unused")
			final
			ResultSet rs = st.executeQuery(sql);
		} catch (final SQLException e) {
			try {
				Database.getSingleton().generaDB();
				Alert.info("Database non presente: è stato rigenerato", "");
			} catch (final SQLException e1) {
				Controllore.getLog().severe("Database non creato: " + e.getMessage());
			}
		}
	}

	private Controllore() {
	}

	public static GeneralFrame getPannello() {
		if(pannello == null){
			pannello = GeneralFrame.getSingleton(view);
		}
		return pannello;
	}

	public static boolean invocaComando(final AbstractCommand comando) throws Exception {
		return Controllore.getSingleton().getCommandManager().invocaComando(comando);
	}

	/**
	 * Controlla se esiste sul db l'utente guest, altrimenti lo crea
	 */
	private static void setStartUtenteLogin() {
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

	public static Controllore getSingleton() {
		if (singleton == null) {
			synchronized (Controllore.class) {
				if (singleton == null) {
					singleton = new Controllore();
				}
			} // if
		} // if
		return singleton;
	}

	public Object getUtenteLogin() {
		return utenteLogin;
	}

	public static void setUtenteLogin(final Utenti utenteLogin) {
		Controllore.utenteLogin = utenteLogin;
	}

	public AggiornatoreManager getAggiornatoreManager() {
		if (aggiornatoreManager == null) {
			aggiornatoreManager = AggiornatoreManager.getSingleton();
		}
		return aggiornatoreManager;
	}

	public CommandManager getCommandManager() {
		if (commandManager == null) {
			commandManager = CommandManager.getIstance();
		}
		return commandManager;
	}

	public FrameBase getView() {
		return view;
	}

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