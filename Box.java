public class Box{
    private Point boxPos; //where this box is
    private Point orgPos; //where this box is trying to get;
    
    public Point getBoxPos() {
        return boxPos;
    }
    public void setBoxPos(Point boxPos) {
        this.boxPos = boxPos;
    }
    public Point getOrgPos() {
        return orgPos;
    }
    
    public void setOrgPos(Point orgPos) {
        this.orgPos = orgPos;
    }

    public Box(Point boxPoint)
    {
        boxPos = boxPoint.clone();
        orgPos = boxPoint.clone();
    }

    public Box(Point boxPoint, Point originPoint)
    {
        boxPos = boxPoint.clone();
        orgPos = originPoint.clone();
    }
    public Box(Box B){
        boxPos = B.boxPos.clone();
        orgPos = B.orgPos.clone();
    }
    @Override
    public Box clone() {
        return new Box(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() == this.getClass()) {
            return this.boxPos.equals(((Box)obj).boxPos) && this.orgPos.equals(((Box)obj).orgPos);
        }
        return false;
    }
}