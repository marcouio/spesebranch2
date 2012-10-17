package domain;

import java.util.Set;

public interface IGruppi {
	
	public String getDescrizione();

	public void setDescrizione(String descrizione);

	public int getIdGruppo();

	public void setIdGruppo(int idGruppo);

	public String getNome();

	public void setNome(String nome);

	public Set<CatSpese> getCatSpeses();

	public void setCatSpeses(Set<CatSpese> catSpeses);


}
