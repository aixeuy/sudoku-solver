import java.util.*;
import java.io.*;


class Sudoku
{
    /* SIZE is the size parameter of the Sudoku puzzle, and N is the square of the size.  For 
     * a standard Sudoku puzzle, SIZE is 3 and N is 9. */
    int SIZE;

	int N;

    /* The grid contains all the numbers in the Sudoku puzzle.  Numbers which have
     * not yet been revealed are stored as 0. */
    int Grid[][];


    /* The solve() method should remove all the unknown characters ('x') in the Grid
     * and replace them with the numbers from 1-9 that satisfy the Sudoku puzzle. */
    int possible[][];
    boolean choix[][][];
    public void solve() throws CloneNotSupportedException
    {
        /* INSERT YOUR CODE HERE */
    	eliminate(this);
    	//println();
    	//System.out.println("\n");
    	deepeliminate(this);
    	//twineliminate(this);
    	//println();
    	//System.out.println("\n");
    	brutalsolve(this);
    }
    public void eliminate(Sudoku ori){
    	boolean changed=true;
    	while(changed){
    		changed=false;
    	for(int i=0;i<N;i++){
    		for(int j=0;j<N;j++){
    			if(ori.possible[i][j]<=1){
    				continue;
    			}
    			//if(i==4&&j==1){
				//ori.println();
    			//}
        		////////////////////////////////////////////row
    			for(int k=0;k<N;k++){
    			if(ori.possible[i][k]==1&&k!=j){
    				if(ori.choix[i][j][ori.Grid[i][k]-1]){
    					ori.choix[i][j][ori.Grid[i][k]-1]=false;
    					ori.possible[i][j]--;
    					//if(ori.possible[i][j]==0){
    						//System.out.println("\n \n \n \n \n normal"+i+j);
    						//ori.println();
        				//}
    				}
    			}
    			}
    			////////////////////////////////////////////col
    			for(int k=0;k<N;k++){
        			if(ori.possible[k][j]==1&&k!=i){
        				if(ori.choix[i][j][ori.Grid[k][j]-1]){
        					ori.choix[i][j][ori.Grid[k][j]-1]=false;
        					ori.possible[i][j]--;
        					//if(ori.possible[i][j]==0){
        						//System.out.println("\n \n \n \n \n normal"+i+j);//ori.println();
            				//}
        				}
        			}
        			}
    			/////////////////////////////////////////////area
    			int x=(i/SIZE)*SIZE;
    			int y=(j/SIZE)*SIZE;
    			for(int p=x;p<SIZE+x;p++){
    				for(int q=y;q<SIZE+y;q++){
    					if(ori.possible[p][q]==1&&(!(p==i&&q==j))){
    						//System.out.println(i+" "+j);
            				if(ori.choix[i][j][ori.Grid[p][q]-1]){
            					ori.choix[i][j][ori.Grid[p][q]-1]=false;
            					ori.possible[i][j]--;
            					//if(ori.possible[i][j]==0){
                					//System.out.println("\n \n \n \n \n normal"+i+j);//ori.println();
                				//}
            				}
            			}
        			}
    			}
    			/////////////////////////////////////////////decide
    			if(ori.possible[i][j]==1){
    				for(int k=0;k<N;k++){
    					if(ori.choix[i][j][k]){
    						ori.Grid[i][j]=k+1;
    						//System.out.println("at"+i+" "+j+"is"+ori.Grid[i][j]+" "+this.Grid[i][j]);
    						changed=true;
    					}
    				}
    			}
        	}
    	}
    	}
    }
    public void deepeliminate(Sudoku ori){
    	//System.out.println("elm\n");
    	boolean changed=true;
    	while(changed){
    		changed=false;
    	for(int i=0;i<N;i++){
    		for(int j=0;j<N;j++){
    			if(ori.possible[i][j]<=1){
    				continue;
    			}
    			boolean candecide;
    			for(int n=0;n<N;n++){////////////////////////////////////////////row
    				if(ori.choix[i][j][n]){
    					candecide=true;
    					for(int k=0;k<N;k++){	
    						if(ori.possible[i][k]<=1||k==j){
    							continue;
    						}
    						if(ori.choix[i][k][n]){
    							candecide=false;
    							break;
    						}
    					}
    					if(candecide){
        					ori.Grid[i][j]=n+1;
        					ori.possible[i][j]=1;
        					changed=true;
        					//System.out.println("yeal");
        					eliminate(ori);
            			}
    				}
    			}
    			////////////////////////////////////////////col
    			if(ori.possible[i][j]<=1){
    				continue;
    			}
    			for(int n=0;n<N;n++){
    				if(ori.choix[i][j][n]){
    					candecide=true;
    					for(int k=0;k<N;k++){	
    						if(ori.possible[k][j]<=1||k==i){
    							continue;
    						}
    						if(ori.choix[k][j][n]){
    							candecide=false;
    							break;
    						}
    					}
    					if(candecide){
    						ori.Grid[i][j]=n+1;
    						ori.possible[i][j]=1;
    						changed=true;
    						//System.out.println("yeal");
    						eliminate(ori);
    					}
    				}
    			}
    			/////////////////////////////////////////////area
    			if(ori.possible[i][j]<=1){
    				continue;
    			}
    			int x=(i/SIZE)*SIZE;
    			int y=(j/SIZE)*SIZE;
    			for(int n=0;n<N;n++){
    				if(ori.choix[i][j][n]){
    					candecide=true;
    					for(int p=x;p<SIZE+x;p++){
    	    				for(int q=y;q<SIZE+y;q++){	
    	    					if(ori.possible[p][q]<=1||(p==i&&q==j)){
    	    						continue;
    	    					}
    	    					if(ori.choix[p][q][n]){
    	    						candecide=false;
    	    						break;
    	    					}
    	    				}
    					}
    					if(candecide){
    						ori.Grid[i][j]=n+1;
    						ori.possible[i][j]=1;
    						changed=true;
    						//System.out.println("yeal");
    						eliminate(ori);
    					}
    				}
    			}
    			
    			
        	}
    	}
    	}
    }
    public int check(Sudoku sd){/////////////////////////to write check solved all possible is 1(1)  some possible is 0(0)  some possible >1(-1)
    	int check=1;
    	for(int i=0;i<N;i++){
			for(int j=0;j<N;j++){
				if(sd.possible[i][j]==0){
					return 0;
				}
				if(sd.possible[i][j]>1){
					check=-1;
				}
			}
		}
    	return check;
    }
    public boolean brutalsolve(Sudoku ori) throws CloneNotSupportedException{
    	int x=0,y=0;/////////////////////////////////////////////////choose to guess
    	for(int i=0;i<N;i++){
    		for(int j=0;j<N;j++){
        		if((ori.possible[i][j]<ori.possible[x][y]&&ori.possible[i][j]>=2)||(ori.possible[x][y]<2)){
        			x=i;y=j;
        		}
        	}
    	}
    	//System.out.println("x"+x+"y"+y+"po"+ori.possible[x][y]);
    	for(int k=0;k<N;k++){
			if(ori.choix[x][y][k]){/////////// go through all possible
		    	Sudoku sd=new Sudoku(SIZE);
		    	for(int i=0;i<N;i++){
		    		for(int j=0;j<N;j++){
		    			for(int m=0;m<N;m++){
		    				sd.choix[i][j][m]=ori.choix[i][j][m];
		    			}
		    			sd.Grid[i][j]=ori.Grid[i][j];
		    			sd.possible[i][j]=ori.possible[i][j];
		    		}
		    	}
		    	//sd.print();
		    	//System.out.println("\n");
				sd.possible[x][y]=1;
				sd.Grid[x][y]=k+1;
				//System.out.println("ass");
				//sd.println();
				eliminate(sd);
				//System.out.println("el");
				//sd.println();
				deepeliminate(sd);
				//System.out.println("de");
				//sd.println();
				//System.out.println(sd.Grid[x][y]+"start\n\n\n");
				//twineliminate(sd);/////////////copy guess and eliminate
				int check=check(sd);
				//System.out.println("pos"+x+y+"guvl"+sd.Grid[x][y]+"ck"+check);
				if(check==1){//////////////////////////////////////////////////////////////if solved
					this.Grid=sd.Grid;
					//sd.print();
					return true;
				}
				if(check==0){//////////////////////////////////////////////////////////////if false
					continue;
				}
				boolean b=brutalsolve(sd);
				if(b){
					return true;
				}
			}
    	}
    	return false;
    }
    public void println()
    {
       for(int i=0;i<N;i++){
    	   for(int j=0;j<N;j++){
    		   if(possible[i][j]==1){
    			   System.out.print("|"+"(1)"+Grid[i][j]+"        ");
    		   }
    		   else{
    			   System.out.print("|"+"("+possible[i][j]+")");
    			   for(int k=0;k<N;k++){
    				   if(choix[i][j][k]){
    					   System.out.print(k+1);
    				   }
    				   else System.out.print(" ");
    			   }
    		   }
    		   System.out.print("        ");
    	   }
    	   System.out.println("\n");
       }
    }
    /*****************************************************************************/
    /* NOTE: YOU SHOULD NOT HAVE TO MODIFY ANY OF THE FUNCTIONS BELOW THIS LINE. */
    /*****************************************************************************/
 
