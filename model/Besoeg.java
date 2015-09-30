package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;

/**
 * Model for Besoeg
 */
@Entity
public class Besoeg implements Comparable<Besoeg> {
	@Id
	@GeneratedValue
	private int id;
	private String ankomst = null;
	private String afgang = null;
	@ElementCollection(targetClass = Fejl.class)
	@JoinTable(name = "BESOEG_FEJL", joinColumns = @JoinColumn(name = "BESOEG_ID"))
	@Column(name = "FEJL", nullable = false)
	private List<Fejl> fejl = new ArrayList<Fejl>();
	@OneToOne(cascade = CascadeType.ALL)
	private Laesning laesning;
	@OneToOne(cascade = CascadeType.ALL)
	private Lastbil lastbil;
	private int hviletid;

	/**
	 * Må kun bruges af JPA
	 */
	public Besoeg() {}
	
	/**
	 * Initialisere et nyt besoeg, med en ankomst tid samt forbindelse til
	 * laesning og lastbil Krav: lastbil og dennes laesning må ikke være null
	 */
	public Besoeg(Lastbil lastbil, int hviletid) {
		this.ankomst = DateUtil.dateToString(new Date());
		setLaesning(lastbil.getDelordre().getLaesning());
		this.lastbil = lastbil;
		this.hviletid = hviletid;
	}

	/**
	 * Returnerer tiden for ankomst
	 */
	public String getAnkomst() {
		return ankomst;
	}

	/**
	 * Registrerer en ny tid for ankomst Syntaxkrav: yyyyMMddHHmmss
	 */
	public void setAnkomst(String ankomst) {
		this.ankomst = ankomst;
	}

	/**
	 * Returnerer tiden for afgang
	 */
	public String getAfgang() {
		return afgang;
	}

	/**
	 * Registrerer en ny tid for afgang Syntaxkrav: yyyyMMddHHmmss
	 */
	public void setAfgang(String afgang) {
		this.afgang = afgang;
	}

	/**
	 * Returnerer en liste af de fejl som har vaeret på besoeget
	 */
	public List<Fejl> getFejl() {
		return new ArrayList<Fejl>(fejl);
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
				this.laesning.unsetBesoeg();
			}
			this.laesning = laesning;
			if (laesning != null)
				laesning.setBesoeg(this);
		}
	}
	
	/**
	 * Returnerer den tilknyttede instans af lastbil
	 */
	public Lastbil getLastbil() {
		return lastbil;
	}

	/**
	 * Registrerer en fejl til listen af fejl
	 */
	public void addFejl(Fejl fejl) {
		this.fejl.add(fejl);
	}
	
	/**
	 * Returnerer true hvis der har været vægtfejl i besoeget.
	 */
	public boolean harVaegtfejl(){
		return fejl.contains(Fejl.VAEGTFEJL);
	}

	/**
	 * Returnerer den hviletid som chaufføren skal have.
	 */
	public int getHviletid() {
		return hviletid;
	}

	/**
	 * Registrere den hviletid som chaufføren skal have.
	 */
	public void setHviletid(int hviletid) {
		this.hviletid = hviletid;
	}

	/**
	 * Returnerer en String med besoegets ankomsts og afgangs tid
	 * Returnerer en String med besoegets ankomsts og *** som afgangstid hvis afgangstid=null
	 */
	public String toString() {
		String temp = afgang;
		if(afgang == null) temp="**************";
		return DateUtil.getSmartDate(ankomst) + "  ---  " + temp.substring(8, 10) + ":" + temp.substring(10, 12);
	}

	/**
	 * Sorterer besøg efter ankomst
	 */
	@Override
	public int compareTo(Besoeg o) {
		return ankomst.compareTo(o.getAnkomst());
	}

	/**
	 * Afregistrerer laesning
	 */
	public void unsetLaesning() {
		laesning = null;
	}
}