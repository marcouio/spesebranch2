package view;

import grafica.componenti.contenitori.FrameBase;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;

import business.Controllore;
import business.InizializzatoreFinestre;

public class MyWindowListener extends WindowAdapter implements WindowFocusListener, ComponentListener, MouseListener {

	private FrameBase view;

	public MyWindowListener(final FrameBase view) {
		super();
		this.view = view;
	}


	@Override
	public void windowDeiconified(final WindowEvent e) {
		if (Controllore.getSingleton().getInitFinestre().getFinestraVisibile() != null) {
			Controllore.getSingleton().getInitFinestre().getFinestraVisibile().setVisible(true);
		}
		Controllore.getSingleton().getPannello().relocateFinestreLaterali(view);
	}

	@Override
	public void windowClosed(final WindowEvent e) {
		Controllore.getSingleton().quit();
		Controllore.getSingleton().getInitFinestre().quitFinestre();
	}

	@Override
	public void windowIconified(final WindowEvent e) {
		final JFrame[] finestre = Controllore.getSingleton().getInitFinestre().getFinestre();
		for (int i = 0; i < finestre.length; i++) {

			final JFrame jFrame = finestre[i];
			if (jFrame != null) {
				jFrame.setVisible(false);
			}
		}
	}

	@Override
	public void componentResized(final ComponentEvent e) {
		Controllore.getSingleton().getPannello().relocateFinestreLaterali(view);
	}

	@Override
	public void componentMoved(final ComponentEvent e) {
		InizializzatoreFinestre initFinestre = Controllore.getSingleton().getInitFinestre(); 
		if (initFinestre.getFinestraVisibile() != null) {
			initFinestre.getFinestraVisibile().setVisible(true);
			initFinestre.getFinestraVisibile().setState(WindowEvent.WINDOW_DEICONIFIED);
		}
		Controllore.getSingleton().getPannello().relocateFinestreLaterali(view);
	}

	@Override
	public void componentShown(final ComponentEvent e) {
		Controllore.getSingleton().getPannello().relocateFinestreLaterali(view);

	}

	@Override
	public void componentHidden(final ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowGainedFocus(final WindowEvent e) {
	}

	@Override
	public void mouseClicked(final MouseEvent e) {
	}

	@Override
	public void mouseEntered(final MouseEvent e) {
	}

	@Override
	public void mouseExited(final MouseEvent e) {
	}

	@Override
	public void mousePressed(final MouseEvent e) {
	}

	@Override
	public void mouseReleased(final MouseEvent e) {
		final JFrame finVisibile = Controllore.getSingleton().getInitFinestre().getFinestraVisibile();
		if (finVisibile != null) {
			finVisibile.setVisible(true);
			finVisibile.invalidate();
			finVisibile.repaint();
		}

	}

}
