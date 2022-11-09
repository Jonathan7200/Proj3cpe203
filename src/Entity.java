import java.util.List;
import java.util.Objects;
import java.util.*;


import processing.core.PImage;

/**
 * An entity that exists in the world. See EntityKind for the
 * different kinds of entities that exist.
 */
public final class Entity {
    private EntityKind kind;
    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;
    private int resourceLimit;
    private int resourceCount;
    private double actionPeriod;
    private double animationPeriod;
    private int health;
    private int healthLimit;

    public EntityKind getKind() {
        return kind;
    }

    public Point getPosition() {
        return position;
    }

    public String getId() {
        return id;
    }

    public List<PImage> getImages() { return images; }
    public int getImageIndex() { return imageIndex; }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealthLimit() {
        return healthLimit;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Entity(EntityKind kind, String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, double actionPeriod, double animationPeriod, int health, int healthLimit) {
        this.kind = kind;
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
        this.actionPeriod = actionPeriod;
        this.animationPeriod = animationPeriod;
        this.health = health;
        this.healthLimit = healthLimit;







    }

    /**
     * Helper method for testing. Preserve this functionality while refactoring.
     */
    public String log(){
        return this.id.isEmpty() ? null :
                String.format("%s %d %d %d", this.id, this.position.getX(), this.position.getY(), this.imageIndex);
    }

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

    public void nextImage() {
        this.imageIndex = this.imageIndex + 1;
    }
//
    public void executeSaplingActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        this.health++;
        if (!this.transformPlant(world, scheduler, imageStore)) {
            scheduler.scheduleEvent(this, ActiviyAction.createActivityAction(this, world, imageStore), this.actionPeriod);
        }
    }

    public void scheduleActions( EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        switch (this.kind) {
            case DUDE_FULL:
                scheduler.scheduleEvent(this, ActiviyAction.createActivityAction(this, world, imageStore), this.actionPeriod);
                scheduler.scheduleEvent(this, AnimationAction.createAnimationAction(this, 0), this.getAnimationPeriod());
                break;

            case DUDE_NOT_FULL:
                scheduler.scheduleEvent(this, ActiviyAction.createActivityAction(this, world, imageStore), this.actionPeriod);
                scheduler.scheduleEvent(this, AnimationAction.createAnimationAction(this, 0), this.getAnimationPeriod());
                break;

            case OBSTACLE:
                scheduler.scheduleEvent(this, AnimationAction.createAnimationAction(this, 0), this.getAnimationPeriod());
                break;

            case FAIRY:
                scheduler.scheduleEvent(this, ActiviyAction.createActivityAction(this, world, imageStore), this.actionPeriod);
                scheduler.scheduleEvent(this, AnimationAction.createAnimationAction(this, 0), this.getAnimationPeriod());
                break;

            case SAPLING:
                scheduler.scheduleEvent(this, ActiviyAction.createActivityAction(this, world, imageStore), this.actionPeriod);
                scheduler.scheduleEvent(this, AnimationAction.createAnimationAction(this, 0), this.getAnimationPeriod());
                break;

            case TREE:
                scheduler.scheduleEvent(this, ActiviyAction.createActivityAction(this, world, imageStore), this.actionPeriod);
                scheduler.scheduleEvent(this, AnimationAction.createAnimationAction(this, 0), this.getAnimationPeriod());
                break;

            default:
        }
    }


    public void executeTreeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {

        if (!this.transformPlant( world, scheduler, imageStore)) {

            scheduler.scheduleEvent(this, ActiviyAction.createActivityAction(this, world, imageStore), this.actionPeriod);
        }
    }

    public void executeFairyActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> fairyTarget = world.findNearest( this.position, new ArrayList<>(List.of(EntityKind.STUMP)));

        if (fairyTarget.isPresent()) {
            Point tgtPos = fairyTarget.get().position;

            if (this.moveToFairy(world, fairyTarget.get(), scheduler)) {

                Entity sapling = Entity.createSapling(Functions.SAPLING_KEY + "_" + fairyTarget.get().id, tgtPos, imageStore.getImageList( Functions.SAPLING_KEY), 0);

                world.addEntity( sapling);
                sapling.scheduleActions(scheduler, world, imageStore);
            }
        }

