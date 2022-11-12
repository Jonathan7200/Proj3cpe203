import processing.core.PImage;

import java.util.List;

public abstract class AbstractDude extends AbstractAction implements Moveable, Transform{


    public AbstractDude(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, double actionPeriod, double animationPeriod) {
        super(id, position, images, actionPeriod, animationPeriod);
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
    }
    protected int resourceLimit;
    protected int resourceCount;

    public void setResourceCount(int resourceCount) {
        this.resourceCount = resourceCount;
    }



    public int getResourceCount() {
        return resourceCount;
    }

    public int getResourceLimit() {
        return resourceLimit;
    }

    public static final String DUDE_KEY = "dude";
    public static final int DUDE_ACTION_PERIOD = 0;
    public static final int DUDE_ANIMATION_PERIOD = 1;
    public static final int DUDE_LIMIT = 2;
    public static final int DUDE_NUM_PROPERTIES = 3;

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {

    }




    public Point nextPosition(WorldModel world, Point destPos) {
        int horiz = Integer.signum(destPos.getX() - this.getPosition().getX());
        Point newPos = new Point(this.getPosition().getX() + horiz, this.getPosition().getY());

        if (horiz == 0 || world.isOccupied(newPos) && (!(world.getOccupancyCell(newPos) instanceof Stump))) {
            int vert = Integer.signum(destPos.getY() - this.getPosition().getY());
            newPos = new Point(this.getPosition().getX(), this.getPosition().getY() + vert);

            if (vert == 0 || world.isOccupied(newPos) && (!(world.getOccupancyCell(newPos) instanceof Stump))) {
                newPos = this.getPosition();
            }
        }

        return newPos;
    }

}
