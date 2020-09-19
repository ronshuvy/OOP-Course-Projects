package ex2;

/**
 * This class creates spaceship objects according to a given parameters.
 * @author ronshuvy
 */

public class SpaceShipFactory {

    /* Spaceship types in letter representation */
    private static final String HUMAN = "h";
    private static final String DRUNKARD = "d";
    private static final String RUNNER = "r";
    private static final String AGGRESSIVE = "a";
    private static final String BASHER = "b";
    private static final String SPECIAL = "s";

    /**
     * Creates an array of spaceships.
     *
     * @param args the command line arguments that indicate which types of
     * spaceships to create.
     * @return an array of spaceships, null if no args has been given.
     */
    public static SpaceShip[] createSpaceShips(String[] args) {
        if (args.length == 0)
            return null;

        SpaceShip[] spaceships = new SpaceShip[args.length];
        for (int i = 0; i < args.length; i++)
        {
            if (args[i].equals(HUMAN))
                spaceships[i] = new HumanSpaceShip();
            else if (args[i].equals(DRUNKARD))
                spaceships[i] = new DrunkardSpaceShip();
            else if (args[i].equals(RUNNER))
                spaceships[i] = new RunnerSpaceShip();
            else if (args[i].equals(AGGRESSIVE))
                spaceships[i] = new AggressiveSpaceShip();
            else if (args[i].equals(BASHER))
                spaceships[i] = new BasherSpaceShip();
            else if (args[i].equals(SPECIAL))
                spaceships[i] = new SpecialSpaceShip();
            else
                spaceships[i] = null;
        }
        return spaceships;
    }
}
