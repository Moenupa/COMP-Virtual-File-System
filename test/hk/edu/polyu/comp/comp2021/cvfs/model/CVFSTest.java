package hk.edu.polyu.comp.comp2021.cvfs.model;

import hk.edu.polyu.comp.comp2021.cvfs.controller.CVFSController;
import hk.edu.polyu.comp.comp2021.cvfs.controller.CommandType;
import hk.edu.polyu.comp.comp2021.cvfs.view.CVFSView;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
public class CVFSTest {
    CVFS cvfs;

    @Before
    public void setUp() {
        cvfs = new CVFS();
    }

    public void diskTest() {
        cvfs.newDisk(1000);
        cvfs.setDisk(new Disk(10000));
    }

    @Test
    public void storeLoadTest() {
        // below tests have bug and I dont know why
        cvfs.newDisk(100);
        cvfs.getCwd().newDoc("doc1", DocType.TXT, "sample text for doc1");
        cvfs.store("nowStored");
        cvfs.load("nowStored");
        cvfs.delDisk("nowStored");
    }

    @Test
    public void changeDirTest() {
        cvfs.newDisk(1000);
        cvfs.getCwd().newDir("newDir");
        cvfs.changeDir("newDir");
        cvfs.changeDir("..");
    }

    @Test
    public void parsePathTest() {
        String[] correctPath = {"Downloads", ""};
        cvfs.newDisk(1000);
        cvfs.getCwd().newDir("Downloads");
        cvfs.changeDir("Downloads");
        cvfs.getCwd().newDir("Download1");
        cvfs.changeDir("..");

        for (int i = 0; i <= 1; i++)
            System.out.println(cvfs.parsePath("Downloads:Download1")[i]);
    }

    @Test
    public void newCriTestPack() {
        cvfs.newSimpleCri("aa", "type", "equals", "\"txt\"");
        boolean thrown = false;
        try {
            cvfs.newSimpleCri("aa", "type", "equals", "\"txt\"");
        } catch (Exception e) {
            thrown = true;
        }
        assertTrue(thrown);
        thrown = false;
        try {
            cvfs.newNegation("aa", "aa");
        } catch (Exception e) {
            thrown = true;
        }
        assertTrue(thrown);
        thrown = false;
        try {
            cvfs.newBinaryCri("aa", "aa", "&&", "aa");
        } catch (Exception e) {
            thrown = true;
        }
        assertTrue(thrown);

        cvfs.newSimpleCri("bb", "type", "equals", "\"css\"");
        cvfs.newNegation("cc", "aa");
        cvfs.newBinaryCri("dd", "bb", "&&", "cc");
        assertEquals(new Criterion("aa", "type", "equals", "\"txt\""), cvfs.getCri("aa"));
    }

    @Test
    public void printAllCriteria() {
        System.out.println(cvfs.getCriteria());
        cvfs.printAllCriteria();
    }

    @Test
    public void wholeCVFSTest() {
        CVFS cvfs = new CVFS();
        CVFSView view = new CVFSView();
        CVFSController controller = new CVFSController(cvfs, view);
        controller.processCommand(CommandType.newDisk, "newDisk 10000");
        controller.processCommand(CommandType.newDoc, "newDoc docTest txt nothinghere");
        controller.processCommand(CommandType.newDir, "newDir dirTest");
        controller.processCommand(CommandType.undo, "undo");
        controller.processCommand(CommandType.redo, "redo");
    }
}