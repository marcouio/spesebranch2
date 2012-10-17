package domain;

import java.util.Set;


public interface IUtenti {

	public int getIdUtente();

	public void setIdUtente(int idUtente);

	public String getPassword();

	public void setPassword(String password);

	public String getUsername();

	public void setUsername(String username);

	public String getNome();

	public void setNome(String nome);

	public String getCognome();

	public void setCognome(String cognome);

	public Set<Entrate> getEntrates();

	public void setEntrates(Set<Entrate> entrates);

	public Set<SingleSpesa> getSingleSpesas();

	public void setSingleSpesas(Set<SingleSpesa> singleSpesas);
}
