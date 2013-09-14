package view.report;

import grafica.componenti.alert.Alert;
import grafica.componenti.button.ButtonBase;
import grafica.componenti.checkbox.CheckBoxBase;
import grafica.componenti.label.LabelBase;

import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;

import business.ControlloreSpese;
import business.ascoltatori.AscoltatoreAggiornatoreNiente;
import db.UtilDb;

public class ReportView extends AbstractReportView {

	private static final long serialVersionUID = 1L;

	private void settaValoriReportDati(final CheckBoxBase chckbxSpeseVariabili_1,
			final CheckBoxBase chckbxEntrateMensCategorie, final CheckBoxBase chckbxSpeseMensCat,
			final CheckBoxBase chckbxEntratePerCategorie, final CheckBoxBase chckbxSpesePerCategorie,
			final CheckBoxBase chckbxUsciteMensili, final CheckBoxBase chckbxEntrateMensili,
			final CheckBoxBase chckbxUsciteAnnuali, final CheckBoxBase chckbxEntrateAnnuali,
			final CheckBoxBase chckbxSpeseFutili_1, final CheckBoxBase chckbxAvanzo, final CheckBoxBase chckbxMedie) throws Exception {
		
		inserisciUsciteVariabili(chckbxSpeseVariabili_1.isSelected());
		inserisciEntrateCatMensili(chckbxEntrateMensCategorie.isSelected());
		inserisciUsciteCatMensili(chckbxSpeseMensCat.isSelected());
		inserisciEntrateCatAnnuali(chckbxEntratePerCategorie.isSelected());
		inserisciUsciteCatAnnuali(chckbxSpesePerCategorie.isSelected());
		inserisciUsciteMensili(chckbxUsciteMensili.isSelected());
		inserisciEntrateMensili(chckbxEntrateMensili.isSelected());
		inserisciUsciteAnnuali(chckbxUsciteAnnuali.isSelected());
		inserisciEntrateAnnuali(chckbxEntrateAnnuali.isSelected());
		inserisciUsciteFutili(chckbxSpeseFutili_1.isSelected());
		inserisciAvanzo(chckbxAvanzo.isSelected());
		inserisciMediaEntrate(chckbxMedie.isSelected());
		inserisciMediaUscite(chckbxMedie.isSelected());
	}

