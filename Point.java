public class Point {
    private int X, Y;
    
    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }

    public Point(int x, int y) {
        X = x;
        Y = y;
    }

    public Point(Point P) {
        X = P.X;
        Y = P.Y;
    }

    public Point() {
        X = 0;
        Y = 0;
    }

    @Override
    public Point clone() {
        return new Point(this.X,this.Y);
    }

    @Override
    public boolean equals(Object B) {
        if(this.getClass() == B.getClass()){
            return (((Point)B).X == X) && (((Point)B).Y == Y);
        }
        return false;
    }
}