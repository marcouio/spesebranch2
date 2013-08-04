package view;

import grafica.componenti.ExceptionGraphics;
import grafica.componenti.button.ToggleBtnBase;
import grafica.componenti.contenitori.FrameBase;
import grafica.componenti.contenitori.PannelloBase;
import grafica.componenti.pannelloBottoni.Bottone;
import grafica.componenti.pannelloBottoni.PannelloBottoni;
import grafica.componenti.pannelloBottoni.PannelloBottoniInterno;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import view.componenti.movimenti.Movimenti;
import view.entrateuscite.EntrateView;
import view.entrateuscite.UsciteView;
import view.mymenu.MyMenu;
import view.tabelleMesi.PerMesiF;
import business.AltreUtil;
import business.ControlloreSpese;
import business.ascoltatori.AscoltatoreAggiornatoreNiente;
import disegno.immagini.UtilImage;
import domain.wrapper.WrapEntrate;
import domain.wrapper.WrapSingleSpesa;

public class GeneralFrame extends PannelloBase {

	private static final long serialVersionUID = 1L;
	
	private PerMesiF tabPermesi;
	private Movimenti tabMovimenti;
	private NewSql consolle;
	
	private static GeneralFrame singleton;
	private final ArrayList<JPanel> listaPannelli = new ArrayList<JPanel>();

	public static final GeneralFrame getSingleton() throws ExceptionGraphics {
		synchronized (GeneralFrame.class) {
			if (singleton == null) {
				FrameBase view = ControlloreSpese.getSingleton().getView();
				singleton = new GeneralFrame(view);
			}
		} // if
		return singleton;
	} // getSingleton()

