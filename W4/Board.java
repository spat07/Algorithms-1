import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

public class Board {
	
	private int[][] board;
	private final int n;
	
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles)
    {
    	
    	if (tiles == null)
    		throw new IllegalArgumentException("Null input argument!");

    	// create new init board
    	n = tiles.length;
    	
    	if(n < 1)
    		throw new IllegalArgumentException("Unacceptable board size!");
    	
    	board = new int[n][n];
    	for (int i = 0; i < n; i++)
    	{
    		for (int j = 0; j < n; j++)
    		{
    			board[i][j] = tiles[i][j];
    		}
    	}
    	
    }

    // string representation of this board
    public String toString()
    {
    	String str = new String();
    	str = n + "\n";
    	
    	for(int i = 0; i < n; i++)
    	{
    		for(int j = 0; j < n; j++)
    		{
    			str = str.concat(board[i][j] + " ");
    		}
    		str = str.concat("\n");
    	}
    	
    	return str;
    }
    
    

    // board dimension n
    public int dimension()
    {
    	return n;
    }

    // number of tiles out of place
    public int hamming()
    {
    	int h = 0;
    	int count = 1;//goalboard is linear seq
    	
    	for (int i = 0; i < n; i++)
    	{
    		for(int j = 0; j < n; j++)
    		{
    			if(board[i][j] > 0 && board[i][j] != count)
    				h++;
    			count++;
    		}
    	}

    	return h;
    }

    
    // sum of Manhattan distances between tiles and goal
    public int manhattan()
    {
    	int m = 0;
    	int count = 1;
    	for (int i = 0; i < n; i++)
    	{
    		for(int j = 0; j < n; j++)
    		{
    			if(board[i][j] > 0 && board[i][j] != count)
    			{
    				double val = (double) board[i][j];
    				int gr = (int)(Math.ceil(val/n) - 1); //row idx
    				int gc = (int)(val - gr*n - 1); // column idx
    				m += (Math.abs(i - gr) + Math.abs(j - gc));
    			}
    			count++;
    		}
    	}
    	return m;
    }

    // is this board the goal board?
    public boolean isGoal()
    {
    	int count = 1;
    	final int lim = n * n;
    	for(int i = 0; i < n; i++)
    	{
    		for(int j = 0; j < n; j++)
    		{
    			if(board[i][j] != (count%lim))
    				return false;
    			count++;
    		}
    	}
    	
    	return true;
    }

    // does this board equal y?
    public boolean equals(Object y)
    {
    	
    	if (y == null)
    		throw new NullPointerException();

    	if(!this.getClass().equals(y.getClass()))
    		throw new ClassCastException();
    	
    	Board that = (Board)y;
    	
    	if(this.n != that.n)
    		return false;

    	if (Arrays.deepEquals(this.board, that.board))
    		return true;
    	else
    		return false;
    }
    
    private void swap(int zx, int zy, int zxx, int zyy)
    {
    	int temp = this.board[zx][zy];
    	this.board[zx][zy] = this.board[zxx][zyy];
    	this.board[zxx][zyy] = temp;
    }

    // all neighboring boards
    public Iterable<Board> neighbors()
    {
    	int zx = -1, zy = -1;
    	Stack<Board> nb = new Stack<Board>();
    	
    	// find position of the zero, and check for valid neighbors
    	for(int i = 0; i < n; i++)
    		for(int j = 0; j < n; j++)
    			if(board[i][j] == 0)
    			{
    				zx = i;
    				zy = j;
    				i = j = n;
    			}
    	// generate board for valid neighbors
    	if(zx - 1 >= 0)
    	{
    		Board b1 = new Board(board);
    		b1.swap(zx, zy, zx - 1, zy);
    		nb.push(b1);
    	}
		if(zy - 1 >= 0)
		{
    		Board b2 = new Board(board);
    		b2.swap(zx, zy, zx, zy - 1);
    		nb.push(b2);
		}
    	
    	if(zx + 1 < n)
    	{
    		Board b1 = new Board(board);
    		b1.swap(zx, zy, zx + 1, zy);
    		nb.push(b1);
    	}
		if(zy + 1 < n)
		{
    		Board b2 = new Board(board);
    		b2.swap(zx, zy, zx, zy + 1);
    		nb.push(b2);
		}

    	
    	return nb;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin()
    {
    	Board tb = new Board(board);

    	if (n > 1)
    	{    	
	    	for(int i = 0; i < n; i++)
	    	{
	    		if (tb.board[i][0] > 0 && tb.board[i][1] > 0)
	    		{
	    			int t = tb.board[i][0];
	    			tb.board[i][0] = tb.board[i][1];
	    			tb.board[i][1] = t;
	    			break;
	    		}
	    			
	    	}
    		
    	}
    	return tb;
    	
    }

    // unit testing (not graded)
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
    	
    	Board initial = new Board(tiles);
    	System.out.print(initial.toString());
    	System.out.printf("Hamming=%d\n", initial.hamming());
    	System.out.printf("Manhattan=%d\n", initial.manhattan());
    	System.out.println(initial.twin().toString());
    	
    	
    	int[][] tile1 = { {1,2,3,4}, {5,6,7,8},{9,10,11,12}, {13,14,15,0}};
    	Board next = new Board(tile1);
    	System.out.print(next.toString());
    	System.out.printf("Hamming=%d\n", next.hamming());
    	System.out.printf("Manhattan=%d\n", next.manhattan());
    	System.out.println(next.twin().toString());
    	
    }

}
