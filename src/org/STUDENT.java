package org;

public class STUDENT extends PERSONNE {
	public String prenomPere;
	public String profPere;
	public String nomMere;
	public String prenomMere;
	public String profMere;

	/**
	 * Constructeur d'initialisation de STUDENT
	 */
	public STUDENT(String id, String NOM, String PRENOM, String DATE_NAIS,
			String LIEU_NAIS, String ADRESSE, String NUM_TEL,
			String DATE_INSCR, String prenomP, String profP, String nomM,
			String prenomM, String profM) {
		super(id, NOM, PRENOM, DATE_NAIS, LIEU_NAIS, ADRESSE, NUM_TEL,
				DATE_INSCR);
		this.prenomPere = prenomP;
		this.profPere = profP;
		this.nomMere = nomM;
		this.prenomMere = prenomM;
		this.profMere = profM;
	}

	public STUDENT() {
		super("0", "", "", "01/01/1990", "", "", "", "01/01/1990");
		this.prenomPere = "";
		this.profPere = "";
		this.nomMere = "";
		this.prenomMere = "";
		this.profMere = "";
	}

}
