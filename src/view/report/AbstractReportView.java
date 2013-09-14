package view.report;

import java.sql.SQLException;
import java.util.HashMap;

import javax.swing.JFrame;

import business.cache.CacheCategorie;

public abstract class AbstractReportView extends JFrame {

	protected ReportData reportData;
	private static final long serialVersionUID = 1L;

	public AbstractReportView() {
	}

	public AbstractReportView(final ReportData reportData) {
		this.setReportData(reportData);
	}

	public void inserisciUsciteAnnuali(final boolean hasUsciteAnnuali) throws Exception {
		if (hasUsciteAnnuali) {
			Double usciteAnnuali = reportData.generaUsciteAnnuali();
			OggettoReport oggettoReport = new OggettoReport(OggettoReport.TIPO_DOUBLE, usciteAnnuali, "Uscite Annuali");
			reportData.inserisci(oggettoReport);
		}
	}

	public void inserisciEntrateAnnuali(final boolean hasEntrateAnnuali) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		if (hasEntrateAnnuali) {
			Double entrateAnnuali = reportData.generaEntrateAnnuali();
			OggettoReport oggettoReport = new OggettoReport(OggettoReport.TIPO_DOUBLE, entrateAnnuali, "Entrate Annuali");
			reportData.inserisci(oggettoReport);
		}
	}

	public void inserisciEntrateMensili(final boolean hasEntrateMensili) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		if (hasEntrateMensili) {
			HashMap<String, Double> entrateMese = reportData.generaEntrateMese();
			String[]mesi = (String[]) Mesi.getListaMesi().toArray(new String[Mesi.getListaMesi().size()]);
			OggettoReport oggettoReport = new OggettoReport(OggettoReport.TIPO_MAPPA, entrateMese, "Entrate", "Mese", mesi);
			reportData.inserisci(oggettoReport);
		}
	}

	public void inserisciUsciteMensili(final boolean hasUsciteMensili) throws Exception {
		if (hasUsciteMensili) {
			HashMap<String, Double> usciteMese = reportData.generaUsciteMese();
			String[]mesi = (String[]) Mesi.getListaMesi().toArray(new String[Mesi.getListaMesi().size()]);
			OggettoReport oggettoReport = new OggettoReport(OggettoReport.TIPO_MAPPA, usciteMese, "Uscite", "Mese",mesi);
			reportData.inserisci(oggettoReport);
		}
	}

	public void inserisciEntrateCatMensili(final boolean hasEntrateCatMensili) {
		if (hasEntrateCatMensili) {
			String[][] entrateCatMensili = reportData.generaEntrateCatMensili();
			String[] categorie = new String[]{"Fisse","Variabili"};
			String[]mesi = (String[]) Mesi.getListaMesi().toArray(new String[Mesi.getListaMesi().size()]);
			OggettoReport oggettoReport = new OggettoReport(OggettoReport.TIPO_MATRICE, entrateCatMensili, "Entrate", "Mese", "Categoria", mesi, categorie);
			reportData.inserisci(oggettoReport);
		}
	}

	public void inserisciUsciteCatMensili(final boolean hasUsciteCatMensili) {
		if (hasUsciteCatMensili) {
			String[][] usciteCatMensili = reportData.generaUsciteCatMensili();
			String[]categorie = CacheCategorie.getSingleton().getArrayCategorie();
			String[]mesi = (String[]) Mesi.getListaMesi().toArray(new String[Mesi.getListaMesi().size()]);
			OggettoReport oggettoReport = new OggettoReport(OggettoReport.TIPO_MATRICE, usciteCatMensili, "Uscite", "Mese", "Categoria", mesi, categorie);
			reportData.inserisci(oggettoReport);
		}
	}

	public void inserisciEntrateCatAnnuali(final boolean hasEntrateCatAnnuali) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		if (hasEntrateCatAnnuali) {
			HashMap<String, Double> entrateCatAnnuali = reportData.generaEntrateCatAnnuali();
			String[] categorie = new String[]{"Fisse","Variabili"};
			OggettoReport oggettoReport = new OggettoReport(OggettoReport.TIPO_MAPPA, entrateCatAnnuali, "Entrate Annuali", "Categoria", categorie);
			reportData.inserisci(oggettoReport);
		}
	}

	public void inserisciUsciteCatAnnuali(final boolean hasUsciteCatAnnuali) throws Exception {
		if (hasUsciteCatAnnuali) {
			HashMap<String, Double> usciteCatAnnuali = reportData.generaUsciteCatAnnuali();
			String[]categorie = CacheCategorie.getSingleton().getArrayCategorie();
			OggettoReport oggettoReport = new OggettoReport(OggettoReport.TIPO_MAPPA, usciteCatAnnuali, "Uscite Annuali", "Categoria",categorie);
			reportData.inserisci(oggettoReport);
		}
	}

	public void inserisciUsciteVariabili(final boolean hasUsciteVariabili) throws Exception {
		if (hasUsciteVariabili) {
			Double usciteVariabili = reportData.generaUsciteVariabili();
			OggettoReport oggettoReport = new OggettoReport(OggettoReport.TIPO_DOUBLE, usciteVariabili, "Uscite Variabili");
			reportData.inserisci(oggettoReport);
		}
	}

	public void inserisciUsciteFutili(final boolean hasUsciteFutili) throws Exception {
		if (hasUsciteFutili) {
			Double usciteFutili = reportData.generaUsciteFutili();
			OggettoReport oggettoReport = new OggettoReport(OggettoReport.TIPO_DOUBLE, usciteFutili, "Uscite Futili");
			reportData.inserisci(oggettoReport);
		}
	}

	public void inserisciAvanzo(final boolean hasAvanzo) throws Exception {
		if (hasAvanzo) {
			Double avanzo = reportData.generaAvanzo();
			OggettoReport oggettoReport = new OggettoReport(OggettoReport.TIPO_DOUBLE, avanzo, "Saldo");
			reportData.inserisci(oggettoReport);
		}
	}

	public void inserisciMediaEntrate(final boolean hasMediaEntrate) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		if (hasMediaEntrate) {
			Double mediaEntrate = reportData.generaMediaEntrate();
			OggettoReport oggettoReport = new OggettoReport(OggettoReport.TIPO_DOUBLE, mediaEntrate, "Media Mensile Entrate");
			reportData.inserisci(oggettoReport);
		}
	}

	public void inserisciMediaUscite(final boolean hasMediaUscite) throws Exception {
		if (hasMediaUscite) {
			Double mediaUscite = reportData.generaMediaUscite();
			OggettoReport oggettoReport = new OggettoReport(OggettoReport.TIPO_DOUBLE, mediaUscite, "Media Mensile Uscite");
			reportData.inserisci(oggettoReport);
		}
	}

	public void setReportData(ReportData reportData) {
		this.reportData = reportData;
	}

	public ReportData getReportData() {
		return reportData;
	}
}
