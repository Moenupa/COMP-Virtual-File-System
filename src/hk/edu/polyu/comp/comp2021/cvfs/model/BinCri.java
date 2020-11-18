package hk.edu.polyu.comp.comp2021.cvfs.model;

public class BinCri extends Criterion {
    /**
     * Two distinct criteria to be linked
     */
    Criterion cri1, cri2;

    /**
     * Stores the logical operation between the criteria
     */
    String op;

    /**
     * Build a binary criteria
     *
     * @param name name for the new Binary Criteria
     * @param cri1 The first criterion
     * @param op   The relationship between two criteria
     * @param cri2 The second criterion
     */
    BinCri(String name, Criterion cri1, String op, Criterion cri2) {
        super(name);
        this.cri1 = cri1;
        this.cri2 = cri2;
        this.op = op;
    }

    /**
     * Clone Constructor
     *
     * @param x object to be cloned
     */
    private BinCri(BinCri x) {
        super(x.getName());
        this.cri1 = x.cri1;
        this.cri2 = x.cri2;
        this.op = x.op;
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
        switch (op) {
            case "&&":
                return isNeg() ^ (cri1.check(x) && cri2.check(x));
            case "||":
                return isNeg() ^ (cri1.check(x) || cri2.check(x));
            default:
                System.out.println("Error: Invalid Argument. Details: Invalid operation " + op);
                return false;
        }
    }

    /**
     * To recursively print the content of BinCri, brackets used to protect the precedence.
     * @return The string containing all criteria in the current BinCri.
     */
    @Override
    protected String criToString() {
        String base = "(" + cri1.criToString() + ' ' + op + ' ' + cri2.criToString() + ")";

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
