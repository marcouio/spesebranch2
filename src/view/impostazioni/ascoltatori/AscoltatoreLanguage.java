package view.impostazioni.ascoltatori;

import java.awt.event.ActionEvent;

import javax.swing.JComboBox;

import messaggi.I18NManager;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import xml.CoreXMLManager;
import xml.UtilXml;

import business.ascoltatori.AscoltatoreAggiornatoreTutto;

public class AscoltatoreLanguage extends AscoltatoreAggiornatoreTutto {

	private JComboBox<String> comboLanguage;

	public AscoltatoreLanguage(final JComboBox<String> comboLanguage) {
		this.comboLanguage = comboLanguage;
	}

	@Override
	public void actionPerformedOverride(ActionEvent e) {
		Document doc = CoreXMLManager.getSingleton().getDoc();
		Node nodoLang = UtilXml.getNodo("lang", doc); 
		Element elementoLang = UtilXml.getElement(nodoLang);
		if (elementoLang != null) {
			elementoLang.setAttribute("locale", (String) comboLanguage.getSelectedItem());
			UtilXml.writeXmlFile(doc, CoreXMLManager.XMLCOREPATH);
			I18NManager.getSingleton().caricaMessaggi((String) comboLanguage.getSelectedItem(), null);
		}
	}

}
