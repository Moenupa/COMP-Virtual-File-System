package hk.edu.polyu.comp.comp2021.cvfs.model;

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
    private boolean isDocument = false;


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

    @Override
    public String toString() {
        if (!isDocument)
            return "Criterion {" +
                    "name='" + name + '\'' +
                    ", content='" + attr + " " + op + " " + val + "\'" +
                    ", negation='" + negation + "\'}";
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
     *
     * @return True if all parameters are valid.
     */
    public static boolean isValidCri(String name, String attr, String op, String val){
        // FIXME: 还没有用test，只有简单debug 可能会因为isDocument 导致整个function gg
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
            System.out.println("Error: Invalid Argument.");
            return false;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(new Criterion("aa", "type", "equals", "\"txt\""));
        boolean flag;
        flag = Criterion.isValidCri("aa", "type", "equals", "\"txt\"");
        flag &= Criterion.isValidCriName("bA");
        flag &= Criterion.isValidCriName("cE");
        System.out.println(flag);
    }

    // ===================================== private methods for implementation

    /** CriName contains exactly two English letters*/
    private static boolean isValidCriName(String name) {
        // 无 bug 只考虑cri 名字的合法性
        if (name == null || name.length() != 2) return false;

        for (char letter: name.toCharArray())
            if (!Character.isLetter(letter)) return false;

        return true;
    }

    /** Check whether the contents of the criterion is valid.
     * attr is either name, type, or size.
     * If attr is name, op must be contains and val must be a string in double quote;
     * If attr is type, op must be equals and val must be a string in double quote;
     * If attr is size, op can be >, <, >=, <=, ==, or !=, and val must be an integer.*/
    private static boolean isValidCriContent(String attr, String op, String val) {
        // 无bug 只考虑content的合法性
        switch (attr) {
            case "name":
                return op == "contains"
                        && val.startsWith("\"") && val.endsWith("\"");

            case "type":
                return op == "equals"
                        && val.startsWith("\"") && val.endsWith("\"");

            case "size":
                boolean flagOp, flagVal;
                flagOp = op == ">" || op == "<"
                        || op == ">=" || op == "<="
                        || op == "==" || op == "!=";

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
