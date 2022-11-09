public class ActiviyAction extends Action{
    private WorldModel world;
    private ImageStore imageStore;

    public ActiviyAction(Entity entity, WorldModel world, ImageStore imageStore){
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
    }

    @Override
    public void executeAction(EventScheduler scheduler) {
        switch (this.entity.getKind()) {
            case SAPLING:
                this.getActionEntity().executeSaplingActivity(this.world, this.imageStore, scheduler);
                break;
            case TREE:
                this.getActionEntity().executeTreeActivity(this.world, this.imageStore, scheduler);
                break;
            case FAIRY:
                this.getActionEntity().executeFairyActivity(this.world, this.imageStore, scheduler);
                break;
            case DUDE_NOT_FULL:
                this.getActionEntity().executeDudeNotFullActivity(this.world, this.imageStore, scheduler);
                break;
            case DUDE_FULL:
                this.getActionEntity().executeDudeFullActivity(this.world, this.imageStore, scheduler);
                break;
            default:
                throw new UnsupportedOperationException(String.format("executeActivityAction not supported for %s", this.entity.getKind()));
        }
    }


    public static ActiviyAction createActivityAction(Entity entity, WorldModel world, ImageStore imageStore) {
        return new ActiviyAction(entity, world, imageStore);
    }



}



