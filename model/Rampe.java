package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Model for Rampe
 */
@Entity
public class Rampe {
	@Id
	private int rampenummer;
	private RampeStatus rampeStatus;
	private TransportMateriale transportMateriale;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Laesning> laesninger = new ArrayList<Laesning>();

	/**
	 * Må kun bruges af JPA
	 */
	public Rampe() {}
	
	/**
	 * Initialiserer en ny rampe, med rampenummer, rampeStatus (LEDIG) og
	 * transportMateriale. Krav 1: rampenummer skal være unikt i forhold til alle
	 * rampe instanser
	 */
	public Rampe(int rampenummer, TransportMateriale transportMateriale) {
		this.rampenummer = rampenummer;
		this.rampeStatus = RampeStatus.LEDIG;
		this.transportMateriale = transportMateriale;
	}

	/**
	 * Returnerer rampens nummer (ID)
	 */
	public int getRampenummer() {
		return rampenummer;
	}

	/**
	 * Registrerer et nyt nummer (ID) til rampen
	 */
	public void setRampenummer(int rampenummer) {
		this.rampenummer = rampenummer;
	}

	/**
	 * Returnerer rampens status
	 */
	public RampeStatus getRampeStatus() {
		return rampeStatus;
	}

	/**
	 * Registrerer rampens status
	 */
	public void setRampeStatus(RampeStatus rampeStatus) {
		this.rampeStatus = rampeStatus;
	}

	/**
	 * Returnerer rampens tildelte transportmateriale
	 */
	public TransportMateriale getTransportMateriale() {
		return transportMateriale;
	}

	/**
	 * Registrerer rampens tildelte transportmateriale
	 */
	public void setTransportMateriale(TransportMateriale transportMateriale) {
		this.transportMateriale = transportMateriale;
	}

	/**
	 * Returnerer en liste af de laesninger som er igang og står i kø til
	 * rampen
	 */
	public List<Laesning> getLaesninger() {
		return new ArrayList<Laesning>(laesninger);
	}
	
	/**
	 * Returnerer en liste af de laesninger som står i kø til rampen
	 */
	public List<Laesning> getIkkeAktiveLaesninger() {
		ArrayList<Laesning> l = new ArrayList<Laesning>(laesninger);
		if(laesninger.size() > 0){
			Laesning les = l.get(0);
			if(les.getBesoeg().getLastbil().getTrailerStatus() == TrailerStatus.VED_RAMPE)
				l.remove(les);
		}
		return l;
	}

	/**
	 * Registrerer en laesninger til rampen
	 */
	public void addLaesning(Laesning laesning) {
		if (!laesninger.contains(laesning)) {
			if(laesning.getBesoeg().harVaegtfejl()) {
				if (rampeStatus == RampeStatus.LEDIG)
					laesninger.add(0, laesning);
				else
					laesninger.add(1, laesning);
				beregnStartTidspunkter();
			} else {
				laesninger.add(laesning);
			}
			laesning.setRampe(this);
		}
	}

	/**
	 * Afregistrerer en laesninger til rampen
	 */
	public void removeLaesning(Laesning laesning) {
		if (laesninger.contains(laesning)) {
			laesninger.remove(laesning);
			laesning.setRampe(null);
		}
	}

	/**
	 * Omberegner alle forventet start i laesninger som ligger i kø til rampe 
	 * på baggrund af forventede slut på foregående laesning
	 */
	public void beregnStartTidspunkter(){
		if(laesninger.size() > 0){
			Laesning l = laesninger.get(0), temp;
			l.setForventetStart(DateUtil.dateToString(new Date()));
			for(int i = 1; i < laesninger.size(); i++){
				temp = laesninger.get(i);
				temp.setForventetStart(l.beregnForventetSlut());
				l = temp;
			}
		}
	}
	
	/**
	 * Returnerer en String med rampens nummer, rampe status, transport
	 * materiale samt antal læsninger (hvis der er nogen)
	 */
	public String toString() {
		int antalLaesninger = laesninger.size();
		String strLaesninger = "";
		if(antalLaesninger > 0)
			strLaesninger = " (" + antalLaesninger + ")";
		return "Rampe " + rampenummer + ". " + transportMateriale + ": " + rampeStatus + strLaesninger;
	}
	
	/**
	 * Returnerer hvornår rampen forventes ledig
	 */
	public String findNaesteLedigeTid() {
		if(laesninger.size() == 0)
			return DateUtil.dateToString(new Date());
		else
			return laesninger.get(laesninger.size() - 1).beregnForventetSlut();
	}
}