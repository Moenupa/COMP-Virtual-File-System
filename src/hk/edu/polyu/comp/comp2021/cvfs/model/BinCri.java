package hk.edu.polyu.comp.comp2021.cvfs.model;

import java.util.Objects;

/**
 * TODO: 要加 JavaDoc；这个文件 0 Error by Code Inspection
 * 已经完成 println -> exception; 唯一 exception 在
 * Error: 和颜色没加
 */
public class BinCri extends Criterion {
    /**
     * Two distinct criteria to be linked; the first
     */
    private Criterion cri1;

    /**
     * Two distinct criteria to be linked; the second
     */
    private Criterion cri2;

    /**
     * Stores the logical operation between the criteria
     */
    private String operator;

    /**
     * Build a binary criteria
     *
     * @param name name for the new Binary Criteria
     * @param cri1 The first criterion
     * @param operator the logical operator of the two criteria
     * @param cri2 The second criterion
     */
    BinCri(String name, Criterion cri1, String operator, Criterion cri2) {
        super(name);
        this.cri1 = cri1;
        this.cri2 = cri2;
        this.operator = operator;
    }

    /**
     * check if operator is valid
     * @param operator operator for a BinCri
     * @return true if operator is '&&' or '||'
     */
    public static boolean isValidOperator(String operator) {
        return operator.matches("&&|\\|\\|");
    }

    /**
     * get the cri1 of a BinCr object
     * @return cri1
     */
    public Criterion getCri1() {
        return cri1;
    }

    /**
     * get the cr2 of a BinCr object
     * @return cr2
     */
    public Criterion getCri2() {
        return cri2;
    }

    /**
     * get the Operator of a BinCr object
     * @return operator
     */
    public String getOperator() {
        return operator;
    }

    /**
     * Clone Constructor
     * @param cloned object to be cloned
     */
    private BinCri(BinCri cloned) {
        super(cloned.getName());
        this.cri1 = cloned.getCri1();
        this.cri2 = cloned.getCri2();
        this.operator = cloned.getOperator();
    }

    /**
     * @param negName the name of the negative BinCri
     * @return a negative BinCri of this
     */
    @Override
    public BinCri getNegCri(String negName) {
        BinCri that = new BinCri(this);
        that.setNeg();
        that.setName(negName);
        return that;
    }

    /**
     * Check if one file fits multiple criteria.
     *
     * @param unit The unit to be checked.
     * @return True if both conditions hold.
     * @throws RuntimeException if operator invalid
     */
    @Override
    public boolean check(Unit unit) throws RuntimeException {
        switch (operator) {
            case "&&":
                return isNeg() ^ (cri1.check(unit) && cri2.check(unit));
            case "||":
                return isNeg() ^ (cri1.check(unit) || cri2.check(unit));
            default:
                throw new RuntimeException("BinCri operator invalid " + operator + ".");
        }
    }

    /**
     * To recursively print the content of BinCri, brackets used to protect the precedence.
     * @return The string containing all criteria in the current BinCri.
     */
    @Override
    protected String criToString() {
        String base = "(" + cri1.criToString() + ' ' + operator + ' ' + cri2.criToString() + ")";

        if (isNeg())
            base = "!" + base;

        return base;
    }

    /**
     * Print the name of the criterion and calls criToString() to print the content.
     * @return The name and content of the criterion.
     */
    @Override
    public String toString() {
        return "BinaryCriteria '" + getName() + "', { " + criToString() + " }";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BinCri)) return false;
        if (!super.equals(o)) return false;
        BinCri binCri = (BinCri) o;
        return cri1.equals(binCri.getCri1()) &&
                cri2.equals(binCri.getCri2()) &&
                operator.equals(binCri.getOperator());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCri1(), getCri2(), getOperator());
    }

    @Override
    public BinCri clone() {
        return new BinCri(this);
    }
}
