package view.componenti.componentiPannello;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CostruttoreSottoPannello extends JPanel {

	private static final long serialVersionUID       = 1L;
	int                       distanzaDalBordoY      = 20;
	int                       distanzaDalBordoX      = 20;

	private final int         distanzaDaiComponentiY = 0;
	private final int         distanzaDaiComponentiX = 20;

	private final int         larghezzaComponent     = 116;
	private final int         altezzaComponent       = 30;
	private int               indiceX                = distanzaDalBordoX;
	private int               indiceY                = distanzaDalBordoY;
	private JComponent[]      componenti;
	private JLabel[]          labels;

	public static final int   HORIZONTAL             = 0;
	public static final int   VERTICAL               = 1;

	/**
	 * Create the panel.
	 */
	public CostruttoreSottoPannello(final JComponent[] componenti, final JLabel[] labels, int orientation) {
		super();
		this.componenti = componenti;
		this.labels = labels;
		if (orientation == VERTICAL) {
			initGUI(componenti, labels);
			// TODO dimensione pannello
			this.setPreferredSize(new Dimension((this.getMaxWidth(componenti) + this.distanzaDalBordoX * 2), (this.getMaxHeight(componenti) + this.distanzaDalBordoY * 2) * componenti.length));
		} else {
			initLabelOrizzontale(labels);
			initComponentsOrizzontale(componenti);
			this.setPreferredSize(new Dimension((this.getMaxWidth(componenti) + this.distanzaDalBordoX * 2) * componenti.length, (this.getMaxHeight(componenti) + this.distanzaDalBordoY * 2) * 2));
		}
	}
	
	/**
	 * Create the panel.
	 * @throws Exception 
	 */
	public CostruttoreSottoPannello(int orientation) throws Exception {
		super();
		this.componenti = getComponenti();
		this.labels = getLabels();
		if (orientation == VERTICAL) {
			initGUI(componenti, labels);
			// TODO dimensione pannello
			this.setPreferredSize(new Dimension((this.getMaxWidth(componenti) + this.distanzaDalBordoX * 2), (this.getMaxHeight(componenti) + this.distanzaDalBordoY * 2) * componenti.length));
		} else {
			initLabelOrizzontale(labels);
			initComponentsOrizzontale(componenti);
			this.setPreferredSize(new Dimension((this.getMaxWidth(componenti) + this.distanzaDalBordoX * 2) * componenti.length, (this.getMaxHeight(componenti) + this.distanzaDalBordoY * 2) * 2));
		}
	}

	protected JComponent[] getComponenti() throws Exception {
		return componenti;
	}

	protected JLabel[] getLabels() {
		return labels;
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension((this.getMaxWidth(componenti) + this.distanzaDalBordoX * 2) * componenti.length, (this.getMaxHeight(componenti) + this.distanzaDalBordoY * 2) * 3);
	}

	public CostruttoreSottoPannello(ArrayList<JComponent> componenti, ArrayList<JLabel> labels, int orientation) {
		this.setLayout(null);
		if (orientation == HORIZONTAL) {
			initLabelOrizzontale(labels);
			initComponentsOrizzontale(componenti);
			this.setSize(getMaxWidth(componenti) + distanzaDalBordoX * 2, getMaxHeight(componenti) + distanzaDalBordoY * 2);
		} else {
			// TODO
			// initGUI(componenti, labels);
			// this.setPreferredSize(new Dimension((this.getMaxWidth(componenti)
			// + this.distanzaDalBordoX * 2) * componenti.length,
			// (this.getMaxHeight(componenti) + this.distanzaDalBordoY * 2) *
			// 2));
		}
	}

	public CostruttoreSottoPannello() {
	}

	private void initGUI2(final JComponent[] componenti, final JLabel[] labels) {
		this.setLayout(null);
		initLabelOrizzontale(labels);
		initComponentsOrizzontale(componenti);
		this.setSize(getMaxWidth(componenti) + distanzaDalBordoX * 2, getMaxHeight(componenti) + distanzaDalBordoY * 2);

	}

	private void initGUI(final JComponent[] componenti, final JLabel[] labels) {
		this.setLayout(null);
		initComponentsVerticale(componenti, labels);
		this.setSize(getMaxWidth(componenti) + distanzaDalBordoX * 2, getMaxHeight(componenti) + distanzaDalBordoY * 2);

	}

	private void initLabelOrizzontale(final JLabel[] labels) {
		for (JLabel label : labels) {
			if(label != null){
				label.setBounds(indiceX, indiceY, getLarghezzaComponent(), getAltezzaComponent());
				indiceX += distanzaDaiComponentiX + label.getWidth();
				this.add(label);
			}
		}
	}

	private void initLabelOrizzontale(final ArrayList<JLabel> labels) {
		for (JLabel label : labels) {
			if(label != null){
				label.setBounds(indiceX, indiceY, getLarghezzaComponent(), getAltezzaComponent());
				indiceX += distanzaDaiComponentiX + label.getWidth();
				this.add(label);
			}
		}
	}

	private void initComponentsOrizzontale(JComponent[] componenti) {
		indiceX = distanzaDalBordoX;
		for (JComponent component : componenti) {
			if(component != null){
				component.setBounds(indiceX, indiceY + getAltezzaComponent() + distanzaDaiComponentiY, getLarghezzaComponent(), getAltezzaComponent());
				indiceX += distanzaDaiComponentiX + component.getWidth();
				this.add(component);
			}
		}
	}

	private void initComponentsOrizzontale(ArrayList<JComponent> componenti) {
		indiceX = distanzaDalBordoX;
		for (JComponent component : componenti) {
			if(component != null){
				component.setBounds(indiceX, indiceY + distanzaDaiComponentiY + getAltezzaComponent(), getLarghezzaComponent(), getAltezzaComponent());
				indiceX += distanzaDaiComponentiX + component.getWidth();
				this.add(component);
			}

		}
	}

	private void initComponentsVerticale(JComponent[] componenti, JLabel[] labels) {
		indiceX = distanzaDalBordoX;
		for (int i = 0; i < componenti.length; i++) {
			JLabel label = labels[i];
			if(label != null){
				label.setBounds(indiceX, indiceY, getLarghezzaComponent(), getAltezzaComponent());
				indiceY += distanzaDaiComponentiY + label.getHeight();
				this.add(label);
			}
			JComponent component = componenti[i];
			if(component != null){
				component.setBounds(indiceX, indiceY, getLarghezzaComponent(), getAltezzaComponent());
				indiceY += distanzaDaiComponentiY + component.getHeight();
				this.add(component);
			}
		}
	}

	private int getMaxWidth(final JComponent[] componenti) {
		int maxWidth = 0;
		for (JComponent component : componenti) {
			if(component != null){
				if (component.getWidth() > maxWidth) {
					maxWidth = component.getWidth();
				}
			}
		}
		return maxWidth;
	}

	int getMaxWidth(final ArrayList<JComponent> componenti) {
		int maxWidth = 0;
		for (JComponent component : componenti) {
			if(component != null){
				if (component.getWidth() > maxWidth) {
					maxWidth = component.getWidth();
				}
			}
		}
		return maxWidth;
	}

	private int getMaxHeight(final ArrayList<JComponent> componenti) {
		int maxHeight = 0;
		for (JComponent component : componenti) {
			if(component != null){
				if (component.getHeight() > maxHeight) {
					maxHeight = component.getHeight();
				}
			}
		}
		return maxHeight;
	}

	private int getMaxHeight(final JComponent[] componenti) {
		int maxHeight = 0;
		for (JComponent component : componenti) {
			if(component != null){
				if (component.getHeight() > maxHeight) {
					maxHeight = component.getHeight();
				}
			}
		}
		return maxHeight;
	}

	protected int getDistanzaDalBordoY() {
		return distanzaDalBordoY;
	}

	protected void setDistanzaDalBordoY(int distanzaDalBordoY) {
		this.distanzaDalBordoY = distanzaDalBordoY;
	}

	protected int getDistanzaDalBordoX() {
		return distanzaDalBordoX;
	}

	protected void setDistanzaDalBordoX(int distanzaDalBordoX) {
		this.distanzaDalBordoX = distanzaDalBordoX;
	}

	public int getLarghezzaComponent() {
		return larghezzaComponent;
	}

	public int getAltezzaComponent() {
		return altezzaComponent;
	}
}
