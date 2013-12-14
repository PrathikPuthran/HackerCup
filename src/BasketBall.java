import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;


public class BasketBall {
	public static void main(String[] args) throws NumberFormatException, IOException {
		//BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		BufferedReader reader = new BufferedReader(new FileReader(new File("inp/basketball_game.txt")));
		PrintWriter writer = new PrintWriter(new File("inp/opt_2.txt"));
		int t = Integer.parseInt(reader.readLine());
		for(int i=0; i<t; i++) {
			String[] inp = reader.readLine().split(" ");
			int n = Integer.parseInt(inp[0]);
			int m = Integer.parseInt(inp[1]);
			int p = Integer.parseInt(inp[2]);
			Player[] players = new Player[n];
			for(int j=0; j<n; j++) {
				String[] pD = reader.readLine().split(" ");
				players[j] = new Player(pD[0], Integer.parseInt(pD[1]), Integer.parseInt(pD[2]));
			}
			writer.write("Case #"+(i+1)+": "+solve(players, m, p) + "\n");
		}
		reader.close();
		writer.close();
	}
	
	public static String solve(Player[] players, int m, int p) {
		Arrays.sort(players, new Comparator<Player>() {

			@Override
			public int compare(Player o1, Player o2) {
				if(o1.shotPercent != o2.shotPercent) {
					return o2.shotPercent - o1.shotPercent;
				} 
				return o2.height - o1.height;
			}
		}); 
		List<Player> team1 = new ArrayList<Player>();
		List<Player> team2 = new ArrayList<Player>();
		PriorityQueue<Player> p1 = new PriorityQueue<Player>();
		PriorityQueue<Player> p2 = new PriorityQueue<Player>();
		PriorityQueue<Player> r1 = new PriorityQueue<Player>(11, new Comparator<Player>() {

			@Override
			public int compare(Player o1, Player o2) {
				if(o1.minPlayed == o2.minPlayed) {
					return o1.draftNo - o2.draftNo;
				} else {
					return o1.minPlayed - o2.minPlayed;
				}
			}
		});
		PriorityQueue<Player> r2 = new PriorityQueue<Player>(11, new Comparator<Player>() {
			@Override
			public int compare(Player o1, Player o2) {
				if(o1.minPlayed == o2.minPlayed) {
					return o1.draftNo - o2.draftNo;
				} else {
					return o1.minPlayed - o2.minPlayed;
				}				
			}
		});
		int n = players.length;
		int c1 = 0, c2=0;
		for(int i=0; i<n; i++) {
			players[i].draftNo = i;
			if(i%2 == 0) {
				if(c1<p) {
					p1.add(players[i]);
				} else {
					r1.add(players[i]);
				}
				c1++;
				team1.add(players[i]);
			} else {
				if(c2<p) {
					p2.add(players[i]);
				} else {
					r2.add(players[i]);
				}
				c2++;
				team2.add(players[i]);
			}
		}
		for(int i=0; i<m; i++) {
			for(Player plr : p1) {
				plr.minPlayed++;
			}
			for(Player plr : p2) {
				plr.minPlayed++;
			}
			if(r1.size()>0) {
				Player pT1r = p1.remove();
				Player pT1a = r1.remove();
				p1.add(pT1a);
				r1.add(pT1r);
			}
			if(r2.size() > 0) {
				Player pT2r = p2.remove();
				Player pT2a = r2.remove();
				p2.add(pT2a);
				r2.add(pT2r);
			}
		}
		String[] res = new String[2*p];
		int i=0;
		for(Player plr : p1) {
			res[i++] = plr.name;
		}
		for(Player plr : p2) {
			res[i++] = plr.name;
		}
		Arrays.sort(res);
		String ret = "";
		for(int j=0; j<res.length; j++) {
			if(j==0) {
				ret = res[j];
			} else {
				ret = ret +" " + res[j];
			}
		}
		return ret;
	}
	
	public static class Player implements Comparable<Player> {
		String name;
		int shotPercent;
		int height;
		int draftNo;
		int minPlayed = 0;
		public Player(String nam, int sP, int h) {
			this.name = nam;
			this.shotPercent = sP;
			this.height = h;
		}
		@Override
		public int compareTo(Player o) {
			if(o.minPlayed == minPlayed) {
				return o.draftNo - this.draftNo;
			} 
			return o.minPlayed - minPlayed;
		}
	}
}
