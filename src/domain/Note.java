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
 * The persistent class for the NOTE database table.
 * 
 */

@Entity
@Table(name = "NOTE")
public class Note implements AbstractOggettoEntita, Serializable, INote {

	@Column(name = "data")
	private String data;

	@Column(name = "dataIns")
	private String dataIns;

	@Column(name = "descrizione")
	private String descrizione;

	@Id
	@Column(name = "idNote")
	private int idNote;

	@Column(name = "idUtente")
	private int idUtente;

	@Column(name = "nome")
	private String nome;

	// bi-directional many-to-one association to Utenti
	@ManyToOne
	@JoinColumns(value = { @JoinColumn(name="idUtente") })
	private Utenti utenti;

	public Note() {
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



	public int getIdNote() {
		return idNote;
	}



	public void setIdNote(int idNote) {
		this.idNote = idNote;
	}



	public int getIdUtente() {
		return idUtente;
	}



	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
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



	public String toString() {
		return nome;
	}

	@Override
	public String getIdEntita() {
		return Integer.toString(getIdNote());
	}

	@Override
	public void setIdEntita(String idEntita) {
		setIdNote(Integer.parseInt(idEntita));
		
	}

}