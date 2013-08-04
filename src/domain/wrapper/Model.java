package domain.wrapper;

import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

import business.Database;
import business.cache.CacheCategorie;
import business.cache.CacheGruppi;

import command.javabeancommand.AbstractOggettoEntita;

import db.ConnectionPool;
import domain.CatSpese;
import domain.Entrate;
import domain.SingleSpesa;

public class Model {

//	private WrapCatSpese modelCategorie;
//	private WrapGruppi modelGruppi;
//	private WrapBudget modelBudget;
	private WrapEntrate modelEntrate;
//	private WrapRisparmio modelRisparmio;
	private WrapSingleSpesa modelUscita;
//	private WrapUtenti modelUtenti;
//	private WrapLookAndFeel modelLookAndFeel;
	private static Model singleton;
	private static String[][] primoUscite;
	private static String[][] primoEntrate;
	private static String[] nomiColonneUscite;
	private static String[] nomiColonneEntrate;
	private static String[][] movimentiEntrate;
	private static String[][] movimentiUscite;

	private static Vector<CatSpese> catSpese = CacheCategorie.getSingleton().getVettoreCategorie();

	public static Model getSingleton() {
		synchronized (CacheCategorie.class) {
			if (singleton == null) {
				singleton = new Model();
			} // if
		} // if
		return singleton;
	}

	private Model() {
		modelEntrate = new WrapEntrate();
		modelUscita = new WrapSingleSpesa();
	}

	// *************************************TABELLA-USCITE******************************************

