import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {		
		int [][] map = convertStringToMap();
		findLowPoints(map);
	}
	
	public static int calcBasin(int x, int y, boolean [][] visited,int[][] map ) {		
		visited[x][y]=true;
		int counter = 1;
		if(map[x][y]==9) {
			return 0;
		}
		//west
		if(x>0) {
			if(visited[x-1][y]==false) {
				if(map[x][y]<map[x-1][y]) {
					counter +=calcBasin(x-1,y,visited,map);
				}
			}			
		}
		//east
		if(x<map.length-1) {
			if(visited[x+1][y]==false) {
				if(map[x][y]<map[x+1][y]) {
					counter+=calcBasin(x+1,y,visited,map);
				}
			}		
		}
		//north
		if(y>0) {
			if(visited[x][y-1]==false) {
				if(map[x][y]<map[x][y-1]) {
					counter+=calcBasin(x,y-1,visited,map);
				}
			}
			
		}
		//south
		if(y<map[0].length-1) {
			if(visited[x][y+1]==false) {
				if(map[x][y]<map[x][y+1]) {
					counter+=calcBasin(x,y+1,visited,map);
				}
			}			
		}		
		return counter;
	}
	
	public static void findLowPoints(int[][] map) {
		boolean [][] visited = new boolean[map.length][map[0].length];
		int sum =0;
		ArrayList<Integer> basins = new ArrayList<Integer>();
		for(int x=0; x<map.length;x++) {
			for(int y=0;y<map[0].length; y++) {
				int value = map[x][y];
				int north= -1;
				int south = -1;
				int west = -1;
				int east = -1;
				if(x>0) {
					west = map[x-1][y];
				}
				if(x<map.length-1) {
					east = map[x+1][y];
				}
				if(y>0) {
					north = map[x][y-1];
				}
				if(y<map[0].length-1) {
					south = map[x][y+1];
				}
				if((west==-1||west>value)&&(east==-1||east>value)&&(north==-1||north>value)&&(south==-1||south>value)) {
					sum += value;
					sum++;
					basins.add((calcBasin(x,y,visited,map)));
				}
			}
		}
		Collections.sort(basins, Collections.reverseOrder());
		System.out.println("Basin multiple: " + basins.get(0)*basins.get(1)*basins.get(2));
		System.out.println("Risk " + sum);					
	}
	
	public static int[][] convertStringToMap() {
		//load the file
		File file = new File("data.txt");
		Scanner sc = null;
		 try {
				sc = new Scanner(file);
		 } catch (FileNotFoundException e) {
				System.err.print("could not find file");
		 }
		ArrayList<String> words = new ArrayList<String>();
		while(sc.hasNextLine()) {
			words.add(sc.nextLine());
		}
		int length = words.get(0).length();
		int height = words.size();
		int [][] map = new int [length][height];
		
		for(int y=0; y<words.size();y++) {
			for(int x=0; x<words.get(0).length();x++) {
				map[x][y] = Character.getNumericValue(words.get(y).charAt(x));
			}
		}
		return map;
	}
}
