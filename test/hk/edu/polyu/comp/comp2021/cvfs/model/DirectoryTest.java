package hk.edu.polyu.comp.comp2021.cvfs.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
//import org.junit.jupiter.api.Test;

/**
 * Test <code>Directory</code>
 */

public class DirectoryTest {
    /**
     *  Disk
     *      Desktop
     *          NLP
     *              data
     *                  - embedding.txt
     *                  - train.txt
     *                  - test.txt
     *              - paper.css
     *          CV
     *              Null
     *          OOP
     *              gpProj
     *                  instruct
     *                      codes
     *                          - example.java
     *                      - projReq.txt
     *              toAplus.txt
     *      Documents
     *              Null
     *      Downloads
     *          download1
     *          download2
     */

    private final int CAPACITY = 999999;
    private Disk disk;
    private Directory desktop, documents, downloads, oop, nlp, cv, gpProj, instruct , codes, data, download1, download2;
    private Document projReq, example, embedding, train, test, paper, toAplus;
    private final Criterion cri1 = new Criterion("aa", "size", ">=", "40");

    /**
     * Prepared some existed dorectory/document for test.
     */
    @Before
    public void setUp() {
        try {
            disk = new Disk(CAPACITY);
            desktop = disk.newDir("Desktop");
            documents = disk.newDir("Documents");
            downloads = disk.newDir("Downloads");

            nlp = desktop.newDir("NLP");
            data = nlp.newDir("data");
            train = data.newDoc("train", DocType.TXT, "This is the train.txt in /Desktop/NLP/data/");
            test = data.newDoc("test", DocType.TXT, "This is the test.txt in /Desktop/NLP/data/");
            embedding = data.newDoc("embedding", DocType.TXT, "This is the embedding.txt in /Desktop/NLP/data/");
            paper = nlp.newDoc("papers", DocType.CSS, "This is the papers.css in /Desktop/NLP");

            cv = desktop.newDir("CV");

            oop = desktop.newDir("OOP");
            gpProj = oop.newDir("gpProj");
            instruct = gpProj.newDir("instruct");
            codes = instruct.newDir("codes");
            example = codes.newDoc("example", DocType.JAVA, "This is the example.java in /Desktop/OOP/gpProj/instruct/codes/");
            projReq = instruct.newDoc("projReq", DocType.TXT, "This is the projReq.txt in /Desktop/OOP/gpProj/instruct/");
            toAplus = oop.newDoc("toAplus", DocType.TXT, "This is the toAplus.txt in /Desktop/OOP/");

            download1 = downloads.newDir("download1");
            download2 = downloads.newDir("download2");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Test toString()
     */
    @Test
    public void toStringTest() {
        /*  Correct Output:
            Desktop        1258
         */
        System.out.println(desktop.toString());
    }

    /**
     * Test list()
     */
    @Test
    public void listTest() {
        /* Correct Output:
           OOP            586
            ╞═ gpProj         434
            ╞═ toAplus.txt    112
        */
        oop.list();
        download1.list(); // no file warning message
    }

    /**
     * Test newDoc()
     */
    @Test
    public void newDocTest() {
        oop.newDoc("outline",DocType.TXT,"This is the outline of OOP");
        oop.list();
        assertTrue(oop.getCatalog().containsKey("outline"));

        boolean thrown1 = false, thrown2 = false;
        try {
            oop.newDoc("invalidDocNameHere", DocType.TXT, "some content here...");
        } catch (Exception e) {
            thrown1 = true;
        }
        try {
            data.newDoc("train", DocType.CSS, "a css doc's content");
        } catch (Exception e) {
            thrown2 = true;
        }
        assertTrue(thrown1 && thrown2);

        assertFalse(oop.getCatalog().containsKey("invalidDocNameHere"));
    }

    /**
     * Test newDir()
     */
    @Test
    public void newDirTest() {
        cv.newDir("data");
        cv.list();
        assertTrue(cv.getCatalog().containsKey("data"));

        boolean thrown1 = false, thrown2 = false;
        try {
            oop.newDir("invalidDirNameHere");
        } catch (Exception e) {
            thrown1 = true;
        }
        try {
            oop.newDir("gpProj");
        } catch (Exception e) {
            thrown2 = true;
        }
        assertTrue(thrown1 && thrown2);

        assertFalse(oop.getCatalog().containsKey("invalidDirNameHere"));
    }

    /**
     * Test delete()
     */
    @Test
    public void deleteTest() {
        downloads.delete("download1");
        assertFalse(downloads.getCatalog().containsKey("download1"));
        downloads.delete("download2");
        assertEquals(0,downloads.getCatalog().size());
    }

    /**
     * Test updateSize()
     */
    @Test
    public void updateSizeTest() {
        int old_desktopSize = desktop.getSize();
        int old_diskSize = disk.getSize();
        desktop.updateSizeBy(-cv.getSize());
        assertEquals(desktop.getSize(), old_desktopSize - cv.getSize());
        assertEquals(disk.getSize(), old_diskSize - cv.getSize());
    }

    /**
     * Test rename()
     */
    @Test
    public void renameTest() {
        downloads.rename("download1","musics");
        assertEquals("musics",download1.getName());
    }

    /**
     * Test down_rList()
     */
    @Test
    public void down_rlistTest() {
        /* Correct Output:
           Disk           1458
            ╞═ Desktop        1258
                ╞═ OOP            598
                    ╞═ gpProj         438
                    ╞═ instruct       398
                        ╞═ codes          206
                            ╞═ example.java   166
                        ╞═ projReq.txt    152
                    ╞═ toAplus.txt    120
                ╞═ NLP            580
                    ╞═ data           424
                        ╞═ test.txt       124
                        ╞═ embedding.txt  134
                        ╞═ train.txt      126
		            ╞═ papers.css     116
	            ╞═ CV             40
            ╞═ Documents      40
            ╞═ Downloads      120
                ╞═ download2      40
	            ╞═ download1      40
         */
        disk.rList();
        download1.rList(); // no file warning message
    }

    /**
     * Test getpath()
     */
    @Test
    public void getPathTest() {
        System.out.println(instruct.getPath().toString());
    }

    /**
     * Test search()
     */
    @Test
    public void searchTest() {
        disk.search(cri1);      // same as what list shows
        download1.search(cri1); // no file warning message
    }

    /**
     * Test rSearch()
     */
    @Test
    public void rSearchTest() {
        disk.rSearch(cri1);     // same as rList shows
        download1.rSearch(cri1);// no file warning message
    }
}
