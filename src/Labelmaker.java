import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;


public class Labelmaker {
	
	private static long max = Long.MAX_VALUE;
	
	public static void main(String[] args) throws FileNotFoundException {
		String pathname = "inp/labelmaker.txt";
		Scanner scanner = new Scanner(new File(pathname));
		String opt = "inp/lab1.txt";
		PrintWriter writer = new PrintWriter(new File(opt));
		int t = scanner.nextInt();
		scanner.nextLine();
		for(int i=0; i<t; i++) {
			String c1 = scanner.nextLine();
			String[] dat = c1.split(" ");
			String vals = dat[0];
			long n  = Long.parseLong(dat[1]);
			writer.write("Case #"+(i+1) +": "+ go2(vals, n) + "\n");
		}
		scanner.close();
		writer.close();
	}
		
	public static String go2(String val, long n) {
		long nT = val.length();
		if(nT == 1) {
			String result = "";
			for(int i=0;i<n; i++) {
				result += val.charAt(0);
			}
			return result;
		}
		int tm = 0;
		long sum  = nT;
		long curP = nT;
		boolean isOf = false;
		while(n>sum) {
			if(curP > max/nT) {
				isOf = true;
				break;
			}
			tm++;
			curP = curP * nT;
			if(sum > max - curP) {
				break;
			}
			long sum1 = sum + curP;
			if(sum1 >= n) {
				break;
			}
			sum += curP;
		}
		long rel = 0;
		long sub = curP;
		if(tm!=0) {
			rel = n - sum;
		} else {
			rel = n;
		}
		rel--;
		String result = "";
		if(!isOf) {
			sub = sub/nT;
		}
		while(true) {
			long ind = rel/sub;
			for(int i=0; i<val.length(); i++) {
				if(i == (int)ind) {
					result += val.charAt(i);
					break;
				} 
			}
			if(sub == 1) {
				break;
			}
			rel = rel%sub;
			sub = sub/nT;	
		}
		return result;
	}
}
