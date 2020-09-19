package ex2;

import oop.ex2.GameGUI;
import java.awt.*;

/**
 * A spaceship controlled by the user.
 * @author ronshuvy
 */
public class HumanSpaceShip extends SpaceShip {

	//----------------CLASS CONSTANTS----------------
	private static final int LEFT_TURN = 1;
	private static final int RIGHT_TURN = -1;
	private static final int NO_TURN = 0;
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

		// User input actions
		// Teleport
		if (game.getGUI().isTeleportPressed())
			teleport();

		// Accelerate & Turn
		boolean toAccelerate = game.getGUI().isUpPressed();
		boolean turnLeft = game.getGUI().isLeftPressed();
		boolean turnRight = game.getGUI().isRightPressed();
		if (turnLeft && turnRight || !turnLeft && !turnRight)
			getPhysics().move(toAccelerate, NO_TURN);
		else if (turnLeft)
			getPhysics().move(toAccelerate, LEFT_TURN);
		else
			getPhysics().move(toAccelerate, RIGHT_TURN);

		// Shield activation
		if (game.getGUI().isShieldsPressed())
			shieldOn();

		// Fire a shot
		if (game.getGUI().isShotPressed())
			fire(game);

		// Regeneration of the 1 unit of energy of this round
		if (energy < maxEnergyLevel)
			energy++;
	}

	/**
	 * Gets the image of this ship.
	 *
	 * @return returns the image of the ship with or without the shield.
	 */
	@Override
	public Image getImage() {
		return isShieldOn() ? GameGUI.SPACESHIP_IMAGE_SHIELD : GameGUI.SPACESHIP_IMAGE;
	}
}
