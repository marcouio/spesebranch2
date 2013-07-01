package business.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import view.impostazioni.Impostazioni;
import business.ControlloreSpese;

import command.javabeancommand.AbstractOggettoEntita;

import domain.Note;
import domain.Utenti;
import domain.wrapper.WrapNote;

public class CacheNote extends AbstractCacheBase {

	private static CacheNote	singleton;

	private CacheNote() {
		cache = new HashMap<String, AbstractOggettoEntita>();
		caricata = false;
	}

	public static CacheNote getSingleton() {
		synchronized (CacheNote.class) {
			if (singleton == null) {
				singleton = new CacheNote();
			}
		} // if
		return singleton;
	}

	WrapNote	noteDAO	= new WrapNote();

	public Note getNote(final String id) {
		Note note = (Note) cache.get(id);
		if (note == null) {
			note = caricaNota(id);
			if (note != null) {
				cache.put(id, note);
			}
		}
		return (Note) cache.get(id);
	}

	private Note caricaNota(final String id) {
		return (Note) new WrapNote().selectById(Integer.parseInt(id));
	}

	public Map<String, AbstractOggettoEntita> chargeAllNote() {
		final ArrayList<Object> note = noteDAO.selectAll();
		if (note != null && note.size() > 0) {
			for (int i = 0; i < note.size(); i++) {
				final Note nota = (Note) note.get(i);
				final int id = nota.getIdNote();
				if (cache.get(Integer.toString(id)) == null) {
					cache.put(Integer.toString(id), nota);
				}
			}
		}
		caricata = true;
		return cache;
	}

	public Map<String, AbstractOggettoEntita> getAllNote() {
		if (caricata) {
			return cache;
		}
		else {
			return chargeAllNote();
		}
	}

	public ArrayList<Note> getAllNoteForUtente() {
		final ArrayList<Note> listaNote = new ArrayList<Note>();
		final Map<String, AbstractOggettoEntita> mappa = getAllNote();
		final Utenti utente = (Utenti) ControlloreSpese.getSingleton().getUtenteLogin();
		if (mappa != null && utente != null) {
			final Iterator<String> chiavi = mappa.keySet().iterator();

			while (chiavi.hasNext()) {
				final Note nota = (Note) mappa.get(chiavi.next());
				if (nota != null && (nota.getUtenti() != null || nota.getUtenti().getNome().equals("guest"))) {
					if (nota.getUtenti().getIdUtente() == utente.getIdUtente()) {
						listaNote.add(nota);
					}
				}
			}
		}
		return listaNote;
	}

	public ArrayList<Note> getAllNoteForUtenteEAnno() {
		final ArrayList<Note> listaNote = new ArrayList<Note>();
		final Map<String, AbstractOggettoEntita> mappa = getAllNote();
		final Utenti utente = (Utenti) ControlloreSpese.getSingleton().getUtenteLogin();
		final String annoDaText = Impostazioni.getSingleton().getAnnotextField().getText();

		if (mappa != null && utente != null) {
			final Iterator<String> chiavi = mappa.keySet().iterator();

			while (chiavi.hasNext()) {
				final Note nota = (Note) mappa.get(chiavi.next());
				if (nota != null && (nota.getUtenti() != null || nota.getUtenti().getNome().equals("guest"))) {
					final String annoNota = nota.getData().substring(0, 4);
					if (nota.getUtenti().getIdUtente() == utente.getIdUtente() && annoNota.equals(annoDaText)) {
						listaNote.add(nota);
					}
				}
			}
		}
		return listaNote;
	}

}
