package service;

import gui.App;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.*;
import dao.DaoInterface;
import dao.DaoMap;
import dao.DaoJPA;

/**
 * Model for ServiceDao
 */
public class ServiceDAO {

	private static ServiceDAO uniqueInstance;
	private DaoInterface dao;
	private boolean first = true; // Sikring på at createAndStoreSomeObjects kun
									// bliver kaldt én gang
	private boolean hidePopups = false;

	/**
	 * Initialiserer en ny ServiceDAO klasse. Kan kun ske fra denne klasse
	 * (singleton)
	 */
	private ServiceDAO() {
		if(App.useJPA)
			dao = DaoJPA.getInstance();
		else
			dao = DaoMap.getInstance();
	}

	/**
	 * Returnerer en unik instans af service klassen. Hvis der ikke findes en,
	 * så initialiserer den en
	 */
	public static ServiceDAO getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new ServiceDAO();
		}
		return uniqueInstance;
	}

	/**
	 * Opretter en rampe og registrerer den til listen af ramper i dao'en.
	 * Returnerer rampen som er blevet oprettet. Krav: rampenummer skal være
	 * unikt i forhold til alle rampe instanser
	 */
	public Rampe createRampe(int rampenummer,
			TransportMateriale transportMateriale) {
		boolean error = false;
		for (Rampe r : getAllRamper())
			if (r.getRampenummer() == rampenummer)
				error = true;
		if (!error) {
			Rampe rampe = new Rampe(rampenummer, transportMateriale);
			dao.storeRampe(rampe);
			return rampe;
		} else {
			if (!hidePopups)
				Service.getInstance().showInfoBox(
						"Rampens nummer skal være unikt");
			return null;
		}
	}

	/**
	 * Gemmer en opdateret rampe i dao'en. Returnerer rampen som er blevet
	 * opdateret
	 */
	public Rampe updateRampe(Rampe rampe) {
		dao.storeRampe(rampe);
		return rampe;
	}

	/**
	 * Sletter en rampe fra dao'en
	 */
	public void deleteRampe(Rampe rampe) {
		dao.removeRampe(rampe);
	}

	/**
	 * Returnerer en liste af alle ramper fra dao'en
	 */
	public List<Rampe> getAllRamper() {
		return dao.getAllRamper();
	}

	/**
	 * Returnerer en rampe ud fra rampenummer. Returnerer null hvis rampen ikke
	 * findes
	 */
	public Rampe getRampe(int rampenummer) {
		return dao.getRampe(rampenummer);
	}

	/**
	 * Returnerer en liste af ramper uden stop ud fra tranportmateriale
	 */
	public List<Rampe> getRampeUdenStopTM(TransportMateriale transportMateriale) {
		List<Rampe> list = new ArrayList<Rampe>();
		for (Rampe r : dao.getAllRamper())
			if (r.getTransportMateriale() == transportMateriale
					&& r.getRampeStatus() != RampeStatus.STOPPET)
				list.add(r);
		return list;
	}

	/**
	 * Opretter en lastbil og registrerer den til listen af lastbiler i dao'en
	 * Returnerer lastbilen som er blevet oprettet. Returnerer null hvis
	 * lastbilnummeret ikke er unikt. Krav: lastbilnummer skal være unikt i
	 * forhold til alle lastbilinstanser
	 */
	public Lastbil createLastbil(int lastbilnummer, String chauffoer,
			String mobilnummer, double maxLastVaegt, double trailerVaegt) {
		boolean error = false;
		for (Lastbil l : getAllLastbiler())
			if (l.getLastbilnummer() == lastbilnummer)
				error = true;
		if (!error) {
			Lastbil lastbil = new Lastbil(lastbilnummer, chauffoer,
					mobilnummer, maxLastVaegt, trailerVaegt);
			dao.storeLastbil(lastbil);
			return lastbil;
		} else {
			if (!hidePopups)
				Service.getInstance().showInfoBox(
						"Lastbilens nummer skal være unikt");
			return null;
		}
	}

	/**
	 * Redigerer en lastbil i dao'en. Returnerer lastbilen som er blevet
	 * redigeret. Returnerer null hvis det nye lastbilnummeret ikke er unikt.
	 * Krav: lastbilnummer skal være unikt i forhold til alle andre lastbilinstanser
	 */
	public Lastbil updateLastbil(Lastbil lastbil, int lastbilnummer,
			String chauffoer, String mobilnummer, double maxLastVaegt,
			double trailerVaegt) {
		boolean error = false;
		for (Lastbil l : getAllLastbiler())
			if (l.getLastbilnummer() == lastbilnummer
					&& l.getLastbilnummer() != lastbil.getLastbilnummer())
				error = true;
		if (!error) {
			lastbil.setLastbilnummer(lastbilnummer);
			lastbil.setChauffoer(chauffoer);
			lastbil.setMobilnummer(mobilnummer);
			lastbil.setMaxLastVaegt(maxLastVaegt);
			dao.storeLastbil(lastbil);
			return lastbil;
		} else {
			if (!hidePopups)
				Service.getInstance().showInfoBox(
						"Lastbilens nummer skal være unikt");
			return null;
		}
	}

	/**
	 * Gemmer en opdateret lastbil i dao'en. Returnerer lastbilen som er blevet
	 * opdateret.
	 */
	public Lastbil updateLastbil(Lastbil lastbil) {
		dao.storeLastbil(lastbil);
		return lastbil;
	}

	/**
	 * Sletter en lastbil fra dao'en
	 */
	public void deleteLastbil(Lastbil lastbil) {
		dao.removeLastbil(lastbil);
	}

	/**
	 * Returnerer en liste af alle lastbiler fra dao'en
	 */
	public List<Lastbil> getAllLastbiler() {
		return dao.getAllLastbiler();
	}

	/**
	 * Returnerer en lastbil ud fra lastbilnummer. Returnerer null hvis
	 * lastbilnummeret ikke findes
	 */
	public Lastbil getLastbil(int lastbilnummer) {
		return dao.getLastbil(lastbilnummer);
	}

	/**
	 * Opretter en ordre og registrerer den til listen af ordrer i dao'en
	 * Returnerer den ordre som er blevet oprettet. Krav: Ordrenummer skal være
	 * unikt i forhold til alle ordreinstanser
	 */
	public Ordre createOrdre(int ordrenummer,
			TransportMateriale transportMateriale, double bruttovaegt,
			double margenVedVejning, String dato) {
		boolean error = false;
		for (Ordre o : getAllOrdrer())
			if (o.getOrdrenummer() == ordrenummer)
				error = true;
		if (!error) {
			Ordre ordre = new Ordre(ordrenummer, transportMateriale,
					bruttovaegt, margenVedVejning, dato);
			dao.storeOrdre(ordre);
			return ordre;
		} else {
			if (!hidePopups)
				Service.getInstance().showInfoBox(
						"Ordrens nummer skal være unikt");
			return null;
		}
	}

	/**
	 * Gemmer en opdateret ordre i dao'en. Returnerer ordren som er blevet
	 * opdateret
	 */
	public Ordre updateOrdre(Ordre ordre) {
		dao.storeOrdre(ordre);
		return ordre;
	}

	/**
	 * Sletter en ordre fra dao'en
	 */
	public void deleteOrdre(Ordre ordre) {
		dao.removeOrdre(ordre);
	}

	/**
	 * Returnerer en liste af alle ordrer fra dao'en
	 */
	public List<Ordre> getAllOrdrer() {
		return dao.getAllOrdrer();
	}

	/**
	 * Returnerer en liste af alle ordrer fra dao'en fra dags dato
	 */
	public List<Ordre> getAllOrdrerFraDagsDato() {
		ArrayList<Ordre> al = new ArrayList<Ordre>();
		for (Ordre o : dao.getAllOrdrer()) {
			if (DateUtil.formatDate(new Date()).equals(
					DateUtil.formatDate(o.getDato()))) {
				al.add(o);
			}
		}

		return al;
	}

	/**
	 * Henter laesninger alt efter om de er aktive(true), inaktive(false) og
	 * hvad transport materiale som der ønskes (null for alle) samt sorterer
	 * array'et efter tid(true) eller rampe(false)
	 */
	public List<Laesning> hentLaesninger(TransportMateriale tm, boolean tid,
			TrailerStatus ts) {
		List<Laesning> list = new ArrayList<Laesning>();
		for (Laesning laesning : hentLaesningerViaTrailerStatus(ts)) {
			if (laesning.getRampe() != null
					&& (tm == null || laesning.getTransportMateriale() == tm)) {
				if (laesning.getBesoeg() != null) {
					if (laesning.getBesoeg().getAfgang() == null) {
						list.add(laesning);
					}
				} else
					list.add(laesning);
			}
		}
		return Service.getInstance().sorterLaesning(list, tid);
	}

	/**
	 * Henter laesninger der ikke har fået tilknyttet en rampe efter transportmateriale (null for alle)
	 */
	public List<Laesning> hentLaesningerUdenRampe(TransportMateriale tm) {
		List<Laesning> list = new ArrayList<Laesning>();
		for (Ordre o : getAllOrdrer()) {
			for (Delordre d : o.getDelordre()) {
				Laesning l = d.getLaesning();
				if (d.getLaesning() != null && l.getRampeIkkeFundet()
						&& (tm == null || l.getTransportMateriale() == tm))
					list.add(l);
			}
		}
		return list;
	}

	/**
	 * Henter laesninger efter trailerstatus
	 */
	public List<Laesning> hentLaesningerViaTrailerStatus(TrailerStatus ts) {
		List<Laesning> list = new ArrayList<Laesning>();
		for (Ordre o : getAllOrdrer()) {
			for (Delordre d : o.getDelordre()) {
				if (d.getLaesning() != null
						&& d.getLaesning().getBesoeg().getAfgang() == null
						&& ts == d.getLastbil().getTrailerStatus()) {
					Laesning l = d.getLaesning();
					
					list.add(l);
				}
			}
		}
		return list;
	}

	/**
	 * Opretter objekter i dao'en til at teste systemet
	 */
	public void createAndStoreSomeObjects() {
		if (!first)
			return;
		first = false;

		System.out.println("createAndStoreSomeObjects kaldt");

		Lastbil lastbil1 = createLastbil(1, "Karsten", "11111111", 20100, 100);
		Lastbil lastbil2 = createLastbil(2, "Stine", "22222222", 20200, 200);
		Lastbil lastbil3 = createLastbil(3, "Hanne", "33333333", 20300, 300);
		Lastbil lastbil4 = createLastbil(4, "Soeren", "44444444", 20400, 400);
		Lastbil lastbil5 = createLastbil(5, "Peter", "55555555", 19530, 100);
		Lastbil lastbil6 = createLastbil(6, "Christian", "66666666", 21000, 150);
		Lastbil lastbil7 = createLastbil(7, "Michael", "77777777", 23000, 350);
		Lastbil lastbil8 = createLastbil(8, "Ludvig", "88888888", 14399, 100);
		Lastbil lastbil9 = createLastbil(9, "Henning", "99999999", 23000, 350);
		Lastbil lastbil10 = createLastbil(10, "Karl Temp", "10101010", 20500,
				200);
		Lastbil lastbil11 = createLastbil(11, "Henning", "23453029",
				20150, 150);
		Lastbil lastbil12 = createLastbil(12, "Thor", "14843543", 20000, 185);
		Lastbil lastbil13 = createLastbil(13, "Ralle", "95739574", 25000, 185);
		Lastbil lastbil14 = createLastbil(14, "Kasper Tip", "29570583", 24000,
				185);
		Lastbil lastbil15 = createLastbil(15, "Birger", "19346394", 23000,
				220);
		Lastbil lastbil16 = createLastbil(16, "Brock", "02749334", 22500, 220);
		Lastbil lastbil17 = createLastbil(17, "Bønne", "57392523", 20000, 200);
		Lastbil lastbil18 = createLastbil(18, "Johan", "97025397", 20000, 200);
		Lastbil lastbil19 = createLastbil(19, "Nick", "22503528", 20000, 200);
		Lastbil lastbil20 = createLastbil(20, "Butch", "55031705", 20000,
				200);
		Lastbil lastbil21 = createLastbil(21, "Otto", "44306776", 20000,
				200);
		Lastbil lastbil22 = createLastbil(22, "Jess", "78821890", 20000, 200);
		Lastbil lastbil23 = createLastbil(23, "Vatoya", "80894293", 20000, 200);
		Lastbil lastbil24 = createLastbil(24, "Wanda", "20750431", 20000, 200);
		Lastbil lastbil25 = createLastbil(25, "Earl", "19344273", 20000, 200);
		
		Ordre ordre7 = createOrdre(7, TransportMateriale.JULETRAEER, 45000,
				0.01, DateUtil.dateToString(DateUtil.addMinutesToDate(
						new Date(), -1440)));

		Delordre delordre12 = ordre7.createDelordre(2, 40, 10000, DateUtil
				.dateToString(DateUtil.addMinutesToDate(new Date(), -1440)),
				lastbil8);
		Delordre delordre13 = ordre7.createDelordre(3, 40, 10000, DateUtil
				.dateToString(DateUtil.addMinutesToDate(new Date(), -1440)),
				lastbil9);
		Delordre delordre14 = ordre7.createDelordre(4, 40, 10000, DateUtil
				.dateToString(DateUtil.addMinutesToDate(new Date(), -1440)),
				lastbil10);
		Delordre delordre15 = ordre7.createDelordre(5, 60, 15000, DateUtil
				.dateToString(DateUtil.addMinutesToDate(new Date(), -1440)),
				lastbil11);
		Laesning laesning1 = new Laesning(lastbil8.getDelordre());
		Besoeg besoeg1 = lastbil8.createBesoeg(45);
		laesning1.setBesoeg(besoeg1);

		laesning1.setAktuelStartVaegt(150.0);
		laesning1.setAktuelSlutVaegt(10150.0);
		laesning1.setForventetStart(DateUtil.dateToString(DateUtil
				.addMinutesToDate(new Date(), -1440)));
		besoeg1.setAnkomst(DateUtil.dateToString(DateUtil.addMinutesToDate(
				new Date(), -1600)));
		besoeg1.setAfgang(DateUtil.dateToString(DateUtil.addMinutesToDate(
				new Date(), -1475)));
		besoeg1.addFejl(Fejl.VAEGTFEJL);
		besoeg1.addFejl(Fejl.RAMPESTOP);
		besoeg1.addFejl(Fejl.VAEGTFEJL);

		Laesning laesning2 = new Laesning(lastbil9.getDelordre());
		Besoeg besoeg2 = lastbil9.createBesoeg(24);
		laesning2.setAktuelStartVaegt(475.0);
		laesning2.setAktuelSlutVaegt(10480.0);
		laesning2.setForventetStart(DateUtil.dateToString(DateUtil
				.addMinutesToDate(new Date(), -1440)));
		laesning2.setBesoeg(besoeg2);
		besoeg2.setAnkomst(DateUtil.dateToString(DateUtil.addMinutesToDate(
				new Date(), -1500)));
		besoeg2.setAfgang(DateUtil.dateToString(DateUtil.addMinutesToDate(
				new Date(), -1440)));

		Laesning laesning3 = new Laesning(lastbil10.getDelordre());
		Besoeg besoeg3 = lastbil10.createBesoeg(5);
		laesning3.setAktuelStartVaegt(405.2);
		laesning3.setAktuelSlutVaegt(10405.2);
		laesning3.setForventetStart(DateUtil.dateToString(DateUtil
				.addMinutesToDate(new Date(), -1440)));
		laesning3.setBesoeg(besoeg3);
		besoeg3.setAnkomst(DateUtil.dateToString(DateUtil.addMinutesToDate(
				new Date(), -1600)));
		besoeg3.setAfgang(DateUtil.dateToString(DateUtil.addMinutesToDate(
				new Date(), -1575)));

		Laesning laesning4 = new Laesning(lastbil11.getDelordre());
		Besoeg besoeg4 = lastbil11.createBesoeg(30);
		laesning4.setAktuelStartVaegt(569.0);
		laesning4.setAktuelSlutVaegt(15570.0);
		laesning4.setForventetStart(DateUtil.dateToString(DateUtil
				.addMinutesToDate(new Date(), -1440)));
		laesning4.setBesoeg(besoeg4);
		besoeg4.setAnkomst(DateUtil.dateToString(DateUtil.addMinutesToDate(
				new Date(), -1400)));
		besoeg4.setAfgang(DateUtil.dateToString(DateUtil.addMinutesToDate(
				new Date(), -1350)));
		besoeg4.addFejl(Fejl.VAEGTFEJL);

		Ordre ordre1 = createOrdre(1, TransportMateriale.KAR, 10000, 0.01,
				DateUtil.dateToString(new Date()));
		Delordre delordre1 = ordre1.createDelordre(10, 40, 10000,
				DateUtil.dateToString(new Date()), lastbil1);

		Ordre ordre2 = createOrdre(2, TransportMateriale.KAR, 30000, 0.01,
				DateUtil.dateToString(new Date()));
		Delordre delordre2 = ordre2.createDelordre(11, 60, 15000,
				DateUtil.dateToString(new Date()), lastbil2);
		Delordre delordre3 = ordre2.createDelordre(12, 60, 15000,
				DateUtil.dateToString(new Date()), lastbil3);

		Ordre ordre3 = createOrdre(3, TransportMateriale.JULETRAEER, 25000,
				0.01, DateUtil.dateToString(new Date()));
		Delordre delordre4 = ordre3.createDelordre(16, 34, 8000,
				DateUtil.dateToString(new Date()), lastbil4);
		Delordre delordre5 = ordre3.createDelordre(17, 75, 17000,
				DateUtil.dateToString(new Date()), lastbil5);

		Ordre ordre4 = createOrdre(4, TransportMateriale.KAR, 15000, 0.01,
				DateUtil.dateToString(new Date()));
		Delordre delordre6 = ordre4.createDelordre(18, 60, 15000,
				DateUtil.dateToString(new Date()), lastbil6);

		Ordre ordre5 = createOrdre(5, TransportMateriale.KAR, 5000, 0.01,
				DateUtil.dateToString(new Date()));
		Delordre delordre7 = ordre5.createDelordre(24, 20, 5000,
				DateUtil.dateToString(new Date()), lastbil7);

		Ordre ordre6 = createOrdre(6, TransportMateriale.KAR, 45000, 0.01,
				DateUtil.dateToString(new Date()));
		Delordre delordre8 = ordre6.createDelordre(26, 40, 10000,
				DateUtil.dateToString(new Date()), lastbil8);
		Delordre delordre9 = ordre6.createDelordre(27, 40, 10000,
				DateUtil.dateToString(new Date()), lastbil9);
		Delordre delordre10 = ordre6.createDelordre(28, 40, 10000,
				DateUtil.dateToString(new Date()), lastbil10);
		Delordre delordre11 = ordre6.createDelordre(29, 60, 15000,
				DateUtil.dateToString(new Date()), lastbil11);

		Ordre ordre8 = createOrdre(8, TransportMateriale.JULETRAEER, 85000,
				0.01, DateUtil.dateToString(new Date()));
		Delordre delordre16 = ordre8.createDelordre(45, 70, 15000,
				DateUtil.dateToString(new Date()), lastbil12);
		Delordre delordre17 = ordre8.createDelordre(46, 110, 20000,
				DateUtil.dateToString(new Date()), lastbil13);
		Delordre delordre18 = ordre8.createDelordre(47, 110, 20000,
				DateUtil.dateToString(new Date()), lastbil14);
		Delordre delordre19 = ordre8.createDelordre(48, 70, 15000,
				DateUtil.dateToString(new Date()), lastbil15);
		Delordre delordre20 = ordre8.createDelordre(49, 70, 15000,
				DateUtil.dateToString(new Date()), lastbil16);

		Ordre ordre9 = createOrdre(9, TransportMateriale.JULETRAEER, 17500,
				0.01, DateUtil.dateToString(new Date()));
		Delordre delordre21 = ordre9.createDelordre(50, 82, 17500,
				DateUtil.dateToString(new Date()), lastbil17);
		
		Ordre ordre10 = createOrdre(10, TransportMateriale.KASSER, 30000, 0.01, DateUtil.dateToString(new Date()));
		Delordre delordre22 = ordre10.createDelordre(51, 36, 18000,
				DateUtil.dateToString(new Date()), lastbil18);
		Delordre delordre23 = ordre10.createDelordre(52, 24, 12000,
				DateUtil.dateToString(new Date()), lastbil19);
		
		Ordre ordre11 = createOrdre(11, TransportMateriale.KASSER, 50000, 0.01, DateUtil.dateToString(new Date()));
		Delordre delordre25 = ordre11.createDelordre(53, 38, 19000,
	    DateUtil.dateToString(new Date()), lastbil20);
		Delordre delordre26 = ordre11.createDelordre(54, 38, 19000,
				DateUtil.dateToString(new Date()), lastbil21);
		Delordre delordre27 = ordre11.createDelordre(55, 24, 12000,
				DateUtil.dateToString(new Date()), lastbil22);
		
		Ordre ordre12 = createOrdre(12, TransportMateriale.KASSER, 50000, 0.01, DateUtil.dateToString(new Date()));
		Delordre delordre28 = ordre12.createDelordre(56, 38, 19000,
	    DateUtil.dateToString(new Date()), lastbil23);
		Delordre delordre29 = ordre12.createDelordre(57, 38, 19000,
				DateUtil.dateToString(new Date()), lastbil24);
		Delordre delordre30 = ordre12.createDelordre(58, 24, 12000,
				DateUtil.dateToString(new Date()), lastbil25);

		Rampe rampe1 = createRampe(1, TransportMateriale.JULETRAEER);
		Rampe rampe2 = createRampe(2, TransportMateriale.JULETRAEER);
		Rampe rampe3 = createRampe(3, TransportMateriale.JULETRAEER);
		Rampe rampe4 = createRampe(4, TransportMateriale.JULETRAEER);
		Rampe rampe5 = createRampe(5, TransportMateriale.JULETRAEER);
		Rampe rampe6 = createRampe(6, TransportMateriale.JULETRAEER);
		Rampe rampe7 = createRampe(7, TransportMateriale.KAR);
		Rampe rampe8 = createRampe(8, TransportMateriale.KAR);
		Rampe rampe9 = createRampe(9, TransportMateriale.KAR);
		Rampe rampe10 = createRampe(10, TransportMateriale.KAR);
		Rampe rampe11 = createRampe(11, TransportMateriale.JULETRAEER);
		Rampe rampe12 = createRampe(12, TransportMateriale.JULETRAEER);
		Rampe rampe13 = createRampe(13, TransportMateriale.KASSER);
		Rampe rampe14 = createRampe(14, TransportMateriale.KASSER);
		Rampe rampe15 = createRampe(15, TransportMateriale.KASSER);
		Rampe rampe16 = createRampe(16, TransportMateriale.KASSER);
		Rampe rampe17 = createRampe(17, TransportMateriale.KASSER);
		Rampe rampe18 = createRampe(18, TransportMateriale.KASSER);

		Service service = Service.getInstance();
		service.registrerLastbilsAnkomst(1, 45);
		service.registrerLastbilsAnkomst(2, 15);
		service.registrerLastbilsAnkomst(3, 25);
		service.registrerLastbilsAnkomst(4, 35);
		service.registrerLastbilsAnkomst(5, 45);
		service.registrerLastbilsAnkomst(6, 5);
		service.registrerLastbilsAnkomst(7, 1);
		service.registrerLastbilsAnkomst(8, 1);
		service.registrerLastbilsAnkomst(9, 2);
		service.registrerLastbilsAnkomst(10, 2);
		service.registrerLastbilsAnkomst(11, 5);
		service.registrerLastbilsAnkomst(12, 1);
		service.registrerLastbilsAnkomst(13, 1);
		service.registrerLastbilsAnkomst(18, 1);
		service.registrerLastbilsAnkomst(19, 1);
		service.registrerLastbilsAnkomst(20, 1);
		service.registrerLastbilsAnkomst(21, 1);
		service.registrerLastbilsAnkomst(25, 1);
			
		service.registrerAnkomstvaegt(1, 100.0);
		service.registrerAnkomstvaegt(2, 250.0);
		service.registrerAnkomstvaegt(6, 175.0);
		service.registrerAnkomstvaegt(9, 350.0);
		service.registrerAnkomstvaegt(8, 150.0);
		service.registrerAnkomstvaegt(12, 185.0);
		service.registrerAnkomstvaegt(13, 200.0);
		service.registrerAnkomstvaegt(18, 220.0);
		service.registrerAnkomstvaegt(19, 250.0);
		service.registrerAnkomstvaegt(20, 250.0);
		service.registrerAnkomstvaegt(25, 250.0);

		service.registrerStartlaesning(1);
		service.registrerStartlaesning(12);
		service.registrerStartlaesning(13);
		service.registrerStartlaesning(18);
		service.registrerStartlaesning(19);
		service.registrerStartlaesning(25);		
		
		service.registrerFaerdigLaesning(12);
		service.registrerFaerdigLaesning(13);
		service.registrerFaerdigLaesning(25);

		System.out.println("createAndStoreSomeObjects færdig");
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