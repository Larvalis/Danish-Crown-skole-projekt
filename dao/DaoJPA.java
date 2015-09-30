package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import model.Rampe;
import model.Lastbil;
import model.Ordre;

/**
 * Model for DaoCollection
 */
public class DaoJPA implements DaoInterface {

	private static DaoJPA uniqueInstance;
	private EntityManagerFactory emf;
	private EntityManager em;

	/**
	 * Initialisere en ny dao klasse. Kan kun ske fra dao klassen.
	 */
	private DaoJPA() {
		emf = Persistence.createEntityManagerFactory("DanishCrown");
		em = emf.createEntityManager();
	}

	/**
	 * Returnerer en uniqueInstans af dao klassen, hvis der ikke findes en, saa
	 * initialisere den en.
	 */
	public static DaoJPA getInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new DaoJPA();
		}
		return uniqueInstance;
	}

	/**
	 * Registrerer en rampe til listen af ramper
	 */
	public void storeRampe(Rampe rampe) {
		em.getTransaction().begin();
		em.persist(rampe);
		em.getTransaction().commit();
	}

	/**
	 * Afregistrerer en rampen i listen af ramper
	 */
	public void removeRampe(Rampe rampe) {
		em.getTransaction().begin();
		em.remove(rampe);
		em.getTransaction().commit();
	}

	/**
	 * Returnerer en liste af registrerede ramper
	 */
	public List<Rampe> getAllRamper() {
		return em.createQuery("select r from Rampe r", Rampe.class)
				.getResultList();
	}

	/**
	 * Returnerer en rampe ud fra rampenummer. Returnerer null hvis rampen ikke
	 * findes
	 */
	public Rampe getRampe(int rampenummer) {
		try {
			return em
					.createQuery(
							"select r from Rampe r where r.rampenummer = :rampenummer",
							Rampe.class)
					.setParameter("rampenummer", rampenummer).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * Registrerer en lastbil til listen af lastbiler
	 */
	public void storeLastbil(Lastbil lastbil) {
		em.getTransaction().begin();
		em.persist(lastbil);
		em.getTransaction().commit();
	}

	/**
	 * Afregistrerer en lastbil i listen af lastbiler
	 */
	public void removeLastbil(Lastbil lastbil) {
		em.getTransaction().begin();
		em.remove(lastbil);
		em.getTransaction().commit();
	}

	/**
	 * Returnerer en liste af registrerede lastbiler
	 */
	public List<Lastbil> getAllLastbiler() {
		return em.createQuery("select l from Lastbil l", Lastbil.class)
				.getResultList();
	}

	/**
	 * Returnerer en lastbil ud fra lastbilnummer. Returnerer null hvis
	 * lastbilnummeret ikke findes
	 */
	public Lastbil getLastbil(int lastbilnummer) {
		try {
			return em
					.createQuery(
							"select l from Lastbil l where l.lastbilnummer = :lastbilnummer",
							Lastbil.class)
					.setParameter("lastbilnummer", lastbilnummer)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * Registrerer en ordre til listen af ordrer
	 */
	public void storeOrdre(Ordre ordre) {
		em.getTransaction().begin();
		em.persist(ordre);
		em.getTransaction().commit();
	}

	/**
	 * Afregistrerer en ordre i listen af ordrer
	 */
	public void removeOrdre(Ordre ordre) {
		em.getTransaction().begin();
		em.remove(ordre);
		em.getTransaction().commit();
	}

	/**
	 * Returnerer en liste af registrerede ordrer
	 */
	public List<Ordre> getAllOrdrer() {
		return em.createQuery("select o from Ordre o", Ordre.class)
				.getResultList();
	}
}