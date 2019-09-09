package org;

public class TEACHER extends PERSONNE {

	/**
	 * Constructeur d'initialisation de TEACHER
	 */
	public TEACHER(String id, String NOM, String PRENOM, String DATE_NAIS,
			String LIEU_NAIS, String ADRESSE, String NUM_TEL, String DATE_INSCR) {
		super(id, NOM, PRENOM, DATE_NAIS, LIEU_NAIS, ADRESSE, NUM_TEL,
				DATE_INSCR);
	}

	public TEACHER() {
		super("0", "", "", "01/01/1990", "", "", "", "01/01/1990");
	}

}
