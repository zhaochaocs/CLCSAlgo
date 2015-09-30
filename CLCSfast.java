/**
 * Created by Adrian on 5/31/2014.
 */

import java.util.*;
import java.lang.Math;

public class CLCSfast {
    static int[][] arr = new int[4096][2048];
    static List<Position>  bounds = new ArrayList<Position>();

    

    static boolean inBounds(Position[][] paths, char[] A, int x, int y, int upperIndex, int lowerIndex) {
        int lowBoundIndex = -1;  
        int upBoundIndex = -1; 
        boolean isBoundedUpper = false;
        boolean isBoundedLower = false;
        //System.out.println("#############");
        //TODO check later
        if(y >=upperIndex && y <=upperIndex+A.length/2){
            if(y == 8)
                System.out.println("boundedUpper");
            isBoundedUpper = true;
        }
        if(y >=lowerIndex &&  y <=lowerIndex+A.length/2){
             if(y == 8)
                System.out.println("boundedLower");
            isBoundedLower = true;
        }


        if (isBoundedLower){
            lowBoundIndex = A.length/2-(y - lowerIndex)+1;
            if(y == 8)
                System.out.println("lowBoundIndex: " + lowBoundIndex);
        }
        if (isBoundedUpper){
            upBoundIndex = A.length/2-(y - upperIndex)+1;
             if(y == 8)
                System.out.println("upBoundIndex: " + upBoundIndex);

        }
        //System.out.println("INDEX HERE: " + index);
        //System.out.println("UpperHeight: " + paths[upperIndex][index].getFirst());
        //System.out.println("LowerHeight: " + paths[lowerIndex][index].getSecond());
        //System.out.println("Y: " +y);
        //System.out.println("X: " + x);
        //System.out.print("Less Than?: "+ paths[lowerIndex][index].getFirst());
        //System.out.print("Greater Than?: "+ paths[upperIndex][index].getSecond());

        if(isBoundedLower)
            if (x < paths[lowerIndex][lowBoundIndex].getFirst()){
                //System.out.println("returning false");
                return false;
        }
        if(isBoundedUpper)
            if (x > paths[upperIndex][upBoundIndex].getSecond()){
                //System.out.println("returning false");
                return false;
        }
        return true;
    }

    static void buildSingleBoundedPath(Position[][] paths, char[] A, char[] B,
                                       int midIndex, int upperIndex, int lowerIndex
                                       , boolean first) {
        System.out.println("new path");
        int currX = B.length;
        int currY = midIndex + (A.length / 2);
        int i = 0;
        paths[midIndex][i] = new Position(currX, currX, 0);
        int pathLen = 0;
        if(!first){
            updateTable(paths, lowerIndex, upperIndex, midIndex, currY, A, B);
            printTable(A, B);
        }
        while (!(currX == 0 && currY == midIndex)) {
            Position toAdd = new Position(currX, currY, 0);
                if(first){
                    Position toAddShifted = new Position(currX, currY+A.length/2, 0);
                    bounds.add(toAddShifted);
                }
            bounds.add(toAdd);

            boolean left;
            boolean up;
            //System.out.println("currNode" + currX + ", " + currY);
            if(i == 15)
                printTable(A,B);
            if (currY == midIndex) {
                up = false;
            } else {
                //System.out.println("upNode"+currX+ ", "+ (currY-1));
                //System.out.println(arr[currY-1][currX]);
                up = (arr[currY - 1][currX] == arr[currY][currX])
                        && (first || inBounds(paths, A, currX, currY - 1, upperIndex, lowerIndex));
            }
            if (currX == 0) {
                left = false;
            } else {
                //System.out.println("leftNode"+(currX-1)+ ", "+ currY);
                //System.out.println(arr[currY][currX-1]);
                //System.out.println(arr[currY][currX]);

                //System.out.println("trying left");
                left = (arr[currY][currX - 1] == arr[currY][currX])
                        && (first || inBounds(paths, A, currX - 1, currY, upperIndex, lowerIndex));
            }
            
            if (left) {
                //System.out.println("going left");
                currX--;
                paths[midIndex][i].setFirst(currX);
                paths[midIndex][i].setThird(pathLen);
              
            } else if (up) {
                //System.out.println("going up");
                currY--;
                i++;
                paths[midIndex][i] = new Position(currX, currX, pathLen);

            } else {
                //System.out.println("diagonals");
                pathLen = pathLen + 1;
                currX--;
                currY--;
                i++;
                //System.out.println(i);
                paths[midIndex][i] = new Position(currX, currX, pathLen);
            }
        }
        if(first){
            for (int x = 0; x< paths[0].length;x++){
                paths[(A.length /2)][x] = new Position(paths[0][x].getFirst(),
                                        paths[0][x].getSecond(), 
                                        paths[0][x].getThird());
                
            }
        }
        //for(int z = 0 ; z< paths[0].length;z++){
        //    System.out.println(paths[0][z].toString());
        //}
       //System.out.println("penis");
    }
    static void updateTable(Position[][] paths, int lowerIndex, int upperIndex, int beginRow,
                             int endRow, char [] A, char[] B){
        System.out.println(Arrays.toString(bounds.toArray()));
        int lowerBoundStart;
        int upperBoundEnd;
        int lowerCounter = A.length/2 + (lowerIndex-beginRow);
        int upperCounter = A.length/2 + (upperIndex - beginRow);
        //System.out.println(upperCounter);
        //System.out.println(lowerCounter);
        //for (int i = 0; i< paths[upperIndex].length;i++)
        //    System.out.println(paths[upperIndex][i].toString());
        //System.out.println("");
        //for (int i = 0; i< paths[lowerIndex].length;i++)
        //   System.out.println(paths[lowerIndex][i].toString());
        boolean inBoundsLeft;
        boolean inBoundsUp;
        for (int row = beginRow; row <= endRow; row++){
            if (row >= lowerIndex && row<= lowerIndex+A.length/2){
                //System.out.println("is lower bounded");
                lowerBoundStart = paths[lowerIndex][lowerCounter].getFirst();
                lowerCounter--;
            }
            else{
                lowerBoundStart = 0;      
                lowerCounter --;
            }
            if ( row>=upperIndex && row<=upperIndex+A.length/2){
                //System.out.println("is upper bounded");
                upperBoundEnd = paths[upperIndex][upperCounter].getSecond();
                upperCounter--;
            }
            else{
                 upperBoundEnd = B.length;
                 upperCounter--;
            }
            System.out.println("row: "+ row);
            System.out.println("Start: "+lowerBoundStart);
            System.out.println("End: " + upperBoundEnd);
            for(int col = lowerBoundStart; col<= upperBoundEnd; col++){
                if(row == beginRow || col == 0){
                    arr[row][col] = 0;
                }
                else{
                    if (A[row - 1] == B[col - 1] ) {
                        if(inBounds(paths, A, col-1, row-1, upperIndex, lowerIndex ))
                            arr[row][col] = arr[row - 1][col - 1] + 1;
                    }
                    else {
                        inBoundsLeft = inBounds(paths, A, col-1, row, upperIndex, lowerIndex );
                        inBoundsUp = inBounds(paths, A, col, row-1, upperIndex, lowerIndex );
                        if(inBoundsLeft && inBoundsUp)
                            arr[row][col] = Math.max(arr[row - 1][col], arr[row][col - 1]);
                        else if (inBoundsLeft) {
                            arr[row][col]=arr[row ][col-1];
                        }
                        else{
                            arr[row][col]=arr[row - 1][col];
                        }
                    }
                }
            }
        } 
    }


