package hk.edu.polyu.comp.comp2021.cvfs.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CriterionTest {

    Criterion txtCri, cloneCri, sizeCri, nameCri;

    Document sizeDoc, txtDoc, cssDoc;

    @Before
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
    public void CriterionTest() {
        Criterion constructorTest = new Criterion("ct");
        assertEquals("ct", constructorTest.getName());
        assertNull(constructorTest.getAttr());
    }

    @Test
    public void getNameTest() {
        assertEquals("sa", txtCri.getName());
        assertEquals("sa", cloneCri.getName());
        assertEquals("sz", sizeCri.getName());
        assertEquals("nn", nameCri.getName());
    }

    @Test
    public void getAttrTest() {
        assertEquals("type", txtCri.getAttr());
        assertEquals("type", cloneCri.getAttr());
        assertEquals("size", sizeCri.getAttr());
        assertEquals("name", nameCri.getAttr());
    }

    @Test
    public void getOpTest() {
        assertEquals("equals", txtCri.getOp());
        assertEquals("equals", cloneCri.getOp());
        assertEquals(">=", sizeCri.getOp());
        assertEquals("contains", nameCri.getOp());
    }

    @Test
    public void getValTest() {
        assertEquals("\"txt\"", txtCri.getVal());
        assertEquals("\"txt\"", cloneCri.getVal());
        assertEquals("300", sizeCri.getVal());
        assertEquals("\"doc\"", nameCri.getVal());
    }

    @Test
    public void isNegTest() {
        assertFalse(txtCri.isNeg());
        txtCri.setNeg();
        assertTrue(txtCri.isNeg());
        txtCri.setNeg();
    }

    @Test
    public void cloneTest() {
        assertEquals(txtCri.clone(), txtCri);
    }

    @Test
    public void hashCodeTest() {
        assertEquals(txtCri.clone().hashCode(), txtCri.hashCode());
    }

    @Test
    public void getIsDocumentTest() {
        assertTrue(Criterion.getIsDocument().check(sizeDoc));
        assertFalse(Criterion.getIsDocument().check(new Directory("Downloads", null)));
    }

    @Test
    public void cloneTest1() {
        assertEquals(new Criterion("sa", "type", "equals", "\"txt\""), txtCri);
        assertEquals(new Criterion("sa", "type", "equals", "\"txt\""), cloneCri);
    }

    @Test
    public void cloneTest2() {
        assertEquals(txtCri, cloneCri);
    }

    @Test
    public void isValidCriTest() {
        assertFalse(Criterion.isValidCri("tb", "nothing", "==", "\"txt'"));

        assertFalse(Criterion.isValidCri("ta", "type", "other", "\"txt\""));
        assertTrue(Criterion.isValidCri("tc", "type", "equals", "\"'txt\""));

        assertTrue(Criterion.isValidCri("sa", "size", ">=", "30"));
        assertFalse(Criterion.isValidCri("sc", "size", "<=", "2147483648"));

        assertFalse(Criterion.isValidCri("nb", "name", "==", "\"'GG\"'"));
        assertTrue(Criterion.isValidCri("nc", "name", "contains", "\"'txt\"\""));
    }

    @Test
    public void checkTest() {
        int sizeLimit = Integer.parseInt(sizeCri.getVal());
        assertEquals(sizeDoc.getSize() >= sizeLimit, sizeCri.check(sizeDoc));
        assertEquals(cssDoc.getSize() >= sizeLimit, sizeCri.check(cssDoc));
        assertEquals(txtDoc.getSize() >= sizeLimit, sizeCri.check(txtDoc));
        assertTrue(txtCri.check(txtDoc));
        assertTrue(nameCri.check(sizeDoc));
    }

    @Test
    public void toStringTest() {
        System.out.println(Criterion.getIsDocument().toString());
        System.out.println(txtCri.toString());
        System.out.println(txtCri.getNegCri("ng").toString());
    }

    @Test
    public void getNegCriTest() {
        assertTrue(txtCri.getNegCri("gx").isNeg());
        assertFalse(txtCri.getNegCri("gx").getNegCri("gy").isNeg());
        int sizeLimit = Integer.parseInt(sizeCri.getVal());
        assertEquals(!(sizeDoc.getSize() >= sizeLimit), sizeCri.getNegCri("ne").check(sizeDoc));
        assertEquals(!(txtDoc.getSize() >= sizeLimit), sizeCri.getNegCri("ne").check(txtDoc));
        assertEquals(!(cssDoc.getSize() >= sizeLimit), sizeCri.getNegCri("ne").check(cssDoc));
    }

    @Test
    public void isValidCriNameTest() {
        assertTrue(Criterion.isValidCriName("Gd"));
        assertTrue(Criterion.isValidCriName("SV"));
        assertTrue(Criterion.isValidCriName("MC"));
        assertFalse(Criterion.isValidCriName("Gdc"));
        assertFalse(Criterion.isValidCriName("S;"));
        assertFalse(Criterion.isValidCriName("!a"));
    }
}