package business.ascoltatoriMenu;

import java.awt.event.ActionEvent;

import business.Controllore;
import business.ascoltatori.AscoltatoreAggiornatoreTutto;

import command.CommandManager;
import command.RedoCommand;

public class AscoltatoreAvanti extends AscoltatoreAggiornatoreTutto {

	@Override
	protected void actionPerformedOverride(final ActionEvent e) throws Exception {
		final CommandManager managerComandi = Controllore.getSingleton().getCommandManager();
		managerComandi.invocaComando(new RedoCommand());
	}
}
