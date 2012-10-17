package view.componenti.movimenti;

import grafica.componenti.alert.Alert;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import business.Controllore;
import business.aggiornatori.AggiornatoreManager;
import domain.wrapper.WrapEntrate;
import domain.wrapper.WrapSingleSpesa;

public class AscoltatoreNumeroMovimenti implements ActionListener {

	private final String tipo;
	private final String[] nomiColonne;
	JTextField campo;

	public AscoltatoreNumeroMovimenti(final String tipo, final String[] nomiColonne, final JTextField campo) {
		this.tipo = tipo;
		this.nomiColonne = nomiColonne;
		this.campo = campo;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		if (tipo.equals(WrapEntrate.NOME_TABELLA)) {
			try {
				AggiornatoreManager.aggiornaMovimentiEntrateDaEsterno(nomiColonne, Integer.parseInt(campo.getText()));
			} catch (final Exception e1) {
				Alert.segnalazioneErroreGrave(Controllore.getSingleton().getMessaggio("insertnumber")+": "+e1.getMessage());
			}
		} else if (tipo.equals(WrapSingleSpesa.NOME_TABELLA)) {
			try {
				AggiornatoreManager.aggiornaMovimentiUsciteDaEsterno(nomiColonne, Integer.parseInt(campo.getText()));
			} catch (final Exception e1) {
				Alert.segnalazioneErroreGrave(Controllore.getSingleton().getMessaggio("insertnumber")+": "+e1.getMessage());
			}
		}
	}
}