        scheduler.scheduleEvent(this, ActiviyAction.createActivityAction(this, world, imageStore), this.actionPeriod);
    }

    public void executeDudeNotFullActivity( WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> target = world.findNearest(this.position, new ArrayList<>(Arrays.asList(EntityKind.TREE, EntityKind.SAPLING)));

        if (target.isEmpty() || !this.moveToNotFull(world, target.get(), scheduler) || !this.transformNotFull(world, scheduler, imageStore)) {
            scheduler.scheduleEvent( this, ActiviyAction.createActivityAction(this, world, imageStore), this.actionPeriod);
        }
    }

    public void executeDudeFullActivity( WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> fullTarget = world.findNearest( this.position, new ArrayList<>(List.of(EntityKind.HOUSE)));

        if (fullTarget.isPresent() && this.moveToFull(world, fullTarget.get(), scheduler)) {
            this.transformFull(world, scheduler, imageStore);
        } else {
            scheduler.scheduleEvent( this, ActiviyAction.createActivityAction(this, world, imageStore), this.actionPeriod);
        }
    }

    public boolean transformNotFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (this.resourceCount >= this.resourceLimit) {
            Entity dude = Entity.createDudeFull(this.id, this.position, this.actionPeriod, this.animationPeriod, this.resourceLimit, this.images);

            world.removeEntity( scheduler, this);
            scheduler.unscheduleAllEvents( this);

            world.addEntity(dude);
            dude.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }

    public void transformFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        Entity dude = Entity.createDudeNotFull(this.id, this.position, this.actionPeriod, this.animationPeriod, this.resourceLimit, this.images);

        world.removeEntity( scheduler, this);

        world.addEntity( dude);
        dude.scheduleActions( scheduler, world, imageStore);
    }

    public boolean transformPlant(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (this.kind == EntityKind.TREE) {
            return this.transformTree( world, scheduler, imageStore);
        } else if (this.kind == EntityKind.SAPLING) {
            return this.transformSapling(world, scheduler, imageStore);
        } else {
            throw new UnsupportedOperationException(String.format("transformPlant not supported for %s", this));
        }
    }

    public boolean transformTree( WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (this.health <= 0) {
            Entity stump = Entity.createStump(Functions.STUMP_KEY + "_" + this.id, this.position, imageStore.getImageList(Functions.STUMP_KEY));

            world.removeEntity( scheduler, this);

            world.addEntity( stump);

            return true;
        }

        return false;
    }

    public  boolean transformSapling(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
        if (this.health <= 0) {
            Entity stump = Entity.createStump(Functions.STUMP_KEY + "_" + this.id, this.position, imageStore.getImageList( Functions.STUMP_KEY));

            world.removeEntity( scheduler, this);

            world.addEntity( stump);

            return true;
        } else if (this.health >= this.healthLimit) {
            Entity tree = Entity.createTree(Functions.TREE_KEY + "_" + this.id, this.position, Functions.getNumFromRange(Functions.TREE_ACTION_MAX, Functions.TREE_ACTION_MIN), Functions.getNumFromRange(Functions.TREE_ANIMATION_MAX, Functions.TREE_ANIMATION_MIN), Functions.getIntFromRange(Functions.TREE_HEALTH_MAX, Functions.TREE_HEALTH_MIN), imageStore.getImageList( Functions.TREE_KEY));

            world.removeEntity( scheduler, this);

            world.addEntity(tree);
            tree.scheduleActions( scheduler, world, imageStore);

            return true;
        }

        return false;
    }

    public boolean moveToFairy(WorldModel world, Entity target, EventScheduler scheduler) {
        if (Point.adjacent(this.position, target.position)) {
            world.removeEntity(scheduler, target);
            return true;
        } else {
            Point nextPos = this.nextPositionFairy(world, target.position);

            if (!this.position.equals(nextPos)) {
                world.moveEntity( scheduler, this, nextPos);
            }
            return false;
        }
    }

    public  boolean moveToNotFull(WorldModel world, Entity target, EventScheduler scheduler) {
        if (Point.adjacent(this.position, target.position)) {
            this.resourceCount += 1;
            target.health--;
            return true;
        } else {
            Point nextPos = this.nextPositionDude(world, target.position);

            if (!this.position.equals(nextPos)) {
                world.moveEntity( scheduler, this, nextPos);
            }
            return false;
        }
    }

    public boolean moveToFull(WorldModel world, Entity target, EventScheduler scheduler) {
        if (Point.adjacent(this.position, target.position)) {
            return true;
        } else {
            Point nextPos = this.nextPositionDude(world, target.position);

            if (!this.position.equals(nextPos)) {
                world.moveEntity( scheduler, this, nextPos);
            }
            return false;
        }
    }

    public Point nextPositionFairy(WorldModel world, Point destPos) {
        int horiz = Integer.signum(destPos.getX() - this.position.getX());
        Point newPos = new Point(this.position.getX() + horiz, this.position.getY());

        if (horiz == 0 || world.isOccupied( newPos)) {
            int vert = Integer.signum(destPos.getY() - this.position.getY());
            newPos = new Point(this.position.getX(), this.position.getY() + vert);

            if (vert == 0 || world.isOccupied( newPos)) {
                newPos = this.position;
            }
        }

        return newPos;
    }

    public Point nextPositionDude(WorldModel world, Point destPos) {
        int horiz = Integer.signum(destPos.getX() - this.position.getX());
        Point newPos = new Point(this.position.getX() + horiz, this.position.getY());

        if (horiz == 0 || world.isOccupied( newPos) && world.getOccupancyCell(newPos).kind != EntityKind.STUMP) {
            int vert = Integer.signum(destPos.getY() - this.position.getY());
            newPos = new Point(this.position.getX(), this.position.getY() + vert);

            if (vert == 0 || world.isOccupied( newPos) && world.getOccupancyCell( newPos).kind != EntityKind.STUMP) {
                newPos = this.position;
            }
        }

        return newPos;
    }

    public static Entity createHouse(String id, Point position, List<PImage> images) {
        return new Entity(EntityKind.HOUSE, id, position, images, 0, 0, 0, 0, 0, 0);
    }

    public static Entity createObstacle(String id, Point position, double animationPeriod, List<PImage> images) {
        return new Entity(EntityKind.OBSTACLE, id, position, images, 0, 0, 0, animationPeriod, 0, 0);
    }

    public static Entity createTree(String id, Point position, double actionPeriod, double animationPeriod, int health, List<PImage> images) {
        return new Entity(EntityKind.TREE, id, position, images, 0, 0, actionPeriod, animationPeriod, health, 0);
    }

    public static Entity createStump(String id, Point position, List<PImage> images) {
        return new Entity(EntityKind.STUMP, id, position, images, 0, 0, 0, 0, 0, 0);
    }

    public static Entity createSapling(String id, Point position, List<PImage> images, int health) {
        return new Entity(EntityKind.SAPLING, id, position, images, 0, 0, Functions.SAPLING_ACTION_ANIMATION_PERIOD, Functions.SAPLING_ACTION_ANIMATION_PERIOD, 0, Functions.SAPLING_HEALTH_LIMIT);
    }

    public static Entity createFairy(String id, Point position, double actionPeriod, double animationPeriod, List<PImage> images) {
        return new Entity(EntityKind.FAIRY, id, position, images, 0, 0, actionPeriod, animationPeriod, 0, 0);
    }

    public static Entity createDudeNotFull(String id, Point position, double actionPeriod, double animationPeriod, int resourceLimit, List<PImage> images) {
        return new Entity(EntityKind.DUDE_NOT_FULL, id, position, images, resourceLimit, 0, actionPeriod, animationPeriod, 0, 0);
    }

    public static Entity createDudeFull(String id, Point position, double actionPeriod, double animationPeriod, int resourceLimit, List<PImage> images) {
        return new Entity(EntityKind.DUDE_FULL, id, position, images, resourceLimit, 0, actionPeriod, animationPeriod, 0, 0);
    }

    public static PImage getCurrentImage(Object object) {
        if (object instanceof Background background) {
            return background.getImages().get(background.getImageIndex());
        } else if (object instanceof Entity entity) {
            return entity.images.get(entity.imageIndex % entity.images.size());
        } else {
            throw new UnsupportedOperationException(String.format("getCurrentImage not supported for %s", object));
        }
    }

    }
