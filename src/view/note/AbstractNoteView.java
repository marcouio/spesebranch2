package view.note;

import javax.swing.JDialog;

import domain.Utenti;
import domain.wrapper.WrapNote;

public class AbstractNoteView extends JDialog {

	private static final long serialVersionUID = 1L;

	public WrapNote           wrapNote         = null;

	public AbstractNoteView(WrapNote note) {
		this.wrapNote = note;
	}

	public void setNome(String nome) {
		wrapNote.setNome(nome);
	}

	public String getNome() {
		return wrapNote.getNome();
	}

	public void setDescrizione(String descrizione) {
		wrapNote.setDescrizione(descrizione);
	}

	public String getDescrizione() {
		return wrapNote.getDescrizione();
	}

	public void setData(String data) {
		wrapNote.setData(data);
	}

	public String getData() {
		return wrapNote.getData();
	}

	public void setDataIns(String dataIns) {
		wrapNote.setDataIns(dataIns);
	}

	public String getDataIns() {
		return wrapNote.getDataIns();
	}

	public Utenti getUtenti() {
		return wrapNote.getUtenti();
	}

	public void setUtenti(Utenti utente) {
		wrapNote.setUtenti(utente);
	}

}