	/**
	 * Create the frame.
	 * @throws ExceptionGraphics 
	 */
	private GeneralFrame(final Container contenitore) throws ExceptionGraphics {
		super(contenitore);

		final MyMenu menu = new MyMenu(this);
		menu.posizionaSottoA(null, 0, 0);
		menu.setSize(980, 23);

		PannelloBottoni pannelloBottoni = createPannelloBottoni();
		pannelloBottoni.posizionaSottoA(menu, 0, 10);
		pannelloBottoni.setSize(980, 63);

		// movimenti
		tabMovimenti = new Movimenti(this);
		tabMovimenti.getTabMovUscite().setSize(980, 400);
		tabMovimenti.getTabMovEntrate().setSize(980, 400);
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

	private PannelloBottoni createPannelloBottoni() throws ExceptionGraphics{
		final PannelloBottoni pannelloBottoni = new PannelloBottoni(this);

		final ImageIcon iconaMovimenti = new ImageIcon(AltreUtil.IMGUTILPATH+"controlli.gif");
		final Bottone bottoneMovimenti = new Bottone(pannelloBottoni);

		final String movimenti = ControlloreSpese.getSingleton().getMessaggio("transactions");
		final ToggleBtnBase toggleMovimenti = new ToggleBtnBase(movimenti, iconaMovimenti, bottoneMovimenti,bottoneMovimenti, 10,0);
		toggleMovimenti.settaggioBottoneStandard();
		bottoneMovimenti.setBottone(toggleMovimenti);

		ImageIcon ret = new ImageIcon(AltreUtil.IMGUTILPATH+"ret.png");
		ret = UtilImage.resizeImage(18, 36, ret);

		final PannelloBottoniInterno internoMovimenti = new PannelloBottoniInterno(bottoneMovimenti, ret, "Entrate", null, "Uscite", null);
		bottoneMovimenti.setPanelInterno(internoMovimenti);

		internoMovimenti.getBottoniPrimo().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				internoMovimenti.getBottoniSecondo().setSelected(false);
				for (final JPanel pannello : listaPannelli) {
					pannello.setVisible(false);
				}
				tabMovimenti.getTabMovEntrate().setVisible(true);

			}
		});

		internoMovimenti.getBottoniSecondo().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				internoMovimenti.getBottoniPrimo().setSelected(false);
				for (final JPanel pannello : listaPannelli) {
					pannello.setVisible(false);
				}
				tabMovimenti.getTabMovUscite().setVisible(true);
			}
		});


		bottoneMovimenti.setSize(245, 50);
		toggleMovimenti.addActionListener(new AscoltatoreAggiornatoreNiente() {

			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				for (final JPanel pannello : listaPannelli) {
					pannello.setVisible(false);
				}
				tabMovimenti.getTabMovUscite().setVisible(true);
			}
		});


		final ImageIcon iconaUscite = new ImageIcon(AltreUtil.IMGUTILPATH+"blocktable_32.png");
		final String mesi = ControlloreSpese.getSingleton().getMessaggio("months");

		final Bottone bottoneMesi = new Bottone(pannelloBottoni);
		final ToggleBtnBase toggleMesi = new ToggleBtnBase(mesi, iconaUscite,bottoneMesi,bottoneMesi,10,0);
		bottoneMesi.setBottone(toggleMesi);

		bottoneMesi.setSize(245, 50);	
		toggleMesi.settaggioBottoneStandard();
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
					tabPermesi.posizionaSottoA(pannelloBottoni, 0, -40);
					GeneralFrame.this.add(tabPermesi);
					listaPannelli.add(tabPermesi);

				}
				tabPermesi.setVisible(true);
			}
		});

		final Bottone bottoneSql = new Bottone(pannelloBottoni);
		final ImageIcon iconaSQL = new ImageIcon(AltreUtil.IMGUTILPATH+"sql.gif");
		final ToggleBtnBase toggleSql = new ToggleBtnBase("ConsolleSQL", iconaSQL, bottoneSql, bottoneSql, 10,0);
		bottoneSql.setBottone(toggleSql);

		bottoneSql.setSize(245, 50);	
		toggleSql.settaggioBottoneStandard();
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

		final Bottone bottoneEntrateUscite = new Bottone(pannelloBottoni);
		final String addtransaction = ControlloreSpese.getSingleton().getMessaggio("addtransaction");
		final ToggleBtnBase toggleEntrateUscite = new ToggleBtnBase(addtransaction, iconaSoldi, bottoneEntrateUscite, 10,0);
		bottoneEntrateUscite.setBottone(toggleEntrateUscite);

		toggleEntrateUscite.settaggioBottoneStandard();
		toggleEntrateUscite.setPadre(bottoneEntrateUscite);

		final PannelloBottoniInterno internoEntryUscite = new PannelloBottoniInterno(bottoneEntrateUscite, ret, "Entrate", null, "Uscite", null);
		bottoneEntrateUscite.setPanelInterno(internoEntryUscite);
		internoEntryUscite.getBottoniPrimo().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				internoEntryUscite.getBottoniSecondo().setSelected(false);
				final EntrateView dialog = new EntrateView(new WrapEntrate());
				dialog.setLocationRelativeTo(null);
				dialog.setBounds(30, 60, 347, 318);
				dialog.setVisible(true);

			}
		});

		internoEntryUscite.getBottoniSecondo().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				internoEntryUscite.getBottoniPrimo().setSelected(false);
				
				FrameBase contenitorePadre = (FrameBase) GeneralFrame.this.getContenitorePadre();
				final UsciteView dialog = new UsciteView(contenitorePadre, new WrapSingleSpesa());
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setBounds(30, 60, 347, 407);
				dialog.setVisible(true);

			}
		});

		bottoneEntrateUscite.setSize(245, 50);

		pannelloBottoni.addBottone(bottoneMovimenti);
		pannelloBottoni.addBottone(bottoneSql);
		pannelloBottoni.addBottone(bottoneMesi);
		pannelloBottoni.addBottone(bottoneEntrateUscite);

		return pannelloBottoni;
	}

	public void relocateFinestreLaterali(final FrameBase view) {
		if (ControlloreSpese.getSingleton().getInitFinestre().getFinestraVisibile() != null) {
			final Point p = view.getLocation();
			final Dimension d = view.getSize();
			p.setLocation(p.x + d.width + 5, p.y);
			try {
				final JFrame finestraVisibile = ControlloreSpese.getSingleton().getInitFinestre().getFinestraVisibile();
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
		this.tabPermesi = tabPermesi;
	}

	public Movimenti getTabMovimenti() {
		return tabMovimenti;
	}

	public void setTabMovimenti(Movimenti tabMovimenti) {
		this.tabMovimenti = tabMovimenti;
	}

	public NewSql getConsolle() {
		return consolle;
	}

	public void setConsolle(final NewSql consolle) {
		this.consolle = consolle;
	}

}
