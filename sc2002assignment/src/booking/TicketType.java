package booking;

/**
 * Types of ticket that affect pricing.
 */
public enum TicketType {
	STANDARD ("STANDARD"),
    SENIOR ("SENIOR"),
    STUDENT ("STUDENT");

    private final String name;

    /**
     * Constructor for the TicketType enum, taking in the string value of the enum and setting it as an attribute.
     * @param s of the enum.
     */
    private TicketType(String s) {
        name = s;
    }

    /**
     * For string comparison.
     * @param otherName String to be compared to.
     * @return boolean on whether the String value of TicketType is equals to otherName.
     */
    public boolean equalsString(String otherName) {
        // (otherName == null) check is not needed because name.equals(null) returns false 
        return name.equals(otherName);
    }

    /**
     *
     * @return String value of TicketType for string comparison purposes.
     */
    public String toString() {
       return this.name;
    }
}
