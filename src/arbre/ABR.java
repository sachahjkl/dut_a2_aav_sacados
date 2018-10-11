package arbre;

import java.util.LinkedList;
import java.util.List;

import objet.Objet;

public class ABR {
	private LinkedList<Objet> ens;
	private static double valMin = 0, poidsMax = 0;
	private ABR fg;
	private ABR fd;

	public ABR() {
		this.fg = this.fd = null;
		this.ens = new LinkedList<>();
	}

	private ABR(LinkedList<Objet> ens) {
		this.ens = new LinkedList<>(ens);
		this.fg = this.fd = null;
	}

	public LinkedList<Objet> geEnsemble() {
		return ens;
	}

	public void ajout(Objet o, double vo_restant) {
		double valEns = Objet.getSommeVal(ens);
		if (fd == null && fg == null) {
			if ((Objet.getSommePoids(this.ens) + o.getPoids()) <= ABR.poidsMax
					&& valEns + vo_restant + o.getValeur() >= ABR.valMin) {
				ABR.valMin = valEns > ABR.valMin ? valEns : ABR.valMin;
				LinkedList<Objet> tmp = new LinkedList<Objet>(this.ens);
				tmp.add(o);
				fd = new ABR(tmp);
				fg = new ABR(this.ens);
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
		ABR.valMin = v;
	}

	public static void setPoidsMax(double p) {
		ABR.poidsMax = p;
	}
}
