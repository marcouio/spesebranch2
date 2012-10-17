package domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import command.javabeancommand.AbstractOggettoEntita;

/**
 * The persistent class for the ENTRATE database table.
 * 
 */
@Entity
@Table(name = "ENTRATE")
public class Entrate implements AbstractOggettoEntita, Serializable, IEntrate {

	@Column(name = "data", nullable = false, length = 2000000000)
	private String data;

	@Column(name = "dataIns", nullable = false, length = 2000000000)
	private String dataIns;

	@Column(name = "descrizione", nullable = false, length = 2000000000)
	private String descrizione;

	@Column(name = "Fisse_o_Var", nullable = false, length = 2000000000)
	private String FisseoVar;

	@Id
	@Column(name = "idEntrate", nullable = false)
	private int idEntrate;

	// @Column(name="idUtente", nullable=false)
	// private int idUtente;

	@Column(name = "inEuro", nullable = false)
	private double inEuro;

	@Column(name = "nome", nullable = false, length = 2000000000)
	private String nome;

	// bi-directional many-to-one association to Utenti
	@ManyToOne
	@JoinColumns({})
	private Utenti utenti;

	public Entrate() {
	}
	
	public String getData() {
		return data;
	}



	public void setData(String data) {
		this.data = data;
	}



	public String getDataIns() {
		return dataIns;
	}



	public void setDataIns(String dataIns) {
		this.dataIns = dataIns;
	}



	public String getDescrizione() {
		return descrizione;
	}



	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}



	public String getFisseoVar() {
		return FisseoVar;
	}



	public void setFisseoVar(String fisseoVar) {
		FisseoVar = fisseoVar;
	}



	public int getIdEntrate() {
		return idEntrate;
	}



	public void setIdEntrate(int idEntrate) {
		this.idEntrate = idEntrate;
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



	public Utenti getUtenti() {
		return utenti;
	}



	public void setUtenti(Utenti utenti) {
		this.utenti = utenti;
	}


	@Override
	public String getIdEntita() {
		return Integer.toString(getIdEntrate());
	}

	@Override
	public void setIdEntita(String idEntita) {
		setIdEntrate(Integer.parseInt(idEntita));
		
	}

}