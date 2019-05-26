package sudoku;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Sudoku {

	private int m_map[][][] = new int[9][9][9]; // Grille
	private boolean solved; // Résolu ou non
	private boolean has_updated; // Vrai si le "round" actuel à permi de faire avancer la résolution

	public void setGame(int[][] map) {
		solved = false;

		// On crée une grille des possibilités
		for(int x = 0; x < map.length; x++) {
			for(int y = 0; y < map[x].length; y++) {

				// S'il n'y a pas de solution initiale, je définis l'ensemble des possibilités (allant de 1 à 9)
				if(map[x][y] == 0) {
					int[] newInt = {1,2,3,4,5,6,7,8,9};
					m_map[x][y] = newInt;
				}else {
					int[] newInt = {map[x][y],0,0,0,0,0,0,0,0};
					m_map[x][y] = newInt;
				}
			}
		}
	}

	public void resolve() {
		has_updated = true; // On le définit à true pour démarrer le 1er tour

		while (solved == false && has_updated == true){
			has_updated = false; // On le définit à false parce que les "resolvers" vont le mettre true s'ils trouvent une nouvelle solution

			resolveHorizontal();
			resolveVertical();
			resolveBlock();

			solved = checkWin();
		}

	}

	public boolean hasResolved() {
		return solved;
	}

	// Vérifie si une case a une solution
	private boolean hasSolvedNumber(int horizontal, int vertical){
		int solutions = 0;
		for (int u = 0; u < 9; u++) {
			if(m_map[vertical][horizontal][u] > 0){
				solutions++;
			}
		}

		return (solutions == 1);
	}

	// Récupère la solution d'une case
	private int getSolvedNumber(int horizontal, int vertical){
		int solution = 0;
		for (int u = 0; u < 9; u++) {
			if(m_map[vertical][horizontal][u] > 0){
				solution = m_map[vertical][horizontal][u];
			}
		}
		return solution;
	}


	// Trouve des solutions avec la méthode des lignes horizontales
	private void resolveHorizontal() {

		// Pour chaque ligne
		for(int x = 0; x < m_map.length; x++) {

			// Tableau des chiffres utiliséss
			ArrayList used = new ArrayList<Integer>();

			// Pour chaque colonne
			for(int y = 0; y < m_map[x].length; y++) {

				// Si un nombre est utilisé, on l'ajoute au tableau
				if(hasSolvedNumber(y, x)){
					used.add(getSolvedNumber(y, x));
				}
			}

			// N.B : On a trouvé l'ensemble des chiffres utilisés pour une même ligne. A présent, on va supprimer les solutions impossibles

			// Pour chaque colonne
			for(int y = 0; y < m_map[x].length; y++) {

				// Pour chaque solution possible d'une colonne
				for(int z = 0; z < m_map[x][y].length; z++) {

					for(int u = 0; u < used.size(); u++) {

						// Si la solution probable est déja utilisée (et qu'elle n'est pas la solution de sa case), on la supprime
						if(m_map[x][y][z] == (int) used.get(u) && !hasSolvedNumber(y, x)){
							m_map[x][y][z] = 0;
							has_updated = true;
						}
					}
				}
			}
		}
	}

	// Trouve des solutions avec la méthode des lignes verticales
	private void resolveVertical() {

		// Pour chaque ligne
		for(int x = 0; x < m_map.length; x++) {

			// Tableau des chiffres utiliséss
			ArrayList used = new ArrayList<Integer>();

			// Pour chaque colonne
			for(int y = 0; y < m_map[x].length; y++) {

				// Si un nombre est utilisé, on l'ajoute au tableau
				if(hasSolvedNumber(x, y)){
					used.add(getSolvedNumber(x, y));
				}
			}

			// N.B : On a trouvé l'ensemble des chiffres utilisés pour une même colonne. A présent, on va supprimer les solutions impossibles

			// Pour chaque colonne
			for(int y = 0; y < m_map[x].length; y++) {

				// Pour chaque solution possible d'une colonne
				for(int z = 0; z < m_map[y][x].length; z++) {

					for(int u = 0; u < used.size(); u++) {

						// Si la solution probable est déja utilisée (et qu'elle n'est pas la solution de sa case), on la supprime
						if(m_map[y][x][z] == (int) used.get(u) && !hasSolvedNumber(x, y)){
							m_map[y][x][z] = 0;
							has_updated = true;
						}
					}
				}
			}
		}
	}

	private void resolveBlock() {

		for(int b = 1; b < 4; b++) {
			for(int c = 1; c < 4; c++) {

				// Pour chaque bloc (carré de 9 cases)


				// Tableau des chiffres utiliséss
				ArrayList used = new ArrayList<Integer>();

				// Pour chaque case dans le bloc
				for(int x = (b-1)*3; x < b*3; x++) {
					for (int y = (c - 1) * 3; y < c * 3; y++) {

						// Si un nombre est utilisé, on l'ajoute au tableau
						if (hasSolvedNumber(x, y)) {
							used.add(getSolvedNumber(x, y));
						}
					}
				}

				// N.B : On a trouvé l'ensemble des chiffres utilisés pour un même bloc. A présent, on va supprimer les solutions impossibles

				// Pour chaque bloc
				for(int x = (b-1)*3; x < b*3; x++) {
					for (int y = (c - 1) * 3; y < c * 3; y++) {

						for(int z = 0; z < m_map[y][x].length; z++) {
							for (int u = 0; u < used.size(); u++) {

								// Si la solution probable est déja utilisée (et qu'elle n'est pas la solution de sa case), on la supprime
								if (m_map[y][x][z] == (int) used.get(u) && !hasSolvedNumber(x, y)) {
									m_map[y][x][z] = 0;
									has_updated = true;
								}
							}
						}
					}
				}
			}
		}

	}


	// Vérifie si on a résolu le Sudoku
	private boolean checkWin() {
		for (int x = 0; x < m_map.length; x++) {
			for (int y = 0; y < m_map[x].length; y++) {
				if(!hasSolvedNumber(y, x)){
					return false;
				}
			}
		}
		return true;
	}


	// Affiche la grille
	public void showGame(){
		for(int x = 0; x < m_map.length; x++) {
			String line = "";
			for(int y = 0; y < m_map[x].length; y++) {
				line = line + "(";
				for(int z = 0; z < m_map[x][y].length; z++) {
					if(m_map[x][y][z] != 0){
						line = line + m_map[x][y][z];
				}
				}
				line = line + ")";
			}
			System.out.println(line);
		}
	}
}