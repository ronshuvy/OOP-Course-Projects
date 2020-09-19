package ex2;

/** Special spaceship - this spaceship provides extra energy only to HumanSpaceShip, and only if they are
 * close enough. But... it won't be easy! this spaceship is playing hard to get, and will always try to
 * avoid contact.
 *
 * @author ronshuvy
 * */
public class SpecialSpaceShip extends SpaceShip{

	//----------------CLASS CONSTANTS----------------
	private static final int TURN_LEFT = 1;
	private static final int TURN_RIGHT = -1;
	private static final double SHIP_TOO_CLOSE = 0.25;
	private static final double ENEMY_ANGLE_TOWARDS_RUNNER = 0.23;
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
		if (closestShip instanceof HumanSpaceShip)
			energy--;
			closestShip.energy++;

		// In case of threat - teleport!
		double distance = this.getPhysics().distanceFrom(closestShip.getPhysics());
		double angle = closestShip.getPhysics().angleTo(this.getPhysics());
		if (distance < SHIP_TOO_CLOSE && angle < ENEMY_ANGLE_TOWARDS_RUNNER)
			teleport();

		// Run away from the fight
		int turn = (this.getPhysics().angleTo(closestShip.getPhysics()) < 0)
				   ? TURN_LEFT : TURN_RIGHT;
		getPhysics().move(true, turn);

		// Regeneration of the 1 unit of energy of this round
		if (energy < maxEnergyLevel)
			energy++;
	}
}
