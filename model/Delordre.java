package model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * Model for Delordre
 */
@Entity
public class Delordre {
	@Id
	private int delordrenummer;
	private int laessetid;
	private double vaegt;
	private String laessedato;
	@ManyToOne(cascade = CascadeType.ALL)
	private Ordre ordre;
	@OneToOne(cascade = CascadeType.ALL)
	private Laesning laesning;
	@ManyToOne(cascade = CascadeType.ALL)
	private Lastbil lastbil;

	/**
	 * Må kun bruges af JPA
	 */
	public Delordre() {}
	
	/**
	 * Initialiserer en ny delordre, med delordrenummer, laessetid, vaegt,
	 * laessedato og link til ordre. Krav 1: delordrenummer skal være unikt i forhold til alle delordreinstanser. 
	 * Krav 2: ordre må ikke være null
	 */
	public Delordre(int delordrenummer, int laessetid, double vaegt,
			String laessedato, Ordre ordre, Lastbil lastbil) {
		this.delordrenummer = delordrenummer;
		this.laessetid = laessetid;
		this.vaegt = vaegt;
		this.laessedato = laessedato;
		this.ordre = ordre;
		setLastbil(lastbil);
	}

	/**
	 * Returnerer delordrens nummer (ID) krav: skal have sikring udefra
	 */
	public int getDelordrenummer() {
		return delordrenummer;
	}

	/**
	 * Registrerer et nyt nummer (ID) til delordren
	 */
	public void setDelordrenummer(int delordrenummer) {
		this.delordrenummer = delordrenummer;
	}

	/**
	 * Returnerer den tid der er afsat til laesning i minutter
	 */
	public int getLaessetid() {
		return laessetid;
	}

	/**
	 * Registrerer den tid der er afsat til laesning i minutter
	 */
	public void setLaessetid(int laessetid) {
		this.laessetid = laessetid;
	}

	/**
	 * Returnerer delordrens lastbil
	 */
	public Lastbil getLastbil() {
		return lastbil;
	}

	/**
	 * Registrerer delordrens lastbil
	 */
	public void setLastbil(Lastbil lastbil) {
		if (this.lastbil != lastbil)
			this.lastbil = lastbil;
	}

	/**
	 * Returnerer vægten for delordren
	 */
	public double getVaegt() {
		return vaegt;
	}

	/**
	 * Registrerer vægten for delordren
	 */
	public void setVaegt(double vaegt) {
		this.vaegt = vaegt;
	}

	/**
	 * Returnerer datoen for laesning
	 */
	public String getLaessedato() {
		return laessedato;
	}

	/**
	 * Registrerer datoen for laesning Syntaxkrav: yyyyMMddHHmmss
	 */
	public void setLaessedato(String laessedato) {
		this.laessedato = laessedato;
	}

	/**
	 * Returnerer den tilknyttede instans af ordre
	 */
	public Ordre getOrdre() {
		return ordre;
	}

	/**
	 * Registrerer en instans af ordre
	 */
	public void setOrdre(Ordre ordre) {
		if (this.ordre != ordre) {
			if (this.ordre != null)
				this.ordre.removeDelordre(this);
			this.ordre = ordre;
			if (this.ordre != null)
				ordre.addDelordre(this);
		}
	}

	/**
	 * Returnerer den tilknyttede instans af laesning
	 */
	public Laesning getLaesning() {
		return laesning;
	}

	/**
	 * Registrerer en instans af laesning
	 */
	public void setLaesning(Laesning laesning) {
		if (this.laesning != laesning) {
			if (this.laesning != null) {
				this.laesning.unsetDelordre();
			}
			this.laesning = laesning;
			if (laesning != null) {
				laesning.setDelordre(this);
			}
		}
	}

	/**
	 * Returnerer en String med delordrens nummer og dato
	 */
	public String toString() {
		return "delordrenummer: " + delordrenummer + ", dato: "
				+ DateUtil.formatDate(laessedato);
	}

	/**
	 * Afregistrerer laesning
	 */
	public void unsetLaesning() {
		laesning = null;
	}
}