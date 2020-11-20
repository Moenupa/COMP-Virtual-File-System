package hk.edu.polyu.comp.comp2021.cvfs.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CriterionTest {

    Criterion txtCri, cloneCri, sizeCri, nameCri;

    Document sizeDoc, txtDoc, cssDoc;

    @BeforeEach
    public void setUp() {
        txtCri = new Criterion("sa", "type", "equals", "\"txt\"");
        cloneCri = (Criterion) txtCri.clone();
        sizeCri = new Criterion("sz", "size", ">=", "300");
        nameCri = new Criterion("nn", "name", "contains", "\"doc\"");


        sizeDoc = new Document(
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
    void CriterionTest() {
        Criterion constructorTest = new Criterion("ct");
        assertEquals("ct", constructorTest.getName());
        assertNull(constructorTest.getAttr());
    }

    @Test
    void getNameTest() {
        assertEquals("sa", txtCri.getName());
        assertEquals("sa", cloneCri.getName());
        assertEquals("sz", sizeCri.getName());
        assertEquals("nn", nameCri.getName());
    }

    @Test
    void getAttrTest() {
        assertEquals("type", txtCri.getAttr());
        assertEquals("type", cloneCri.getAttr());
        assertEquals("size", sizeCri.getAttr());
        assertEquals("name", nameCri.getAttr());
    }

    @Test
    void getOpTest() {
        assertEquals("equals", txtCri.getOp());
        assertEquals("equals", cloneCri.getOp());
        assertEquals(">=", sizeCri.getOp());
        assertEquals("contains", nameCri.getOp());
    }

    @Test
    void getValTest() {
        assertEquals("\"txt\"", txtCri.getVal());
        assertEquals("\"txt\"", cloneCri.getVal());
        assertEquals("300", sizeCri.getVal());
        assertEquals("\"doc\"", nameCri.getVal());
    }

    @Test
    void isNegTest() {
        assertFalse(txtCri.isNeg());
        txtCri.setNeg();
        assertTrue(txtCri.isNeg());
        txtCri.setNeg();
    }

    @Test
    void cloneTest() {
        assertEquals(txtCri.clone(), txtCri);
    }

    @Test
    void hashCodeTest() {
        assertEquals(txtCri.clone().hashCode(), txtCri.hashCode());
    }

    @Test
    void getIsDocumentTest() {
        assertTrue(Criterion.getIsDocument().check(sizeDoc));
        assertFalse(Criterion.getIsDocument().check(new Directory("Downloads", null)));
    }

    @Test
    void cloneTest1() {
        assertEquals(new Criterion("sa", "type", "equals", "\"txt\""), txtCri);
        assertEquals(new Criterion("sa", "type", "equals", "\"txt\""), cloneCri);
    }

    @Test
    void cloneTest2() {
        assertEquals(txtCri, cloneCri);
    }

    @Test
    void isValidCriTest() {
        assertFalse(Criterion.isValidCri("tb", "nothing", "==", "\"txt'"));

        assertFalse(Criterion.isValidCri("ta", "type", "other", "\"txt\""));
        assertTrue(Criterion.isValidCri("tc", "type", "equals", "\"'txt\""));

        assertTrue(Criterion.isValidCri("sa", "size", ">=", "30"));
        assertFalse(Criterion.isValidCri("sc", "size", "<=", "2147483648"));

        assertFalse(Criterion.isValidCri("nb", "name", "==", "\"'GG\"'"));
        assertTrue(Criterion.isValidCri("nc", "name", "contains", "\"'txt\"\""));
    }

    @Test
    void checkTest() {
        int sizeLimit = Integer.parseInt(sizeCri.getVal());
        assertEquals(sizeDoc.getSize() >= sizeLimit, sizeCri.check(sizeDoc));
        assertEquals(cssDoc.getSize() >= sizeLimit, sizeCri.check(cssDoc));
        assertEquals(txtDoc.getSize() >= sizeLimit, sizeCri.check(txtDoc));
        assertTrue(txtCri.check(txtDoc));
        assertTrue(nameCri.check(sizeDoc));
    }

    @Test
    void toStringTest() {
        assertEquals("Criterion { IsDocument }", Criterion.getIsDocument().toString());
        assertEquals("Criterion 'sa', { type equals \"txt\" }", txtCri.toString());
    }

    @Test
    void getNegCriTest() {
        assertTrue(txtCri.getNegCri("gx").isNeg());
        assertFalse(txtCri.getNegCri("gx").getNegCri("gy").isNeg());
        int sizeLimit = Integer.parseInt(sizeCri.getVal());
        assertEquals(!(sizeDoc.getSize() >= sizeLimit), sizeCri.getNegCri("ne").check(sizeDoc));
        assertEquals(!(txtDoc.getSize() >= sizeLimit), sizeCri.getNegCri("ne").check(txtDoc));
        assertEquals(!(cssDoc.getSize() >= sizeLimit), sizeCri.getNegCri("ne").check(cssDoc));
    }

    @Test
    void isValidCriNameTest() {
        assertTrue(Criterion.isValidCriName("Gd"));
        assertTrue(Criterion.isValidCriName("SV"));
        assertTrue(Criterion.isValidCriName("MC"));
        assertFalse(Criterion.isValidCriName("Gdc"));
        assertFalse(Criterion.isValidCriName("S;"));
        assertFalse(Criterion.isValidCriName("!a"));
    }
}