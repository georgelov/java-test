package se.tain.exercise.refactor.model;

/**
 * @author Anonymous
 * @author Last changed by: George (georgiy.lovyagin@gmail.com)
 * @version Mar 23, 2014
 * @deprecated Use <code>PhoneNumber</code>
 */
@Deprecated
class Num {
    private final String val;

    /**
     * @param x
     */
    public Num(String x) {
        this.val = x;
    }

    /**
     * @return
     */
    public String getNumber() {
        return val;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Num [val=" + val + "]";
    }

}
