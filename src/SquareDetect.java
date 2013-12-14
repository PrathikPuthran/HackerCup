import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;


public class SquareDetect {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		//BufferedReader reader = new BufferedReader(new FileReader(new File("inp/square_detector.txt")));
		PrintWriter writer = new PrintWriter(new File("inp/opt.txt"));
		int t = Integer.parseInt(reader.readLine());
		for(int i=0; i<t; i++) {
			int n = Integer.parseInt(reader.readLine());
			String[] img = new String[n];
			for(int j=0; j<n; j++) {
				img[j] = reader.readLine();
			}
			writer.write("Case #"+(i+1)+": " + solve(img) + "\n");
		}
		reader.close();
		writer.close();
	}
	
	public static String solve(String[] img) {
		int minX = -1;
		int minY = -1;
		int maxX = -1;
		int maxY = -1;
		if(img == null) {
			return "NO";
		} else {

			int n = img.length;
			for(int i=0; i<n; i++) {
				for(int j=0; j<n; j++) {
					if(img[i].charAt(j) == '#') {
						if(minX == -1 && minY == -1) {
							minX = j;
							minY = i;
						}
						if(minY == i) {
							maxX = j;
						}
						maxY = Math.max(maxY, i);
						if(j<minX || j>maxX) {
							return "NO";
						}
					} else {
						if(minX != -1 && (i<=maxY) && (j>=minX && j<=maxX)) {
							return "NO";
						}
					}
				}
			}
			int d1 = Math.abs(minX - maxX);
			int d2 = Math.abs(minY - maxY);
			if(d1==d2) {
				return "YES";
			}
			return "NO";
		}
	}
	
	public static String solve2(String[] img) {
		if(img == null) {
			return "NO";
		} else {
			int minX = 100;
			int minY = 100;
			int maxX = -1;
			int maxY = -1;
			int n = img.length;
			for(int y=0; y<n; y++) {
				for(int x=0; x<n; x++) {
					if(img[y].charAt(x) == '#') {
						minX = Math.min(minX, x);
						minY = Math.min(minY, y);
						maxX = Math.max(maxX, x);
						maxY = Math.max(maxY, y);
					}
				}
			}
			int d1 = Math.abs(minX - maxX);
			int d2 = Math.abs(minY - maxY);
			if(d1 != d2) {
				return "NO";
			}
			
			for(int y=0; y<n; y++) {
				for(int x=0; x<n; x++) {
					if(img[y].charAt(x) == '#') {
						if(x<minX || x>maxX) {
							return "NO";
						}
					} else {
						if((y>=minY && y<=maxY) && (x>=minX && x<=maxX)) {
							return "NO";
						}
					}
				}
			}
			return "YES";
		}
	}
}
