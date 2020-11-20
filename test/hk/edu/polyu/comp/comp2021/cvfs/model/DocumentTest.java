package hk.edu.polyu.comp.comp2021.cvfs.model;

import org.junit.jupiter.api.Test;

class DocumentTest {

    Document newDoc = new Document("newDoc", null, DocType.TXT, "some content here...");
    Document emptyDoc = new Document("newDoc", null, DocType.TXT, "");

    @Test
    void toStringTest() {
        System.out.println(newDoc);
        System.out.println(emptyDoc);
    }

}