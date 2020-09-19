package ex2;

/** Aggressive spaceship - This ship pursues other ships and tries to fire at them. */
public class AggressiveSpaceShip extends SpaceShip{

	//----------------CLASS CONSTANTS----------------
	private static final int TURN_LEFT = 1;
	private static final int TURN_RIGHT = -1;
	private static final double MIN_ANGLE_TO_FIRE = 0.21;
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

		SpaceShip closestShip = game.getClosestShipTo(this);
		// Accelerate and turn towards the closest ship.
		int turn = (this.getPhysics().angleTo(closestShip.getPhysics()) < 0)
				   ? TURN_RIGHT : TURN_LEFT;
		getPhysics().move(true, turn);

		// Fire
		double angle = this.getPhysics().angleTo(game.getClosestShipTo(this).getPhysics());
		if (angle < MIN_ANGLE_TO_FIRE)
			fire(game);

		// Regeneration of the 1 unit of energy of this round
		if (energy < maxEnergyLevel)
			energy++;
	}
}
