public abstract class AbstractAnimated extends Entity{
    protected double actionPeriod;
    protected double animationPeriod;


    public double getAnimationPeriod() {
        switch (this.kind) {
            case DUDE_FULL:
            case DUDE_NOT_FULL:
            case OBSTACLE:
            case FAIRY:
            case SAPLING:
            case TREE:
                return this.animationPeriod;
            default:
                throw new UnsupportedOperationException(String.format("getAnimationPeriod not supported for %s", this.kind));
        }
    }

    public void scheduleActions( EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        switch (this.kind) {
            case DUDE_FULL:
                scheduler.scheduleEvent(this, ActivityAction.createActivityAction(this, world, imageStore), this.actionPeriod);
                scheduler.scheduleEvent(this, AnimationAction.createAnimationAction(this, 0), this.getAnimationPeriod());
                break;

            case DUDE_NOT_FULL:
                scheduler.scheduleEvent(this, ActivityAction.createActivityAction(this, world, imageStore), this.actionPeriod);
                scheduler.scheduleEvent(this, AnimationAction.createAnimationAction(this, 0), this.getAnimationPeriod());
                break;

            case OBSTACLE:
                scheduler.scheduleEvent(this, AnimationAction.createAnimationAction(this, 0), this.getAnimationPeriod());
                break;

            case FAIRY:
                scheduler.scheduleEvent(this, ActivityAction.createActivityAction(this, world, imageStore), this.actionPeriod);
                scheduler.scheduleEvent(this, AnimationAction.createAnimationAction(this, 0), this.getAnimationPeriod());
                break;

            case SAPLING:
                scheduler.scheduleEvent(this, ActivityAction.createActivityAction(this, world, imageStore), this.actionPeriod);
                scheduler.scheduleEvent(this, AnimationAction.createAnimationAction(this, 0), this.getAnimationPeriod());
                break;

            case TREE:
                scheduler.scheduleEvent(this, ActivityAction.createActivityAction(this, world, imageStore), this.actionPeriod);
                scheduler.scheduleEvent(this, AnimationAction.createAnimationAction(this, 0), this.getAnimationPeriod());
                break;

            default:
        }
    }

    public abstract void executeActivity();




}
