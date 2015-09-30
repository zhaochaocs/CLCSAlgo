//Position class modified from stack overflow
public class Position {
    private int first;
    private int second;
    private int third;

    public Position(int first, int second, int third) {
    	super();
    	this.first = first;
    	this.second = second;
        this.third = third;
    }

    public Position(){
        super();
        this.first = 0;
        this.second = 0;
        this.third = 0;
    }


    public boolean equals(Object other) {
        if (other instanceof Position) {
            Position otherPair = (Position) other;
            return (this.first == otherPair.first && this.second == otherPair.second);
            
        }
        return false;
    }


    public String toString()
    { 
           return "(" + first + ", " + second + "," + third + ")"; 
    }

    public int getFirst() {
    	return first;
    }

    public void setFirst(int first) {
    	this.first = first;
    }

    public int getSecond() {
    	return second;
    }

    public void setSecond(int second) {
    	this.second = second;
    }
    
    public int getThird() {
        return third;
    }
    
    public void setThird(int third) {
        this.third = third;
    }
}