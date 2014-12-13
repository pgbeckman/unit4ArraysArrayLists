import java.util.*;

public class Velocity {
    public ArrayList<Integer> dx = new ArrayList<Integer>();
    public ArrayList<Integer> dy = new ArrayList<Integer>();
    
    public Velocity() {
        
    }

    public void addVector(int dxIn, int dyIn) {
        dx.add(dxIn);
        dy.add(dyIn);
    }

    public int getLength() {
        return dx.size();
    }
    
    public int getDx(int xind) {
        return dx.get(xind);
    }
    
    public int getDy(int yind) {
        return dy.get(yind);
    }
    
    public void removeVector(int xind, int yind) {
        dx.remove(xind);
        dy.remove(yind);
    }
}
