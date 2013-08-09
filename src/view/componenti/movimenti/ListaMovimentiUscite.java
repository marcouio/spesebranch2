package view.componenti.movimenti;

import grafica.componenti.button.ButtonBase;
import grafica.componenti.textfield.testo.TextFieldTesto;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JDialog;

import business.AltreUtil;
import business.aggiornatori.AggiornatoreManager;
import business.cache.CacheCategorie;
import domain.CatSpese;
import domain.SingleSpesa;
import domain.wrapper.Model;
import domain.wrapper.WrapEntrate;
import domain.wrapper.WrapSingleSpesa;

public class ListaMovimentiUscite extends AbstractListaMov {

	private static final long serialVersionUID = 1L;

	public ListaMovimentiUscite(final Container container) {
		super(container);
		ButtonBase pulsanteNMovimenti = getPanFiltraMov().getPulsanteNMovimenti();
		TextFieldTesto campo = getPanFiltraMov().getCampo();
		pulsanteNMovimenti.addActionListener(new AscoltatoreNumeroMovimenti(WrapSingleSpesa.NOME_TABELLA, createNomiColonne(), campo));
	}

	@Override
	public String[][] createMovimenti() {
		try {
			return Model.getSingleton().movimentiUscite(getNumEntry(), WrapSingleSpesa.NOME_TABELLA);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return movimenti;
	}

	@Override
	public String[] createNomiColonne() {
		try {
			return (String[]) AltreUtil.generaNomiColonne(WrapSingleSpesa.NOME_TABELLA);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void impostaTableSpecifico() {

	}

	@Override
	public ActionListener getListener() {
		return new ActionListener() {

			@Override
			public void actionPerformed(final ActionEvent e) {
				try {
					final FiltraDialog dialog = new FiltraDialog() {
						private static final long serialVersionUID = 1L;

						@Override
						public String[][] getMovimenti() {
							final Vector<SingleSpesa> uscite = new WrapSingleSpesa()
									.movimentiUsciteFiltrate(getDataDa(), getDataA(), getNome(), getEuro(), getCategoria());
							String[][] mov = null;
							try {
								mov = Model.getSingleton().movimentiFiltratiUscitePerNumero(WrapEntrate.NOME_TABELLA, uscite);
							} catch (Exception e) {
								e.printStackTrace();
							}
							AggiornatoreManager.aggiornaMovimentiUsciteDaFiltro(createNomiColonne(), mov);
							return mov;
						}

						{
							comboBoxCat = new JComboBox(CacheCategorie.getSingleton().getVettoreCategoriePerCombo());
							comboBoxCat.setBounds(512, 26, 89, 25);
							getContentPane().add(comboBoxCat);

						}

						@Override
						protected String getCategoria() {
							if (comboBoxCat.getSelectedItem() != null) {
								final int idCat = ((CatSpese) comboBoxCat.getSelectedItem()).getIdCategoria();
								categoria = Integer.toString(idCat);
							}
							return categoria;
						}
					};
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (final Exception e1) {
					e1.printStackTrace();
				}

			}
		};
	}

	@Override
	protected String getTipo() {
		return WrapSingleSpesa.NOME_TABELLA;
	}
}
