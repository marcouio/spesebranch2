package view;

import grafica.componenti.alert.Alert;
import grafica.componenti.button.ButtonBase;
import grafica.componenti.label.LabelBase;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;

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

		String msgDesc = ControlloreSpese.getSingleton().getMessaggio("description");
		JLabel lblGestionespese = new LabelBase(msgDesc, this);
		lblGestionespese.setBounds(184, 45, 391, 28);

		String msgVersion = ControlloreSpese.getSingleton().getMessaggio("version");
		JLabel lblVersione = new LabelBase(msgVersion, this);
		lblVersione.setBounds(184, 85, 240, 28);

		JLabel lblVersione2 = new LabelBase(msgVersion, this);
		lblVersione2.setBounds(184, 85, 240, 28);

		JLabel lblMarcoMolinari = new LabelBase(ControlloreSpese.getSingleton().getMessaggio("copyright"), this);
		lblMarcoMolinari.setBounds(184, 124, 327, 28);

		JLabel help = new LabelBase(ControlloreSpese.getSingleton().getMessaggio("clickherehelp"), this);
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
