package hk.edu.polyu.comp.comp2021.cvfs.model;

public class BinCri extends Criterion{
    /**
     * Two distinct criteria to be linked
     */
    Criterion cri1,cri2;

    /**
     * Stores the logical operation between the criteria
     */
    String op;

    /**
     * Build a binary criteria
     * @param cri1 The first criterion
     * @param cri2 The second criterion
     * @param op The relationship between two criteria
     */
    BinCri(String name,Criterion cri1,Criterion cri2,String op){
        super(name);
        this.cri1=cri1;
        this.cri2=cri2;
        this.op=op;
    }


    /**
     * Check if one file fits multiple criteria.
     * @param x The unit to be checked.
     * @return True if both conditions hold.
     */
    @Override
    public boolean check(Unit x) {
        switch (op){
            case "&&":
                return cri1.check(x)&&cri2.check(x);
            case "||":
                return cri1.check(x)||cri2.check(x);
            default :
                return false;
        }
    }

    /**
     * To recursively print the content of BinCri, brackets used to protect the precedence.
     * @return The string containing all criteria in the current BinCri.
     */
    @Override
    protected String criToString() {
        return "("+cri1.criToString()+' '+op+' '+cri2.criToString()+")";
    }

    /**
     * Print the name of the criterion and calls criToString() to print the content.
     * @return The name and content of the criterion.
     */
    @Override
    public String toString() {
        return String.format("BinaryCriteria '%s', {", getName()) + criToString() + "}";
    }
}
