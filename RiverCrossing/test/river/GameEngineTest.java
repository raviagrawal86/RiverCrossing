package river;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import river.GameEngine.Location;

public class GameEngineTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testObjects() {
        GameObject farmer = new Farmer();
        GameObject wolf = new Wolf();
        GameObject goose = new Goose();
        GameObject beans = new Beans();
        Assert.assertEquals("Farmer", farmer.getName());
        Assert.assertEquals(Location.START, farmer.getLocation());
        Assert.assertEquals("", farmer.getSound());
        Assert.assertEquals("Wolf", wolf.getName());
        Assert.assertEquals(Location.START, wolf.getLocation());
        Assert.assertEquals("Howl", wolf.getSound());
        Assert.assertEquals("Goose", goose.getName());
        Assert.assertEquals(Location.START, goose.getLocation());
        Assert.assertEquals("Honk", goose.getSound());
        Assert.assertEquals("Beans", beans.getName());
        Assert.assertEquals(Location.START, beans.getLocation());
        Assert.assertEquals("", beans.getSound());
    }
    
    @Test
    public void testMidTransport() {
        GameEngine engine = new GameEngine();
        Assert.assertEquals(Location.START, engine.getLocation(Item.Item.MID));
        engine.loadBoat(Item.Item.MID);
        Assert.assertEquals(Location.BOAT, engine.getLocation(Item.Item.MID));
        engine.rowBoat();
        Assert.assertEquals(Location.BOAT, engine.getLocation(Item.Item.MID));
        engine.unloadBoat();
        Assert.assertEquals(Location.FINISH, engine.getLocation(Item.Item.MID));
    }

    @Test
    public void testWinningGame() {

        GameEngine engine = new GameEngine();

        // transport the goose
        engine.loadBoat(Item.Item.MID);
        engine.rowBoat();
        engine.unloadBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back alone
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // transport the wolf
        engine.loadBoat(Item.Item.TOP);
        engine.rowBoat();
        engine.unloadBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // transport the goose
        engine.loadBoat(Item.Item.MID);
        engine.rowBoat();
        engine.unloadBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // transport the beans
        engine.loadBoat(Item.Item.BOTTOM);
        engine.rowBoat();
        engine.unloadBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back alone
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // transport the goose
        engine.loadBoat(Item.Item.MID);
        engine.rowBoat();
        engine.unloadBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertTrue(engine.gameIsWon());
    }

    @Test
    public void testLosingGame() {

        GameEngine engine = new GameEngine();

        // transport the goose
        engine.loadBoat(Item.Item.MID);
        engine.rowBoat();
        engine.unloadBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back alone
        engine.rowBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // transport the wolf
        engine.loadBoat(Item.Item.TOP);
        engine.rowBoat();
        engine.unloadBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // go back alone
        engine.rowBoat();
        Assert.assertTrue(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());
    }

    @Test
    public void testError() {

        GameEngine engine = new GameEngine();

        // transport the goose
        engine.loadBoat(Item.Item.MID);
        engine.rowBoat();
        engine.unloadBoat();
        Assert.assertFalse(engine.gameIsLost());
        Assert.assertFalse(engine.gameIsWon());

        // save the state
        GameEngine.Location topLoc = engine.getLocation(Item.Item.TOP);
        GameEngine.Location midLoc = engine.getLocation(Item.Item.MID);
        GameEngine.Location bottomLoc = engine.getLocation(Item.Item.BOTTOM);
        GameEngine.Location playerLoc = engine.getLocation(Item.Item.PLAYER);

        engine.loadBoat(Item.Item.TOP);

        // check that the state has not changed
        Assert.assertEquals(topLoc, engine.getLocation(Item.Item.TOP));
        Assert.assertEquals(midLoc, engine.getLocation(Item.Item.MID));
        Assert.assertEquals(bottomLoc, engine.getLocation(Item.Item.BOTTOM));
        Assert.assertEquals(playerLoc, engine.getLocation(Item.Item.PLAYER));
    }
}
