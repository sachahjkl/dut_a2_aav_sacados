package sacADos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import arbre.AB;
import objet.Objet;

public class SacADos {
	private String chemin;
	private float poids_maximal;
	private ArrayList<Objet> transporte, obj_pos;

	public SacADos(String c, float p) {
		this();
		this.chemin = c;
		this.poids_maximal = p;
		this.obj_pos = new ArrayList<>();
	}

	public SacADos() {
		this.transporte = new ArrayList<>();
	}

	public void lectureFic() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(this.chemin));
		String s = reader.readLine();
		while (s != null) {
			String[] split = s.split(" ; ");
			split[1] = split[1].contains(".") ? split[1] : split[1] + ".0";
			split[2] = split[2].contains(".") ? split[2] : split[2] + ".0";
			this.obj_pos.add(new Objet(split[0], Float.parseFloat(split[1]), Float.parseFloat(split[2])));
			s = reader.readLine();
		}
		reader.close();
	}

	public float getPoids() {
		return Objet.getSommePoids(transporte);
	}

	public float getVal() {
		return Objet.getSommeVal(transporte);
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (transporte.size() == 0)
			return sb.toString();
		for (int i = 0; i < transporte.size() - 1; ++i) {
			sb.append(transporte.get(i) + "\n");
		}
		sb.append(this.transporte.get(transporte.size() - 1));
		return sb.toString();
	}

	public void viderSac() {
		this.transporte.clear();
	}

	public void resoudreGloutone() {
		this.viderSac();
		Collections.sort(this.obj_pos, Collections.reverseOrder());
		for (Objet o : this.obj_pos) {
			if (this.getPoids() + o.getPoids() <= this.poids_maximal)
				this.transporte.add(o);
		}
	}

	public void resoudreProgDyn() {
		this.viderSac();
		int poidsMaxS = (int) (this.poids_maximal * 10);
		float[][] M = new float[this.obj_pos.size()][(int) poidsMaxS + 1];
		for (int j = 0; j < poidsMaxS + 1; ++j) {
			if (this.obj_pos.get(0).getPoids() * 10 > j)
				M[0][j] = 0f;
			else
				M[0][j] = this.obj_pos.get(0).getValeur();
		}
		for (int i = 1; i < M.length; ++i) {
			for (int j = 0; j < poidsMaxS + 1; ++j) {
				if (this.obj_pos.get(i).getPoids() * 10 > j)
					M[i][j] = M[i - 1][j];
				else
					M[i][j] = Math.max(M[i - 1][j], M[i - 1][(int) (j - this.obj_pos.get(i).getPoids() * 10)]
							+ this.obj_pos.get(i).getValeur());
			}
		}
		int j = poidsMaxS;
		int i = M.length - 1;
		while (M[i][j] == M[i][j - 1])
			--j;
		while (j > 0) {
			while (i > 0 && M[i][j] == M[i - 1][j]) {
				--i;
			}
			j = (int) (j - (this.obj_pos.get(i).getPoids() * 10));
			if (j >= 0) // j>0 selon le tutoriel mais dans ce cas, refuse que le sac soit exactement
						// rempli.
				this.transporte.add(this.obj_pos.get(i));
			--i;
		}
	}

	public void resoudrePSE() {
		AB a = new AB();
		ArrayList<Objet> tmp = new ArrayList<>(this.obj_pos);
		this.resoudreGloutone();
		AB.setValMin(this.getVal());
		AB.setPoidsMax(poids_maximal);
		this.viderSac();
		for (Objet o : obj_pos) {
			a.ajout(o, Objet.getSommeVal(tmp));
			tmp.remove(o);
		}
		this.transporte = new ArrayList<Objet>(a.max());
	}

	public static void main(String[] args) throws IOException {
//		SacADos mon_sac = new SacADos("./items/items.txt", 30f); // différence avec poids max 30f et fic. itemsEval
		SacADos mon_sac = new SacADos(args[0], Float.parseFloat(args[1]));
		String methode = args[2];
//		String methode = "pse"; // gloutonne, prog. dynamique, pse
		mon_sac.lectureFic();
		long startTime = System.nanoTime();
		switch (methode) {
		case "gloutonne":
			mon_sac.resoudreGloutone();
			break;
		case "prog.dynamique":
			mon_sac.resoudreProgDyn();
			break;
		case "pse":
			mon_sac.resoudrePSE();
			break;
		default:
			System.err.println("Vous n'avez pas saisie une methode valide. (gloutonne, prog.dynamique ou pse)");
			System.exit(-1);
		}
		long endTime = System.nanoTime();
		long timeElapsed = endTime - startTime;
		TimeRecord.addTime(methode, timeElapsed);
		System.out.println("Temps ecoule: " + timeElapsed + "ns");
		System.out.println(mon_sac.toString());
		System.out.println("Poids : " + mon_sac.getPoids() + "; Valeur : " + mon_sac.getVal());
		// commentaire diff
	}
}
