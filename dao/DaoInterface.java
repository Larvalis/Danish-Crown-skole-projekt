package dao;

import java.util.List;

import model.Lastbil;
import model.Ordre;
import model.Rampe;

public interface DaoInterface {

	void storeRampe(Rampe rampe);
	void removeRampe(Rampe rampe);
	List<Rampe> getAllRamper();
	Rampe getRampe(int rampenummer);
	
	void storeLastbil(Lastbil lastbil);
	void removeLastbil(Lastbil lastbil);
	List<Lastbil> getAllLastbiler();
	Lastbil getLastbil(int lastbilnummer);
	
	void storeOrdre(Ordre ordre);
	void removeOrdre(Ordre ordre);
	List<Ordre> getAllOrdrer();
}