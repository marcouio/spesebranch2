package domain;

public interface ISingleSpesa {

	public String getData();

	public void setData(String data);

	public String getDescrizione();

	public void setDescrizione(String descrizione);

	public int getIdSpesa();

	public void setidSpesa(int idSpesa);

	public double getInEuro();

	public void setInEuro(double inEuro);

	public String getNome();

	public void setNome(String nome);

	public String getDataIns();

	public void setDataIns(String dataIns);

	public CatSpese getCatSpese();

	public void setCatSpese(CatSpese catSpese);

	public Utenti getUtenti();

	public void setUtenti(Utenti utenti);

}