	private static String[][] setTabellaUscitaPrimo() {

		final int numColonne = catSpese.size();
		final String[] nomiColonne = new String[numColonne];

		for (int i = 0; i < catSpese.size(); i++) {
			nomiColonne[i] = catSpese.get(i).getNome();
		}

		primoUscite = new String[12][numColonne];

		for (int i = 0; i < 12; i++) {
			for (int x = 0; x < catSpese.size(); x++) {
				try {
					primoUscite[i][x] = Double.toString(Database.speseMeseCategoria(i + 1, catSpese.get(x).getIdCategoria()));
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		}
		return primoUscite;
	}

	private static String[] nomiColonneUscite() {

		final int numColonne = catSpese.size();
		nomiColonneUscite = new String[numColonne];

		for (int i = 0; i < catSpese.size(); i++) {
			nomiColonneUscite[i] = catSpese.get(i).getNome();
		}
		return nomiColonneUscite;
	}

	public static String[][] getPrimoUscite() {
		return setTabellaUscitaPrimo();
	}

	public static void setPrimoUscite(final String[][] primo) {
		Model.primoUscite = primo;
	}

	public static void setNomiColonneUscite(final String[] nomiColonneUscite) {
		Model.nomiColonneUscite = nomiColonneUscite;
	}

	public static String[] getNomiColonneUscite() {
		return nomiColonneUscite();
	}

	// *************************************TABELLA-ENTRATE******************************************

	private static String[][] setTabellaEntratePrimo() {

		final String[] nomiColonneEntrate = nomiColonneEntrate();
		final int numColonne = nomiColonneEntrate.length;

		primoEntrate = new String[12][numColonne];

		for (int i = 0; i < 12; i++) {
			for (int x = 0; x < numColonne; x++) {
				try {
					primoEntrate[i][x] = Double.toString(Database.getSingleton().entrateMeseTipo(i + 1, nomiColonneEntrate[x]));
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		}
		return primoEntrate;
	}

	private static String[] nomiColonneEntrate() {

		nomiColonneEntrate = new String[2];
		nomiColonneEntrate[0] = "Fisse";
		nomiColonneEntrate[1] = "Variabili";

		return nomiColonneEntrate;
	}

	public static String[][] getPrimoEntrate() {
		return setTabellaEntratePrimo();
	}

	public static void setPrimoEntrate(final String[][] primo) {
		Model.primoUscite = primo;
	}

	public static void setNomiColonneEntrate(final String[] nomiColonneEntrate) {
		Model.nomiColonneEntrate = nomiColonneEntrate;
	}

	public static String[] getNomiColonneEntrate() {
		return nomiColonneEntrate();
	}

	// *************************************CATEGORIE-PERCOMBOBOX***********************************

	private Map<String, AbstractOggettoEntita> getCatPerCombo(final boolean ricarica) {
		final CacheCategorie cache = CacheCategorie.getSingleton();
		cache.setCaricata(!ricarica);
		return cache.getAllCategorie();
	}

	public Object[] getCategorieCombo(final boolean ricarica) {
		final Map<String, AbstractOggettoEntita> cat = getCatPerCombo(ricarica);
		return cat.values().toArray();
	}

	// *************************************GRUPPI-PERCOMBOBOX***********************************

	private Map<String, AbstractOggettoEntita> getGruppiPerCombo(final boolean ricarica) {
		final CacheGruppi cache = CacheGruppi.getSingleton();
		cache.setCaricata(!ricarica);
		return cache.getAllGruppi();
	}

	public Object[] getGruppiCombo(final boolean ricarica) {
		final Map<String, AbstractOggettoEntita> gruppi = getGruppiPerCombo(ricarica);
		return gruppi.values().toArray();
	}

	// *************************************MOVIMENTI-ENTRATE***********************************

	/**
	 * Valorizza una matrice utile per i pannelli movimenti in entrata. crea il
	 * numero di righe specificato in parametro con le entita' della tabella
	 * passata in parametro
	 * 
	 * }
	 * 
	 * @param numEntry
	 * @param tabella
	 * @return String[][]
	 * @throws Exception 
	 */
	public String[][] movimentiFiltratiEntratePerNumero(final String tabella, final Vector<Entrate> entry1) throws Exception {
		final Vector<String> nomi = Database.getSingleton().nomiColonne(tabella);

		final int numEntry = entry1.size();

		if (entry1.size() > 0 && (entry1.size() == numEntry || entry1.size() >= numEntry)) {
			movimentiEntrate = new String[numEntry][nomi.size()];
			for (int x = 0; x < entry1.size(); x++) {
				final Entrate entrate = entry1.get(x);
				movimentiEntrate[x][0] = entrate.getData().toString();
				movimentiEntrate[x][1] = entrate.getNome();
				movimentiEntrate[x][2] = entrate.getDescrizione();
				movimentiEntrate[x][3] = Double.toString(entrate.getInEuro());
				movimentiEntrate[x][4] = entrate.getFisseoVar();
				movimentiEntrate[x][5] = Integer.toString(entrate.getIdEntrate());
				movimentiEntrate[x][6] = entrate.getDataIns();

			}
		} else if (entry1.size() > 0 && entry1.size() < numEntry) {
			movimentiEntrate = new String[numEntry][nomi.size()];
			for (int x = 0; x < entry1.size(); x++) {
				final Entrate entrate = entry1.get(x);
				movimentiEntrate[x][0] = entrate.getData().toString();
				movimentiEntrate[x][1] = entrate.getNome();
				movimentiEntrate[x][2] = entrate.getDescrizione();
				movimentiEntrate[x][3] = Double.toString(entrate.getInEuro());
				movimentiEntrate[x][4] = entrate.getFisseoVar();
				movimentiEntrate[x][5] = Integer.toString(entrate.getIdEntrate());
				movimentiEntrate[x][6] = entrate.getDataIns();
			}
			for (int y = entry1.size(); y < numEntry; y++) {
				for (int z = 0; z < nomi.size(); z++) {
					movimentiEntrate[y][z] = "0";
				}
			}
		} else {
			movimentiEntrate = new String[numEntry][nomi.size()];
			for (int x = 0; x < numEntry; x++) {
				for (int z = 0; z < nomi.size(); z++) {
					movimentiEntrate[x][z] = "0";
				}
			}
		}
		ConnectionPool.getSingleton().chiudiOggettiDb(null);
		return movimentiEntrate;
	}

	/**
	 * Valorizza una matrice utile per i pannelli movimenti in entrata. crea il
	 * numero di righe specificato in parametro con le entita' della tabella
	 * passata in parametro
	 * 
	 * }
	 * 
	 * @param numEntry
	 * @param tabella
	 * @return String[][]
	 * @throws Exception 
	 */
	public String[][] movimentiEntrate(final int numEntry, final String tabella) throws Exception {
		final Vector<String> nomi = Database.getSingleton().nomiColonne(tabella);
		final Vector<Entrate> entry1 = Model.getSingleton().modelEntrate.dieciEntrate(numEntry);

		if (entry1.size() > 0 && (entry1.size() == numEntry || entry1.size() >= numEntry)) {
			movimentiEntrate = new String[numEntry][nomi.size()];
			for (int x = 0; x < entry1.size(); x++) {
				final Entrate entrate = entry1.get(x);
				movimentiEntrate[x][0] = entrate.getData().toString();
				movimentiEntrate[x][1] = entrate.getNome();
				movimentiEntrate[x][2] = entrate.getDescrizione();
				movimentiEntrate[x][3] = Double.toString(entrate.getInEuro());
				movimentiEntrate[x][4] = entrate.getFisseoVar();
				movimentiEntrate[x][5] = Integer.toString(entrate.getIdEntrate());
				movimentiEntrate[x][6] = entrate.getDataIns();

			}
		} else if (entry1.size() > 0 && entry1.size() < numEntry) {
			movimentiEntrate = new String[numEntry][nomi.size()];
			for (int x = 0; x < entry1.size(); x++) {
				final Entrate entrate = entry1.get(x);
				movimentiEntrate[x][0] = entrate.getData().toString();
				movimentiEntrate[x][1] = entrate.getNome();
				movimentiEntrate[x][2] = entrate.getDescrizione();
				movimentiEntrate[x][3] = Double.toString(entrate.getInEuro());
				movimentiEntrate[x][4] = entrate.getFisseoVar();
				movimentiEntrate[x][5] = Integer.toString(entrate.getIdEntrate());
				movimentiEntrate[x][6] = entrate.getDataIns();
			}
			for (int y = entry1.size(); y < numEntry; y++) {
				for (int z = 0; z < nomi.size(); z++) {
					movimentiEntrate[y][z] = "0";
				}
			}
		} else {
			movimentiEntrate = new String[numEntry][nomi.size()];
			for (int x = 0; x < numEntry; x++) {
				for (int z = 0; z < nomi.size(); z++) {
					movimentiEntrate[x][z] = "0";
				}
			}
		}
		ConnectionPool.getSingleton().chiudiOggettiDb(null);
		return movimentiEntrate;
	}

	// *************************************MOVIMENTI-USCITE***********************************

	public String[][] movimentiFiltratiUscitePerNumero(final String tabella, final Vector<SingleSpesa> uscite) throws Exception {
		final Vector<String> nomi = Database.getSingleton().nomiColonne(tabella);

		final int numUscite = uscite.size();

		if (uscite.size() > 0 && (uscite.size() == numUscite || uscite.size() >= numUscite)) {
			movimentiUscite = new String[numUscite][nomi.size()];
			for (int x = 0; x < numUscite; x++) {
				final SingleSpesa uscita = uscite.get(x);
				movimentiUscite[x][0] = uscita.getData();
				movimentiUscite[x][1] = uscita.getNome();
				movimentiUscite[x][2] = uscita.getDescrizione();
				movimentiUscite[x][3] = Double.toString(uscita.getInEuro());
				movimentiUscite[x][4] = uscita.getCatSpese().getNome();
				movimentiUscite[x][5] = Integer.toString(uscita.getIdSpesa());
				movimentiUscite[x][6] = uscita.getDataIns();

			}
		} else if (uscite.size() > 0 && uscite.size() < numUscite) {

			movimentiUscite = new String[numUscite][nomi.size()];
			for (int x = 0; x < uscite.size(); x++) {

				final SingleSpesa uscita = uscite.get(x);
				movimentiUscite[x][0] = uscita.getData();
				movimentiUscite[x][1] = uscita.getNome();
				movimentiUscite[x][2] = uscita.getDescrizione();
				movimentiUscite[x][3] = Double.toString(uscita.getInEuro());
				movimentiUscite[x][4] = uscita.getCatSpese() != null ? uscita.getCatSpese().getNome() : "Nessuna";
				movimentiUscite[x][5] = Integer.toString(uscita.getIdSpesa());
				movimentiUscite[x][6] = uscita.getDataIns();

				for (int y = uscite.size(); y < numUscite; y++) {
					for (int z = 0; z < nomi.size(); z++) {
						movimentiUscite[y][z] = "0";
					}
				}
			}
		} else {
			movimentiUscite = new String[numUscite][nomi.size()];
			for (int x = 0; x < numUscite; x++) {
				for (int z = 0; z < nomi.size(); z++) {
					movimentiUscite[x][z] = "0";
				}
			}

		}
		ConnectionPool.getSingleton().chiudiOggettiDb(null);
		return movimentiUscite;

	}

	/**
	 * Valorizza una matrice utile per i pannelli movimenti in uscita. crea il
	 * numero di righe specificato in parametro con le entita' della tabella
	 * passata in parametro
	 * 
	 * @param numUscite
	 * @param tabella
	 * @return String[][]
	 * @throws Exception 
	 */
	public String[][] movimentiUscite(final int numUscite, final String tabella) throws Exception {
		final Vector<String> nomi = Database.getSingleton().nomiColonne(tabella);
		final ArrayList<SingleSpesa> uscite = Model.getSingleton().modelUscita.dieciUscite(numUscite);
		
		if (uscite != null && uscite.size() > 0 && (uscite.size() == numUscite || uscite.size() >= numUscite)) {
			movimentiUscite = new String[numUscite][nomi.size()];
			for (int x = 0; x < numUscite; x++) {
				final SingleSpesa uscita = uscite.get(x);
				movimentiUscite[x][0] = uscita.getData();
				movimentiUscite[x][1] = uscita.getNome();
				movimentiUscite[x][2] = uscita.getDescrizione();
				movimentiUscite[x][3] = Double.toString(uscita.getInEuro());
				movimentiUscite[x][4] = uscita.getCatSpese().getNome();
				movimentiUscite[x][5] = Integer.toString(uscita.getIdSpesa());
				movimentiUscite[x][6] = uscita.getDataIns();

			}
		} else if (uscite != null && uscite.size() > 0 && uscite.size() < numUscite) {

			movimentiUscite = new String[numUscite][nomi.size()];
			for (int x = 0; x < uscite.size(); x++) {

				final SingleSpesa uscita = uscite.get(x);
				movimentiUscite[x][0] = uscita.getData();
				movimentiUscite[x][1] = uscita.getNome();
				movimentiUscite[x][2] = uscita.getDescrizione();
				movimentiUscite[x][3] = Double.toString(uscita.getInEuro());
				movimentiUscite[x][4] = uscita.getCatSpese() != null ? uscita.getCatSpese().getNome() : "Nessuna";
				movimentiUscite[x][5] = Integer.toString(uscita.getIdSpesa());
				movimentiUscite[x][6] = uscita.getDataIns();

				for (int y = uscite.size(); y < numUscite; y++) {
					for (int z = 0; z < nomi.size(); z++) {
						movimentiUscite[y][z] = "0";
					}
				}
			}
		} else {
			movimentiUscite = new String[numUscite][nomi.size()];
			for (int x = 0; x < numUscite; x++) {
				for (int z = 0; z < nomi.size(); z++) {
					movimentiUscite[x][z] = "0";
				}
			}

		}
		ConnectionPool.getSingleton().chiudiOggettiDb(null);
		return movimentiUscite;

	}
}
