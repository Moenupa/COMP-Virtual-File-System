package hk.edu.polyu.comp.comp2021.cvfs.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CVFSTest {

    CVFS cvfs;

    @Before
    public void setUp() {
        cvfs = new CVFS();
        cvfs.newSimpleCri("aa", "type", "equals", "\"txt\"");
        assertTrue(true);
    }

    @Test
    public void newDisk() {
    }

    @Test
    public void newSimpleCri() {
    }

    @Test
    public void newNegation() {
    }

    @Test
    public void newBinaryCri() {
    }

    @Test
    public void printAllCriteria() {
        cvfs.printAllCriteria();
    }
}