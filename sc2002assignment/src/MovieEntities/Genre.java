package MovieEntities;

public enum Genre {

    ACTION ("ACTION"),
    ANIMATION ("ANIMATION"),
    BLOCKBUSTER ("BLOCKBUSTER"),
    COMEDY ("COMEDY"),
    CRIME ("CRIME"),
    DRAMA ("DRAMA"),
    FANTASY ("FANTASY"),
    HISTORICAL ("HISTORICAL"),
    HORROR ("HORROR"),
    ROMANCE ("ROMANCE"),
    SCIFI ("SCIFI"),
    THRILLER ("THRILLER");
    
    private final String name; 
    
    /**
     * 
     * Constructor for Genre enum 
     * @param name
     */
    private Genre(String name) {
        
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
