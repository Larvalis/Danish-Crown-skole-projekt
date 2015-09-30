package model;

/**
 * Doc for enum RampeStatus
 */
public enum RampeStatus {
	/**
	 * En læsning er i gang ved rampen
	 */
	OPTAGET,
	/**
	 * Rampen er parat til at igangsætte en læsning
	 */
	LEDIG,
	/**
	 * En rampe er stoppet og ingen læsninger er ved rampen
	 */
	STOPPET
}