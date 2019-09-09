package org;

public class PERSONNE {

	public String ID;
	public String NOM;
	public String PRENOM;
	public String DATE_NAIS;
	public String LIEU_NAIS;
	public String ADRESSE;
	public String NUM_TEL;
	public String DATE_INSCRIPTION;
	
	public PERSONNE(String id, String NOM, String PRENOM, String DATE_NAIS, String LIEU_NAIS, 
	 		String ADRESSE, String NUM_TEL, String DATE_INSC)
	{
		this.ID = id;
		this.NOM = NOM;
		this.PRENOM = PRENOM;
		this.DATE_NAIS = DATE_NAIS;
		this.LIEU_NAIS = LIEU_NAIS;
		this.ADRESSE = ADRESSE;
		this.NUM_TEL = NUM_TEL;
		this.DATE_INSCRIPTION = DATE_INSC;
	}
}
