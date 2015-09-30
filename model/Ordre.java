package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Model for Ordre
 */
@Entity
public class Ordre {
	@Id
	private int ordrenummer;
	private TransportMateriale transportMateriale;
	private double bruttovaegt;
	private double margenVedVejning;
	private String dato;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Delordre> delordrer = new ArrayList<Delordre>();

	/**
	 * Må kun bruges af JPA
	 */
	public Ordre() {}
	
	/**
	 * Initialiserer en ny ordre, med ordrenummer, transportMateriale,
	 * bruttovaegt, margenVedVejning og dato. Krav 1: ordrenummer skal være unikt i
	 * forhold til alle ordre instanser. Krav 2: bruttovaegt < 0. Krav 3:
	 * transportMateriale må ikke være null
	 */
	public Ordre(int ordrenummer, TransportMateriale transportMateriale,
			double bruttovaegt, double margenVedVejning, String dato) {
		this.ordrenummer = ordrenummer;
		this.transportMateriale = transportMateriale;
		this.bruttovaegt = bruttovaegt;
		this.margenVedVejning = margenVedVejning;
		this.dato = dato;
	}

	/**
	 * Returnerer ordrens nummer (ID)
	 */
	public int getOrdrenummer() {
		return ordrenummer;
	}

	/**
	 * Registrerer et nyt nummer (ID) til ordren. Krav 1: skal have sikring udefra
	 */
	public void setOrdrenummer(int ordrenummer) {
		this.ordrenummer = ordrenummer;
	}

	/**
	 * Returnerer ordrens tildelte transportmateriale
	 */
	public TransportMateriale getTransportMateriale() {
		return transportMateriale;
	}

	/**
	 * Registrerer ordrens tildelte transportmateriale
	 */
	public void setTransportMateriale(TransportMateriale transportMateriale) {
		this.transportMateriale = transportMateriale;
	}

	/**
	 * Returnerer bruttovaegten for ordren
	 */
	public double getBruttovaegt() {
		return bruttovaegt;
	}

	/**
	 * Registrerer bruttovaegten for ordren
	 */
	public void setBruttovaegt(double bruttovaegt) {
		this.bruttovaegt = bruttovaegt;
	}

	/**
	 * Returnerer den procentvise tilladte margen ved vejning som decimal (fx
	 * 4.5% = 0.045)
	 */
	public double getMargenVedVejning() {
		return margenVedVejning;
	}

	/**
	 * Registrerer den procentvise tilladte margen ved vejning som decimal (fx
	 * 4.5% = 0.045)
	 */
	public void setMargenVedVejning(double margenVedVejning) {
		this.margenVedVejning = margenVedVejning;
	}

	/**
	 * Returnerer datoen for ordren
	 */
	public String getDato() {
		return dato;
	}

	/**
	 * Registrerer datoen for ordren. Syntaxkrav: yyyyMMddHHmmss (efterlad HHmmss
	 * som "000000")
	 */
	public void setDato(String dato) {
		this.dato = dato;
	}

	/**
	 * Returnerer en liste af de delordrer som hører til ordren
	 */
	public List<Delordre> getDelordre() {
		return new ArrayList<Delordre>(delordrer);
	}

	/**
	 * Opretter en Delordre, og registrerer den til listen af delordrer.
	 * Returnerer delordren som er blevet oprettet
	 */
	public Delordre createDelordre(int delordrenummer, int laessetid,
			double vaegt, String laessedato, Lastbil lastbil) {
		Delordre delordre = new Delordre(delordrenummer, laessetid, vaegt,
				laessedato, this, lastbil);
		lastbil.setDelordre(delordre);
		delordrer.add(delordre);
		return delordre;
	}
	
	/**
	 * Afregistrerer og sletter en delordre fra ordren
	 */
	public void deleteDelordre(Delordre delordre) {
		if (delordrer.contains(delordre)) {
			delordrer.remove(delordre);
			delordre.setOrdre(null);
			delordre = null;
		}
	}

	/**
	 * Registrerer en delordre til ordren
	 */
	public void addDelordre(Delordre delordre) {
		if (!delordrer.contains(delordre)) {
			delordrer.add(delordre);
			delordre.setOrdre(this);
		}
	}

	/**
	 * Afregistrerer en delordre til ordren
	 */
	public void removeDelordre(Delordre delordre) {
		if (delordrer.contains(delordre)) {
			delordrer.remove(delordre);
			delordre.setOrdre(null);
		}
	}

	/**
	 * Returnerer en String med ordrens nummer og dato
	 */
	public String toString() {
		return "ordrenummer: " + ordrenummer + ", dato: "
				+ DateUtil.formatDate(dato);
	}
}