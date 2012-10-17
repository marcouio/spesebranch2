package domain;

import java.util.Set;

public interface ICatSpese {
	
	public String getDescrizione();


	public void setDescrizione(String descrizione);


	public int getIdCategoria();


	public void setIdCategoria(int idCategoria);


	public String getImportanza();


	public void setImportanza(String importanza);


	public String getNome();


	public void setNome(String nome);


	public Budget getBudget();


	public void setBudget(Budget budget);


	public Gruppi getGruppi();


	public void setGruppi(Gruppi gruppi);


	public Set<SingleSpesa> getSingleSpesas();


	public void setSingleSpesas(Set<SingleSpesa> singleSpesas);

}
