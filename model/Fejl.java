package model;
/**
 *  Doc for enum Fejl
 *  Fejl der kan opstå under en trailers besøg ved Danish Crown
 */
public enum Fejl {
	/**
	 * Forekomst af rampestop, som har ændret læsnings priotering
	 */
	RAMPESTOP, 
	/**
	 * Efter endt læsning er vægten af trailer ikke i overenstemmelse med det ønskede krav
	 */
	VAEGTFEJL, 
	/**
	 * Der kan ikke findes en rampe til den ønskede trailer
	 */
	RAMPE_IKKE_FUNDET
}