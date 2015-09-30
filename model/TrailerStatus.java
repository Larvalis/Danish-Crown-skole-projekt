package model;

/**
 * Doc for enum TrailerStatus
 */
public enum TrailerStatus {
	/**
	 * Traileren er ikke ved Danish Crown
	 */
	IKKE_VED_DC,
	/**
	 * Traileren er ankommet til Danish Crown.
	 * Chaufføren har registreret sin ankomst
	 */
	ANKOMMET,
	/**
	 * Traileren er blevet vejet af trailermedarbejder og klar til læsning
	 */
	KLAR_TIL_LAESNING,
	/**
	 * Traileren er ved rampe og er i gang med at blive læsset
	 */
	VED_RAMPE,
	/**
	 * Traileren er færdiglæsset og venter på at blive kontrolvejet
	 */
	FAERDIG_LAESSET,
	/**
	 * Traileren er parat til afhentning af chaufføren
	 */
	TIL_AFHENTNING
}