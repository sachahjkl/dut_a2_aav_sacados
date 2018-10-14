package objet;

import java.util.List;

public class Objet implements Comparable<Objet> {
	private String nom;
	private float poids, valeur;

	public Objet(String n, float p, float v) {
		this.nom = new String(n);
		this.poids = p;
		this.valeur = v;
	}

	public String getNom() {
		return this.nom;
	}

	public float getPoids() {
		return this.poids;
	}

	public float getValeur() {
		return this.valeur;
	}

	public float getRapportVP() {
		return this.valeur / this.poids;
	}

	public int compareTo(Objet o) {
		return o.getRapportVP() > this.getRapportVP() ? -1 : (o.getRapportVP() < this.getRapportVP() ? 1 : 0);
	}

	public String toString() {
		return this.getNom() + " ; " + this.getPoids() + " ; " + this.getValeur();
	}

	public static float getSommeVal(List<Objet> lo) {
		float res = 0;
		if(lo == null) return res;
		for (Objet o : lo)
			res += o.valeur;
		return res;
	}

	public static float getSommePoids(List<Objet> lo) {
		float res = 0;
		if(lo == null) return res;
		for (Objet o : lo)
			res += o.poids;
		return res;
	}
}
