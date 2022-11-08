package CineplexEntities;

/**
 * Status of the cinema, depending on the proportion of seats filled.
 */
public enum CinemaStatus {
    SOLD_OUT ("SOLD OUT"),
    SELLING_FAST ("SELLING FAST"),
    AVAILABLE ("AVAILABLE");

    private final String name;

    /**
     * Constructor for CinemaStatus enum, taking in the string value of the enum and setting it as an attribute.
     * @param name of the enum.
     */
    private CinemaStatus(String name) { this.name = name; }

    /**
     * For string comparison.
     * @param otherName String to be compared to.
     * @return boolean on whether the String value of CinemaStatus is equals to otherName.
     */
    public boolean equalsString(String otherName) {
        // (otherName == null) check is not needed because name.equals(null) returns false
        return name.equals(otherName);
    }

    /**
     *
     * @return String value of CinemaStatus for string comparison purposes.
     */
    public String toString() {
        return this.name;
    }
}