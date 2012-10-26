package view;

import grafica.componenti.contenitori.FrameBase;
import grafica.componenti.contenitori.PannelloBase;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import view.bottoni.Bottone;
import view.bottoni.PannelloBottoni;
import view.bottoni.PannelloBottoniInterno;
import view.bottoni.ToggleBtn;
import view.componenti.movimenti.Movimenti;
import view.entrateuscite.EntrateView;
import view.entrateuscite.UsciteView;
import view.mymenu.MyMenu;
import view.tabelleMesi.PerMesiF;
import business.AltreUtil;
import business.Controllore;
import business.ascoltatori.AscoltatoreAggiornatoreNiente;
import domain.wrapper.WrapEntrate;
import domain.wrapper.WrapSingleSpesa;

public class GeneralFrame extends PannelloBase {

	private static final long serialVersionUID = 1L;
	private static PerMesiF tabPermesi;
	private static Movimenti tabMovimenti;
	private static NewSql consolle;
	private static GeneralFrame singleton;
	private final ArrayList<JPanel> listaPannelli = new ArrayList<JPanel>();

	public static final GeneralFrame getSingleton(final Container contenitore) {
		if (singleton == null) {
			synchronized (GeneralFrame.class) {
				if (singleton == null) {
					singleton = new GeneralFrame(contenitore);
				}
			} // if
		} // if
		return singleton;
	} // getSingleton()

	/**
	 * Create the frame.
	 */
	private GeneralFrame(final Container contenitore) {
		super(contenitore);

		final MyMenu menu = new MyMenu(this);
		menu.posizionaSottoA(null, 0, 0);
		menu.setSize(980, 23);

		PannelloBottoni pannelloBottoni = createPannelloBottoni();
		pannelloBottoni.posizionaSottoA(menu, 0, 10);
		pannelloBottoni.setSize(980, 93);

		// movimenti
		tabMovimenti = new Movimenti(this);
		tabMovimenti.getTabMovUscite().setSize(980, 500);
		tabMovimenti.getTabMovEntrate().setSize(980, 500);
		tabMovimenti.getTabMovUscite().posizionaSottoA(pannelloBottoni, 0, 0);
		tabMovimenti.getTabMovEntrate().posizionaSottoA(pannelloBottoni, 0, 0);

		this.add(tabMovimenti.getTabMovEntrate());
		this.add(tabMovimenti.getTabMovUscite());
		listaPannelli.add(tabMovimenti.getTabMovEntrate());
		listaPannelli.add(tabMovimenti.getTabMovUscite());


		for (final JPanel pannello : listaPannelli) {
			pannello.setVisible(false);
		}
		tabMovimenti.getTabMovUscite().setVisible(true);

		repaint();
	}

	private PannelloBottoni createPannelloBottoni() {
		final PannelloBottoni pannelloBottoni = new PannelloBottoni(this);
		final ImageIcon iconaMovimenti = new ImageIcon(AltreUtil.IMGUTILPATH+"controlli.gif");
		final ImageIcon iconaMovimentiPic = new ImageIcon(AltreUtil.IMGUTILPATH+"controlli_pic.gif");
		final ToggleBtn toggleMovimenti = new ToggleBtn(Controllore.getSingleton().getMessaggio("transactions"), iconaMovimenti);
		toggleMovimenti.settaggioBottoneStandard();
		final Bottone bottoneMovimenti = new Bottone(toggleMovimenti, this);
		toggleMovimenti.addActionListener(new AscoltatoreAggiornatoreNiente() {

			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				for (final JPanel pannello : listaPannelli) {
					pannello.setVisible(false);
				}
				tabMovimenti.getTabMovUscite().setVisible(true);
			}
		});

