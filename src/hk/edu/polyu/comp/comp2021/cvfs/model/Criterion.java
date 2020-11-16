package hk.edu.polyu.comp.comp2021.cvfs.model;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Objects;

public class Criterion implements Cloneable {
    /**
     * The name of the criterion, containing exactly 2 English letters.
     */
    private String name;

    /**
     * The name of the attribute. One of ["name","type","size"].
     */
    private String attr;

    /**
     * The name of the option.
     * name -> contains
     * type -> equals
     * size -> a comparison mark
     */
    private String op;

    /**
     * name,type -> A string in double quote
     * size -> An integer
     */
    private String val;

    /**
     * Null by default, set when a binary criteria is generated.
     */
    private Criterion other;

    /**
     * To decide whether the result of check should be negated, false by default.
     */
    private boolean negation = false;

    /**
     * When this is a binary criteria, the field is to determine the logic relation between two conditions.
     * 0 means this is not a binary criteria.
     */
    private int logicOp = 0;

    static final int AND = 1;
    static final int OR = 2;

    /**
     * Used to mark the special criterion IsDocument
     */
    private boolean isDocumentMark = false;

    /** A ScriptEngine to evaluate the result*/
    private static ScriptEngine engine = new ScriptEngineManager().getEngineByName("js");

    /**
     * The special criterion isDocument
     */
    static Criterion isDocument = new Criterion("IsDocument",null,null,null);

    static {
        isDocument.isDocumentMark = true;
    }

    /**
     * Get the special criterion isDocument
     * @return the reference to isDocument
     */
    public static Criterion getIsDocument() {
        return isDocument;
    }

    /**
     * Create a criterion.
     *
     * @param name The name of the criterion.
     * @param attr The name of the attribute.
     * @param op   The name of the operation.
     * @param val  The value of the operation.
     */
    public Criterion(String name, String attr, String op, String val) {
        this.name = name;
        this.attr = attr;
        this.op = op;
        this.val = val;
    }

    /**
     * A clone constructor
     */
    private Criterion(Criterion x) {
        name = x.getName();
        attr = x.getAttr();
        op = x.getOp();
        val = x.getVal();
    }


    public Object clone() {
        return new Criterion(this);
    }

    /**
     * @return The name of the criterion
     */
    public String getName() {
        return name;
    }

    /**
     * @return The attribute of the criterion.
     */
    public String getAttr() {
        return attr;
    }

    /**
     * @return The operation of the criterion.
     */
    public String getOp() {
        return op;
    }

    /**
     * @return The value of the operation.
     */
    public String getVal() {
        return val;
    }

    /**
     * Set this Criterion to its Negative
     */
    public void setNeg() {
        negation = !negation;
    }

    /**
     * @return a negative criterion of this
     */
    public Criterion getNegCri() {
        Criterion that = new Criterion(this);
        that.setNeg();
        return that;
    }

    public String toString() {
        if (!isDocumentMark)
            return "Criterion {" +
                    "name='" + name + "'" +
                    ", content='" + attr + " " + op + " " + val + "'" +
                    ", negation='" + negation + "'}";
        return "Criterion {IsDocument}";
    }

    /**
     * @return True if the check result is negated.
     */
    public boolean isNeg() {
        return negation;
    }

    /**
     * Check whether all parameters are valid. Rubrics over the declarations of fields.
     * !!! Does not support 'IsDocument' criterion !!!
     * @return True if all parameters are valid.
     */
    public static boolean isValidCri(String name, String attr, String op, String val){
        return (isValidCriName(name) && isValidCriContent(attr, op, val));
    }