    /* Default constructor.  This will initialize all positions to the default 0
     * value.  Use the read() function to load the Sudoku puzzle from a file or
     * the standard input. */
    public Sudoku( int size )
    {
        SIZE = size;
        N = size*size;

        Grid = new int[N][N];
        possible = new int[N][N];
        choix = new boolean[N][N][N];
        for( int i = 0; i < N; i++ ) {
            for( int j = 0; j < N; j++ ) {
                Grid[i][j] = 0;
                possible[i][j] = 0;
            }
        }
    }


    /* readInteger is a helper function for the reading of the input file.  It reads
     * words until it finds one that represents an integer. For convenience, it will also
     * recognize the string "x" as equivalent to "0". */
    static int readInteger( InputStream in ) throws Exception
    {
        int result = 0;
        boolean success = false;

        while( !success ) {
            String word = readWord( in );

            try {
                result = Integer.parseInt( word );
                success = true;
            } catch( Exception e ) {
                // Convert 'x' words into 0's
                if( word.compareTo("x") == 0 ) {
                    result = 0;
                    success = true;
                }
                // Ignore all other words that are not integers
            }
        }

        return result;
    }


    /* readWord is a helper function that reads a word separated by white space. */
    static String readWord( InputStream in ) throws Exception
    {
        StringBuffer result = new StringBuffer();
        int currentChar = in.read();
	String whiteSpace = " \t\r\n";
        // Ignore any leading white space
        while( whiteSpace.indexOf(currentChar) > -1 ) {
            currentChar = in.read();
        }

        // Read all characters until you reach white space
        while( whiteSpace.indexOf(currentChar) == -1 ) {
            result.append( (char) currentChar );
            currentChar = in.read();
        }
        return result.toString();
    }


