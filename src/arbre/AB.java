package arbre;

import java.util.ArrayList;
import java.util.List;

import objet.Objet;

public class AB {
	private ArrayList<Objet> ens;
	private static double valMin = 0, poidsMax = 0;
	private AB fg;
	private AB fd;

	public AB() {
		this.fg = this.fd = null;
		this.ens = new ArrayList<>();
	}

	private AB(ArrayList<Objet> ens) {
		this.ens = new ArrayList<>(ens);
		this.fg = this.fd = null;
	}

	public void ajout(Objet o, double vo_restant) {
		double valEns = Objet.getSommeVal(ens);
		if (fd != null)
			fd.ajout(o, vo_restant);
		if (fg != null)
			fg.ajout(o, vo_restant);
		if (fd == null && (Objet.getSommePoids(this.ens) + o.getPoids() <= AB.poidsMax)
				&& (valEns + vo_restant >= AB.valMin)) {
			AB.valMin = valEns + o.getValeur() > AB.valMin ? valEns + o.getValeur() : AB.valMin;
			ArrayList<Objet> tmp = new ArrayList<Objet>(this.ens);
			tmp.add(o);
			fd = new AB(tmp);
		}
		if (fg == null && valEns + vo_restant - o.getValeur() >= AB.valMin)
			fg = new AB(ens);
	}

	public List<Objet> max() {
		if (fd == null && fg == null)
			return this.ens;
		if (fd != null && fg != null) {
			if (Objet.getSommeVal(fd.max()) > Objet.getSommeVal(fg.max()))
				return fd.max();
			if (Objet.getSommeVal(fd.max()) == Objet.getSommeVal(fg.max()))
				return fd.max();
			else
				return fg.max();
		}
		if (fd == null && fg != null)
			return fg.max();
		else
			return fd.max();
	}

	public static void setValMin(double v) {
		AB.valMin = v;
	}

	public static void setPoidsMax(double p) {
		AB.poidsMax = p;
	}
}
