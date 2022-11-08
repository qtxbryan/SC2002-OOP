package CineplexEntities;

/**
 * Type of cinema which affects ticket pricing.
 */
public enum CinemaType {
    PLATINUM ("PLATINUM"),
    NORMAL ("NORMAL");

    private final String name;

    /**
     * Constructor for CinemaType enum, taking in the string value of the enum and setting it as an attribute.
     * @param name of the enum.
     */
    private CinemaType(String name) { this.name = name; }

    /**
     * For string comparison.
     * @param otherName String to be compared to.
     * @return boolean on whether the String value of CinemaType is equals to otherName.
     */
    public boolean equalsString(String otherName) {
        // (otherName == null) check is not needed because name.equals(null) returns false
        return name.equals(otherName);
    }

    /**
     *
     * @return String value of CinemaType for string comparison purposes.
     */
    public String toString() {
        return this.name;
    }
}