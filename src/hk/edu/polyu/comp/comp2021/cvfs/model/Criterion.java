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

//    /**
//     * Null by default, set when a binary criteria is generated.
//     */
//    private Criterion other;

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
    static final String[] logicOpString = {" Error "," && "," || "};

    /**
     * Used to mark the special criterion IsDocument
     */
    private boolean isDocumentMark = false;

    /** A ScriptEngine to evaluate the result*/
    private static ScriptEngine engine = new ScriptEngineManager().getEngineByName("js");

    /**
     * The special criterion isDocument
     */
    static Criterion isDocument = new Criterion("IsDocument");

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
     * Constructor with only name initialized.
     */
    public Criterion(String name){this.name=name;}

    /**
     * A clone constructor
     */
    private Criterion(Criterion x) {
        name = x.getName();
        attr = x.getAttr();
        op = x.getOp();
        val = x.getVal();
//        other = x.getOther();
        logicOp = x.logicOp;
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

//    /**
//     * @return The other Binary Criterion
//     */
//    public Criterion getOther() {
//        return other;
//    }

//    /** set a valid criterion onto the this.other
//     * @param logicOp logic operator to this.other
//     * @param other Criterion to be on this.other
//     */
//    public void setOther(String logicOp, Criterion other) {
//        if (other == null) {
//            System.out.println("Error: Invalid Arguments. Details: null added to " + this);
//            return;
//        }
//        if (!logicOp.equals("&&") && !logicOp.equals("||")) {
//            System.out.println("Error: Invalid Arguments. Details: illegal LogicOp added to " + this + logicOp);
//            return;
//        }
//
//        if (this.other == null) this.other = other;
//        else System.out.println("Error: Invalid Arguments. Details: other is not null " + this);
//
//        if (logicOp.equals("&&")) this.logicOp = AND;
//        else if (logicOp.equals("||")) this.logicOp = OR;
//    }

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
        if (isDocumentMark) return "Criterion {IsDocument}";

        return String.format("Criterion '%s', {", getName()) + criToString() + "}";
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

        String expression = toJsString();

        engine.put("size", x.getSize());
        engine.put("name", x.getName());

        if (expression.contains("type")) {
            if (x instanceof Document)
                engine.put("type", ((Document) x).getType().toString());
            else {
                System.out.println("Error: Invalid Argument. Details: Type error: " + x);
                return false;
            }
        }

        try {
            return (boolean) engine.eval(expression);
        } catch (ScriptException e) {
            System.out.println("Error: Invalid Argument. Details: invalid script: " + expression);
            return false;
        }

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


        Document txtdoc = new Document("mydoc", null, DocType.TXT, "");
        Document sizedoc = new Document("mydoc", null, DocType.HTML, "something more than just content");

        System.out.println(sizedoc);

        Criterion cri1 = new Criterion("aa", "type", "equals", "\"txt\"");
        Criterion cri2 = new Criterion("bb", "size", "<=", "100");
        Criterion cri3 = new Criterion("cc", "size", ">=", "30");

//        cri3.setOther("&&", cri2);
//        cri1.setOther("||", cri3);
        Criterion cri4 = cri1.getNegCri();

        System.out.println(cri1);
        System.out.println("sizedoc: " + cri1.check(sizedoc) + ", txtdoc: " + cri1.check(txtdoc));
        System.out.println(cri4);
        System.out.println("sizedoc: " + cri4.check(sizedoc) + ", txtdoc: " + cri4.check(txtdoc));

    }

    /** Check whether CriName is valid.
     * @param name the name of the criterion
     * @return boolean, whether it contains exactly 2 English letters.
     */
    public static boolean isValidCriName(String name) {
        if (name == null || name.length() != 2) return false;

        for (char letter: name.toCharArray())
            if (!Character.isLetter(letter)) return false;

        return true;
    }

    // ===================================== private and protected methods for implementation

    /** Convert to a js String to evaluate
     * @return js String
     */
    private String toJsString() {
        // the last one in the linked list

        return criToJsString(this);
    }

    /** toString for a single Criterion object
     * @return toString
     */
    protected String criToString() {
        String base;
        base = String.format("%s %s %s", getAttr(), getOp(), getVal());

        if (isNeg())
            base = "!(" + base + ")";

        return base;
    }

    /** toJsString for a single Criterion object
     * @param cur current Criterion object;
     * @return toString
     */
    private static String criToJsString(Criterion cur) {
        String base;
        switch (cur.getAttr()) {
            case "name":
                base = String.format("\"%s\".contains(\"%s\")", cur.getAttr(), cur.getVal());
                break;

            case "type":
                base = String.format("%s == %s", cur.getAttr(), cur.getVal());
                break;

            case "size":
                base = String.format("%s %s %s", cur.getAttr(), cur.getOp(), cur.getVal());
                break;
            default:
                throw new IllegalArgumentException();
        }

        if (cur.isNeg())
            base = "!(" + base + ")";

        return base;
    }

    /** Check whether CriContent (attr, op, val) is valid.
     * @param attr  'name'      | 'type'    | 'size'
     * @param op    'contains'  | 'equals'  | 6 logOps
     * @param val   '"text"'    | '"text"'  | integer
     * @return boolean
     */
    private static boolean isValidCriContent(String attr, String op, String val) {
        if (attr == null || op == null || val == null) {
            System.out.println("Error: Invalid Argument. Details: Null Cri-Content detected.");
            return false;
        }

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
