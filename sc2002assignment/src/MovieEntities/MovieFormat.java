package MovieEntities;

public enum MovieFormat {

    TWOD ("TwoD"),
    THREED ("ThreeD"),
    IMAX ("Imax");
    
    
    private final String name; 
    
    private MovieFormat(String name) {
        
        this.name = name; 
        
    }
    
    /**
     * 
     * String comparison
     * @param name String to be compared to 
     * @return boolean on whether String value of Genre is equals to name
     * 
     */
    public boolean equals(String name) {
        
        return name.equals(name);
    
    }
    
    /**
     * 
     * @return String value of Genre 
     */
    public String toString() {
        
        return this.name; 
    }
}
