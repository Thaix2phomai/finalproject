package vn.com.techmaster.wineshopping_project.model;

public enum Category {
    Wine("Wine"),
    Whyskey("Whyskey"),
    Tequila("Tequila"),
    Beer("Beer"),
    Cigar("Cigar");

    public final String label;

    private Category(String label) {
        this.label = label;
    }

}
