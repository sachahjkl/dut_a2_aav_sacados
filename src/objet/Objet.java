package objet;

import java.util.List;

public class Objet implements Comparable<Objet> {
	private String nom;
	private double poids, valeur;

	public Objet(String n, double p, double v) {
		this.nom = new String(n);
		this.poids = p;
		this.valeur = v;
	}

	public String getNom() {
		return this.nom;
	}

	public double getPoids() {
		return this.poids;
	}

	public double getValeur() {
		return this.valeur;
	}

	public double getRapportVP() {
		return this.valeur / this.poids;
	}

	public int compareTo(Objet o) {
		return o.getRapportVP() > this.getRapportVP() ? -1 : (o.getRapportVP() < this.getRapportVP() ? 1 : 0);
	}

	public String toString() {
		return this.getNom() + " ; " + this.getPoids() + " ; " + this.getValeur();
	}

	public static double getSommeVal(List<Objet> lo) {
		double res = 0;
		for (Objet o : lo)
			res += o.valeur;
		return res;
	}

	public static double getSommePoids(List<Objet> lo) {
		double res = 0;
		for (Objet o : lo)
			res += o.poids;
		return res;
	}
}
