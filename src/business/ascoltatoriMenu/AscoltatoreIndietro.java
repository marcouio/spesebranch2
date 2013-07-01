package business.ascoltatoriMenu;

import java.awt.event.ActionEvent;

import business.ControlloreSpese;
import business.ascoltatori.AscoltatoreAggiornatoreTutto;

import command.CommandManager;
import command.UndoCommand;

public class AscoltatoreIndietro extends AscoltatoreAggiornatoreTutto {

	@Override
	protected void actionPerformedOverride(final ActionEvent e) throws Exception {
		super.actionPerformedOverride(e);
		final CommandManager managerComandi = ControlloreSpese.getSingleton().getCommandManager();
		managerComandi.invocaComando(new UndoCommand());
	}

}
