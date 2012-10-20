package domain;

import java.io.Serializable;
import command.javabeancommand.AbstractOggettoEntita;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * The persistent class for the BUDGET database table.
 * 
 */

@Entity
@Table(name = "budget")
public class Budget implements AbstractOggettoEntita, Serializable {

	@Id
	@Column(name = "idBudget", nullable = false)
	private int idBudget;

	@Column(name = "idCategorie", nullable = false)
	private int idCategorie;

	@Column(name = "percSulTot", nullable = false)
	private double percSulTot;

	// bi-directional one-to-one association to CatSpese
	@OneToOne
	@JoinColumns(value = { @JoinColumn(name="idCategorie") })
	private CatSpese catSpese;

	public Budget() {
	}

	public int getIdBudget() {
		return idBudget;
	}



	public void setIdBudget(int idBudget) {
		this.idBudget = idBudget;
	}



	public int getIdCategorie() {
		return idCategorie;
	}



	public void setIdCategorie(int idCategorie) {
		this.idCategorie = idCategorie;
	}



	public double getPercSulTot() {
		return percSulTot;
	}



	public void setPercSulTot(double percSulTot) {
		this.percSulTot = percSulTot;
	}



	public CatSpese getCatSpese() {
		return catSpese;
	}

	public void setCatSpese(CatSpese catSpese) {
		this.catSpese = catSpese;
	}

	@Override
	public String getIdEntita() {
		return Integer.toString(getIdBudget());
	}

	@Override
	public void setIdEntita(String idEntita) {
		setIdBudget(Integer.parseInt(idEntita));
	}

	@Override
	public String getNome() {
		return null;
	}

}