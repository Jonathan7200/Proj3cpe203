public class AnimationAction extends Action{
    protected int repeatCount;

    public AnimationAction(Entity entity, int repeatCount){
        this.entity = entity;
        this.repeatCount = repeatCount;
    }

    @Override
    public void executeAction(EventScheduler scheduler) {
        this.getActionEntity().nextImage();

        if (this.repeatCount != 1) {
            scheduler.scheduleEvent( this.entity, createAnimationAction(this.entity, Math.max(this.repeatCount - 1, 0)), this.getActionEntity().getAnimationPeriod());
        }
    }


    public static Action createAnimationAction(Entity entity, int repeatCount) {
        return new AnimationAction( entity, repeatCount);
    }

}
