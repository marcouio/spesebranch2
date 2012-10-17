package domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import command.javabeancommand.AbstractOggettoEntita;

/**
 * The persistent class for the LOOKANDFEEL database table.
 * 
 */
@Entity
@Table(name = "LOOKANDFEEL")
public class Lookandfeel implements AbstractOggettoEntita, Serializable, ILookandfeel {

	@Id
	@Column(name = "idLook", nullable = false)
	private int idLook;

	@Column(name = "nome", nullable = false)
	private String nome;

	@Column(name = "usato", nullable = false)
	private int usato;

	@Column(name = "valore", nullable = false)
	private String valore;

	public Lookandfeel() {
	}

	public int getIdLook() {
		return idLook;
	}

	public void setIdLook(int idLook) {
		this.idLook = idLook;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getUsato() {
		return usato;
	}

	public void setUsato(int usato) {
		this.usato = usato;
	}



	public String getValore() {
		return valore;
	}



	public void setValore(String valore) {
		this.valore = valore;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return nome;
	}

	@Override
	public String getIdEntita() {
		return Integer.toString(getIdLook());
	}

	@Override
	public void setIdEntita(String idEntita) {
		setIdLook(Integer.parseInt(idEntita));
	}

}