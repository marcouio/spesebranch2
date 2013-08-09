package view;

import grafica.componenti.button.ToggleBtnBase;
import grafica.componenti.contenitori.PannelloBase;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class ListenerBtnMov implements ActionListener{

	ToggleBtnBase[] deselecting; 
	PannelloBase[] visibiling;
	GeneralFrame generalFrame;
	
	
	
	public ListenerBtnMov(ToggleBtnBase[] deselecting, PannelloBase[] visibiling, GeneralFrame generalFrame) {
		this.deselecting = deselecting;
		this.visibiling = visibiling;
		this.generalFrame = generalFrame;
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		for (int i = 0; i < deselecting.length; i++) {
			deselecting[i].setSelected(false);
		}
		for (final JPanel pannello : generalFrame.getListaPannelli()) {
			pannello.setVisible(false);
		}
		for (int i = 0; i < visibiling.length; i++) {
			visibiling[i].setVisible(true);
		}
		
	}

}
