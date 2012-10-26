package view;

import grafica.componenti.ExceptionGraphics;
import grafica.componenti.contenitori.PannelloBase;

import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;

public class OggettoVistaBase extends PannelloBase {
	protected Font titolo;

	public OggettoVistaBase(final GridLayout gridLayout, final Container container) throws ExceptionGraphics {
		super(gridLayout, container);
	}

	public OggettoVistaBase(final Container container) {
		super(container);
		titolo = new Font("Tahoma", Font.BOLD | Font.ITALIC, 14);

	}
	protected static final long serialVersionUID = 1L;


}
