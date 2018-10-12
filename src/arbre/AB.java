package arbre;

import java.util.LinkedList;
import java.util.List;

import objet.Objet;

public class AB {
	private LinkedList<Objet> ens;
	private static double valMin = 0, poidsMax = 0;
	private AB fg;
	private AB fd;

	public AB() {
		this.fg = this.fd = null;
		this.ens = new LinkedList<>();
	}

	private AB(LinkedList<Objet> ens) {
		this.ens = new LinkedList<>(ens);
		this.fg = this.fd = null;
	}

	public void ajout(Objet o, double vo_restant) {
		double valEns = Objet.getSommeVal(ens);
		if (fd == null && fg == null) {
			if ((Objet.getSommePoids(this.ens) + o.getPoids()) <= AB.poidsMax
					&& valEns + vo_restant + o.getValeur() >= AB.valMin) {
				AB.valMin = valEns + o.getValeur() > AB.valMin ? valEns + o.getValeur() : AB.valMin;
				LinkedList<Objet> tmp = new LinkedList<Objet>(this.ens);
				tmp.add(o);
				fd = new AB(tmp);
				fg = new AB(this.ens);
			}

		} else {
			fd.ajout(o, vo_restant);
			fg.ajout(o, vo_restant);
		}
	}

	public List<Objet> max() {
		if (fd == null && fg == null)
			return this.ens;
		if (Objet.getSommeVal(fd.max()) > Objet.getSommeVal(fg.max()))
			return fd.max();
		if (Objet.getSommeVal(fd.max()) == Objet.getSommeVal(fg.max()))
			return fd.max();
		else
			return fg.max();
	}

	public static void setValMin(double v) {
		AB.valMin = v;
	}

	public static void setPoidsMax(double p) {
		AB.poidsMax = p;
	}
}
