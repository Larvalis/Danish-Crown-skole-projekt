package service;

import java.util.Date;
import model.DateUtil;

public class Validation {

	private static final String ERRORTITLE = "Fejl";
	private static Service service = Service.getInstance();
	
	/**
	 * Checker om strengen kan parses til int og evt. om strengen er tom.
	 * Ved fejl vises en warningbox med fejlbesked
	 */
	public static boolean checkInt(String value, boolean maaVaereTom, String txtNavn) {
		boolean noError = false;
		try {
			if (!maaVaereTom && value.equals("")) {
				service.showWarningBox(ERRORTITLE, txtNavn + " må ikke være tomt");
			} else {
				int check = Integer.parseInt(value);
				if (check < 0)
					service.showWarningBox(ERRORTITLE, txtNavn + " skal rumme et positivt heltal");
				else
					noError = true;
			}
		} catch (NumberFormatException err) {
			service.showWarningBox(ERRORTITLE, txtNavn + " skal rumme et positivt heltal");
		}
		return noError;
	}

	/**
	 * Checker om strengen kan parses til double og evt. om strengen er tom.
	 * Ved fejl vises en warningbox med fejlbesked
	 */
	public static boolean checkDouble(String value, boolean maaVaereTom,
			String txtNavn) {
		boolean noError = false;
		try {
			if (!maaVaereTom && value.equals("")) {
				service.showWarningBox(ERRORTITLE, txtNavn + " må ikke være tomt");
			} else {
				double check = Double.parseDouble(value);
				noError = true;
			}
		} catch (NumberFormatException err) {
			service.showWarningBox(ERRORTITLE, txtNavn + " skal rumme et tal");
		}
		return noError;
	}

	/**
	 * Checker om strengen er tom.
	 * Ved fejl vises en warningbox med fejlbesked
	 */
	public static boolean checkEmpty(String value, String txtNavn) {
		boolean noError = false;
		if (value.equals(""))
			service.showWarningBox(ERRORTITLE, txtNavn + " må ikke være tomt");
		else
			noError = true;
		return noError;
	}

	/**
	 * Checker om strengen kan parses til Date og evt. om strengen er tom.
	 * Ved fejl vises en warningbox med fejlbesked
	 */
	public static boolean checkDate(String value, boolean maaVaereTom,
			String txtNavn) {
		boolean noError = false;
		try {
			if (!maaVaereTom && value.equals("")) {
				service.showWarningBox(ERRORTITLE, txtNavn + " må ikke være tomt");
			} else {
				Date check = DateUtil.createDate(value);
				noError = true;
			}
		} catch (IllegalArgumentException err) {
			service.showWarningBox(ERRORTITLE, txtNavn + " skal rumme en dato i formatet yyyy-MM-dd (f.eks. 2010-12-14)");
		}
		return noError;
	}
}