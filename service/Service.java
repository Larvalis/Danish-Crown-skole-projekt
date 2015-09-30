package service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import model.*;

/**
 * Model for Service
 */
public class Service {

	private static Service uniqueInstance;
	private ServiceDAO serviceDAO;
	private boolean hidePopups = false;

	/**
	 * Initialiserer en ny Service klasse. Kan kun ske fra denne klasse
	 * (singleton)
	 */
	private Service() {
		serviceDAO = ServiceDAO.getInstance();
	}

	/**
	 * Returnerer en unik instans af service klassen. Hvis der ikke findes en,
	 * så initialiserer den en
	 */
	public static Service getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new Service();
		}
		return uniqueInstance;
	}

	/**
	 * Sætter en laesning i kø på en rampe og beregner forventetStart
	 */
	public void findOgTildelRampe(Laesning laesning) {
		System.out.println("findOgTildelRampe kaldt");
		Rampe rampe = null;
		Date tidligst = new Date(Long.MAX_VALUE);
		TransportMateriale tm = laesning.getTransportMateriale();
		for (Rampe r : serviceDAO.getAllRamper()) {
			RampeStatus rs = r.getRampeStatus();
			if (r.getTransportMateriale() == tm && rs != RampeStatus.STOPPET) {
				if (tidligst.after(DateUtil.stringToDate(r
						.findNaesteLedigeTid()))) {
					tidligst = DateUtil.stringToDate(r.findNaesteLedigeTid());
					rampe = r;
				}
			}
		}
		if (rampe != null) {
			laesning.setForventetStart(DateUtil.dateToString(tidligst));
			rampe.addLaesning(laesning);
			serviceDAO.updateRampe(rampe);
		} else {
			laesning.setRampeIkkeFundet(true);
		}
	}

	/**
	 * Registrerer lastbils ankomst, opretter en laesning, opretter et besoeg og
	 * initialiserer findRampe(laesning)
	 */
	public void registrerLastbilsAnkomst(int lastbilnummer, int hviletid) {
		System.out.println("registrerLastbilsAnkomst kaldt");
		if (lastbilnummer <= 0)
			throw new RuntimeException("Lastbilnummer skal være større end 0");
		if (hviletid < 0)
			throw new RuntimeException("Hviletid må ikke være negativ");

		Lastbil lastbil = serviceDAO.getLastbil(lastbilnummer);
		if (lastbil == null)
			throw new RuntimeException(
					"Der findes ingen lastbil med dette lastbilnummer");
		if (lastbil.getTrailerStatus() != TrailerStatus.IKKE_VED_DC)
			throw new RuntimeException("Lastbilen er allerede ved DC");

		Laesning laesning = new Laesning(lastbil.getDelordre());
		laesning.setBesoeg(lastbil.createBesoeg(hviletid));
		lastbil.setTrailerStatus(TrailerStatus.ANKOMMET);
		findOgTildelRampe(laesning);
		serviceDAO.updateLastbil(lastbil);
	}

	/**
	 * Registrerer ankomstvægten på traileren
	 */
	public void registrerAnkomstvaegt(int lastbilnummer, Double vaegt) {
		System.out.println("registrerAnkomstvaegt kaldt");
		Lastbil lastbil = serviceDAO.getLastbil(lastbilnummer);
		Delordre delordre = lastbil.getDelordre();
		delordre.getLaesning().setAktuelStartVaegt(vaegt);
		lastbil.setTrailerStatus(TrailerStatus.KLAR_TIL_LAESNING);
		serviceDAO.updateLastbil(lastbil);
	}

	/**
	 * Registrerer tiden for aktuel start og sætter trailerstatus
	 */
	public void registrerStartlaesning(int lastbilnummer) {
		System.out.println("registrerStartlaesning kaldt");
		Lastbil lastbil = serviceDAO.getLastbil(lastbilnummer);
		Laesning laesning = lastbil.getDelordre().getLaesning();

		laesning.setAktuelStart(DateUtil.dateToString(new Date()));
		lastbil.setTrailerStatus(TrailerStatus.VED_RAMPE);
		laesning.getRampe().setRampeStatus(RampeStatus.OPTAGET);
		serviceDAO.updateLastbil(lastbil);
	}

	/**
	 * Registrerer tiden for aktuel slut, og fjerner traileren fra rampen og gør
	 * rampen klar til næste laesning
	 */
	public void registrerFaerdigLaesning(int lastbilnummer) {
		System.out.println("registrerFaerdigLaesning kaldt");
		Lastbil lastbil = serviceDAO.getLastbil(lastbilnummer);
		Laesning laesning = lastbil.getDelordre().getLaesning();

		laesning.setAktuelSlut(DateUtil.dateToString(new Date()));
		Rampe rampe = laesning.getRampe();
		rampe.removeLaesning(laesning);
		if (rampe.getLaesninger().size() == 0)
			rampe.setRampeStatus(RampeStatus.LEDIG);
		else
			rampe.beregnStartTidspunkter();
		lastbil.setTrailerStatus(TrailerStatus.FAERDIG_LAESSET);
		serviceDAO.updateLastbil(lastbil);
	}

	/**
	 * Registrerer afgangsvægten på traileren, sætter trailerstatus. Hvis der er
	 * vægt fejl håndteres dette også
	 */
	public void registrerAfgangsvaegt(int lastbilnummer, double vaegt) {
		System.out.println("registrerAfgangsvaegt kaldt");
		Lastbil lastbil = serviceDAO.getLastbil(lastbilnummer);
		Laesning laesning = lastbil.getDelordre().getLaesning();
		laesning.setAktuelSlutVaegt(vaegt);
		if (laesning.tjekVaegt()) {
			lastbil.setTrailerStatus(TrailerStatus.TIL_AFHENTNING);
			sendSMS(lastbilnummer);
		} else {
			laesning.getBesoeg().addFejl(Fejl.VAEGTFEJL);
			lastbil.setTrailerStatus(TrailerStatus.KLAR_TIL_LAESNING);
			System.out.println("VaegtFejl");
			laesning.setAktuelSlutVaegt(0);
			findOgTildelRampe(laesning);
			showWarningBox("Vægtfejl", "Fejl ved vægt af trailer: "
					+ lastbilnummer
					+ "\nTraileren er sat til omlæsning ved rampe: "
					+ laesning.getRampe().getRampenummer());
		}
		serviceDAO.updateLastbil(lastbil);
	}

	/**
	 * Registrerer lastbils afgang og sætter trailerstatus
	 */
	public void registrerLastbilsAfgang(int lastbilnummer) {
		System.out.println("registrerLastbilsAfgang kaldt");
		Lastbil lastbil = serviceDAO.getLastbil(lastbilnummer);
		lastbil.getBesoeg().last().setAfgang(DateUtil.dateToString(new Date()));
		lastbil.setTrailerStatus(TrailerStatus.IKKE_VED_DC);
		serviceDAO.updateLastbil(lastbil);
	}

	/**
	 * Flytter en laesning til en anden rampe
	 */
	public void flytTilAndenRampe(Laesning laesning, int rampenummer) {
		if (laesning.getBesoeg().getLastbil().getTrailerStatus() != TrailerStatus.VED_RAMPE) {
			Rampe gammelRampe = laesning.getRampe();
			Rampe nyRampe = serviceDAO.getRampe(rampenummer);
			if (nyRampe != null) {
				if (laesning.getTransportMateriale() == nyRampe
						.getTransportMateriale()) {
					laesning.setRampe(nyRampe);
					nyRampe.beregnStartTidspunkter();
					gammelRampe.beregnStartTidspunkter();
					serviceDAO.updateRampe(nyRampe);
					serviceDAO.updateRampe(gammelRampe);
				} else
					showWarningBox(
							"Forkert rampe",
							"Rampens transportmateriale stemmer ikke overens med læsningens transportmateriale!");
			} else
				showWarningBox("Rampe ikke fundet", "Rampen findes ikke!");
		} else
			showWarningBox("Aktiv læsning!",
					"Aktive læsninger kan ikke flyttes");
	}

	/**
	 * Viser en bekræftelsespopupbox med to knapper. Returnerer true hvis
	 * første knap bliver trykket på
	 */
	public boolean showConfirmBox(String titel, String tekst, String okTekst,
			String ejOkTekst) {
		return JOptionPane.showOptionDialog(null, tekst, titel,
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE,
				null, new String[] { okTekst, ejOkTekst }, null) == 0;
	}

	/**
	 * Viser en inputpopupbox med et tekstfelt og to knapper. Returnerer et
	 * objekt som er null hvis brugeren annullerer. Ellers returneres brugerens
	 * input
	 */
	public Object showInputBox(String titel, String tekst, String inputTekst) {
		return JOptionPane.showInputDialog(null, tekst, titel,
				JOptionPane.INFORMATION_MESSAGE, null, null, null);
	}

	/**
	 * Viser en inputdialogbox med et tekstfelt og et dropdown panel med
	 * muligheder. Returnerer et objekt som er null hvis brugeren annullerer.
	 * Ellers returneres brugerens input
	 */
	public Object showInputDialog(String titel, String tekst,
			Object[] possibleValues) {
		return JOptionPane.showInputDialog(null, tekst, titel,
				JOptionPane.INFORMATION_MESSAGE, null, possibleValues,
				possibleValues[0]);
	}

	/**
	 * Viser en infopopupbox
	 */
	public void showInfoBox(String tekst) {
		JOptionPane.showMessageDialog(null, tekst);
	}

	/**
	 * Viser en advarselpopupbox
	 */
	public void showWarningBox(String titel, String tekst) {
		JOptionPane.showMessageDialog(null, tekst, titel,
				JOptionPane.WARNING_MESSAGE);
	}

	/**
	 * Udskriver en sms med lastbilens nr og tlf nummer, og besked om afhentningsklar
	 * trailer. Hvis hviletid ikke er overholdt sendes sms om dette
	 */
	public void sendSMS(int lastbilnummer) {
		System.out.println("sendSMS kaldt på lastbil: " + lastbilnummer);
		Lastbil lastbil = serviceDAO.getLastbil(lastbilnummer);
		int hviletid = lastbil.getBesoeg().last().getHviletid();
		int besoegstid = DateUtil.minsDiff(
				DateUtil.stringToDate(lastbil.getBesoeg().last().getAnkomst()),
				new Date());
		if (besoegstid >= hviletid) {
			if (!hidePopups)
				showInfoBox("SMS til lastbil: " + lastbilnummer +" ("  + lastbil.getMobilnummer()
						+ ")\nBesked: Din trailer er klar til afhentning!");
		} else if (!hidePopups)
			showInfoBox("Hviletid skal overholdes. SMS sendes om: "
					+ (hviletid - besoegstid) + " minut(ter) til lastbil "
					+ lastbilnummer + " (" + lastbil.getMobilnummer() + ")");
	}

	/**
	 * Sorterer en liste af læsninger efter tid(true) eller rampe(false)
	 */
	public List<Laesning> sorterLaesning(List<Laesning> list, boolean tid) {
		if (tid)
			Collections.sort(list, new LaesningTidSort());
		else
			Collections.sort(list, new LaesningRampeSort());
		return list;
	}

	/**
	 * Hvis rampestop er true sættes rampe til STOPPET og finder nye ramper til
	 * alle dens læsninger. Hvis rampestop er false sættes rampe til LEDIG
	 */
	public void setRampestop(Rampe rampe, boolean rampestop) {
		if (rampestop) {
			rampe.setRampeStatus(RampeStatus.STOPPET);
			for (Laesning l : rampe.getLaesninger()) {
				Besoeg besoeg = l.getBesoeg();
				besoeg.addFejl(Fejl.RAMPESTOP);
				Lastbil lastbil = besoeg.getLastbil();
				if (lastbil.getTrailerStatus() == TrailerStatus.VED_RAMPE)
					lastbil.setTrailerStatus(TrailerStatus.KLAR_TIL_LAESNING);
				findOgTildelRampe(l);
			}
		} else {
			rampe.setRampeStatus(RampeStatus.LEDIG);
			for (Laesning l : serviceDAO.hentLaesningerUdenRampe(rampe
					.getTransportMateriale())) {
				l.setRampe(rampe);
			}
		}
		serviceDAO.updateRampe(rampe);
	}

	/**
	 * Registrerer hidePopups
	 */
	public void setHidePopups(boolean hide) {
		hidePopups = hide;
	}

	/**
	 * Returnerer hidePopups
	 */
	public boolean getHidePopups() {
		return hidePopups;
	}
}