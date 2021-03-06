package data;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * @author martijn
 */
public class ShotTest {
    Shot shot1;
    Shot shot2;
    Shot shot3;

    @Before
    public void initialize() {
        shot1 = new CameraShot("s1", "d", 1, 3);
        shot2 = new CameraShot("s2", "d", 2, 4);
        shot3 = new CameraShot("s3", "d", 2, 3);
    }
    
    @Test
    public void constructorNoArgumentsTest() {
        Shot shot = new CameraShot();
        assertEquals("", shot.getName());
    }

    @Test
    public void getInstruments() {
        Instrument instrument = new Instrument("name", "description");
        shot1.addInstrument(instrument);
        assertEquals(1, shot1.getInstruments().size());
        assertTrue(shot1.getInstruments().contains(instrument));
    }

    @Test
    public void setInstruments() {
        ArrayList<Instrument> instruments = new ArrayList<>();
        Instrument instrument = new Instrument("name", "description");
        instruments.add(instrument);
        shot1.setInstruments(instruments);
        assertEquals(instruments, shot1.getInstruments());
    }

    @Test
    public void addInstrument() {
        ArrayList<Instrument> instruments = new ArrayList<>();
        assertEquals(instruments, shot1.getInstruments());
        Instrument instrument = new Instrument("name", "description");
        shot1.addInstrument(instrument);
        instruments.add(instrument);
        assertEquals(instruments, shot1.getInstruments());
    }

    @Test
    public void getBeginCountProperty() {
        assertEquals(shot1.getBeginCount(), shot1.getBeginCountProperty().doubleValue(), 0);
    }

    @Test
    public void getEndCountProperty() {
        assertEquals(shot1.getEndCount(), shot1.getEndCountProperty().doubleValue(), 0);
    }
    @Test
    public void getNameTest() {
        assertEquals("s1", shot1.getName());
    }

    @Test
    public void getDescriptionTest() {
        assertEquals("d", shot1.getDescription());
    }

    @Test
    public void setInstanceTest() {
        int instance = shot3.getInstance();
        CameraShot shot = new CameraShot("test", "d", 1 ,2);
        assertEquals(instance + 1, shot.getInstance());
    }

    @Test
    public void getStartCountTest() {
        assertEquals(1, shot1.getBeginCount(), 0);
    }

    @Test
    public void getEndCountTest() {
        assertEquals(3, shot1.getEndCount(), 0);
    }

    @Test
    public void smallerCompareTest() {
        assertEquals(-1, shot1.compareTo(shot2));
    }

    @Test
    public void biggerCompareTest() {
        assertEquals(1, shot2.compareTo(shot1));
    }

    @Test
    public void equalCompareTest() {
        shot3.setBeginCount(1);
        assertEquals(0, shot1.compareTo(shot3));
    }

    @Test
    public void fullOverlapCompareTest() {
        shot1.setEndCount(4);
        assertEquals(-1, shot1.compareTo(shot3));
    }

    @Test
    public void oneSideOverlapTest() {
        assertTrue(shot1.areOverlapping(shot2, 0));
        assertTrue(shot2.areOverlapping(shot1, 0));
    }

    @Test
    public void fullOverlapTest() {
        shot2.setBeginCount(0);
        assertTrue(shot1.areOverlapping(shot2, 0));
        assertTrue(shot2.areOverlapping(shot1, 0));
        assertTrue(shot1.areOverlapping(shot1, 0));
    }

    @Test
    public void noOverlapTest() {
        shot2.setBeginCount(3);
        assertFalse(shot1.areOverlapping(shot2, 0));
        assertFalse(shot2.areOverlapping(shot1, 0));
    }
    
    @Test
    public void removedFromCollidesTest() {
        shot2.setBeginCount(0);
        assertTrue(shot1.areOverlapping(shot2, 0));
        shot2.setBeginCount(3);
        assertFalse(shot2.areOverlapping(shot1, 0));
    }
}
