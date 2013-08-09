package view.componenti.movimenti;

import grafica.componenti.button.ButtonBase;
import grafica.componenti.contenitori.PannelloBase;
import grafica.componenti.label.LabelBase;
import grafica.componenti.textfield.testo.TextFieldTesto;

import java.awt.Container;

import business.ControlloreSpese;

public class PanFiltraMovimenti extends PannelloBase {

	private static final long	serialVersionUID	= 1L;
	private TextFieldTesto	campo;
	private ButtonBase	pulsanteNMovimenti;
	
	AbstractListaMov pannelloListaMov;

	public PanFiltraMovimenti(Container contenitore) {
		super(contenitore);
		this.pannelloListaMov = (AbstractListaMov) contenitore;
		initGui();
	}

	private void initGui() {
		String transactions = ControlloreSpese.getSingleton().getMessaggio("transactions")+":";
		LabelBase movim = new LabelBase(transactions, this);
		movim.posizionaSottoA(null, 24, 5);
		
		campo = new TextFieldTesto("20", this);
		campo.posizionaADestraDi(movim, 10, 0);
		campo.setSize(32, 25);
		
		String cambia = ControlloreSpese.getSingleton().getMessaggio("change");
		pulsanteNMovimenti = new ButtonBase(cambia, this);
		pulsanteNMovimenti.posizionaADestraDi(campo, 20, 0);
		pulsanteNMovimenti.setSize(89, 25);
		
		final ButtonBase btnFiltraMovimenti = new ButtonBase(this);
		btnFiltraMovimenti.setText(ControlloreSpese.getSingleton().getMessaggio("filtertrans"));
		btnFiltraMovimenti.setBounds(292, 6, 179, 25);
		btnFiltraMovimenti.addActionListener(pannelloListaMov.getListener());
		this.setSize(this.getLarghezza(), this.getAltezza());
	}

	public TextFieldTesto getCampo() {
		return campo;
	}

	public ButtonBase getPulsanteNMovimenti() {
		return pulsanteNMovimenti;
	}


}
