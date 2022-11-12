public class AnimationAction extends Action{
    private AbstractAnimated entity;
    protected int repeatCount;

    public AnimationAction(AbstractAnimated entity, int repeatCount){
        this.entity = entity;
        this.repeatCount = repeatCount;
    }


    public void executeAction(EventScheduler scheduler) {
        this.entity.nextImage();

        if (this.repeatCount != 1) {
            scheduler.scheduleEvent(this.entity, createAnimationAction(this.entity, Math.max(this.repeatCount - 1, 0)), this.entity.getAnimationPeriod());
        }
    }


    public static AnimationAction createAnimationAction(AbstractAnimated entity, int repeatCount)
    {
        return new AnimationAction(entity, repeatCount);
    }
}
