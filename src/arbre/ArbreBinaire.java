package arbre;

public class ArbreBinaire {
	private int valeur;
	private ArbreBinaire sousAbGauche = null;
	private ArbreBinaire sousAbDroit = null;
	
	public ArbreBinaire(int x, ArbreBinaire g, ArbreBinaire d) {
		this.valeur = x;
	}

	public int getValeur() {
		return valeur;
	}

	public ArbreBinaire getSousAbGauche() {
		return sousAbGauche;
	}

	public ArbreBinaire getSousAbDroit() {
		return sousAbDroit;
	}
	
}
