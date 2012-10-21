package domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import command.javabeancommand.AbstractOggettoEntita;

/**
 * The persistent class for the SINGLESPESA database table.
 * 
 */
@Entity
@Table(name = "SINGLE_SPESA")
public class SingleSpesa implements AbstractOggettoEntita, Serializable, ISingleSpesa {

	@Column(name = "Data", nullable = false, length = 2000000000)
	private String Data;

	@Column(name = "descrizione", nullable = false, length = 2000000000)
	private String descrizione;

	@Id
	@Column(name = "idSpesa", nullable = false)
	private int idSpesa;
	
	@Column(name = "idCategorie", nullable = false)
	private int idCategorie;

	@Column(name = "inEuro", nullable = false)
	private double inEuro;

	@Column(name = "nome", nullable = false, length = 2000000000)
	private String nome;

	@Column(name = "dataIns", nullable = false, length = 2000000000)
	private String dataIns;

	// bi-directional many-to-one association to CatSpese
	@ManyToOne
	@JoinColumns(value = { @JoinColumn(name="idCategorie") })
	private CatSpese catSpese;

	// bi-directional many-to-one association to Utenti
	@ManyToOne
	@JoinColumns(value = { @JoinColumn(name="idUtente") })
	private Utenti utenti;

	public SingleSpesa() {
	}

	public String getData() {
		return Data;
	}



	public void setData(String data) {
		Data = data;
	}



	public String getDescrizione() {
		return descrizione;
	}



	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}



	public int getIdSpesa() {
		return idSpesa;
	}



	public void setIdSpesa(int idSpesa) {
		this.idSpesa = idSpesa;
	}



	public double getInEuro() {
		return inEuro;
	}



	public void setInEuro(double inEuro) {
		this.inEuro = inEuro;
	}



	public String getNome() {
		return nome;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}



	public String getDataIns() {
		return dataIns;
	}



	public void setDataIns(String dataIns) {
		this.dataIns = dataIns;
	}



	public CatSpese getCatSpese() {
		return catSpese;
	}



	public void setCatSpese(CatSpese catSpese) {
		this.catSpese = catSpese;
	}



	public Utenti getUtenti() {
		return utenti;
	}



	public void setUtenti(Utenti utenti) {
		this.utenti = utenti;
	}

	@Override
	public String toString() {
		return nome;
	}

	@Override
	public String getIdEntita() {
		return Integer.toString(getIdSpesa());
	}

	@Override
	public void setIdEntita(String idEntita) {
		setIdSpesa(Integer.parseInt(idEntita));
	}

	public int getIdCategorie() {
		return idCategorie;
	}

	public void setIdCategorie(int idCategorie) {
		this.idCategorie = idCategorie;
	}

}