package view.report;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Vector;

import view.entrateuscite.EntrateView;
import business.AltreUtil;
import business.Database;
import business.cache.CacheCategorie;
import domain.CatSpese;
import domain.wrapper.WrapCatSpese;

public class ReportData {
	
	Vector<CatSpese> categorie = null;
	
	public ReportData() throws Exception {
		categorie = CacheCategorie.getSingleton().getVettoreCategorie();
	}
	
	ArrayList<OggettoReport> datiReport = new ArrayList<OggettoReport>();
	
	public void inserisci(final OggettoReport  oggettoReport){
		datiReport.add(oggettoReport);
	}
	
	public ArrayList<OggettoReport> getListaOggetti(){
		return datiReport;
	}
	
	public void reset(){
		datiReport.clear();
	}

	
	public Double generaUsciteAnnuali() throws Exception{
		return Database.Annuale();
	}
	
	
	public Double generaEntrateAnnuali() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		return Database.EAnnuale();
	}
	
	public HashMap<String, Double> generaEntrateMese() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
		
		HashMap<String, Double> entrateMese = new HashMap<String, Double>();
		
		for (int i = 1; i <= 12; i++) {
			String mese = Mesi.getMeseStringa(i);
			entrateMese.put(mese, Database.getSingleton().totaleEntrateMese(i));
		}
		return entrateMese;
	}
	
	public HashMap<String, Double> generaUsciteMese() throws Exception {
		HashMap<String, Double> usciteMese = new HashMap<String, Double>();
		for (int i = 1; i <= 12; i++) {
			String mese = Mesi.getMeseStringa(i);
			usciteMese.put(mese, Database.getSingleton().totaleUsciteMese(i));
		}
		return usciteMese;
	}
		
	
	public HashMap<String, Double> generaUsciteCatAnnuali() throws Exception {
		HashMap<String, Double> usciteCatAnnuali = new HashMap<String, Double>();
		for (int i = 0; i < categorie.size(); i++) {
			final CatSpese categoria = categorie.get(i);
			String nome = categoria.getNome();
			usciteCatAnnuali.put(nome, Database.totaleUscitaAnnoCategoria(categoria.getIdCategoria()));
		}
		return usciteCatAnnuali;
	}
	
	public HashMap<String, Double> generaEntrateCatAnnuali() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		HashMap<String, Double> entrateCatAnnuali = new HashMap<String, Double>(); 
		ArrayList<String> nomiColonne = EntrateView.getLista();
		for (int i = 0; i < nomiColonne.size(); i++) {
			entrateCatAnnuali.put(nomiColonne.get(i), Database.totaleEntrateAnnoCategoria(nomiColonne.get(i))); 
		}
		return entrateCatAnnuali;
	}
	
	public String[][] generaUsciteCatMensili() {
		String[][] usciteCatMensili;
		final int numColonne = categorie.size();
		final String[] nomiColonne = new String[numColonne];

		for (int i = 0; i < categorie.size(); i++) {
			nomiColonne[i] = categorie.get(i).getNome();
		}

		usciteCatMensili = new String[12][categorie.size()];

		for (int i = 0; i < 12; i++) {
			for (int x = 0; x < categorie.size(); x++) {
				try {
					final int idCat = categorie.get(x).getIdCategoria();
					final Double spesaMeseCategoria = Database.speseMeseCategoria(i + 1, idCat);
					usciteCatMensili[i][x] = spesaMeseCategoria.toString();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return usciteCatMensili;
	}
	
	public String[][] generaEntrateCatMensili() {
		ArrayList<String> nomiColonne = EntrateView.getLista();
		String [][] entrateCatMensili = new String[12][2];
		for (int i = 0; i < 12; i++) {
			for (int x = 0; x < 2; x++) {
				try {
					final Double entrataMeseTipo = Database.getSingleton().entrateMeseTipo((i + 1),
							nomiColonne.get(x));
					entrateCatMensili[i][x] = entrataMeseTipo.toString();
				} catch (final Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		return entrateCatMensili;
	}
	
	public Double generaUsciteVariabili() throws Exception {
		return Database.percentoUscite(WrapCatSpese.IMPORTANZA_VARIABILE);
	}
	
	public Double generaUsciteFutili() throws Exception {
		return Database.percentoUscite(WrapCatSpese.IMPORTANZA_FUTILE);
	}
	
	public Double generaAvanzo() throws Exception {
		return AltreUtil.arrotondaDecimaliDouble((Database.EAnnuale()) - (Database.Annuale()));
	}
	
	public Double generaMediaUscite() throws Exception {
		return Database.Annuale() / new GregorianCalendar().get(Calendar.MONTH + 1);
	}
	
	public Double generaMediaEntrate() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return Database.EAnnuale() / new GregorianCalendar().get(Calendar.MONTH + 1);
	}
	
}
