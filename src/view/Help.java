package view;

import grafica.componenti.alert.Alert;
import grafica.componenti.button.ButtonBase;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;

import view.font.LabelListaGruppi;
import business.AltreUtil;
import business.ControlloreSpese;
import business.ascoltatori.AscoltatoreAggiornatoreNiente;

public class Help extends JDialog {

	private static final long serialVersionUID = 1L;

	public Help() {
		setLayout(null);
		ImageIcon image = new ImageIcon(AltreUtil.IMGUTILPATH+"index1.jpeg");
		JLabel label = new JLabel("");
		label.setIcon(image);
		label.setBounds(36, 55, 138, 297);
		add(label);

		JLabel lblGestionespese = new LabelListaGruppi(ControlloreSpese.getSingleton().getMessaggio("description"));
		lblGestionespese.setBounds(184, 45, 391, 28);
		add(lblGestionespese);

		JLabel lblVersione = new LabelListaGruppi(ControlloreSpese.getSingleton().getMessaggio("version"));
		lblVersione.setBounds(184, 85, 240, 28);
		add(lblVersione);

		JLabel lblVersione2 = new LabelListaGruppi(ControlloreSpese.getSingleton().getMessaggio("version"));
		lblVersione2.setBounds(184, 85, 240, 28);
		add(lblVersione2);

		JLabel lblMarcoMolinari = new LabelListaGruppi(ControlloreSpese.getSingleton().getMessaggio("copyright"));
		lblMarcoMolinari.setBounds(184, 124, 327, 28);
		add(lblMarcoMolinari);

		JLabel help = new LabelListaGruppi(ControlloreSpese.getSingleton().getMessaggio("clickherehelp"));
		help.setText(ControlloreSpese.getSingleton().getMessaggio("cliccahelp"));
		help.setBounds(184, 163, 215, 28);

		add(help);

		ButtonBase btnHelp = new ButtonBase(this);
		btnHelp.setText(ControlloreSpese.getSingleton().getMessaggio("help"));
		btnHelp.setBounds(389, 166, 91, 23);
		btnHelp.addActionListener(new AscoltatoreAggiornatoreNiente() {

			@Override
			protected void actionPerformedOverride(final ActionEvent e) throws Exception {
				super.actionPerformedOverride(e);
				Desktop desktop = Desktop.getDesktop();
				try {
					desktop.open(new File("help.pdf"));
				} catch (Exception e1) {
					Alert.segnalazioneErroreWarning(e1.getMessage());
				}

			}
		});
		add(btnHelp);
	}
}
