package hk.edu.polyu.comp.comp2021.cvfs.model;

import org.junit.Before;
import org.junit.Test;

public class CVFSTest {

    public CVFS cvfs;

    @Before
    public void setUp() {
        cvfs = new CVFS();
    }

    @Test
    public void newDisk() {
        cvfs.newDisk(1000);
    }

    @Test
    public void newCriTestPack() {
        cvfs.newSimpleCri("aa", "type", "equals", "\"txt\"");
        cvfs.newSimpleCri("bb", "type", "equals", "\"css\"");
        cvfs.newNegation("cc", "aa");
        cvfs.newBinaryCri("dd", "bb", "&&", "cc");
    }

    @Test
    public void printAllCriteria() {
        cvfs.printAllCriteria();
    }
}