		final String uscite = Controllore.getSingleton().getMessaggio("withdrawal");
		final ToggleBtn toggleMovimentiUscite = new ToggleBtn(uscite, iconaMovimentiPic, -1, 20);
		toggleMovimentiUscite.settaggioBottoneStandard();
		final Bottone bottoneMovimentiUscite = new Bottone(toggleMovimentiUscite, this);
		toggleMovimentiUscite.addActionListener(new AscoltatoreAggiornatoreNiente() {

			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				for (final JPanel pannello : listaPannelli) {
					pannello.setVisible(false);
				}
				tabMovimenti.getTabMovUscite().setVisible(true);
				toggleMovimentiUscite.setSelected(false);
			}
		});
		final String entrate = Controllore.getSingleton().getMessaggio("income");
		final ToggleBtn toggleMovimentiEntrate = new ToggleBtn(entrate, iconaMovimentiPic, -1, 20);
		toggleMovimentiEntrate.settaggioBottoneStandard();
		final Bottone bottoneMovimentiEntrate = new Bottone(toggleMovimentiEntrate,this);
		toggleMovimentiEntrate.addActionListener(new AscoltatoreAggiornatoreNiente() {

			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				for (final JPanel pannello : listaPannelli) {
					pannello.setVisible(false);
				}
				tabMovimenti.getTabMovEntrate().setVisible(true);
				toggleMovimentiEntrate.setSelected(false);
				bottoneMovimentiEntrate.getContenuto().getGruppoBottoni().clearSelection();
				bottoneMovimentiEntrate.getBottone().setSelected(false);
			}
		});

		final PannelloBottoniInterno pp = new PannelloBottoniInterno(this);
		final ArrayList<Bottone> dueButton = new ArrayList<Bottone>();
		dueButton.add(bottoneMovimentiUscite);
		dueButton.add(bottoneMovimentiEntrate);
		pp.addDueBottoni(dueButton);
		bottoneMovimenti.setContenuto(pp);

		toggleMovimenti.setPadre(bottoneMovimenti);

		final ImageIcon iconaUscite = new ImageIcon(AltreUtil.IMGUTILPATH+"blocktable_32.png");
		final String mesi = Controllore.getSingleton().getMessaggio("months");

		final ToggleBtn toggleMesi = new ToggleBtn(mesi, iconaUscite);
		toggleMesi.settaggioBottoneStandard();
		final Bottone bottoneMesi = new Bottone(toggleMesi,this);
		toggleMesi.setPadre(bottoneMesi);
		toggleMesi.addActionListener(new AscoltatoreAggiornatoreNiente() {

			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				for (final JPanel pannello : listaPannelli) {
					pannello.setVisible(false);
				}
				if(tabPermesi == null){
					tabPermesi = new PerMesiF(GeneralFrame.this);
					tabPermesi.setSize(950, 500);
					tabPermesi.posizionaSottoA(pannelloBottoni, 0, -65);
					GeneralFrame.this.add(tabPermesi);
					listaPannelli.add(tabPermesi);

				}
				tabPermesi.setVisible(true);
			}
		});

		final ImageIcon iconaSQL = new ImageIcon(AltreUtil.IMGUTILPATH+"sql.gif");
		final ToggleBtn toggleSql = new ToggleBtn("ConsolleSQL", iconaSQL);
		toggleSql.settaggioBottoneStandard();
		final Bottone bottoneSql = new Bottone(toggleSql,this);
		toggleSql.setPadre(bottoneSql);
		toggleSql.addActionListener(new AscoltatoreAggiornatoreNiente() {

			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				for (final JPanel pannello : listaPannelli) {
					pannello.setVisible(false);
				}
				if(consolle == null){
					consolle = new NewSql(GeneralFrame.this);
					consolle.setSize(980, 500);
					consolle.posizionaSottoA(pannelloBottoni, 0, -55);
					GeneralFrame.this.add(consolle);
					listaPannelli.add(consolle);
				}
				consolle.setVisible(true);
			}
		});

		final ImageIcon iconaSoldi = new ImageIcon(AltreUtil.IMGUTILPATH+"soldi.gif");
		final ImageIcon iconaSoldiPic = new ImageIcon(AltreUtil.IMGUTILPATH+"soldi_pic.gif");

		final String addtransaction = Controllore.getSingleton().getMessaggio("addtransaction");
		final ToggleBtn toggleEntrateUscite = new ToggleBtn(addtransaction, iconaSoldi);

		toggleEntrateUscite.settaggioBottoneStandard();
		final Bottone bottoneEntrateUscite = new Bottone(toggleEntrateUscite,this);
		toggleEntrateUscite.setPadre(bottoneEntrateUscite);

		final String charge = Controllore.getSingleton().getMessaggio("charge");
		final ToggleBtn toggleInsUscite = new ToggleBtn(charge, iconaSoldiPic, -1, 20);

		toggleInsUscite.settaggioBottoneStandard();
		final Bottone bottoneInsUscite = new Bottone(toggleInsUscite,this);
		toggleInsUscite.addActionListener(new AscoltatoreAggiornatoreNiente() {

			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				try {
					final PannelloBottoni pannelloEntrateUscite = bottoneEntrateUscite.getContenuto();
					final UsciteView dialog = new UsciteView(new WrapSingleSpesa());
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setBounds(30, 60, 347, 407);
					dialog.setVisible(true);
					pannelloEntrateUscite.getGruppoBottoni().clearSelection();
				} catch (final Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		final String income = Controllore.getSingleton().getMessaggio("income");
		final ToggleBtn toggleInsEntrate = new ToggleBtn(income, iconaSoldiPic, -1, 20);

		toggleInsEntrate.settaggioBottoneStandard();
		final Bottone bottoneInsEntrate = new Bottone(toggleInsEntrate,this);
		toggleInsEntrate.addActionListener(new AscoltatoreAggiornatoreNiente() {

			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				final PannelloBottoni pannelloEntrateUscite = bottoneEntrateUscite.getContenuto();
				final EntrateView dialog = new EntrateView(new WrapEntrate());
				dialog.setLocationRelativeTo(null);
				dialog.setBounds(30, 60, 347, 318);
				dialog.setVisible(true);
				pannelloEntrateUscite.getGruppoBottoni().clearSelection();
			}
		});

		final PannelloBottoniInterno EntrateUsciteContenuto = new PannelloBottoniInterno(this);
		final ArrayList<Bottone> dueBottoni = new ArrayList<Bottone>();
		dueBottoni.add(bottoneInsUscite);
		dueBottoni.add(bottoneInsEntrate);
		EntrateUsciteContenuto.addDueBottoni(dueBottoni);
		bottoneEntrateUscite.setContenuto(EntrateUsciteContenuto);

		pannelloBottoni.addBottone(bottoneSql);
		pannelloBottoni.addBottone(bottoneMesi);
		pannelloBottoni.addBottone(bottoneMovimenti);
		pannelloBottoni.addBottone(bottoneEntrateUscite);
		return pannelloBottoni;
	}

	public void relocateFinestreLaterali(final FrameBase view) {
		if (Controllore.getSingleton().getInitFinestre().getFinestraVisibile() != null) {
			final Point p = view.getLocation();
			final Dimension d = view.getSize();
			p.setLocation(p.x + d.width + 5, p.y);
			try {
				final JFrame finestraVisibile = Controllore.getSingleton().getInitFinestre().getFinestraVisibile();
				finestraVisibile.setLocation(p);
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}

	public PerMesiF getTabPermesi() {
		return tabPermesi;
	}

	public void setTabPermesi(final PerMesiF tabPermesi) {
		GeneralFrame.tabPermesi = tabPermesi;
	}

	public Movimenti getTabMovimenti() {
		return tabMovimenti;
	}

	public void setTabMovimenti(final Movimenti tabMovimenti) {
		GeneralFrame.tabMovimenti = tabMovimenti;
	}

	public NewSql getConsolle() {
		return consolle;
	}

	public void setConsolle(final NewSql consolle) {
		GeneralFrame.consolle = consolle;
	}

}
