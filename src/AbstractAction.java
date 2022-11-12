import processing.core.PImage;

import java.util.List;

public abstract class AbstractAction extends AbstractAnimated{
    protected double actionPeriod;


    public AbstractAction(String id, Point position, List<PImage> images, double actionPeriod, double AnimationPeriod) {
        super(id, position, images, AnimationPeriod);
        this.actionPeriod = actionPeriod;


    }


    public double getActionPeriod() {
        return actionPeriod;
    }

    public abstract void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this, new ActivityAction (this, world, imageStore), this.getActionPeriod());
        scheduler.scheduleEvent(this, new AnimationAction (this, 0), this.getAnimationPeriod());
    }






}
