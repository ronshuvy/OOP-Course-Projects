package ex2;

/** Basher spaceship - This ship attempts to collide with other ships. */
public class BasherSpaceShip extends SpaceShip{

	//----------------CLASS CONSTANTS----------------
	private static final int TURN_LEFT = 1;
	private static final int TURN_RIGHT = -1;
	private static final double SHIP_TOO_CLOSE = 0.19;
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

		// Turn on shield
		double distance = this.getPhysics().distanceFrom(closestShip.getPhysics());
		if (distance <= SHIP_TOO_CLOSE)
			shieldOn();

		// Regeneration of the 1 unit of energy of this round
		if (energy < maxEnergyLevel)
			energy++;
	}
}
