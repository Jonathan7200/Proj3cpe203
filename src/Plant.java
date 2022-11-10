public abstract class Plant extends AbstractAnimated implements Transform {
    protected int health;
    protected int healthLimit;

    public int getHealth() {
        return health;
    }
    public int getHealthLimit() {
        return healthLimit;
    }

    public void setHealth(int health) {
        this.health = health;
    }
    public void setHealthLimit(int healthLimit) {
        this.healthLimit = healthLimit;
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


    public abstract void executeActivity();
}
