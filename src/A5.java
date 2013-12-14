import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;


public class A5 {
	public static void main(String[] args) throws FileNotFoundException {
		String pathname = "inp/aaaaaa.txt";
		Scanner scanner = new Scanner(new File(pathname));
		String opt = "inp/a5_out.txt";
		PrintWriter writer = new PrintWriter(new File(opt));
		int t = scanner.nextInt();
		scanner.nextLine();
		for(int i=0; i<t; i++) {
			int n = scanner.nextInt();
			int m = scanner.nextInt();
			scanner.nextLine();
			String[] lot = new String[n];
			for(int j=0; j<n; j++) {
				lot[j] = scanner.nextLine();
			}
			writer.write("Case #"+(i+1)+":" + solv(lot) + "\n");
		}
		scanner.close();
		writer.close();
	}
	
	public static int solv(String[] lot) {
		int n = lot.length;
		int m = lot[0].length();
		int[][][][] dp = new int[n][m][4][2];
		for(int i=0; i<n; i++) {
			for(int j=0; j<m; j++) {
				for(int k=0; k<4; k++) {
					for(int l=0; l<2; l++)
						dp[i][j][k][l] = -1;
				}
			}
		}
		return f(0, 0, -1, 0, lot, dp);
	}
	
	public static int f(int r, int c, int d, int isOp, String[] lot, int[][][][] dp) {
		int rM = lot.length;
		int cM = lot[0].length();
		if(r>=rM || c>=cM || r<0 || c<0 || lot[r].charAt(c) == '#') {
			return 0;
		} else if(d!= -1 && dp[r][c][d][isOp] != -1) {
			return dp[r][c][d][isOp];
		} else {
			if(d == -1) {
				return 1 + Math.max(f(r, c+1, 2, 0, lot, dp), f(r+1, c, 3, 0, lot, dp));
			} else {
				int m1 = 1;
				if(d==2) {
					int r1 = f(r, c+1, 2, isOp, lot, dp);
					int d1 = f(r+1, c, 3, isOp, lot, dp);
					int u1 = 0;
					if(isOp == 0) {
						u1 = f(r-1, c, 1, 1, lot, dp);
					}
					m1 = m1 + Math.max(Math.max(r1, d1), u1);
				} else if(d==3) {
					int r1 = f(r, c+1, 2, isOp, lot, dp);
					int d1 = f(r+1, c, 3, isOp, lot, dp);
					int l1 = 0;
					if(isOp == 0) {
						l1 = f(r, c-1, 0, 1, lot, dp);
					}
					m1 = m1 + Math.max(Math.max(r1, d1), l1);
				} else if(d==0) {
					int d1 = f(r+1, c, 3, isOp, lot, dp);
					int l1 = f(r, c-1, 0, 1, lot, dp);
					m1 = m1 + Math.max(d1, l1);
				} else {
					int r1 = f(r, c+1, 2, isOp, lot, dp);
					int u1 = f(r-1, c, 1, 1, lot, dp);
					m1 = m1 + Math.max(r1, u1);
				}
				dp[r][c][d][isOp] = m1;
				return m1;
			}
		}
	}
}
