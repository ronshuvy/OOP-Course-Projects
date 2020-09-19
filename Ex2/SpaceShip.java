package ex2;

import java.awt.Image;
import oop.ex2.*;

/**
 * The class represents a general spaceship.
 * @author ronshuvy
 */
public abstract class SpaceShip{

    //----------------CLASS CONSTANTS----------------
    // RULES OF THE GAME
    /* Energy settings */
    private static final int START_MAX_ENERGY_LEVEL = 210;
    private static final int START_ENERGY_LEVEL = 190;
    private static final int PLUS_ENERGY_IN_BASHING = 18;
    private static final int HIT_ENERGY_COST = 10;
    private static final int SHOT_ENERGY_COST = 19;
    private static final int TELEPORT_ENERGY_COST = 140;
    private static final int SHIELD_ON_ENERGY_COST = 3;
    /* Health settings */
    private static final int START_HEALTH_LEVEL = 22;
    private static final int DEAD = 0;
    private static final int SHOT_HEALTH_DAMAGE = 1, COLLISION_HEALTH_DAMAGE = 1;
    /* Other */
    private static final int ROUNDS_INTERVAL_BETWEEN_FIRE = 7;
    private static final int FIRE_IS_ALLOWED = 0;
    //----------------------------------------------

    //----------------CLASS ATTRIBUTES----------------
    /* The spaceship physical aspects (position, speed, direction) */
    private SpaceShipPhysics physicalState;

    /** The max energy level a spaceship can reach */
    protected int maxEnergyLevel;

    /** Current energy level */
    protected int energy;

    /* Current health level */
    private int health;

    /* Current health level */
    private boolean shieldOn;

    /* Number of rounds to wait until fire is possible again */
    private int coolingRounds;
    //----------------------------------------------

    /**
     * @return True if shield is on, False otherwise.
     */
    protected boolean isShieldOn(){
        return shieldOn;
    }

    /*
     * Initialize all data members
     */
    private void initMembers(){
        physicalState = new SpaceShipPhysics();
        maxEnergyLevel = START_MAX_ENERGY_LEVEL;
        energy = START_ENERGY_LEVEL;
        health = START_HEALTH_LEVEL;
        shieldOn = false;
        coolingRounds = FIRE_IS_ALLOWED;
    }

    /**
     * Creates a new spaceship.
     */
    public SpaceShip(){
        initMembers();
    }

    /**
     * Does the actions of this ship for this round. 
     * This is called once per round by the SpaceWars game driver.
     * 
     * @param game the game object to which this ship belongs.
     */
    public void doAction(SpaceWars game) {
        // Routine tasks
        shieldOn = false;
        if (coolingRounds > 0)
            coolingRounds --;
    }

    /**
     * This method is called every time a collision with this ship occurs 
     */
    public void collidedWithAnotherShip(){
        if (shieldOn)
        {
            energy += PLUS_ENERGY_IN_BASHING;
            maxEnergyLevel += PLUS_ENERGY_IN_BASHING;
        } else {
            health -= COLLISION_HEALTH_DAMAGE;
            maxEnergyLevel -= HIT_ENERGY_COST;

            // make sure the current energy level is NOT above the new maximum
            if (energy > maxEnergyLevel)
                energy = maxEnergyLevel;
        }
    }

    /** 
     * This method is called whenever a ship has died. It resets the ship's 
     * attributes, and starts it at a new random position.
     */
    public void reset(){
        initMembers();
    }

    /**
     * Checks if this ship is dead.
     * 
     * @return true if the ship is dead. false otherwise.
     */
    public boolean isDead() {
        return health <= DEAD;
    }

    /**
     * Gets the physics object that controls this ship.
     * 
     * @return the physics object that controls the ship.
     */
    public SpaceShipPhysics getPhysics() {
        return physicalState;
    }

    /**
     * This method is called by the SpaceWars game object when ever this ship
     * gets hit by a shot.
     */
    public void gotHit() {
        if (!shieldOn)
        {
            health -= SHOT_HEALTH_DAMAGE;
            maxEnergyLevel -= HIT_ENERGY_COST;

            // make sure the current energy level is not above the new maximum
            if (energy > maxEnergyLevel)
                energy = maxEnergyLevel;
        }
    }

    /**
     * Gets the image of this ship.
     *
     * @return returns the image of the ship with or without the shield.
     */
    public Image getImage()
    {
        return shieldOn ? GameGUI.ENEMY_SPACESHIP_IMAGE_SHIELD : GameGUI.ENEMY_SPACESHIP_IMAGE;
    }

    /**
     * Attempts to fire a shot.
     * 
     * @param game the game object.
     */
    public void fire(SpaceWars game) {
        if (coolingRounds == FIRE_IS_ALLOWED && energy >= SHOT_ENERGY_COST)
        {
            game.addShot(physicalState);
            energy -= SHOT_ENERGY_COST;
            coolingRounds = ROUNDS_INTERVAL_BETWEEN_FIRE;
        }
    }

    /**
     * Attempts to turn on the shield.
     */
    public void shieldOn() {
        if (!shieldOn && energy >= SHIELD_ON_ENERGY_COST)
        {
            energy -= SHIELD_ON_ENERGY_COST;
            shieldOn = true;
        }
    }

    /**
     * Attempts to teleport.
     */
    public void teleport() {
       if (energy >= TELEPORT_ENERGY_COST)
       {
           energy -= TELEPORT_ENERGY_COST;
           physicalState = new SpaceShipPhysics();
       }
    }
    
}
