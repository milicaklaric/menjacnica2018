package menjacnica.gui.kontroler;

import java.awt.Component;
import java.awt.EventQueue;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import menjacnica.Menjacnica;
import menjacnica.MenjacnicaInterface;
import menjacnica.Valuta;
import menjacnica.gui.DodajKursGUI;
import menjacnica.gui.IzvrsiZamenuGUI;
import menjacnica.gui.MenjacnicaGUI;
import menjacnica.gui.ObrisiKursGUI;
import menjacnica.gui.models.MenjacnicaTableModel;

public class GUIKontroler {

	public static MenjacnicaInterface menjacnica = new Menjacnica();
	
	public static MenjacnicaGUI mgp;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIKontroler.mgp = new MenjacnicaGUI();
					GUIKontroler.mgp.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void sacuvajUFajl() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showSaveDialog(null);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();

				menjacnica.sacuvajUFajl(file.getAbsolutePath());
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(mgp, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void ucitajIzFajla() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(null);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				menjacnica.ucitajIzFajla(file.getAbsolutePath());
				mgp.prikaziSveValute(menjacnica.vratiKursnuListu());
			}	
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(mgp, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	public static void prikaziDodajKursGUI() {
		DodajKursGUI prozor = new DodajKursGUI();
		prozor.setLocationRelativeTo(mgp);
		prozor.setVisible(true);
	}

	public static void prikaziObrisiKursGUI() {
		
		if (mgp.table.getSelectedRow() != -1) {
			MenjacnicaTableModel model = (MenjacnicaTableModel)(mgp.table.getModel());
			ObrisiKursGUI prozor = new ObrisiKursGUI(model.vratiValutu(mgp.table.getSelectedRow()));
			prozor.setLocationRelativeTo(mgp);
			prozor.setVisible(true);
		}
	}
	
	public static void prikaziIzvrsiZamenuGUI() {
		if (mgp.table.getSelectedRow() != -1) {
			MenjacnicaTableModel model = (MenjacnicaTableModel)(mgp.table.getModel());
			IzvrsiZamenuGUI prozor = new IzvrsiZamenuGUI(model.vratiValutu(mgp.table.getSelectedRow()));
			prozor.setLocationRelativeTo(mgp);
			prozor.setVisible(true);
		}
	}
	
	public static void unesiKurs(String naziv, String skraceniNaziv, int sifra, 
			double prodajni, double kupovni, double srednji) {
		try {
			Valuta valuta = new Valuta();

			// Punjenje podataka o valuti
			valuta.setNaziv(naziv);
			valuta.setSkraceniNaziv(skraceniNaziv);
			valuta.setSifra(sifra);
			valuta.setProdajni(prodajni);
			valuta.setKupovni(kupovni);
			valuta.setSrednji(srednji);
			
			// Dodavanje valute u kursnu listu
			menjacnica.dodajValutu(valuta);

			// Osvezavanje glavnog prozora
			mgp.prikaziSveValute(menjacnica.vratiKursnuListu());
			
			//Zatvaranje DodajValutuGUI prozora
			
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(),
						"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	public static void obrisiValutu(Valuta valuta) {
		try{
			menjacnica.obrisiValutu(valuta);
			
			mgp.prikaziSveValute(menjacnica.vratiKursnuListu());
			
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	public static String izvrsiZamenu(Valuta valuta, boolean b, double iznos){
		try{
			double konacniIznos = 
					menjacnica.izvrsiTransakciju(valuta,b ,iznos);
		
			return (""+konacniIznos);
		} catch (Exception e1) {
		JOptionPane.showMessageDialog(null, e1.getMessage(),
				"Greska", JOptionPane.ERROR_MESSAGE);
		}
		
		return null;
	}
	
}
