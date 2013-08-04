package business.aggiornatori;

import grafica.componenti.contenitori.ScrollPaneBase;
import grafica.componenti.table.table.TableBase;

import java.awt.print.Paper;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import view.GeneralFrame;
import view.componenti.componentiPannello.SottoPannelloCategorie;
import view.componenti.componentiPannello.SottoPannelloDatiEntrate;
import view.componenti.componentiPannello.SottoPannelloDatiSpese;
import view.componenti.componentiPannello.SottoPannelloMesi;
import view.componenti.componentiPannello.SottoPannelloTotali;
import view.componenti.movimenti.AscoltatoreBottoniEntrata;
import view.componenti.movimenti.AscoltatoreBottoniUscita;
import view.impostazioni.CategorieView;
import view.tabelleMesi.TabellaEntrata;
import view.tabelleMesi.TabellaUscita;
import view.tabelleMesi.TabellaUscitaGruppi;
import aggiornatori.IAggiornatore;
import business.AltreUtil;
import business.ControlloreSpese;
import business.Database;
import business.cache.CacheCategorie;
import business.generatori.TableModelEntrate;
import business.generatori.TableModelUscite;
import db.ConnectionPool;
import domain.CatSpese;
import domain.Gruppi;
import domain.wrapper.Model;
import domain.wrapper.WrapCatSpese;
import domain.wrapper.WrapEntrate;
import domain.wrapper.WrapGruppi;
import domain.wrapper.WrapSingleSpesa;

public class AggiornatoreManager {

	public static final String         AGGIORNA_ENTRATE = "entrate";
	public static final String         AGGIORNA_USCITE  = "uscite";
	public static final String         AGGIORNA_ALL     = "all";
	public static final String         AGGIORNA_NULLA   = "nulla";

	private static AggiornatoreManager singleton;

	public static AggiornatoreManager getSingleton() {
		if (singleton == null) {
			singleton = new AggiornatoreManager();
		}
		return singleton;
	}

	private AggiornatoreManager() {}

	public IAggiornatore creaAggiornatore(final String cosaAggiornare) {
		IAggiornatore aggiornatore = null;
		if (cosaAggiornare.equals(AGGIORNA_ENTRATE)) {
			aggiornatore = new AggiornatoreEntrate();
		} else if (cosaAggiornare.equals(AGGIORNA_USCITE)) {
			aggiornatore = new AggiornatoreUscite();
		} else if (cosaAggiornare.equals(AGGIORNA_ALL)) {
			aggiornatore = new AggiornatoreTotale();
		} else if (cosaAggiornare.equals(AGGIORNA_NULLA)) {
			aggiornatore = new AggiornatoreNull();
		}
		return aggiornatore;
	}

	// ***************************************** METODI AGGIORNAMENTO

