package business.generatori;

import grafica.componenti.table.TableModel;

import java.util.HashMap;
import java.util.Vector;

import business.Controllore;
import business.Database;
import business.cache.CacheCategorie;
import domain.CatSpese;

public class TableModelUscite extends TableModel{
	public static HashMap<Integer, String> mapMesi = new HashMap<Integer, String>();
	 
	static{
		mapMesi.put(1, Controllore.getSingleton().getMessaggio("january"));
		mapMesi.put(2, Controllore.getSingleton().getMessaggio("february"));
		mapMesi.put(3, Controllore.getSingleton().getMessaggio("march"));
		mapMesi.put(4, Controllore.getSingleton().getMessaggio("april"));
		mapMesi.put(5, Controllore.getSingleton().getMessaggio("may"));
		mapMesi.put(6, Controllore.getSingleton().getMessaggio("june"));
		mapMesi.put(7, Controllore.getSingleton().getMessaggio("july"));
		mapMesi.put(8, Controllore.getSingleton().getMessaggio("august"));
		mapMesi.put(9, Controllore.getSingleton().getMessaggio("september"));
		mapMesi.put(10, Controllore.getSingleton().getMessaggio("october"));
		mapMesi.put(11, Controllore.getSingleton().getMessaggio("november"));
		mapMesi.put(12, Controllore.getSingleton().getMessaggio("december"));
	}

	Vector<CatSpese> categorie;
	
	public TableModelUscite(Object parametro) throws Exception {
		super(parametro);
	}

	private static final long serialVersionUID = 1L;

	@Override
	protected void preBuild(Object parametro) throws Exception {
		aggiungiNomiColonne();
		for (int i = 1; i <= 12; i++) {
			Riga riga = new Riga();
			riga.add(mapMesi.get(i));
			for (int x = 0; x < getCategorie().size(); x++) {
				int idCategoria = Integer.parseInt(categorie.get(x).getIdEntita());
				String spesaMeseCat = Double.toString(Database.speseMeseCategoria(i, idCategoria));
				riga.add(spesaMeseCat);
			}
			addRiga(riga);
		}
	}
	
	private void aggiungiNomiColonne(){
		
		addColumn(Controllore.getSingleton().getMessaggio("months"));
		for (int i = 0; i < getCategorie().size(); i++) {
			addColumn(getCategorie().get(i).getNome());
		}
	}

	public Vector<CatSpese> getCategorie() {
		if(categorie == null){
			categorie = CacheCategorie.getSingleton().getVettoreCategorie();
		}
		return categorie;
	}

}
