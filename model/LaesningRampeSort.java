package model;

import java.util.Comparator;

/**
 * Sortering af ramper efter rampenummer
 */
public class LaesningRampeSort implements Comparator<Laesning> {

	@Override
	public int compare(Laesning o1, Laesning o2) {
		int o1Rampenummer;
		int o2Rampenummer;
		if(o1.getRampe() != null)
			o1Rampenummer = o1.getRampe().getRampenummer();
		else 
			o1Rampenummer = -1;
		if(o2.getRampe() != null)
			o2Rampenummer = o2.getRampe().getRampenummer();
		else 
			o2Rampenummer = -1;
		return o1Rampenummer - o2Rampenummer;
	}
}