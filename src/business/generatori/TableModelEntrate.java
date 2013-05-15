package business.generatori;

import grafica.componenti.table.TableModel;

import java.util.HashMap;

import business.Controllore;
import business.Database;

public class TableModelEntrate extends TableModel{
	public static HashMap<Integer, String> mapMesi = new HashMap<Integer, String>();

	static{
		mapMesi.put(1, getMessaggio("january"));
		mapMesi.put(2, getMessaggio("february"));
		mapMesi.put(3, getMessaggio("march"));
		mapMesi.put(4, getMessaggio("april"));
		mapMesi.put(5, getMessaggio("may"));
		mapMesi.put(6, getMessaggio("june"));
		mapMesi.put(7, getMessaggio("july"));
		mapMesi.put(8, getMessaggio("august"));
		mapMesi.put(9, getMessaggio("september"));
		mapMesi.put(10, getMessaggio("october"));
		mapMesi.put(11, getMessaggio("november"));
		mapMesi.put(12, getMessaggio("december"));
	}

	private static String getMessaggio(String msg) {
		return Controllore.getSingleton().getMessaggio(msg);
	}

	String[] listaColonne;

	public TableModelEntrate(final Object parametro) throws Exception {
		super(parametro);
	}

	private static final long serialVersionUID = 1L;

	@Override
	protected void preBuild(final Object parametro) throws Exception {
		aggiungiNomiColonne();
		for (int i = 1; i <= 12; i++) {
			Riga riga = new Riga();
			riga.add(mapMesi.get(i));
			for (int x = 0; x < getListaColonne().length; x++) {
				String entrataMeseTipo = Double.toString(Database.getSingleton().entrateMeseTipo((i), listaColonne[x]));
				riga.add(entrataMeseTipo);
			}
			addRiga(riga);
		}
	}

	private Riga aggiungiNomiColonne(){

		String mesi = Controllore.getSingleton().getMessaggio("months");
		addColumn(mesi);
		for (int i = 0; i < getListaColonne().length; i++) {
			addColumn(getListaColonne()[i]);
		}
		return getNomiColonne();
	}

	public String[] getListaColonne() {
		if(listaColonne == null){
			String fisse = Controllore.getSingleton().getMessaggio("fixity");
			String variabili = Controllore.getSingleton().getMessaggio("variables");
			listaColonne = new String[]{fisse, variabili};
		}
		return listaColonne;
	}

}