    /* This function reads a Sudoku puzzle from the input stream in.  The Sudoku
     * grid is filled in one row at at time, from left to right.  All non-valid
     * characters are ignored by this function and may be used in the Sudoku file
     * to increase its legibility. */
    public void read( InputStream in ) throws Exception
    {
        for( int i = 0; i < N; i++ ) {
            for( int j = 0; j < N; j++ ) {
                Grid[i][j] = readInteger( in );
                if(Grid[i][j]==0){
                	possible[i][j]=N;
                	for(int k=0;k<N;k++){
                	choix[i][j][k]=true;
                	}
                }
                else{
                	possible[i][j]=1;
                }
            }
        }
    }


    /* Helper function for the printing of Sudoku puzzle.  This function will print
     * out text, preceded by enough ' ' characters to make sure that the printint out
     * takes at least width characters.  */
    void printFixedWidth( String text, int width )
    {
        for( int i = 0; i < width - text.length(); i++ )
            System.out.print( " " );
        System.out.print( text );
    }


    /* The print() function outputs the Sudoku grid to the standard output, using
     * a bit of extra formatting to make the result clearly readable. */
    public void print()
    {
        // Compute the number of digits necessary to print out each number in the Sudoku puzzle
        int digits = (int) Math.floor(Math.log(N) / Math.log(10)) + 1;

        // Create a dashed line to separate the boxes 
        int lineLength = (digits + 1) * N + 2 * SIZE - 3;
        StringBuffer line = new StringBuffer();
        for( int lineInit = 0; lineInit < lineLength; lineInit++ )
            line.append('-');

        // Go through the Grid, printing out its values separated by spaces
        for( int i = 0; i < N; i++ ) {
            for( int j = 0; j < N; j++ ) {
                printFixedWidth( String.valueOf( Grid[i][j] ), digits );
                // Print the vertical lines between boxes 
                if( (j < N-1) && ((j+1) % SIZE == 0) )
                    System.out.print( " |" );
                System.out.print( " " );
            }
            System.out.println();

            // Print the horizontal line between boxes
            if( (i < N-1) && ((i+1) % SIZE == 0) )
                System.out.println( line.toString() );
        }
    }
    public static void testtime(Sudoku s) throws CloneNotSupportedException{
    	long startTime = System.nanoTime();
    	
    	// repeat the process a certain number of times, to make more accurate average measurements.
    	for (int rep=0;rep<10;rep++) {
    	    
    	    // This is how to generate lists of random IDs. 
    	    // For firstList, we generate 16000 IDs
    	    // For secondList, we generate 16000 IDs
    		s.solve();
            
            // Print out the (hopefully completed!) puzzle
            //s.print();
    	    
    	    // run the intersection method
    	    
    	}
    	
    	// get the time after the intersection
    	long endTime = System.nanoTime();
    	
    	
    	System.out.println("Running time: "+ (endTime-startTime)/10.0 + " nanoseconds");
        }
    /* The main function reads in a Sudoku puzzle from the standard input, 
     * unless a file name is provided as a run-time argument, in which case the
     * Sudoku puzzle is loaded from that file.  It then solves the puzzle, and
     * outputs the completed puzzle to the standard output. */
    public static void main( String args[] ) throws Exception
    {
        InputStream in;
        if( args.length > 0 ) 
            in = new FileInputStream( args[0] );
        else
            in = System.in;

        // The first number in all Sudoku files must represent the size of the puzzle.  See
        // the example files for the file format.
        int puzzleSize = readInteger( in );
        if( puzzleSize > 100 || puzzleSize < 1 ) {
            System.out.println("Error: The Sudoku puzzle size must be between 1 and 100.");
            System.exit(-1);
        }

        Sudoku s = new Sudoku( puzzleSize );

        // read the rest of the Sudoku puzzle
        s.read( in );

        // Solve the puzzle.  We don't currently check to verify that the puzzle can be
        // successfully completed.  You may add that check if you want to, but it is not
        // necessary.
        testtime(s);
        s.solve();
        
        // Print out the (hopefully completed!) puzzle
        s.print();
        
    }
}
