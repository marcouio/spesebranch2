package domain;

public interface ILookandfeel {

	public int getIdLook();

	public void setIdLook(int idLook);

	public String getNome();

	public void setNome(String nome);

	public int getUsato();

	public void setUsato(int usato);
	
	public String getValore();
	
	public void setValore(String valore);

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString();
}
