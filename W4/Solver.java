import java.util.Comparator;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;

public class Solver {
	
	private Stack<Board> res = null;
	private int moves;
	private boolean solvable = true;
	
	//comparators
	private static final Comparator<SearchNode> BY_HAM = new ByHam();
	private static class ByHam implements Comparator<SearchNode>
	{
		public int compare(SearchNode a, SearchNode b)
		{
			int v1 = a.Current.hamming() + a.moves;
			int v2 = b.Current.hamming() + b.moves;
			if (v1 < v2)
				return -1;
			else if (v1 > v2)
				return 1;
			else
				return 0;
		}
	}

	private static final Comparator<SearchNode> BY_MAN = new ByMan();
	private static class ByMan implements Comparator<SearchNode>
	{
		public int compare(SearchNode a, SearchNode b)
		{
			int v1 = a.Current.manhattan() + a.moves;
			int v2 = b.Current.manhattan() + b.moves;
			if (v1 < v2)
				return -1;
			else if (v1 > v2)
				return 1;
			else
				return 0;
		}
	}

	private class SearchNode{
		SearchNode Prev;
		Board Current;
		int moves;
		
		
		public SearchNode(SearchNode p, Board c, int m)
		{
			Prev = p;
			Current = c;
			moves = m;
		}
		

	}
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial)
    {
    	// MinPQ Q
    	MinPQ<SearchNode> pq = new MinPQ<SearchNode>(BY_MAN);
    	//MinPQ<SearchNode> pq = new MinPQ<SearchNode>(BY_HAM);
    	
    	// MinPQ for twin
    	MinPQ<SearchNode> tpq = new MinPQ<SearchNode>(BY_MAN);
    	//MinPQ<SearchNode> tpq = new MinPQ<SearchNode>(BY_HAM);
    	
    	SearchNode node = new SearchNode(null, initial, 0);//start with 0 moves
    	SearchNode tNode = new SearchNode(null, initial.twin(), 0);
    	
    	// Insert initial board to min PQ
    	pq.insert(node);
    	
    	//insert twin
    	tpq.insert(tNode);
    	
    	// until we find a solution - BFS
    	//System.out.println("Solving");
    	//int iter = 0;
    	//while(iter <= 500000)
    	while(true)
    	{
    		SearchNode n = pq.delMin();
    		
    		//System.out.println("-----------------------------Normal----------------------------------");
    		//System.out.printf("Selected choice: score = %d + %d\n", n.Current.hamming(), moves);
    		//System.out.printf("Slected choice: score = %d + %d\n", n.Current.manhattan(), n.moves);
    		//System.out.println(n.Current.toString());
    		
    		if(n.Current.isGoal())
    		{
    			moves = n.moves;
    			//System.out.printf("total iterations to solve %d\n", iter);
    			res = new Stack<Board>();
    			// create iterable
    			while(n != null)
    			{
    				res.push(n.Current);
    				n = n.Prev;
    			}

    			break;
    		}
    		
    		//System.out.printf("Adding neighbors, moves = %d\n", n.moves + 1);
    		moves = n.moves + 1;
    		// compute neighbors(Board class), find min from PQ
    		for(Board b : n.Current.neighbors())
    		{
    			// avoid queueing, if its already in the queue
    			if(!isExisting(b, n))
    			{
    				//System.out.println(b.toString());
    				//System.out.printf("score = %d + %d\n", b.manhattan(), n.moves + 1);
    				//System.out.printf("score = %d + %d\n", b.hamming(), n.moves + 1);
    				SearchNode nb = new SearchNode(n,b,n.moves + 1);
    				pq.insert(nb);
    			}
    		}

    		
    		// check feasibility for twin board
    		SearchNode tn = tpq.delMin();

    		//System.out.println("-----------------------------twin----------------------------------");;
    		//System.out.printf("Selected choice: score = %d + %d\n", tn.Current.hamming(), tn.moves);
    		//System.out.printf("Slected choice: score = %d + %d\n", tn.Current.manhattan(), tn.moves);
    		//System.out.println(tn.Current.toString());
    		
    		if(tn.Current.isGoal())
    		{
    			// board is unsolvable
    			//System.out.println("twin board solved!");
    			solvable = false;
    			moves = -1;
    			break;
    		}
    		
    		for(Board b : tn.Current.neighbors())
    		{
    			if(!isExisting(b, tn))
    			{
    				//System.out.println(b.toString());
    				//System.out.printf("score = %d + %d\n", b.manhattan(), n.moves + 1);
    				//System.out.printf("score = %d + %d\n", b.hamming(), n.moves + 1);
    				SearchNode nb = new SearchNode(tn, b, tn.moves + 1);
    				tpq.insert(nb);
    			}
    		}
    		
    		//tracker
    		//System.out.printf("%c", '.');
    		//if(iter++ % 80 == 0)
    		//	System.out.println("\n");
    	}
    	
    	
    	//if(iter >= 500000)
    	//	System.out.println("!!****reached limit*****!!");

    }

    private boolean isExisting(Board b, SearchNode n)
    {

    	while(n != null)
    	{
    		if(b.equals(n.Current))
    			return true;
    		n = n.Prev;
    	}
    	return false;
    }
    // is the initial board solvable? (see below)
    public boolean isSolvable()
    {
    	return solvable;
    }

    // min number of moves to solve initial board
    public int moves()
    {
    	return moves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution()
    {
    	// Keep list of selected neighbors
    	return res;
    }

    // test client (see below) 
    public static void main(String[] args)
    {
    	In in = new In(args[0]);
    	
    	int n = in.readInt();
    	
    	int[][] tiles = new int[n][n];
    	for (int i = 0; i < n; i++)
    	{
    		for (int j = 0; j < n; j++)
    		{
    			tiles[i][j] = in.readInt();
    		}
    	}

    	Board tb = new Board(tiles);

    	//System.out.println("**** Input ******");
    	//System.out.println(tb.toString());
    	
    	Solver sol = new Solver(tb);
    	if(sol.isSolvable())
    	{
    		//System.out.println("!!!!***** Solution****!!!!");
    		System.out.printf("Minimum number of moves = %d\n", sol.moves());
    		for (Board t : sol.solution())
    		{
    			System.out.println(t.toString());
    		}
    	}
    	else
    	{
    		System.out.println("No solution possible!");
    	}
    }

}