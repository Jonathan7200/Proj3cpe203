//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
///**
// * An action that can be taken by an entity
// */
//public final class Action {
//    private ActionKind kind;
//    private Entity entity;
//    private WorldModel world;
//    private ImageStore imageStore;
//    private int repeatCount;
//
//    //Getter
//
//
//    public ActionKind getKind() {
//        return kind;
//    }
//
//    public Entity getActionEntity() {
//        return entity;
//    }
//
//
//
//    public Action(ActionKind kind, Entity entity, WorldModel world, ImageStore imageStore, int repeatCount) {
//        this.kind = kind;
//        this.entity = entity;
//        this.world = world;
//        this.imageStore = imageStore;
//        this.repeatCount = repeatCount;
//    }
//
////    public void executeAction(EventScheduler scheduler) {
////        switch (this.kind) {
////            case ACTIVITY:
////                executeActivityAction(scheduler);
////                break;
////
////            case ANIMATION:
////                executeAnimationAction(scheduler);
////                break;
////        }
////    }
//
//
//
////    public void executeAnimationAction(EventScheduler scheduler) {
////        this.getActionEntity().nextImage();
////
////        if (this.repeatCount != 1) {
////            scheduler.scheduleEvent( this.entity, Action.createAnimationAction(this.entity, Math.max(this.repeatCount - 1, 0)), this.getActionEntity().getAnimationPeriod());
////        }
////    }
//
////    public void executeActivityAction(EventScheduler scheduler) {
////        switch (this.entity.getKind()) {
////            case SAPLING:
////                this.getActionEntity().executeSaplingActivity(this.world, this.imageStore, scheduler);
////                break;
////            case TREE:
////                this.getActionEntity().executeTreeActivity(this.world, this.imageStore, scheduler);
////                break;
////            case FAIRY:
////                this.getActionEntity().executeFairyActivity(this.world, this.imageStore, scheduler);
////                break;
////            case DUDE_NOT_FULL:
////                this.getActionEntity().executeDudeNotFullActivity(this.world, this.imageStore, scheduler);
////                break;
////            case DUDE_FULL:
////                this.getActionEntity().executeDudeFullActivity(this.world, this.imageStore, scheduler);
////                break;
////            default:
////                throw new UnsupportedOperationException(String.format("executeActivityAction not supported for %s", this.entity.getKind()));
////        }
////    }
////    public static Action createAnimationAction(Entity entity, int repeatCount) {
////        return new Action(ActionKind.ANIMATION, entity, null, null, repeatCount);
////    }
//
////    public static Action createActivityAction(Entity entity, WorldModel world, ImageStore imageStore) {
////        return new Action(ActionKind.ACTIVITY, entity, world, imageStore, 0);
////    }
//
//
//
//
//
//
//}