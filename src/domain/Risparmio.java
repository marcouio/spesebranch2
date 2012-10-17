package domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import command.javabeancommand.AbstractOggettoEntita;

/**
 * The persistent class for the RISPARMIO database table.
 * 
 */
@Entity
@Table(name = "RISPARMIO")
public class Risparmio implements AbstractOggettoEntita, Serializable, IRisparmio {

	@Id
	@Column(name = "idRisparmio", nullable = false)
	private int idRisparmio;

	@Column(name = "PerSulTotale", nullable = false)
	private double PerSulTotale;

	public Risparmio() {
	}

	@Override
	public int getIdRisparmio() {
		return this.idRisparmio;
	}

	@Override
	public void setIdRisparmio(final int idRisparmio) {
		this.idRisparmio = idRisparmio;
	}

	@Override
	public double getPerSulTotale() {
		return this.PerSulTotale;
	}

	@Override
	public void setPerSulTotale(final double PerSulTotale) {
		this.PerSulTotale = PerSulTotale;
	}

	@Override
	public String getNome() {
		return null;
	}

	@Override
	public String getIdEntita() {
		return Integer.toString(getIdRisparmio());
	}

	@Override
	public void setIdEntita(String idEntita) {
		setIdRisparmio(Integer.parseInt(idEntita));
	}

}