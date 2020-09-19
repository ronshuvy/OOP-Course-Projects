package ex2;

import java.util.Random;

/** Drunkard spaceship - Its pilot had a tad too much to drink. */
public class DrunkardSpaceShip extends SpaceShip {

	//----------------CLASS CONSTANTS----------------
	private static final int TELEPORT = 1;
	private static final int ACCELERATE = 2;
	private static final int SHIELD = 3;
	private static final int FIRE = 4;
	private static final int MAX_ACTION_CODE = 4;
	private static final int RIGHT_TURN = -1;
	//----------------------------------------------

	/**
	 * Does the actions of this ship for this round.
	 * This is called once per round by the SpaceWars game driver.
	 *
	 * @param game the game object to which this ship belongs.
	 */
	@Override
	public void doAction(SpaceWars game) {
		// Perform routine tasks
		super.doAction(game);

		Random rand = new Random();
		int randAction = rand.nextInt(MAX_ACTION_CODE) + 1;
		boolean toAccelerate = false;
		switch (randAction)
		{
		case TELEPORT:
			teleport();
			break;
		case ACCELERATE:
			toAccelerate = true;
			break;
		case SHIELD:
			shieldOn();
			break;
		case FIRE:
			fire(game);
			break;
		}
		this.getPhysics().move(toAccelerate, RIGHT_TURN);

		// Regeneration of the 1 unit of energy of this round
		if (energy < maxEnergyLevel)
			energy++;
	}
}