    /**
     * Check if the unit x fits the criterion.
     * Print a warning and return false if x is null.
     *
     * @param x The unit to be checked.
     * @return True if the condition holds.
     */
    public boolean check(Unit x) {
        if (x == null) {
            System.out.println("Error: Invalid Argument. Details: Checking a null Unit with " + this);
            return false;
        }

        if (isDocumentMark)
            return x instanceof Document;

        String matcher;
        switch (getAttr()) {
            case "name":
                matcher = getVal().substring(1, val.length() - 1);
                return negation == x.getName().contains(matcher);

            case "type":
                matcher = getVal().substring(1, val.length() - 1);
                if (x instanceof Document)
                    return ((Document) x).getType().toString().equals(matcher);
                return false;

            case "size":
                matcher = getAttr() + getOp() + getVal();
                boolean eval = fitCri(x.getSize(), matcher);

                return (negation ^ eval);
        }
        System.out.println("Error: Invalid Argument. Details: when checking unit find an Illegal " + this);
        return false;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Criterion criterion = (Criterion) o;
        return negation == criterion.negation &&
                Objects.equals(name, criterion.name) &&
                Objects.equals(attr, criterion.attr) &&
                Objects.equals(op, criterion.op) &&
                Objects.equals(val, criterion.val);
    }

    public int hashCode() {
        return Objects.hash(name, attr, op, val, negation);
    }

    public static void main(String[] args) {
        System.out.println(new Criterion("aa", "type", "equals", "\"txt\""));
        boolean flag;
        flag = Criterion.isValidCri("aa", "type", "equals", "\"txt\"");
        flag &= Criterion.isValidCriName("bA");
        flag &= Criterion.isValidCriName("cE");
        System.out.println(flag);

        long startTime =  System.currentTimeMillis();
        Document newdoc = new Document("mydoc", null, DocType.TXT, "content");
        Document sizedoc = new Document("mydoc", null, DocType.HTML, "something more than just content");
        Criterion sizefilter = new Criterion("bb", "size", ">=", "30");
        long objectTime = System.currentTimeMillis();
        System.out.println("NewDoc checking with sizefilter "+ sizefilter.check(newdoc));
        long midTime = System.currentTimeMillis();
        System.out.println("SizeDoc checking with sizefilter " + sizefilter.check(sizedoc));
        long endTime =  System.currentTimeMillis();
        System.out.println("Object created: "+(objectTime - startTime));
        System.out.println("Half-Checked: "+(midTime - startTime));
        System.out.println("Total time: "+(endTime - startTime));
    }

    // ===================================== private methods for implementation

    /** Check whether the give unit size conforms the criterion
     * @param size the size of the checked unit;
     * @param matcher the boolean expression of the criterion.
     * @return boolean, whether
     */
    private static boolean fitCri(int size, String matcher) {
        engine.put("size", size);
        boolean eval = false;
        try {
            eval = (boolean) engine.eval(matcher);
        } catch (ScriptException e) {
            System.out.println("Error: Script Exception. Details: Error in evaluating criterion " + matcher);
        }
        return eval;
    }

    /** Check whether CriName is valid.
     * @param name the name of the criterion
     * @return boolean, whether it contains exactly 2 English letters.
     */
    private static boolean isValidCriName(String name) {
        if (name == null || name.length() != 2) return false;

        for (char letter: name.toCharArray())
            if (!Character.isLetter(letter)) return false;

        return true;
    }

    /** Check whether CriContent (attr, op, val) is valid.
     * @param attr  'name'      | 'type'    | 'size'
     * @param op    'contains'  | 'equals'  | 6 logOps
     * @param val   '"text"'    | '"text"'  | integer
     * @return boolean
     */
    private static boolean isValidCriContent(String attr, String op, String val) {

        switch (attr) {
            case "name":
                return op.equals("contains")
                        && val.startsWith("\"") && val.endsWith("\"");

            case "type":
                return op.equals("equals")
                        && val.startsWith("\"") && val.endsWith("\"");

            case "size":
                boolean flagOp, flagVal;
                flagOp = op.equals(">") || op.equals("<")
                        || op.equals(">=") || op.equals("<=")
                        || op.equals("==") || op.equals("!=");

                try {
                    Integer.parseInt(val);
                    flagVal = true;
                } catch (NumberFormatException e) {
                    flagVal = false;
                }

                return flagOp && flagVal;
        }
        return false;
    }

}
