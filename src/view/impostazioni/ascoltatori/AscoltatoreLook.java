package view.impostazioni.ascoltatori;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import view.GeneralFrame;
import view.impostazioni.Impostazioni;
import business.Database;
import domain.Lookandfeel;
import domain.wrapper.WrapLookAndFeel;

public class AscoltatoreLook implements ActionListener {

	JComboBox<?> comboLook;
	Vector<Lookandfeel> vettore;

	public AscoltatoreLook(final JComboBox<?> comboLook, final Vector<Lookandfeel> vettore) {
		this.comboLook = comboLook;
		this.vettore = vettore;
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		String look = "";
		final Lookandfeel valoreLook = (Lookandfeel) comboLook.getSelectedItem();
		if (valoreLook != null && !valoreLook.getNome().equals("")) {
			look = valoreLook.getValore();
			for (int i = 0; i < vettore.size(); i++) {
				final Lookandfeel lookAnd = vettore.get(i);
				lookAnd.setUsato(0);
				final HashMap<String, String> campi = new HashMap<String, String>();
				final HashMap<String, String> clausole = new HashMap<String, String>();
				campi.put(WrapLookAndFeel.USATO, "0");
				clausole.put(WrapLookAndFeel.ID, Integer.toString(lookAnd.getIdLook()));
				try {
					Database.getSingleton().eseguiIstruzioneSql("update", WrapLookAndFeel.NOME_TABELLA, campi, clausole);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			//se si è scelto il look di sistema non aggiorna il db segnandolo come usato
			if (!valoreLook.getValore().equals(UIManager.getSystemLookAndFeelClassName())) {
				valoreLook.setUsato(1);
				final HashMap<String, String> campi = new HashMap<String, String>();
				final HashMap<String, String> clausole = new HashMap<String, String>();
				campi.put(WrapLookAndFeel.USATO, "1");
				clausole.put(WrapLookAndFeel.ID, Integer.toString(valoreLook.getIdLook()));
				try {
					Database.getSingleton().eseguiIstruzioneSql("update", WrapLookAndFeel.NOME_TABELLA, campi, clausole);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		} else {
			look = "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel";
		}
		try {
			UIManager.setLookAndFeel(look);
			SwingUtilities.updateComponentTreeUI(GeneralFrame.getSingleton());
			SwingUtilities.updateComponentTreeUI(Impostazioni.getSingleton());
		} catch (final Exception e1) {
			comboLook.setSelectedIndex(0);
			e1.printStackTrace();
		} 
	}
}