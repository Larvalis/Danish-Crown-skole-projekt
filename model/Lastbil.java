package model;

import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * Model for Lastbil
 */
@Entity
public class Lastbil {
	@Id
	@GeneratedValue
	private int id;
	private int lastbilnummer;
	private String chauffoer;
	private String mobilnummer;
	private TrailerStatus trailerStatus;
	private double MaxLastVaegt;
	private double trailerVaegt;
	@OneToOne(cascade = CascadeType.ALL)
	private Delordre aktuelDelordre;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Besoeg> besoeg = new TreeSet<Besoeg>();

	/**
	 * Må kun bruges af JPA
	 */
	public Lastbil() {}
	
	/**
	 * Initialiserer en ny lastbil, med lastbilnummer, chauffoer og link til
	 * rampe, lastbil og aktuelDelordre. Krav 1: rampe, lastbil og aktuelDelordre må ikke
	 * være null. Krav 2: lastbilnummer skal være unikt i forhold til alle
	 * lastbil instanser
	 */
	public Lastbil(int lastbilnummer, String chauffoer, String mobilnummer,
			double maxLastVaegt, double trailerVaegt) {
		this.lastbilnummer = lastbilnummer;
		this.chauffoer = chauffoer;
		this.mobilnummer = mobilnummer;
		this.trailerStatus = TrailerStatus.IKKE_VED_DC;
		MaxLastVaegt = maxLastVaegt;
		this.trailerVaegt = trailerVaegt;
	}
	
	/**
	 * Returnerer lastbilens Delordre
	 */
	public Delordre getDelordre() {
		return aktuelDelordre;
	}

	/**
	 * Registrerer lastbilens Delordre
	 */
	public void setDelordre(Delordre aktuelDelordre) {
		if (this.aktuelDelordre != aktuelDelordre)
			this.aktuelDelordre = aktuelDelordre;	
	}

	/**
	 * Returnerer lastbilens nummer (ID) krav: skal have sikring udefra
	 */
	public int getLastbilnummer() {
		return lastbilnummer;
	}

	/**
	 * Registrerer et nyt nummer (ID) til lastbilen
	 */
	public void setLastbilnummer(int lastbilnummer) {
		this.lastbilnummer = lastbilnummer;
	}

	/**
	 * Returnerer chauffoerens navn
	 */
	public String getChauffoer() {
		return chauffoer;
	}

	/**
	 * Registrerer chauffoerens navn
	 */
	public void setChauffoer(String chauffoer) {
		this.chauffoer = chauffoer;
	}

	/**
	 * Returnerer mobilnummeret på lastbilen
	 */
	public String getMobilnummer() {
		return mobilnummer;
	}

	/**
	 * Registrerer mobilnummeret på lastbilen
	 */
	public void setMobilnummer(String mobilnummer) {
		this.mobilnummer = mobilnummer;
	}

	/**
	 * Returnerer den maximale vaegt som traileren kan bære
	 */
	public double getMaxLastVaegt() {
		return MaxLastVaegt;
	}

	/**
	 * Registrerer den maximale vaegt som traileren kan bære
	 */
	public void setMaxLastVaegt(double maxLastVaegt) {
		MaxLastVaegt = maxLastVaegt;
	}

	/**
	 * Returnerer en liste af de besoeg som lastbilen har foretaget
	 */
	public TreeSet<Besoeg> getBesoeg() {
		return new TreeSet<Besoeg>(besoeg);
	}

	/**
	 * Opretter, registrerer og returnere et besoeg til lastbilen
	 */
	public Besoeg createBesoeg(int hviletid) {
		Besoeg besoeg = new Besoeg(this, hviletid);
		if (!this.besoeg.contains(besoeg)) {
			this.besoeg.add(besoeg);
		}
		return besoeg;
	}

	/**
	 * Afregistrerer et besoeg til lastbilen
	 */
	public void removeBesoeg(Besoeg besoeg) {
		if (this.besoeg.contains(besoeg))
			this.besoeg.remove(besoeg);
	}

	/**
	 * Returnerer trailerens vaegt
	 */
	public double getTrailerVaegt() {
		return trailerVaegt;
	}

	/**
	 * Registrerer trailerens vaegt
	 */
	public void setTrailerVaegt(double trailerVaegt) {
		this.trailerVaegt = trailerVaegt;
	}

	/**
	 * Returnerer trailerens status
	 */
	public TrailerStatus getTrailerStatus() {
		return trailerStatus;
	}

	/**
	 * Registrerer trailerens status
	 */
	public void setTrailerStatus(TrailerStatus trailerStatus) {
		if(trailerStatus == TrailerStatus.KLAR_TIL_LAESNING)
			aktuelDelordre.getLaesning().setAktuelStart(null);
		this.trailerStatus = trailerStatus;
	}

	/**
	 * Returnerer en String med lastbils nr og chauffoers navn
	 */
	public String toString() {
		return "lastbilnummer: " + lastbilnummer + " (" + chauffoer + ")";
	}
}