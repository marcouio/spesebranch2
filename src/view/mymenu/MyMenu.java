package view.mymenu;

import grafica.componenti.menu.MenuBarBase;

import java.awt.Container;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import view.FinestraListaComandi;
import view.componenti.componentiPannello.PannelloAScomparsa2;
import view.entrateuscite.EntrateView;
import view.entrateuscite.UsciteView;
import view.grafici.dialogGraph.GrEntrate1;
import view.grafici.dialogGraph.GrEntrate2;
import view.grafici.dialogGraph.GrGenerale;
import view.grafici.dialogGraph.GrGenerale2;
import view.grafici.dialogGraph.GrUscite1;
import view.grafici.dialogGraph.GrUscite2;
import view.impostazioni.CategorieView;
import view.impostazioni.GruppiView;
import view.impostazioni.Impostazioni;
import view.login.Login;
import view.login.Registrazione;
import view.note.MostraNoteView;
import view.report.ReportView;
import business.AltreUtil;
import business.Controllore;
import business.InizializzatoreFinestre;
import business.ascoltatori.AscoltatoreAggiornatoreNiente;
import business.ascoltatoriMenu.AscoltatoreAvanti;
import business.ascoltatoriMenu.AscoltatoreCaricaDatabase;
import business.ascoltatoriMenu.AscoltatoreCreaDialog;
import business.ascoltatoriMenu.AscoltatoreIndietro;
import business.ascoltatoriMenu.AscoltatoreInfo;
import domain.wrapper.WrapCatSpese;
import domain.wrapper.WrapEntrate;
import domain.wrapper.WrapGruppi;
import domain.wrapper.WrapSingleSpesa;

public class MyMenu extends MenuBarBase {

	private static final long serialVersionUID = 1L;

	public MyMenu(final Container container) {
		super(container);
		init();
	}

	private void init() {
		//		this.setBounds(0, 0, 1000, 20);

		// crea un menu
		final JMenu file = new JMenu("File");
		this.add(file);

		// item di un menu
		final JMenuItem menuItem = new JMenuItem(Controllore.getSingleton().getMessaggio("otherdatabase"));
		final ActionListener ascolto = new AscoltatoreCaricaDatabase();
		menuItem.addActionListener(ascolto);
		file.add(menuItem);

		// item Login
		final JMenuItem menuItem2 = new JMenuItem("Login");
		final ActionListener login = new AscoltatoreCreaDialog(new Login(), 380, 260);
		menuItem2.addActionListener(login);
		file.add(menuItem2);

		// item Login
		final JMenuItem registra = new JMenuItem(Controllore.getSingleton().getMessaggio("register"));
		final ActionListener registrazione = new AscoltatoreCreaDialog(new Registrazione());
		registra.addActionListener(registrazione);
		file.add(registra);

		final JMenuItem chiudi = new JMenuItem(Controllore.getSingleton().getMessaggio("close"));
		chiudi.addActionListener(new AscoltatoreAggiornatoreNiente() {

			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				System.exit(0);

			}
		});
		file.add(chiudi);

		final JMenu modifica = new JMenu(Controllore.getSingleton().getMessaggio("edit"));
		add(modifica);

		final JMenuItem indietro = new JMenuItem(Controllore.getSingleton().getMessaggio("undo"));
		indietro.addActionListener(new AscoltatoreIndietro());
		modifica.add(indietro);

		final JMenuItem avanti = new JMenuItem(Controllore.getSingleton().getMessaggio("redo"));
		avanti.addActionListener(new AscoltatoreAvanti());
		modifica.add(avanti);

		final JMenu finestre = new JMenu(Controllore.getSingleton().getMessaggio("windows"));
		add(finestre);

		final JCheckBoxMenuItem listaComandi = new JCheckBoxMenuItem(Controllore.getSingleton().getMessaggio("commands"));
		finestre.add(listaComandi);

		final JCheckBoxMenuItem mntmReport = new JCheckBoxMenuItem(Controllore.getSingleton().getMessaggio("report"));
		finestre.add(mntmReport);

