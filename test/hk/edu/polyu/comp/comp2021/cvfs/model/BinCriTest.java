package hk.edu.polyu.comp.comp2021.cvfs.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BinCriTest {

    Criterion sizeUpBound, sizeLowBound, txtBound;
    Document txtSizeDoc, txtDoc, cssDoc;
    BinCri sizeLowAndUp, negSize, compositeCri;

    @BeforeEach
    void setUp() {
        sizeUpBound = new Criterion("sa", "size","<=", "300");
        sizeLowBound = new Criterion("sb", "size",">=", "50");
        txtBound = new Criterion("ta", "type", "equals", "\"txt\"");

        sizeLowAndUp = new BinCri("gg", sizeLowBound, "&&", sizeUpBound);
        negSize = sizeLowAndUp.getNegCri("ng");
        compositeCri = new BinCri("cp", negSize, "||", txtBound);

        txtSizeDoc = new Document(
                "sizedoc", null, DocType.TXT,
                "Contents in the sizeDoc must be long enough to ensure the judgements can hold...\n" +
                        " and only in this way can the validity of criterion be examined...\n"
        );
        txtDoc = new Document(
                "txtDoc", null, DocType.TXT, "some text here"
        );
        cssDoc = new Document(
                "cssDoc", null, DocType.CSS,
                "body\n" + "{\n" +
                        "    background-color:#d0e4fe;\n" + "}\n" + "h1\n" + "{\n" + "    color:orange;\n" +
                        "    text-align:center;\n" + "}\n" + "p\n" + "{\n" +
                        "    font-family:\"Times New Roman\";\n" + "    font-size:20px;\n" + "}"
        );
    }

    @Test
    void isValidOperatorTest() {
        assertTrue(BinCri.isValidOperator("&&") && BinCri.isValidOperator("||"));
        assertFalse(BinCri.isValidOperator("&|"));
    }

    @Test
    void checkTest() {
        assertTrue(sizeLowAndUp.check(txtDoc));
        assertFalse(sizeLowAndUp.check(txtSizeDoc));
        assertFalse(sizeLowAndUp.check(cssDoc));

        assertTrue(compositeCri.check(txtDoc));
        assertTrue(compositeCri.check(txtSizeDoc));
        assertTrue(compositeCri.check(cssDoc));
    }

    @Test
    void getNegCriTest() {
        assertFalse(negSize.check(txtDoc));
        assertTrue(negSize.check(txtSizeDoc));
        assertTrue(negSize.check(cssDoc));
    }

    @Test
    void toStringTest() {

        assertEquals(
                "BinaryCriteria 'gg', { (size >= 50 && size <= 300) }",
                sizeLowAndUp.toString());
    }

    @Test
    void hashCodeTest() {
        assertEquals(sizeLowAndUp.hashCode(), sizeLowAndUp.clone().hashCode());
    }

    @Test
    void equalsTest() {
        assertEquals(sizeLowAndUp, sizeLowAndUp.clone());
    }
}