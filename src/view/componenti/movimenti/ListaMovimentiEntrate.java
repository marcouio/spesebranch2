package view.componenti.movimenti;

import grafica.componenti.button.ButtonBase;
import grafica.componenti.textfield.testo.TextFieldTesto;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JDialog;

import business.AltreUtil;
import business.ControlloreSpese;
import business.aggiornatori.AggiornatoreManager;
import domain.Entrate;
import domain.wrapper.Model;
import domain.wrapper.WrapEntrate;

public class ListaMovimentiEntrate extends AbstractListaMov {

	private static final long serialVersionUID = 1L;

	public ListaMovimentiEntrate(final Container container) {
		super(container);
		ButtonBase pulsanteNMovimenti = getPanFiltraMov().getPulsanteNMovimenti();
		TextFieldTesto campo = getPanFiltraMov().getCampo();
		pulsanteNMovimenti.addActionListener(new AscoltatoreNumeroMovimenti(WrapEntrate.NOME_TABELLA, createNomiColonne(), campo));
	}

	@Override
	public String[][] createMovimenti() {
		try {
			return Model.getSingleton().movimentiEntrate(getNumEntry(), WrapEntrate.NOME_TABELLA);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return movimenti;
	}

	@Override
	public String[] createNomiColonne() {
		try {
			return (String[]) AltreUtil.generaNomiColonne(WrapEntrate.NOME_TABELLA);
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
						public String[][] getMovimenti() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
							final Vector<Entrate> entrate = new WrapEntrate().movimentiEntrateFiltrati(getDataDa(), getDataA(), getNome(), getEuro(), getCategoria());
							String[][] mov = null;
							try {
								mov = Model.getSingleton().movimentiFiltratiEntratePerNumero(WrapEntrate.NOME_TABELLA, entrate);
							} catch (Exception e) {
								e.printStackTrace();
							}
							AggiornatoreManager.aggiornaMovimentiEntrateDaFiltro(createNomiColonne(), mov);
							return mov;
						}

						{
							// array per Categori

							final ArrayList<String> lista = new ArrayList<String>();
							lista.add("");
							lista.add(ControlloreSpese.getSingleton().getMessaggio("variables"));
							lista.add(ControlloreSpese.getSingleton().getMessaggio("fixity"));
							comboBoxCat = new JComboBox(lista.toArray());

							comboBoxCat.setBounds(512, 26, 89, 25);
							getContentPane().add(comboBoxCat);
						}

						@Override
						protected String getCategoria() {
							if (comboBoxCat.getSelectedItem() != null && comboBoxCat.getSelectedIndex() != 0) {
								categoria = (String) comboBoxCat.getSelectedItem();
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
		return WrapEntrate.NOME_TABELLA;
	}

}