    static void findShortestPaths(Position[][] paths, char[] A, char[] B,
                                  int lowerIndex, int upperIndex) {

        //System.out.println("Upper: " + upperIndex + " " + "Lower: " + lowerIndex);
        if (upperIndex - lowerIndex <= 1) {
            return;
        }
        int midIndex = (upperIndex + lowerIndex) / 2;
        buildSingleBoundedPath(paths, A, B, midIndex, lowerIndex, upperIndex, false);
        findShortestPaths(paths, A, B, lowerIndex, midIndex);
        findShortestPaths(paths, A, B, midIndex, upperIndex);
    }


    static void initPaths(char[] A, char[] B, Position[][] paths) {
        for (int i = 0; i < paths.length; i++) {
            for (int j = 0; j < A.length/2+1; j++) {
                paths[i][j] = new Position();
            }
        }
    }
    static void printTable(char []A, char[] B){
        System.out.print("       ");
        for(int i = 0; i< B.length; i ++){
            System.out.print(B[i] + "  ");
        }
        System.out.println("");
        System.out.print("    ");
        for(int i =0; i< B.length+1;i++){
            System.out.print(arr[0][i]);
            System.out.print("  ");
        }
        System.out.println("");
        for (int i = 1; i< A.length+1; i++){
            System.out.print("  ");
            System.out.print(A[i-1]);
            for (int j = 0; j <B.length+1; j++){
                Position toCheck = new Position(i,j,0);

                //System.out.println(toCheck.toString());
                for(int x = 0; x < bounds.size();x++){
                    if (bounds.get(x).getFirst() == j && bounds.get(x).getSecond() ==i){
                        System.out.print("\u001B[31m");
                        break;
                    }
                }
                if(arr[i][j] <= 9)System.out.print("0");
                System.out.print(arr[i][j]);
                System.out.print(" ");
                System.out.print("\u001B[0m");

            }
            System.out.println("");
        }
    }

    static void buildTable(char[] A, char[] B) {
        int m = A.length, n = B.length;
        int i, j;
        for (i = 0; i <= m; i++) arr[i][0] = 0;
        for (j = 0; j <= n; j++) arr[0][j] = 0;
        for (i = 1; i <= m; i++) {
            for (j = 1; j <= n; j++) {
                if (A[i - 1] == B[j - 1]) {
                    arr[i][j] = arr[i - 1][j - 1] + 1;
                } else {
                    arr[i][j] = Math.max(arr[i - 1][j], arr[i][j - 1]);

                }
            }
        }
    }

    static int pathLoop(Position[][] paths, char [] A) {
        int max = 0;
        for (int i = 0; i < paths.length; i++) {
            if (paths[i][A.length/2].getThird() > max)
                max = paths[i][A.length/2].getThird();
        }
        return max;
    }


    public static void main(String args[]) {
        Scanner s = new Scanner(System.in);
        int T = s.nextInt();
        char[] A, B;


        for (int tc = 0; tc < T; tc++) {
            String c = s.next();
            c += new StringBuilder(c).toString();
            A = c.toCharArray();
            B = s.next().toCharArray();
            Position[][] paths = new Position[A.length / 2 +1][A.length/2 + 1];
            initPaths(A, B, paths);
            //findShortestPaths(paths, A, B,paths[A.length-1] ,paths[0]);
            buildTable(A, B);
            printTable(A,B);
            buildSingleBoundedPath(paths, A, B, 0, -1, -1, true);
            //updateTable(paths, 4,0, 1 , 5,A, B);
            //printTable(A,B);
            findShortestPaths(paths, A, B, 0, A.length / 2 );
            int max = pathLoop(paths, A);
            System.out.println(max);

        }
    }

}
