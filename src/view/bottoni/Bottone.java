package view.bottoni;

import grafica.componenti.contenitori.PannelloBase;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.AbstractButton;

public class Bottone extends PannelloBase {

	private static final long serialVersionUID = 1L;
	private AbstractButton    bottone;
	private PannelloBottoni   contenuto        = null;
	private boolean           isEspanso;

	public static final int   RIEMPITO         = 0;

	//	public Bottone(final Container contenitore) {
	//		super(contenitore);
	//		init();
	//	}

	public Bottone(final AbstractButton bottone, final Container contenitore) {
		super(contenitore);
		init();
		this.bottone = bottone;
		this.contenuto = new PannelloBottoni(contenitore);
		this.add(bottone, BorderLayout.NORTH);
	}

	public Bottone(final PannelloBottoni contenuto, final Container contenitore) {
		super(contenitore);
		init();
		this.contenuto = contenuto;
		this.add(this.contenuto, BorderLayout.SOUTH);
	}

	public Bottone(final PannelloBottoni contenuto, final AbstractButton bottone, final Container contenitore) {
		super(contenitore);
		init();
		this.contenuto = contenuto;
		this.bottone = bottone;
		this.add(this.bottone, BorderLayout.NORTH);
		this.add(this.contenuto, BorderLayout.SOUTH);

	}

	private void init() {
		this.setLayout(new BorderLayout());
	}

	public void espandi() {
		setEspanso(true);
		if (contenuto != null) {
			contenuto.setVisible(true);
		}
		revalidate();
		repaint();
	}

	public void contrai() {
		setEspanso(false);
		if (contenuto != null) {
			contenuto.setVisible(false);
		}
	}

	public AbstractButton getBottone() {
		return bottone;
	}

	protected void setBottone(final AbstractButton bottone) {
		this.bottone = bottone;
	}

	public PannelloBottoni getContenuto() {
		return contenuto;
	}

	public void setContenuto(final PannelloBottoni contenuto) {
		this.contenuto = contenuto;
		add(this.contenuto);
		this.contenuto.setVisible(false);
	}

	public void setEspanso(final boolean isEspanso) {
		this.isEspanso = isEspanso;
	}

	public boolean isEspanso() {
		return isEspanso;
	}

}
