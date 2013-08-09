package view;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JPanel;

import view.componenti.movimenti.Movimenti;
import view.entrateuscite.EntrateView;
import view.entrateuscite.UsciteView;
import view.tabelleMesi.PerMesiF;
import business.AltreUtil;
import business.ControlloreSpese;
import business.ascoltatori.AscoltatoreAggiornatoreNiente;
import disegno.immagini.UtilImage;
import domain.wrapper.WrapEntrate;
import domain.wrapper.WrapSingleSpesa;

import grafica.componenti.ExceptionGraphics;
import grafica.componenti.button.ToggleBtnBase;
import grafica.componenti.contenitori.FrameBase;
import grafica.componenti.contenitori.PannelloBase;
import grafica.componenti.pannelloBottoni.Bottone;
import grafica.componenti.pannelloBottoni.PannelloBottoni;
import grafica.componenti.pannelloBottoni.PannelloBottoniInterno;

public class PanBtnSpese extends PannelloBottoni{

	GeneralFrame generalFrame;
	
	public PanBtnSpese(Container contenitore) throws ExceptionGraphics {
		super(contenitore);
		generalFrame = (GeneralFrame) contenitore;
		initGui();
	}

	private void initGui() throws ExceptionGraphics {
		
		Movimenti tabMovimenti = generalFrame.getTabMovimenti();
		
		final ImageIcon iconaMovimenti = new ImageIcon(AltreUtil.IMGUTILPATH+"controlli.gif");
		final Bottone bottoneMovimenti = new Bottone(this);

		final String movimenti = ControlloreSpese.getSingleton().getMessaggio("transactions");
		final ToggleBtnBase toggleMovimenti = new ToggleBtnBase(movimenti, iconaMovimenti, bottoneMovimenti,bottoneMovimenti, 10,0);
		toggleMovimenti.settaggioBottoneStandard();
		bottoneMovimenti.setBottone(toggleMovimenti);

		ImageIcon ret = new ImageIcon(AltreUtil.IMGUTILPATH+"ret.png");
		ret = UtilImage.resizeImage(18, 36, ret);

		final PannelloBottoniInterno internoMovimenti = new PannelloBottoniInterno(bottoneMovimenti, ret, "Entrate", null, "Uscite", null);
		bottoneMovimenti.setPanelInterno(internoMovimenti);

		ToggleBtnBase[] deselectPrimo = new ToggleBtnBase[]{internoMovimenti.getBottoniSecondo()};
		PannelloBase[] visibilePrimo = new PannelloBase[]{tabMovimenti,tabMovimenti.getTabMovEntrate()};
		internoMovimenti.getBottoniPrimo().addActionListener(new ListenerBtnMov(deselectPrimo, visibilePrimo,generalFrame));

		ToggleBtnBase[] deselectSecondo = new ToggleBtnBase[]{internoMovimenti.getBottoniPrimo()};
		PannelloBase[] visibileSecondo = new PannelloBase[]{tabMovimenti,tabMovimenti.getTabMovUscite()};
		internoMovimenti.getBottoniSecondo().addActionListener(new ListenerBtnMov(deselectSecondo, visibileSecondo,generalFrame));

		bottoneMovimenti.setSize(245, 50);
		toggleMovimenti.addActionListener(new ListenerBtnMov(deselectPrimo, visibilePrimo, generalFrame));


		final ImageIcon iconaUscite = new ImageIcon(AltreUtil.IMGUTILPATH+"blocktable_32.png");
		final String mesi = ControlloreSpese.getSingleton().getMessaggio("months");

		final Bottone bottoneMesi = new Bottone(this);
		final ToggleBtnBase toggleMesi = new ToggleBtnBase(mesi, iconaUscite,bottoneMesi,bottoneMesi,10,0);
		bottoneMesi.setBottone(toggleMesi);

		bottoneMesi.setSize(245, 50);	
		toggleMesi.settaggioBottoneStandard();
		toggleMesi.setPadre(bottoneMesi);
		toggleMesi.addActionListener(new AscoltatoreAggiornatoreNiente() {

			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				ArrayList<JPanel> listaPannelli = generalFrame.getListaPannelli();
				for (final JPanel pannello : listaPannelli) {
					pannello.setVisible(false);
				}
				PerMesiF tabPermesi = generalFrame.getTabPermesi();
				if(tabPermesi == null){
					tabPermesi = new PerMesiF(generalFrame);
					tabPermesi.setSize(950, 500);
					tabPermesi.posizionaSottoA(PanBtnSpese.this, 0, -40);
					generalFrame.add(tabPermesi);
					listaPannelli.add(tabPermesi);

				}
				tabPermesi.setVisible(true);
			}
		});

		final Bottone bottoneSql = new Bottone(this);
		final ImageIcon iconaSQL = new ImageIcon(AltreUtil.IMGUTILPATH+"sql.gif");
		final ToggleBtnBase toggleSql = new ToggleBtnBase("ConsolleSQL", iconaSQL, bottoneSql, bottoneSql, 10,0);
		bottoneSql.setBottone(toggleSql);

		bottoneSql.setSize(245, 50);	
		toggleSql.settaggioBottoneStandard();
		toggleSql.setPadre(bottoneSql);
		toggleSql.addActionListener(new AscoltatoreAggiornatoreNiente() {

			@Override
			public void actionPerformedOverride(final ActionEvent e) {
				ArrayList<JPanel> listaPannelli = generalFrame.getListaPannelli();
				for (final JPanel pannello : listaPannelli) {
					pannello.setVisible(false);
				}
				NewSql consolle = generalFrame.getConsolle();
				if(consolle == null){
					consolle = new NewSql(generalFrame);
					consolle.setSize(980, 500);
					consolle.posizionaSottoA(PanBtnSpese.this, 0, -55);
					generalFrame.add(consolle);
					listaPannelli.add(consolle);
				}
				consolle.setVisible(true);
			}
		});

		final ImageIcon iconaSoldi = new ImageIcon(AltreUtil.IMGUTILPATH+"soldi.gif");

		final Bottone bottoneEntrateUscite = new Bottone(this);
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
				
				FrameBase contenitorePadre = (FrameBase) generalFrame.getContenitorePadre();
				final UsciteView dialog = new UsciteView(contenitorePadre, new WrapSingleSpesa());
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setBounds(30, 60, 347, 407);
				dialog.setVisible(true);

			}
		});

		bottoneEntrateUscite.setSize(245, 50);

		this.addBottone(bottoneMovimenti);
		this.addBottone(bottoneSql);
		this.addBottone(bottoneMesi);
		this.addBottone(bottoneEntrateUscite);

	}

	private static final long	serialVersionUID	= 1L;

}
