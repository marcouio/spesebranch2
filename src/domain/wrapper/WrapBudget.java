package domain.wrapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Vector;

import business.DBUtil;
import business.cache.CacheCategorie;

import command.javabeancommand.AbstractOggettoEntita;

import db.Clausola;
import db.ConnectionPool;
import db.dao.IDAO;
import domain.Budget;
import domain.CatSpese;
import domain.IBudget;

public class WrapBudget extends Observable implements IDAO, IBudget{


	public static final String NOME_TABELLA = "budget";
	public static final String ID = "idBudget";
	public static final String IDCATEGORIE = "idCategorie";
	public static final String PERCSULTOT = "percSulTot";
	
	Budget budget;
	
	public WrapBudget() {
		budget = new Budget();
	}

	@Override
	public Object selectById(int id) throws Exception {
		Connection cn = ConnectionPool.getSingleton().getConnection();
		String sql = "SELECT * FROM "+NOME_TABELLA+" WHERE "+ID+" = " +id;
		
		Budget budget = null;
		
		try {
			
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			if(rs.next()){
				budget = new Budget();
				setidBudget(rs.getInt(1));
				CatSpese categoria = CacheCategorie.getSingleton().getCatSpese(Integer.toString(rs.getInt(2)));
				setCatSpese(categoria);
				setpercSulTot(rs.getDouble(3));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				cn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ConnectionPool.getSingleton().chiudiOggettiDb(cn);
		}
		return budget;

	}

	@Override
	public Vector<Object> selectAll() throws Exception {
		Vector<Object> budgets = new Vector<Object>();
		Connection cn = DBUtil.getConnection();
		String sql = "SELECT * FROM " + NOME_TABELLA ;
		try{
			Statement st = cn.createStatement();
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()){
				Budget budget = new Budget();
				setidBudget(rs.getInt(1));
				CatSpese categoria = CacheCategorie.getSingleton().getCatSpese(Integer.toString(rs.getInt(2)));
				setCatSpese(categoria);
				setpercSulTot(rs.getDouble(3));
				budgets.add(budget);
			}
		
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				cn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return budgets;
	}

	@Override
	public boolean insert(Object oggettoEntita) throws Exception {
		boolean ok = false;
		Connection cn = ConnectionPool.getSingleton().getConnection();
		String sql = "";
		try {
			
			sql="INSERT INTO " + NOME_TABELLA + " (" + IDCATEGORIE+", "+PERCSULTOT+") VALUES(?,?)";
			PreparedStatement ps = cn.prepareStatement(sql);
			if (getCatSpese() != null)
				ps.setInt(1, getCatSpese().getIdCategoria());
			ps.setDouble(2, getpercSulTot());			
			
			ps.executeUpdate();
			ok = true;
		} catch (Exception e) {
			ok = false;
			e.printStackTrace();
		} finally {
			try {
				cn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ConnectionPool.getSingleton().chiudiOggettiDb(cn);
		}
		return ok;
	}

	@Override
	public boolean delete(int id) throws Exception {
		boolean ok = false;
		String sql = "DELETE FROM "+NOME_TABELLA+" WHERE "+ID+" = "+id;
		Connection cn = DBUtil.getConnection();
		
		try {
			Statement st = cn.createStatement();
			st.executeUpdate(sql);
			ok=true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			ok=false;
		}
		try {
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBUtil.closeConnection();
		return ok;
	}

	@Override
	public boolean update(Object oggettoEntita) throws Exception {
		boolean ok = false;
		Connection cn = DBUtil.getConnection();
		
		Budget budget = (Budget) oggettoEntita;
		String sql = "UPDATE "+NOME_TABELLA+ " SET " +IDCATEGORIE+ " = " +getidCategorie()+", "+PERCSULTOT+" = "+getpercSulTot()+
				" WHERE "+ ID +" = "+getidBudget();
		try {
			Statement st = cn.createStatement();
			st.executeUpdate(sql);
			ok=true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			ok=false;
		}
		try {
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBUtil.closeConnection();
		return ok;
	}

	@Override
	public boolean deleteAll() throws Exception {
		boolean ok = false;
		String sql = "DELETE FROM "+NOME_TABELLA;
		Connection cn = DBUtil.getConnection();
		
		try {
			Statement st = cn.createStatement();
			st.executeUpdate(sql);
			ok=true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			ok=false;
		}
		try {
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBUtil.closeConnection();
		return ok;
	}

	@Override
	public AbstractOggettoEntita getEntitaPadre() {
		return budget;
	}

	@Override
	public int getidBudget() {
		return budget.getIdBudget();
	}

	@Override
	public void setidBudget(int idBudget) {
		budget.setIdBudget(idBudget);
		
	}

	@Override
	public int getidCategorie() {
		return budget.getIdCategorie();
	}

	@Override
	public void setidCategorie(int idCategorie) {
		budget.setIdCategorie(idCategorie);		
	}

	@Override
	public double getpercSulTot() {
		return budget.getPercSulTot();
	}

	@Override
	public void setpercSulTot(double percSulTot) {
		budget.setPercSulTot(percSulTot);		
	}

	@Override
	public CatSpese getCatSpese() {
		return budget.getCatSpese();
	}

	@Override
	public void setCatSpese(CatSpese catSpese) {
		budget.setCatSpese(catSpese);		
	}

	@Override
	public String getNome() {
		return budget.getNome();
	}
	@Override
	public void notifyObservers() {
		super.notifyObservers();
	}
	
	@Override
	protected synchronized void setChanged() {
		super.setChanged();
	}

	@Override
	public Object selectWhere(ArrayList<Clausola> clausole, String appendToQuery)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
