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
 * The persistent class for the UTENTI database table.
 * 
 */
@Entity
@Table(name = "UTENTI")
public class Utenti implements AbstractOggettoEntita, Serializable, IUtenti {

	@Id
	@Column(name = "idUtente", nullable = false)
	private int idUtente;

	@Column(name = "password", nullable = false, length = 2000000000)
	private String password;

	@Column(name = "username", nullable = false, length = 2000000000)
	private String username;

	@Column(name = "nome", nullable = false, length = 2000000000)
	private String nome;

	@Column(name = "cognome", nullable = false, length = 2000000000)
	private String cognome;

	// bi-directional many-to-one association to Entrate
	@OneToMany(mappedBy = "utenti")
	private Set<Entrate> entrates;

	// bi-directional many-to-one association to SingleSpesa
	@OneToMany(mappedBy = "utenti")
	private Set<SingleSpesa> singleSpesas;

	public Utenti() {
	}

	public int getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public Set<Entrate> getEntrates() {
		return entrates;
	}

	public void setEntrates(Set<Entrate> entrates) {
		this.entrates = entrates;
	}

	public Set<SingleSpesa> getSingleSpesas() {
		return singleSpesas;
	}

	public void setSingleSpesas(Set<SingleSpesa> singleSpesas) {
		this.singleSpesas = singleSpesas;
	}

	@Override
	public String getIdEntita() {
		return Integer.toString(getIdUtente());
	}

	@Override
	public void setIdEntita(String idEntita) {
		setIdUtente(Integer.parseInt(idEntita));
	}

}