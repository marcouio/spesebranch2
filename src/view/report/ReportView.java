package view.report;

import grafica.componenti.alert.Alert;
import grafica.componenti.button.ButtonBase;

import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

import db.UtilDb;

import view.font.CheckBoxF;
import view.font.LabelListaGruppi;
import business.ControlloreSpese;
import business.ascoltatori.AscoltatoreAggiornatoreNiente;

public class ReportView extends AbstractReportView {

	private static final long serialVersionUID = 1L;

	private void settaValoriReportDati(final JCheckBox chckbxSpeseVariabili_1,
			final JCheckBox chckbxEntrateMensCategorie, final JCheckBox chckbxSpeseMensCat,
			final JCheckBox chckbxEntratePerCategorie, final JCheckBox chckbxSpesePerCategorie,
			final JCheckBox chckbxUsciteMensili, final JCheckBox chckbxEntrateMensili,
			final JCheckBox chckbxUsciteAnnuali, final JCheckBox chckbxEntrateAnnuali,
			final JCheckBox chckbxSpeseFutili_1, final JCheckBox chckbxAvanzo, final JCheckBox chckbxMedie) throws Exception {
		
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
		final JLabel Istruzioni = new LabelListaGruppi(ControlloreSpese.getSingleton().getMessaggio("selectreport"));
		Istruzioni.setText(ControlloreSpese.getSingleton().getMessaggio("select") + ":");
		Istruzioni.setBounds(12, 12, 207, 20);
		getContentPane().add(Istruzioni);

		final JCheckBox chckbxEntrateAnnuali = new CheckBoxF(ControlloreSpese.getSingleton().getMessaggio("yearincome"));
		chckbxEntrateAnnuali.setBounds(22, 40, 197, 23);
		getContentPane().add(chckbxEntrateAnnuali);

		final JCheckBox chckbxUsciteAnnuali = new CheckBoxF(ControlloreSpese.getSingleton().getMessaggio("yearoutcome"));
		chckbxUsciteAnnuali.setBounds(22, 67, 197, 23);
		getContentPane().add(chckbxUsciteAnnuali);

		final JCheckBox chckbxEntrateMensili = new CheckBoxF(ControlloreSpese.getSingleton().getMessaggio("monthlyincome"));
		chckbxEntrateMensili.setBounds(22, 94, 197, 23);
		getContentPane().add(chckbxEntrateMensili);

		final JCheckBox chckbxUsciteMensili = new CheckBoxF(ControlloreSpese.getSingleton().getMessaggio("monthlyoutcome"));
		chckbxUsciteMensili.setBounds(22, 121, 197, 23);
		getContentPane().add(chckbxUsciteMensili);

		final JCheckBox chckbxSpesePerCategorie = new CheckBoxF(ControlloreSpese.getSingleton().getMessaggio("catspeseyear"));
		chckbxSpesePerCategorie.setBounds(22, 148, 197, 23);
		getContentPane().add(chckbxSpesePerCategorie);

		final JCheckBox chckbxEntratePerCategorie = new CheckBoxF(ControlloreSpese.getSingleton().getMessaggio(
				"catentrateyear"));
		chckbxEntratePerCategorie.setBounds(22, 175, 197, 23);
		getContentPane().add(chckbxEntratePerCategorie);

		final JCheckBox chckbxSpeseMensCat = new CheckBoxF(ControlloreSpese.getSingleton().getMessaggio("catspesemonth"));
		chckbxSpeseMensCat.setBounds(22, 229, 197, 23);
		getContentPane().add(chckbxSpeseMensCat);

		final JCheckBox chckbxEntrateMensCategorie = new CheckBoxF(ControlloreSpese.getSingleton().getMessaggio(
				"catentratemonth"));
		chckbxEntrateMensCategorie.setBounds(22, 202, 197, 23);
		getContentPane().add(chckbxEntrateMensCategorie);

		final JCheckBox chckbxSpeseVariabili_1 = new CheckBoxF("% "
				+ ControlloreSpese.getSingleton().getMessaggio("spesevar"));
		chckbxSpeseVariabili_1.setBounds(22, 255, 197, 23);
		getContentPane().add(chckbxSpeseVariabili_1);

		final JCheckBox chckbxSpeseFutili_1 = new CheckBoxF("% " + ControlloreSpese.getSingleton().getMessaggio("spesefut"));
		chckbxSpeseFutili_1.setBounds(22, 282, 197, 23);
		getContentPane().add(chckbxSpeseFutili_1);

		final JCheckBox chckbxMedie = new CheckBoxF(ControlloreSpese.getSingleton().getMessaggio("annualaverages"));
		chckbxMedie.setBounds(22, 336, 197, 23);
		getContentPane().add(chckbxMedie);

		final JCheckBox chckbxAvanzo = new CheckBoxF(ControlloreSpese.getSingleton().getMessaggio("avanzo"));
		chckbxAvanzo.setBounds(22, 309, 197, 23);
		getContentPane().add(chckbxAvanzo);

		final JButton btnGeneraReport = new ButtonBase(ControlloreSpese.getSingleton().getMessaggio("reports"), this);
		btnGeneraReport.setBounds(22, 366, 197, 25);
		getContentPane().add(btnGeneraReport);

		btnGeneraReport.addActionListener(new AscoltatoreAggiornatoreNiente() {

			@Override
			protected void actionPerformedOverride(final ActionEvent e) throws Exception {
				super.actionPerformedOverride(e);

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
