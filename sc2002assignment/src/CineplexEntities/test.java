package CineplexEntities;

public class test {
    public static void main(String[] args) {
        Cineplex newCineplex1 = new Cineplex("CAT");
        System.out.println(newCineplex1.getCinemaIDs());
        newCineplex1.getCinemas().get(0).printCinemaLayout();
        
        Cineplex newCineplex2 = new Cineplex("DTE");
        System.out.println(newCineplex2.getCinemaIDs());
        
        Cineplex newCineplex3 = new Cineplex("JEM");
        System.out.println(newCineplex3.getCinemaIDs());
    }
}