	public static boolean aggiornamentoPerImpostazioni() {
		try {
			if (SottoPannelloMesi.getComboMese() != null) {
				SottoPannelloMesi.azzeraCampi();
			}
			SottoPannelloCategorie.azzeraCampi();
			if (aggiornamentoGenerale(WrapEntrate.NOME_TABELLA) && aggiornamentoGenerale(WrapSingleSpesa.NOME_TABELLA) && aggiornaPannelloTotali()) {
				return true;
			}
		} catch (final Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	public static boolean aggiornaPannelloTotali() {
		try {
			if (SottoPannelloTotali.getPercentoFutili() != null) {
				SottoPannelloTotali.getPercentoFutili().setText(Double.toString(Database.percentoUscite(WrapCatSpese.IMPORTANZA_FUTILE)));
				SottoPannelloTotali.getPercentoVariabili().setText(Double.toString(Database.percentoUscite(WrapCatSpese.IMPORTANZA_VARIABILE)));
				SottoPannelloTotali.getAvanzo().setText(Double.toString(AltreUtil.arrotondaDecimaliDouble((Database.EAnnuale()) - (Database.Annuale()))));
			}
			ConnectionPool.getSingleton().chiudiOggettiDb(null);
			return true;
		} catch (final Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Il metodo genera una matrice di movimenti in uscita con numero di righe
	 * passato in parametro. Aggiunge la tabella con i nuovi valori allo
	 * scrollpane del pannello ListaMovEntrat. Infine gli applica un ascoltatore
	 * 
	 * @param nomiColonne
	 * @param numUscite
	 */
	public static boolean aggiornaMovimentiUsciteDaFiltro(final String[] nomiColonne, final String[][] movimenti) {
		try {
			TableBase table1 = ControlloreSpese.getSingleton().getPannello().getTabMovimenti().getTabMovUscite().getTable();
			final JScrollPane scrollPane = ControlloreSpese.getSingleton().getPannello().getTabMovimenti().getTabMovUscite().getScrollPane();
			table1 = new TableBase(movimenti, nomiColonne,scrollPane);
			scrollPane.setViewportView(table1);
			table1.addMouseListener(new AscoltatoreBottoniUscita(table1));
			return true;
		} catch (final Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * Il metodo genera una matrice di movimenti in uscita con numero di righe
	 * passato in parametro. Aggiunge la tabella con i nuovi valori allo
	 * scrollpane del pannello ListaMovEntrat. Infine gli applica un ascoltatore
	 * 
	 * @param nomiColonne
	 * @param numUscite
	 */
	public static boolean aggiornaMovimentiUsciteDaEsterno(final String[] nomiColonne, final int numUscite) {
		try {
			final String[][] movimenti = Model.getSingleton().movimentiUscite(numUscite, WrapSingleSpesa.NOME_TABELLA);
			TableBase table1 = ControlloreSpese.getSingleton().getPannello().getTabMovimenti().getTabMovUscite().getTable();
			final JScrollPane scrollPane = ControlloreSpese.getSingleton().getPannello().getTabMovimenti().getTabMovUscite().getScrollPane();
			table1 = new TableBase(movimenti, nomiColonne, scrollPane);
			scrollPane.setViewportView(table1);
			table1.addMouseListener(new AscoltatoreBottoniUscita(table1));
			return true;
		} catch (final Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Il metodo genera una matrice di movimenti in entrata con numero di righe
	 * passato in parametro. Aggiunge la tabella con i nuovi valori allo
	 * scrollpane del pannello ListaMovEntrat. Infine gli applica un ascoltatore
	 * 
	 * @param nomiColonne
	 * @param numEntry
	 */
	public static boolean aggiornaMovimentiEntrateDaFiltro(final String[] nomiColonne, final String[][] movimenti) {
		try {
			TableBase table1 = ControlloreSpese.getSingleton().getPannello().getTabMovimenti().getTabMovEntrate().getTable();
			final JScrollPane scrollPane = ControlloreSpese.getSingleton().getPannello().getTabMovimenti().getTabMovEntrate().getScrollPane();
			table1 = new TableBase(movimenti, nomiColonne, scrollPane);
			scrollPane.setViewportView(table1);
			table1.addMouseListener(new AscoltatoreBottoniEntrata(table1));
			return true;
		} catch (final Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Il metodo genera una matrice di movimenti in entrata con numero di righe
	 * passato in parametro. Aggiunge la tabella con i nuovi valori allo
	 * scrollpane del pannello ListaMovEntrat. Infine gli applica un ascoltatore
	 * 
	 * @param nomiColonne
	 * @param numEntry
	 */
	public static boolean aggiornaMovimentiEntrateDaEsterno(final String[] nomiColonne, final int numEntry) {
		try {
			final String[][] movimenti = Model.getSingleton().movimentiEntrate(numEntry, WrapEntrate.NOME_TABELLA);
			if(ControlloreSpese.getSingleton().getView()!=null){
				final JScrollPane scrollPane = ControlloreSpese.getSingleton().getPannello().getTabMovimenti().getTabMovEntrate().getScrollPane();
				TableBase table1 = ControlloreSpese.getSingleton().getPannello().getTabMovimenti().getTabMovEntrate().getTable();
				table1 = new TableBase(movimenti, nomiColonne,scrollPane);
				scrollPane.setViewportView(table1);
				table1.addMouseListener(new AscoltatoreBottoniEntrata(table1));
				return true;
			}
			return false;
		} catch (final Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Dopo una variazione o inserimento di un movimento permette
	 * l'aggiornamento di tutti i pannelli rispetto al tipo (Uscita,
	 * 'WrapSingleSpesa.NOME_TABELLA', e Entrata, 'WrapEntrate.NOME_TABELLA')
	 * 
	 * @param tipo
	 * @throws Exception
	 */
	public static boolean aggiornamentoGenerale(final String tipo)
			throws Exception {

		if (tipo.equals(WrapSingleSpesa.NOME_TABELLA)) {
			final String[] nomiColonne = (String[]) AltreUtil.generaNomiColonne(WrapSingleSpesa.NOME_TABELLA);
			if (aggiornaTabellaUscite() && aggiornaTabellaGruppi() && aggiornaMovimentiUsciteDaEsterno(nomiColonne, 25) && aggiornaPannelloDatiSpese()) {
				return true;
			}
		} else if (tipo.equals(WrapEntrate.NOME_TABELLA)) {
			final String[] nomiColonne = (String[]) AltreUtil.generaNomiColonne(WrapEntrate.NOME_TABELLA);
			if (aggiornaMovimentiEntrateDaEsterno(nomiColonne, 25) && aggiornaTabellaEntrate() && aggiornaTabellaGruppi() && aggiornaPannelloDatiEntrate()) {
				return true;
			}
		} else {
			throw new Exception("Aggiornamento non gestito: " + tipo);
		}
		return false;
	}

	public static boolean aggiornaPannelloDatiEntrate() {
		try {
			if (SottoPannelloDatiEntrate.getEnAnCorso() != null) {
				SottoPannelloDatiEntrate.getEnAnCorso().setText(Double.toString(Database.EAnnuale()));
				SottoPannelloDatiEntrate.getEnMeCorso().setText(Double.toString(Database.EMensileInCorso()));
				SottoPannelloDatiEntrate.getEntrateMesePrec().setText(Double.toString(Database.Emensile()));
				ConnectionPool.getSingleton().chiudiOggettiDb(null);
			}
			return true;
		} catch (final Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean aggiornaPannelloDatiSpese() {
		try {
			if (SottoPannelloDatiSpese.getMeseInCors() != null) {
				SottoPannelloDatiSpese.getMeseInCors().setText(Double.toString(Database.MensileInCorso()));
				SottoPannelloDatiSpese.getMesePrecUsc().setText(Double.toString(Database.Mensile()));
				SottoPannelloDatiSpese.getSpeseAnnuali().setText(Double.toString(Database.Annuale()));
				ConnectionPool.getSingleton().chiudiOggettiDb(null);
			}
			return true;
		} catch (final Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Il metodo aggiorna la combobox dei gruppi nel pannello Categorie
	 * passandogli come parametro una entita 'Gruppi'. L'aggiornamento avviene
	 * scorrendo gli elementi della combo: quando id del gruppo passato come
	 * parametro e' lo stesso di quello nella combo, quest'ultimo viene
	 * eliminato. Quindi aggiunge il nuovo gruppo nella stessa posizione di
	 * quello eliminato
	 * 
	 * @param gruppo
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static void aggiornaGruppi(final Gruppi gruppo, final CategorieView categoria) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		int max = 0;
		final String sql = "SELECT MAX(" + WrapGruppi.ID + ") FROM " + WrapGruppi.NOME_TABELLA;
		try {
			final Connection cn = ConnectionPool.getSingleton().getConnection();

			final Statement st = cn.createStatement();
			final ResultSet rs = st.executeQuery(sql);
			max = rs.getInt(1);
			ConnectionPool.getSingleton().chiudiOggettiDb(cn);
		} catch (final Exception e) {
			e.printStackTrace();
		}
		final JComboBox gruppi = categoria.getComboGruppi();

		gruppi.setSelectedIndex(0);
		int i = 1;
		for (i = 1; i <= max; i++) {

			Gruppi gruppo1 = (Gruppi) gruppi.getItemAt(i);
			if (gruppo1 == null) {
				gruppo1 = new Gruppi();
				gruppo1.setIdGruppo(-1);
			}
			if (gruppo.getIdGruppo() == gruppo1.getIdGruppo()) {
				gruppi.removeItemAt(i);
				final CatSpese categoriaPresa = CacheCategorie.getSingleton().getCatSpese(Integer.toString(gruppo.getIdGruppo()));
				// non è possibile sostituirlo la categoria presa dal database
				// con quella passata nel parametro
				// perché il parametro mantiene i vecchi settaggi e non si
				// aggiorna
				gruppi.insertItemAt(categoriaPresa, i);
				ConnectionPool.getSingleton().chiudiOggettiDb(null);
			}
		}

		ConnectionPool.getSingleton().chiudiOggettiDb(null);

	}

	// aggiorna le categorie nel pannello di uscite
	/**
	 * Il metodo aggiorna la combobox delle categorie nel pannello uscite
	 * passandogli come parametro una entita CatSpese. L'aggiornamento avviene
	 * scorrendo gli elementi della combo: quando id della categoria passata
	 * come parametro e' lo stesso di quello nella combo, quest'ultima viene
	 * eliminata. Quindi aggiunge la nuova categoria nella stessa posizione di
	 * quella eliminata
	 * 
	 * @param CatSpese
	 */
	public static void aggiornaCategorie(final CatSpese categoria, final JComboBox comboCategorie) {
		int max = 0;
		final String sql = "SELECT MAX(" + WrapCatSpese.ID + ") FROM " + WrapCatSpese.NOME_TABELLA;
		try {
			final Connection cn = ConnectionPool.getSingleton().getConnection();

			final Statement st = cn.createStatement();
			final ResultSet rs = st.executeQuery(sql);
			max = rs.getInt(1);
			ConnectionPool.getSingleton().chiudiOggettiDb(cn);
		} catch (final Exception e) {
			e.printStackTrace();
		}

		final JComboBox categorie1 = comboCategorie;

		int i = 1;
		for (i = 1; i <= max; i++) {

			CatSpese catspese1 = (CatSpese) categorie1.getItemAt(i);
			if (catspese1 == null) {
				catspese1 = new CatSpese();
				catspese1.setIdCategoria(-1);
			}
			if (categoria.getIdCategoria() == catspese1.getIdCategoria()) {
				categorie1.removeItemAt(i);
				final CatSpese categoriaPresa = CacheCategorie.getSingleton().getCatSpese(Integer.toString(categoria.getIdCategoria()));
				// non è possibile sostituirlo la categoria presa dal database
				// con quella passata nel parametro
				// perché il parametro mantiene i vecchi settaggi e non si
				// aggiorna
				categorie1.insertItemAt(categoriaPresa, i);	
			}
		}
	}

	// aggiorno tabella uscite/mese in seguito a variazioni di altre tabelle
	/**
	 * il metodo aggiorna la matrice primo[][] che rappresenta i dati della
	 * tabella uscite. Utile nel caso in cui vengano aggiornte altre tabelle e
	 * si vogliano aggiornare anche questi dati.
	 * 
	 * @throws Exception
	 */
	public static boolean aggiornaTabellaGruppi() {
		try {
			final JScrollPane pane = TabellaUscitaGruppi.getScrollPane();
			if(pane != null){
				final JTable table = TabellaUscitaGruppi.getDatiPerTabella(pane);
				pane.setViewportView(table);
			}
			return true;
		} catch (final Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	// aggiorno tabella uscite/mese in seguito a variazioni di altre tabelle
	/**
	 * il metodo aggiorna la matrice primo[][] che rappresenta i dati della
	 * tabella uscite. Utile nel caso in cui vengano aggiornte altre tabelle e
	 * si vogliano aggiornare anche questi dati.
	 * 
	 * @throws Exception
	 */
	public static boolean aggiornaTabellaUscite() {

		try {
			TableModelUscite model = new TableModelUscite(null);
			GeneralFrame pannelloPrinc = ControlloreSpese.getSingleton().getPannello();
			if(pannelloPrinc.getTabPermesi() != null && pannelloPrinc.getTabPermesi().getTabUscite() != null){
				final JScrollPane pane = pannelloPrinc.getTabPermesi().getTabUscite().getScrollPane();
				if(pane != null){
					final TableBase table = TabellaUscita.createTable(model,pane); 
					pane.setViewportView(table);
				}
			}
			return true;
		} catch (final Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	// aggiorno tabella entrate/mese in seguito a variazioni di altre tabelle
	/**
	 * Metodo che serve per aggiornare la matrice entrate/mese dopo variazioni
	 * avvenute in altri pannelli
	 * 
	 * @throws Exception
	 */
	public static boolean aggiornaTabellaEntrate() {
		try {
			GeneralFrame pannelloPrinc = ControlloreSpese.getSingleton().getPannello();
			if(pannelloPrinc.getTabPermesi() != null && pannelloPrinc.getTabPermesi() .getTabEntrate() != null){
				final ScrollPaneBase pane = pannelloPrinc.getTabPermesi().getTabEntrate().getScrollPane();
				if(pane != null){
					TableModelEntrate model = new TableModelEntrate(null);
					final TableBase table = TabellaEntrata.createTable(model, pane);
					pane.setViewportView(table);
				}
			}
			return true;
		} catch (final Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static void aggiornamentoComboBox(final Vector<CatSpese> categorie) {
		final DefaultComboBoxModel model = new DefaultComboBoxModel(categorie);
		if (SottoPannelloCategorie.getCategorieCombo() != null) {
			SottoPannelloCategorie.getCategorieCombo().setModel(model);
			SottoPannelloCategorie.getCategorieCombo().validate();
			SottoPannelloCategorie.getCategorieCombo().repaint();
		}
	}
}
