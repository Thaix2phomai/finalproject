package vn.com.techmaster.wineshopping_project.model;

public enum PaymentType {
    PREPAID("PrePaid"),
    POSTPAID("PostPaid");

    public final String label;

    private PaymentType(String label) {
        this.label = label;
    }
}
