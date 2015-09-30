package model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * Model for Laesning
 */
@Entity
public class Laesning {
	@Id
	@GeneratedValue
	private int id;
	private String forventetStart;
	private String aktuelStart = null;
	private String aktuelSlut = null;
	private double aktuelStartVaegt = 0;
	private double aktuelSlutVaegt = 0;
	@ManyToOne(cascade = CascadeType.ALL)
	private Rampe rampe;
	@OneToOne(cascade = CascadeType.ALL)
	private Besoeg besoeg;
	@OneToOne(cascade = CascadeType.ALL)
	private Delordre delordre;
	private boolean rampeIkkeFundet = false;

	/**
	 * Må kun bruges af JPA
	 */
	public Laesning() {}
	
	/**
	 * Initialiserer en ny laesning, med link til delordre. Krav: delordre må
	 * ikke være null
	 */
	public Laesning(Delordre delordre) {
		delordre.setLaesning(this);
	}

	/**
	 * Returnerer tiden for det forventede start tidspunkt
	 */
	public String getForventetStart() {
		return forventetStart;
	}

	/**
	 * Registrerer et nyt tidspunkt for forventetStart Syntaxkrav:
	 * yyyyMMddHHmmss
	 */
	public void setForventetStart(String forventetStart) {
		this.forventetStart = forventetStart;
	}

	/**
	 * Returnerer tiden for det aktuelle start tidspunkt
	 */
	public String getAktuelStart() {
		return aktuelStart;
	}

	/**
	 * Registrerer et nyt tidspunkt for aktuelStart Syntaxkrav: yyyyMMddHHmmss
	 */
	public void setAktuelStart(String aktuelStart) {
		this.aktuelStart = aktuelStart;
	}

	/**
	 * Returnerer tiden for det aktuelle slut tidspunkt
	 */
	public String getAktuelSlut() {
		return aktuelSlut;
	}

	/**
	 * Registrerer et nyt tidspunkt for aktuelSlut Syntaxkrav: yyyyMMddHHmmss
	 */
	public void setAktuelSlut(String aktuelSlut) {
		this.aktuelSlut = aktuelSlut;
	}

	/**
	 * Returnerer den aktuelle start vaegt
	 */
	public double getAktuelStartVaegt() {
		return aktuelStartVaegt;
	}

	/**
	 * Registrerer den aktuelle start vaegt
	 */
	public void setAktuelStartVaegt(double aktuelStartVaegt) {
		this.aktuelStartVaegt = aktuelStartVaegt;
	}

	/**
	 * Returnerer den aktuelle slut vaegt
	 */
	public double getAktuelSlutVaegt() {
		return aktuelSlutVaegt;
	}

	/**
	 * Registrerer den aktuelle slut vaegt
	 */
	public void setAktuelSlutVaegt(double aktuelSlutVaegt) {
		this.aktuelSlutVaegt = aktuelSlutVaegt;
	}

	/**
	 * Returnerer den tilknyttede instans af rampe
	 */
	public Rampe getRampe() {
		return rampe;
	}

	/**
	 * Registrerer en instans af rampe
	 */
	public void setRampe(Rampe rampe) {
		if (this.rampe != rampe) {
			if (this.rampe != null)
				this.rampe.removeLaesning(this);
			this.rampe = rampe;
			if (rampe != null) {
				rampe.addLaesning(this);
				setRampeIkkeFundet(false);
			}
		}
	}

	/**
	 * Returnerer den tilknyttede instans af besoeg
	 */
	public Besoeg getBesoeg() {
		return besoeg;
	}

	/**
	 * Registrerer en instans af besoeg
	 */
	public void setBesoeg(Besoeg besoeg) {
		if (this.besoeg != besoeg) {
			if (this.besoeg != null) {
				this.besoeg.unsetLaesning();
			}
			this.besoeg = besoeg;
			if (besoeg != null)
				besoeg.setLaesning(this);
		}
	}

	/**
	 * Returnerer den tilknyttede instans af delordre
	 */
	public Delordre getDelordre() {
		return delordre;
	}

	/**
	 * Registrerer en instans af delordre
	 */
	public void setDelordre(Delordre delordre) {
		if (this.delordre != delordre) {
			if (this.delordre != null) {
				this.delordre.unsetLaesning();
			}
			this.delordre = delordre;
			if (delordre != null) {
				delordre.setLaesning(this);
			}
		}
	}

	/**
	 * Returnerer false hvis der er fundet en rampe, og true hvis der ikke er
	 * fundet en rampe og forbindelsen mellem Laesning og Rampe derfor er null
	 */
	public boolean getRampeIkkeFundet() {
		return rampeIkkeFundet;
	}

	/**
	 * Registrerer om der er fundet en Rampe, hvis true, sætter en fejl i besoeg
	 */
	public void setRampeIkkeFundet(boolean rampeIkkeFundet) {
		if (this.rampeIkkeFundet != rampeIkkeFundet) {
			this.rampeIkkeFundet = rampeIkkeFundet;
			if (rampeIkkeFundet) {
				besoeg.addFejl(Fejl.RAMPE_IKKE_FUNDET);
				setRampe(null);
			}
		}
	}

	/**
	 * Returnerer tiden for det forventede sluttidspunkt
	 */
	public String beregnForventetSlut() {
		if (aktuelStart == null)
			return DateUtil.dateToString((DateUtil.addMinutesToDate(
					DateUtil.stringToDate(forventetStart),
					delordre.getLaessetid())));
		else
			return DateUtil
					.dateToString((DateUtil.addMinutesToDate(
							DateUtil.stringToDate(aktuelStart),
							delordre.getLaessetid())));
	}

	/**
	 * Returnerer transport materialet for ordren
	 */
	public TransportMateriale getTransportMateriale() {
		return delordre.getOrdre().getTransportMateriale();
	}

	/**
	 * Returnerer en String med laesningens forventet start, aktuel start,
	 * forventet slut, aktuel slut og aktuel vaegt.
	 */
	public String toString() {
		String str = "";
		str += "Læsning: " + getDelordre().getDelordrenummer()
				+ ", Start: "
				+ DateUtil.formatTimestamp(forventetStart);
		if (getRampe()!=null) str+=" (R:"+getRampe().getRampenummer()+")";
		return str;
	}

	/**
	 * Afregistrerer besoeg
	 */
	public void unsetBesoeg() {
		besoeg = null;
	}

	/**
	 * Afregistrerer delordre
	 */
	public void unsetDelordre() {
		delordre = null;
	}

	/**
	 * Returnerer true hvis aktuelSlutVaegt er indenfor vægtmargenen og er under maximal last vægt kapacitet
	 */
	public boolean tjekVaegt() {
		if(aktuelSlutVaegt > getDelordre().getLastbil().getMaxLastVaegt())
			return false;
		double delvaegt = delordre.getVaegt() + aktuelStartVaegt;
		double margen = delordre.getOrdre().getMargenVedVejning();
		double delvaegtMax = delvaegt * (1 + margen);
		double delvaegtMin = delvaegt * (1 - margen);
		return (aktuelSlutVaegt > delvaegtMin && aktuelSlutVaegt < delvaegtMax);
	}
}