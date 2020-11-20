package hk.edu.polyu.comp.comp2021.cvfs.model;

/**
 * TODO: 要加 JavaDoc；这个文件 0 Error
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
     *
     * @param x object to be cloned
     */
    private BinCri(BinCri x) {
        super(x.getName());
        this.cri1 = x.getCri1();
        this.cri2 = x.getCri2();
        this.operator = x.getOperator();
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
     * @param x The unit to be checked.
     * @return True if both conditions hold.
     */
    @Override
    public boolean check(Unit x) {
        switch (operator) {
            case "&&":
                return isNeg() ^ (cri1.check(x) && cri2.check(x));
            case "||":
                return isNeg() ^ (cri1.check(x) || cri2.check(x));
            default:
                System.out.println("Error: Invalid Argument. Details: Invalid operation " + operator);
                return false;
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
}
