package model;

import java.util.Comparator;

/**
 * Sortering af ramper efter tider
 */
public class LaesningTidSort implements Comparator<Laesning> {

	@Override
	public int compare(Laesning o1, Laesning o2) {
		Long l1;
		if (o1.getAktuelStart() != null) {
			l1 = Long.parseLong(o1.getAktuelStart());
			if (o2.getAktuelStart() != null) {
				return (int) (l1 - Long.parseLong(o2.getAktuelStart()));
			} else
				return (int) (l1 - Long.parseLong(o2.getForventetStart()));
		} else {
			l1 = Long.parseLong(o1.getForventetStart());
			if (o2.getAktuelStart() != null) {
				return (int) (l1 - Long.parseLong(o2.getAktuelStart()));
			} else
				return (int) (l1 - Long.parseLong(o2.getForventetStart()));
		}
	}
}
