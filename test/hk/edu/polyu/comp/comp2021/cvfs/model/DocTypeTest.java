package hk.edu.polyu.comp.comp2021.cvfs.model;

import junit.framework.TestCase;

public class DocTypeTest extends TestCase {

    public void testIsValidDocType() {
        assertTrue(DocType.isValidDocType("txt"));
        assertFalse(DocType.isValidDocType("tct"));
        assertTrue(DocType.isValidDocType("html"));
    }

    public void testParse() {
        assertEquals(DocType.TXT, DocType.parse("txt"));
        assertEquals(DocType.CSS, DocType.parse("css"));
        assertEquals(DocType.HTML, DocType.parse("html"));
        assertEquals(DocType.JAVA, DocType.parse("java"));
        assertEquals(null, DocType.parse("tct"));
    }
}