	/**
	 * Create the panel
	 * 
	 * @throws FileNotFoundException
	 */
	public ReportView() throws FileNotFoundException {
		setReportData(new ReportData());
		getContentPane().setLayout(null);
		this.setTitle("Report");
		this.setSize(250, 425);
		String selreportmsg = ControlloreSpese.getSingleton().getMessaggio("selectreport");
		final JLabel Istruzioni = new LabelBase(selreportmsg, this);
		Istruzioni.setText(ControlloreSpese.getSingleton().getMessaggio("select") + ":");
		Istruzioni.setBounds(12, 12, 207, 20);

		String msgYearIncome = ControlloreSpese.getSingleton().getMessaggio("yearincome");
		final CheckBoxBase chckbxEntrateAnnuali = new CheckBoxBase(msgYearIncome, this);
		chckbxEntrateAnnuali.setBounds(22, 40, 197, 23);
		getContentPane().add(chckbxEntrateAnnuali);

		final CheckBoxBase chckbxUsciteAnnuali = new CheckBoxBase(ControlloreSpese.getSingleton().getMessaggio("yearoutcome"), this);
		chckbxUsciteAnnuali.setBounds(22, 67, 197, 23);
		getContentPane().add(chckbxUsciteAnnuali);

		final CheckBoxBase chckbxEntrateMensili = new CheckBoxBase(ControlloreSpese.getSingleton().getMessaggio("monthlyincome"), this);
		chckbxEntrateMensili.setBounds(22, 94, 197, 23);
		getContentPane().add(chckbxEntrateMensili);

		final CheckBoxBase chckbxUsciteMensili = new CheckBoxBase(ControlloreSpese.getSingleton().getMessaggio("monthlyoutcome"), this);
		chckbxUsciteMensili.setBounds(22, 121, 197, 23);
		getContentPane().add(chckbxUsciteMensili);

		final CheckBoxBase chckbxSpesePerCategorie = new CheckBoxBase(ControlloreSpese.getSingleton().getMessaggio("catspeseyear"), this);
		chckbxSpesePerCategorie.setBounds(22, 148, 197, 23);
		getContentPane().add(chckbxSpesePerCategorie);

		final CheckBoxBase chckbxEntratePerCategorie = new CheckBoxBase(ControlloreSpese.getSingleton().getMessaggio("catentrateyear"), this);
		chckbxEntratePerCategorie.setBounds(22, 175, 197, 23);
		getContentPane().add(chckbxEntratePerCategorie);

		final CheckBoxBase chckbxSpeseMensCat = new CheckBoxBase(ControlloreSpese.getSingleton().getMessaggio("catspesemonth"), this);
		chckbxSpeseMensCat.setBounds(22, 229, 197, 23);
		getContentPane().add(chckbxSpeseMensCat);

		final CheckBoxBase chckbxEntrateMensCategorie = new CheckBoxBase(ControlloreSpese.getSingleton().getMessaggio("catentratemonth"), this);
		chckbxEntrateMensCategorie.setBounds(22, 202, 197, 23);
		getContentPane().add(chckbxEntrateMensCategorie);

		final CheckBoxBase chckbxSpeseVariabili_1 = new CheckBoxBase("% "
				+ ControlloreSpese.getSingleton().getMessaggio("spesevar"), this);
		chckbxSpeseVariabili_1.setBounds(22, 255, 197, 23);
		getContentPane().add(chckbxSpeseVariabili_1);

		final CheckBoxBase chckbxSpeseFutili_1 = new CheckBoxBase("% " + ControlloreSpese.getSingleton().getMessaggio("spesefut"), this);
		chckbxSpeseFutili_1.setBounds(22, 282, 197, 23);
		getContentPane().add(chckbxSpeseFutili_1);

		final CheckBoxBase chckbxMedie = new CheckBoxBase(ControlloreSpese.getSingleton().getMessaggio("annualaverages"), this);
		chckbxMedie.setBounds(22, 336, 197, 23);
		getContentPane().add(chckbxMedie);

		final CheckBoxBase chckbxAvanzo = new CheckBoxBase(ControlloreSpese.getSingleton().getMessaggio("avanzo"), this);
		chckbxAvanzo.setBounds(22, 309, 197, 23);
		getContentPane().add(chckbxAvanzo);

		final JButton btnGeneraReport = new ButtonBase(ControlloreSpese.getSingleton().getMessaggio("reports"), this);
		btnGeneraReport.setBounds(22, 366, 197, 25);
		getContentPane().add(btnGeneraReport);

		btnGeneraReport.addActionListener(new AscoltatoreAggiornatoreNiente() {

			@Override
			protected void actionPerformedOverride(final ActionEvent e) throws Exception {
				super.actionPerformedOverride(e);
				
				reportData.reset();
				
				settaValoriReportDati(chckbxSpeseVariabili_1, chckbxEntrateMensCategorie, chckbxSpeseMensCat,
						chckbxEntratePerCategorie, chckbxSpesePerCategorie, chckbxUsciteMensili, chckbxEntrateMensili,
						chckbxUsciteAnnuali, chckbxEntrateAnnuali, chckbxSpeseFutili_1, chckbxAvanzo, chckbxMedie);

				try {
					IScrittoreReport scrittoreReport = new ScrittoreReportTxt(reportData);
					scrittoreReport.generaReport();
				} catch (Exception e11) {
					e11.printStackTrace();
				}
				Alert.segnalazioneInfo("Aggiornato Report: " + UtilDb.dataToString(new Date(), "dd/MM/yyyy HH:mm"));
			}

		});
		ControlloreSpese.getLog().info("Registrato Report: " + UtilDb.dataToString(new Date(), "dd/MM/yyyy HH:mm"));

	}
}
