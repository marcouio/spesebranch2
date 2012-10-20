package domain;

import java.io.Serializable;
import java.util.Set;
import command.javabeancommand.AbstractOggettoEntita;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * The persistent class for the CATSPESE database table.
 * 
 */
@Entity
@Table(name = "cat_spese", schema = "DEFAULT")
public class CatSpese implements AbstractOggettoEntita,Serializable, ICatSpese {

	@Column(name = "descrizione", nullable = false, length = 2000000000)
	private String descrizione;

	@Id
	@Column(name = "idCategoria", nullable = false)
	private int idCategoria;

	@Column(name="idGruppo", nullable=false)
	private int idGruppo;

	@Column(name = "importanza", nullable = false, length = 2000000000)
	private String importanza;

	@Column(name = "nome", nullable = false, length = 2000000000)
	private String nome;

	// bi-directional one-to-one association to Budget
	@OneToOne(mappedBy = "catSpese")
	private Budget budget;

	// bi-directional many-to-one association to Gruppi
	@ManyToOne
	@JoinColumns(value = { @JoinColumn(name="idGruppo") })
	private Gruppi gruppi;

	// bi-directional many-to-one association to SingleSpesa
	@OneToMany(mappedBy = "catSpese")
	private Set<SingleSpesa> singleSpesas;

	public CatSpese() {
		
	}
	
	public String getDescrizione() {
		return descrizione;
	}


	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}


	public int getIdCategoria() {
		return idCategoria;
	}


	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}


	public String getImportanza() {
		return importanza;
	}


	public void setImportanza(String importanza) {
		this.importanza = importanza;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public Budget getBudget() {
		return budget;
	}


	public void setBudget(Budget budget) {
		this.budget = budget;
	}


	public Gruppi getGruppi() {
		return gruppi;
	}


	public void setGruppi(Gruppi gruppi) {
		this.gruppi = gruppi;
	}


	public Set<SingleSpesa> getSingleSpesas() {
		return singleSpesas;
	}


	public void setSingleSpesas(Set<SingleSpesa> singleSpesas) {
		this.singleSpesas = singleSpesas;
	}


	@Override
	public String toString() {
		return nome;
	}

	@Override
	public String getIdEntita() {
		return Integer.toString(getIdCategoria());
	}

	@Override
	public void setIdEntita(String idEntita) {
		setIdCategoria(Integer.parseInt(idEntita));
	}

	public int getIdGruppo() {
		return idGruppo;
	}

	public void setIdGruppo(int idGruppo) {
		this.idGruppo = idGruppo;
	}
}