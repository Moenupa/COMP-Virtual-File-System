package hk.edu.polyu.comp.comp2021.cvfs.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class DocumentTest {

    Directory newDir = new Disk(999);
    Document newDoc = newDir.newDoc("newDoc", DocType.TXT, "some content here...");
    Document emptyDoc = newDir.newDoc("emptyDoc", DocType.TXT, "");

    @Test
    public void toStringTest() {
        String newDocString = newDoc.toString();
        assertTrue(newDocString.contains("txt")
                && newDocString.contains("newDoc")
        );
        assertEquals(newDir.toString(), newDoc.getParent().toString());
        System.out.println(newDoc);
        System.out.println(emptyDoc);
    }

}