		final JCheckBoxMenuItem chckbxmntmDati = new JCheckBoxMenuItem(Controllore.getSingleton().getMessaggio("summarydata"));
		chckbxmntmDati.addActionListener(new AscoltatoreAggiornatoreNiente() {
			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				PannelloAScomparsa2 pas;
				try {
					pas = ((PannelloAScomparsa2) Controllore.getSingleton().getInitFinestre().getFinestra(InizializzatoreFinestre.INDEX_PANNELLODATI, null));
					Controllore.getSingleton().getInitFinestre().setVisibilitaFinestre(pas, finestre, chckbxmntmDati);
					Controllore.getPannello().relocateFinestreLaterali(Controllore.getSingleton().getView());
				} catch (final Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		finestre.add(chckbxmntmDati);

		final JCheckBoxMenuItem mntmNote = new JCheckBoxMenuItem(Controllore.getSingleton().getMessaggio("notes"));
		finestre.add(mntmNote);
		mntmNote.addActionListener(new AscoltatoreAggiornatoreNiente() {

			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				MostraNoteView note;
				try {
					note = ((MostraNoteView) Controllore.getSingleton().getInitFinestre().getFinestra(InizializzatoreFinestre.INDEX_NOTE, null));
					Controllore.getSingleton().getInitFinestre().setVisibilitaFinestre(note, finestre, mntmNote);
					Controllore.getPannello().relocateFinestreLaterali(Controllore.getSingleton().getView());
				} catch (final Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		mntmReport.addActionListener(new AscoltatoreAggiornatoreNiente() {

			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				ReportView report;
				try {
					report = ((ReportView) Controllore.getSingleton().getInitFinestre().getFinestra(InizializzatoreFinestre.INDEX_REPORT, null));
					Controllore.getSingleton().getInitFinestre().setVisibilitaFinestre(report, finestre, mntmReport);
					Controllore.getPannello().relocateFinestreLaterali(Controllore.getSingleton().getView());
				} catch (final Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		listaComandi.addActionListener(new AscoltatoreAggiornatoreNiente() {

			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				FinestraListaComandi history;
				try {
					history = ((FinestraListaComandi) Controllore.getSingleton().getInitFinestre().getFinestra(InizializzatoreFinestre.INDEX_HISTORY, null));
					Controllore.getSingleton().getInitFinestre().setVisibilitaFinestre(history, finestre, listaComandi);
					Controllore.getPannello().relocateFinestreLaterali(Controllore.getSingleton().getView());
				} catch (final Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		final JMenu mnStrumenti = new JMenu(Controllore.getSingleton().getMessaggio("tools"));
		add(mnStrumenti);

		final JMenu mnImpostazioni = new JMenu(Controllore.getSingleton().getMessaggio("options"));
		mnStrumenti.add(mnImpostazioni);

		final JMenuItem mntmConfigurazione = new JMenuItem(Controllore.getSingleton().getMessaggio("config"));
		mntmConfigurazione.addActionListener(new AscoltatoreAggiornatoreNiente() {
			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				final Impostazioni dialog = new Impostazioni();
				dialog.pack();
				dialog.setVisible(true);
				dialog.setModalityType(ModalityType.APPLICATION_MODAL);
			}
		});
		mnImpostazioni.add(mntmConfigurazione);

		final JMenuItem mntmCategorie = new JMenuItem(Controllore.getSingleton().getMessaggio("categories"));
		mntmCategorie.addActionListener(new AscoltatoreAggiornatoreNiente() {
			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				final CategorieView dialog2 = new CategorieView(new WrapCatSpese());
				dialog2.pack();
				dialog2.setVisible(true);
				dialog2.setModalityType(ModalityType.APPLICATION_MODAL);
			}
		});
		mnImpostazioni.add(mntmCategorie);

		final JMenuItem mntmGr = new JMenuItem(Controllore.getSingleton().getMessaggio("groups"));
		mntmGr.addActionListener(new AscoltatoreAggiornatoreNiente() {
			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				final GruppiView dialog = new GruppiView(new WrapGruppi());
				dialog.pack();
				dialog.setVisible(true);
				dialog.setModalityType(ModalityType.APPLICATION_MODAL);
			}
		});
		mnImpostazioni.add(mntmGr);

		final JMenu mnGrafici = new JMenu(Controllore.getSingleton().getMessaggio("charts"));
		mnStrumenti.add(mnGrafici);

		final JMenu mnEntrate = new JMenu(Controllore.getSingleton().getMessaggio("entries"));
		mnGrafici.add(mnEntrate);

		final JMenuItem mntmEntratePerTipo = new JMenuItem(Controllore.getSingleton().getMessaggio("fortype"));
		mnEntrate.add(mntmEntratePerTipo);

		final JMenuItem mntmEntrateMensili = new JMenuItem(Controllore.getSingleton().getMessaggio("monthly"));
		mnEntrate.add(mntmEntrateMensili);

		final JMenu mnUscite = new JMenu(Controllore.getSingleton().getMessaggio("charge"));
		mnGrafici.add(mnUscite);

		final JMenuItem mntmMensiliPerCategoria = new JMenuItem(Controllore.getSingleton().getMessaggio("monthlycat"));
		mnUscite.add(mntmMensiliPerCategoria);

		final JMenuItem mntmPerCategorie = new JMenuItem(Controllore.getSingleton().getMessaggio("forcategories"));
		mntmPerCategorie.addActionListener(new AscoltatoreAggiornatoreNiente() {
			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				try {	
					final GrUscite1 dialog = new GrUscite1();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (final Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		mnUscite.add(mntmPerCategorie);

		final JMenuItem mntmPerMesi = new JMenuItem(Controllore.getSingleton().getMessaggio("formonthly"));
		mntmPerMesi.addActionListener(new AscoltatoreAggiornatoreNiente() {
			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				try {
					final GrUscite2 dialog = new GrUscite2();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (final Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		mnUscite.add(mntmPerMesi);
		mntmMensiliPerCategoria.addActionListener(new AscoltatoreAggiornatoreNiente() {

			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				AltreUtil.deleteFileDaDirectory2("./immagini/");
				final GrGenerale dialog = new GrGenerale();
				dialog.setSize(700, 700);
				dialog.setVisible(true);
				dialog.setModalityType(ModalityType.APPLICATION_MODAL);
			}
		});

		final JMenu mnTotali = new JMenu(Controllore.getSingleton().getMessaggio("totals"));
		mnGrafici.add(mnTotali);

		final JMenuItem mntmSaldo = new JMenuItem(Controllore.getSingleton().getMessaggio("balance"));
		mntmSaldo.addActionListener(new AscoltatoreAggiornatoreNiente() {
			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				try {
					final GrGenerale2 dialog = new GrGenerale2();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setSize(700, 700);
					dialog.setVisible(true);
				} catch (final Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		mnTotali.add(mntmSaldo);

		final JMenu mnDati = new JMenu(Controllore.getSingleton().getMessaggio("dataentry"));
		mnStrumenti.add(mnDati);

		final JMenuItem mntmEntrate = new JMenuItem(Controllore.getSingleton().getMessaggio("entries"));
		mntmEntrate.addActionListener(new AscoltatoreAggiornatoreNiente() {
			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				final EntrateView dialog = new EntrateView(new WrapEntrate());
				dialog.setLocationRelativeTo(null);
				dialog.setBounds(0, 0, 347, 318);
				dialog.setVisible(true);
			}
		});
		mnDati.add(mntmEntrate);

		final JMenuItem mntmUscite = new JMenuItem(Controllore.getSingleton().getMessaggio("charge"));
		mntmUscite.addActionListener(new AscoltatoreAggiornatoreNiente() {
			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				try {
					final UsciteView dialog = new UsciteView(new WrapSingleSpesa());
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setBounds(0, 0, 347, 407);
					dialog.setVisible(true);
				} catch (final Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		mnDati.add(mntmUscite);
		mntmEntrateMensili.addActionListener(new AscoltatoreAggiornatoreNiente() {

			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				try {
					final GrEntrate2 dialog = new GrEntrate2();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (final Exception e1) {
					e1.printStackTrace();
				}

			}
		});
		mntmEntratePerTipo.addActionListener(new AscoltatoreAggiornatoreNiente() {
			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				final JFrame f = new JFrame();
				try {
					AltreUtil.deleteFileDaDirectory2("./immagini/");
					final GrEntrate1 dialog = new GrEntrate1(f, null, true);
					dialog.setSize(700, 700);
					dialog.setVisible(true);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setModalityType(ModalityType.APPLICATION_MODAL);
				} catch (final Exception e1) {
					e1.printStackTrace();
				} finally {
					f.dispose();
				}
			}
		});

		final JMenu help = new JMenu("Help");
		add(help);

		final JMenuItem info = new JMenuItem("Info");
		info.addActionListener(new AscoltatoreInfo());
		help.add(info);

		final JMenuItem manuale = new JMenuItem(Controllore.getSingleton().getMessaggio("userguide"));
		help.add(manuale);

	}

}
