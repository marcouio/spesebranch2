package domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import command.javabeancommand.AbstractOggettoEntita;

/**
 * The persistent class for the GRUPPI database table.
 * 
 */
@Entity
@Table(name = "GRUPPI")
public class Gruppi implements AbstractOggettoEntita, Serializable, IGruppi {

	@Column(name = "descrizione", nullable = false, length = 2000000000)
	private String descrizione;

	@Id
	@Column(name = "idGruppo", nullable = false)
	private int idGruppo;

	@Column(name = "nome", nullable = false, length = 2000000000)
	private String nome;

	// bi-directional many-to-one association to CatSpese
	@OneToMany(mappedBy = "gruppi")
	private Set<CatSpese> catSpeses;

	public Gruppi() {
	}

	public String getDescrizione() {
		return descrizione;
	}



	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}



	public int getIdGruppo() {
		return idGruppo;
	}



	public void setIdGruppo(int idGruppo) {
		this.idGruppo = idGruppo;
	}



	public String getNome() {
		return nome;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}



	public Set<CatSpese> getCatSpeses() {
		return catSpeses;
	}



	public void setCatSpeses(Set<CatSpese> catSpeses) {
		this.catSpeses = catSpeses;
	}



	@Override
	public String toString() {
		return nome;
	}

	@Override
	public String getIdEntita() {
		return Integer.toString(getIdGruppo());
	}

	@Override
	public void setIdEntita(String idEntita) {
		setIdGruppo(Integer.parseInt(idEntita));
		
	}
}