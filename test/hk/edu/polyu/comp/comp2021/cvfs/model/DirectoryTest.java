package hk.edu.polyu.comp.comp2021.cvfs.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DirectoryTest {
    /**
     *  root
     *      desktop
     *          nlp
     *              data
     *                  - embedding.txt
     *                  - train.txt
     *                  - test.txt
     *                  - paper.css
     *          oop
     *              gpProj
     *                  - requirement.txt
     *                  - example.java
     *          cv
     *          - notes.txt
     *      document
     *          - ToAplus.txt
     *      downloads
     *          download1
     *          download2
     */

    static final int CAPACITY = 999999;
    Disk root;
    Directory desktop, documents, downloads, oop, nlp, cv, gpProj, data, download1, download2;
    Document notes, projReq, example, outline, embedding, train, test, paper, ToAplus;

    Criterion cri1 = new Criterion("aa", "size" , ">=", "40");

    @Before
    public void setUp() {
        try {
            root = new Disk(CAPACITY);
            desktop = root.newDir("Desktop");
            documents = root.newDir("Document");
            downloads = root.newDir("Downloads");

            nlp = desktop.newDir("NLP");
            oop = desktop.newDir("OOP");
            cv = desktop.newDir("CV");

            gpProj = oop.newDir("gpProj");
            download1 = downloads.newDir("download1");
            download2 = downloads.newDir("download2");

            data = nlp.newDir("data");

            notes = desktop.newDoc("notes", DocType.TXT, "This is the papers.css in /Desktop/NLP");

            projReq = gpProj.newDoc("projReq", DocType.TXT, "This is the assignment.txt in /Desktop/OOP/gpProj/");
            example = gpProj.newDoc("example", DocType.JAVA, "This is the example.java in /Desktop/OOP/gpProj/");
            outline = oop.newDoc("outline", DocType.HTML, "This is the outline.html in /Desktop/OOP/");

            embedding = data.newDoc("embedding", DocType.TXT, "This is the embedding.txt in /Desktop/NLP/data/");
            train = data.newDoc("train", DocType.TXT, "This is the train.txt in /Desktop/NLP/data/");
            test = data.newDoc("test", DocType.TXT, "This is the test.txt in /Desktop/NLP/data/");
            paper = data.newDoc("papers", DocType.CSS, "This is the papers.css in /Desktop/NLP");

            ToAplus = documents.newDoc("ToAplus", DocType.TXT, "This is the notes.txt in /Documents/");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void listTest() {
        root.list();
        System.out.println();
        data.list();
        assertTrue(true);
    }

    @Test
    public void rlistTest() {
        root.down_rList();
    }

    @Test
    public void searchTest() {
        root.search(cri1);
    }

    @Test
    public void rSearchTest() {
        root.rSearch(cri1);
    }

    @Test
    public void toStringTest() {
        System.out.println(root.toString());
    }

}