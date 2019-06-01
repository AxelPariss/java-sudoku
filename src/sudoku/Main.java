package sudoku;

public class Main {

	public static void main(String[] args) {

		Sudoku s = new Sudoku();
		int[][] map = {
				{5,3,0,0,7,0,0,0,0},
				{6,0,0,1,9,5,0,0,0},
				{0,9,8,0,0,0,0,6,0},

				{8,0,0,0,6,0,0,0,3},
				{4,0,0,8,0,3,0,0,1},
				{7,0,0,0,2,0,0,0,6},

				{0,6,0,0,0,0,2,8,0},
				{0,0,0,4,1,9,0,0,5},
				{0,0,0,0,8,0,0,7,9}
		};


		s.setGame(map);
		s.showLogs(); // Si on veut voir le détail de l'algorithme
		s.resolve();
		s.showGame();

		if(s.hasResolved()){
			System.out.println("Sudoku résolu!");
		}else{
			System.out.println("L'algo à du mal...");
		}
	}

}
