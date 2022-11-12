public class ActivityAction extends Action{
    private AbstractAction entity;
    private WorldModel world;
    private ImageStore imageStore;

    public ActivityAction(AbstractAction entity, WorldModel world, ImageStore imageStore){
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
    }



    @Override
    public void executeAction(EventScheduler scheduler) {
        entity.executeActivity(this.world, this.imageStore, scheduler);
//        switch (this.entity.getKind()) {
//            case SAPLING:
//                this.getActionEntity().executeSaplingActivity(this.world, this.imageStore, scheduler);
//                break;
//            case TREE:
//                this.getActionEntity().executeTreeActivity(this.world, this.imageStore, scheduler);
//                break;
//            case FAIRY:
//                this.getActionEntity().executeFairyActivity(this.world, this.imageStore, scheduler);
//                break;
//            case DUDE_NOT_FULL:
//                this.getActionEntity().executeDudeNotFullActivity(this.world, this.imageStore, scheduler);
//                break;
//            case DUDE_FULL:
//                this.getActionEntity().executeDudeFullActivity(this.world, this.imageStore, scheduler);
//                break;
//            default:
//                throw new UnsupportedOperationException(String.format("executeActivityAction not supported for %s", this.entity.getKind()));
//        }
    }


    public static ActivityAction createActivityAction(AbstractAction entity, WorldModel world, ImageStore imageStore) {
        return new ActivityAction(entity, world, imageStore);
    }



}



