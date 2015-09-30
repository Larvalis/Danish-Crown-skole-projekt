package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Rampe;
import model.Lastbil;
import model.Ordre;

/**
 * Model for DaoCollection
 */
public class DaoMap implements DaoInterface{

	private static DaoMap uniqueInstance;
	private static Map<Integer, Rampe> ramper = new HashMap<Integer, Rampe>();
	private static Map<Integer, Lastbil> lastbiler = new HashMap<Integer, Lastbil>();
	private static Map<Integer, Ordre> ordrer = new HashMap<Integer, Ordre>();

	/**
	 * Initialisere en ny dao klasse. Kan kun ske fra dao klassen
	 */
	private DaoMap() {
	}

	/**
	 * Returnerer en uniqueInstans af dao klassen, hvis der ikke findes en, så initialiserer den en
	 */
	public static DaoMap getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new DaoMap();
		}
		return uniqueInstance;
	}

	/**
	 * Registrerer en rampe til listen af ramper
	 */
	public void storeRampe(Rampe rampe) {
		if (!ramper.containsValue(rampe))
			ramper.put(rampe.getRampenummer(), rampe);
	}

	/**
	 * Afregistrerer en rampe i listen af ramper
	 */
	public void removeRampe(Rampe rampe) {
		if (ramper.containsValue(rampe))
			ramper.remove(rampe.getRampenummer());
	}

	/**
	 * Returnerer en liste af registrerede ramper
	 */
	public List<Rampe> getAllRamper() {
		return new ArrayList<Rampe>(ramper.values());
	}
	
	/**
	 * Returnerer en rampe ud fra rampenummer. Returnerer null hvis rampen ikke findes
	 */
	public Rampe getRampe(int rampenummer) {
		return ramper.get(rampenummer);
	}

	/**
	 * Registrerer en lastbil til listen af lastbiler
	 */
	public void storeLastbil(Lastbil lastbil) {
		if (!lastbiler.containsValue(lastbil))
			lastbiler.put(lastbil.getLastbilnummer(), lastbil);
	}

	/**
	 * Afregistrerer en lastbil i listen af lastbiler
	 */
	public void removeLastbil(Lastbil lastbil) {
		if (lastbiler.containsValue(lastbil))
			lastbiler.remove(lastbil.getLastbilnummer());
	}

	/**
	 * Returnerer en liste af registrerede lastbiler
	 */
	public List<Lastbil> getAllLastbiler() {
		return new ArrayList<Lastbil>(lastbiler.values());
	}
	
	/**
	 * Returnerer en lastbil ud fra lastbilnummer. Returnerer null hvis lastbilnummeret ikke findes
	 */
	public Lastbil getLastbil(int lastbilnummer) {
		return lastbiler.get(lastbilnummer);
	}

	/**
	 * Registrerer en ordre til listen af ordrer
	 */
	public void storeOrdre(Ordre ordre) {
		if (!ordrer.containsValue(ordre))
			ordrer.put(ordre.getOrdrenummer(), ordre);
	}

	/**
	 * Afregistrerer en ordre i listen af ordrer
	 */
	public void removeOrdre(Ordre ordre) {
		if (ordrer.containsValue(ordre))
			ordrer.remove(ordre.getOrdrenummer());
	}

	/**
	 * Returnerer en liste af registrerede ordrer
	 */	
	public List<Ordre> getAllOrdrer() {
		return new ArrayList<Ordre>(ordrer.values());
	}
}