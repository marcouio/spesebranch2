package view;

import grafica.componenti.ExceptionGraphics;
import grafica.componenti.contenitori.FrameBase;
import grafica.componenti.contenitori.PannelloBase;
import grafica.componenti.pannelloBottoni.PannelloBottoni;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import view.componenti.movimenti.Movimenti;
import view.mymenu.MyMenu;
import view.tabelleMesi.PerMesiF;
import business.ControlloreSpese;

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

		tabMovimenti = new Movimenti(this);

		PannelloBottoni pannelloBottoni = createPannelloBottoni();
		pannelloBottoni.posizionaSottoA(menu, 0, 10);
		pannelloBottoni.setSize(980, 63);

		// movimenti
		tabMovimenti.setSize(980, 420);
		tabMovimenti.posizionaSottoA(pannelloBottoni, 0, 0);

		listaPannelli.add(tabMovimenti.getTabMovEntrate());
		listaPannelli.add(tabMovimenti.getTabMovUscite());
		listaPannelli.add(tabMovimenti);


		for (final JPanel pannello : listaPannelli) {
			pannello.setVisible(false);
		}
		tabMovimenti.setVisible(true);
		tabMovimenti.getTabMovUscite().setVisible(true);
		
	}

	private PannelloBottoni createPannelloBottoni() throws ExceptionGraphics{
		return new PanBtnSpese(this);
	}

	public void relocateFinestreLaterali(final FrameBase view) {
		
		final JFrame finestraVisibile = ControlloreSpese.getSingleton().getInitFinestre().getFinestraVisibile();
		if (finestraVisibile != null) {
			
			final Point p = view.getLocation();
			final Dimension d = view.getSize();
			p.setLocation(p.x + d.width + 5, p.y);
			try {
				
				if(finestraVisibile.isActive()){
					finestraVisibile.setLocation(p);
				}
				
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

	public ArrayList<JPanel> getListaPannelli() {
		return listaPannelli;
	}

}
