package domain.wrapper;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Set;

import command.javabeancommand.AbstractOggettoEntita;

import db.Clausola;
import db.ConnectionPool;
import db.dao.IDAO;
import db.dao.UtilityDAO;
import domain.Budget;
import domain.CatSpese;
import domain.Gruppi;
import domain.ICatSpese;
import domain.SingleSpesa;

public class WrapCatSpese extends Observable implements ICatSpese, IDAO {

	public static final String NOME_TABELLA = "cat_spese";
	public static final String ID = "idCategoria";
	public static final String DESCRIZIONE = "descrizione";
	public static final String IMPORTANZA = "importanza";
	public static final String NOME = "nome";
	public static final String IDGRUPPO = "idGruppo";

	public static final String IMPORTANZA_FUTILE = "Futili";
	public static final String IMPORTANZA_VARIABILE = "Variabili";
	public static final String IMPORTANZA_FISSO = "Fisse";
	
	private final CatSpese categoria;
	
	IDAO genericDao = UtilityDAO.getDaoByTipo(CatSpese.class);

	public WrapCatSpese() {
		categoria = new CatSpese();
	}

	@Override
	public Object selectById(int id) throws Exception {
		try {
			return genericDao.selectById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			ConnectionPool.getSingleton().chiudiOggettiDb(null);
		}
		return null;
	}

	@Override
	public ArrayList<?> selectAll() throws Exception {
		try {
			return (ArrayList<?>) genericDao.selectAll();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			ConnectionPool.getSingleton().chiudiOggettiDb(null);
		}
		return null;
	}

	@Override
	public boolean insert(Object oggettoEntita) throws Exception {
		try {
			return genericDao.insert(oggettoEntita);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			ConnectionPool.getSingleton().chiudiOggettiDb(null);
		}
		return false;
	}

	@Override
	public boolean delete(int id) throws Exception {
		try {
			return genericDao.delete(id);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			ConnectionPool.getSingleton().chiudiOggettiDb(null);
		}
		return false;
	}

	@Override
	public boolean update(Object oggettoEntita) throws Exception {
		try {
			return genericDao.update(oggettoEntita);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			ConnectionPool.getSingleton().chiudiOggettiDb(null);
		}
		return false;
	}

	@Override
	public boolean deleteAll() throws Exception {
		try {
			return genericDao.deleteAll();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			ConnectionPool.getSingleton().chiudiOggettiDb(null);
		}
		return false;
	}

	@Override
	public String getDescrizione() {
		return categoria.getDescrizione();
	}

	@Override
	public void setDescrizione(String descrizione) {
		categoria.setDescrizione(descrizione);
	}

	@Override
	public int getIdCategoria() {
		return categoria.getIdCategoria();
	}

	@Override
	public void setIdCategoria(int idCategoria) {
		categoria.setIdCategoria(idCategoria);
	}

	@Override
	public String getImportanza() {
		return categoria.getImportanza();
	}

	@Override
	public void setImportanza(String importanza) {
		categoria.setImportanza(importanza);
	}

	@Override
	public String getNome() {
		return categoria.getNome();
	}

	@Override
	public void setNome(String nome) {
		categoria.setNome(nome);
	}

	@Override
	public Budget getBudget() {
		return categoria.getBudget();
	}

	@Override
	public void setBudget(Budget budget) {
		categoria.setBudget(budget);
	}

	@Override
	public Gruppi getGruppi() {
		return categoria.getGruppi();
	}

	@Override
	public void setGruppi(Gruppi gruppi) {
		categoria.setGruppi(gruppi);
	}

	@Override
	public Set<SingleSpesa> getSingleSpesas() {
		return categoria.getSingleSpesas();
	}

	@Override
	public void setSingleSpesas(Set<SingleSpesa> singleSpesas) {
		categoria.setSingleSpesas(singleSpesas);
	}

	@Override
	public void notifyObservers() {
		super.notifyObservers();
	}

	@Override
	public synchronized void setChanged() {
		super.setChanged();
	}

	@Override
	public AbstractOggettoEntita getEntitaPadre() throws Exception {
		return categoria;
	}

	@Override
	public Object selectWhere(ArrayList<Clausola> clausole, String appendToQuery)
			throws Exception {
		return genericDao.selectWhere(clausole, appendToQuery);
	}

}
