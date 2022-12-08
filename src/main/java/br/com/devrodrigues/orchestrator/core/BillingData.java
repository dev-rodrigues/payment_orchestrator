package br.com.devrodrigues.orchestrator.core;

import java.util.Objects;

public final class BillingData {
    private final String user;
    private final String fullName;
    private final String peopleDocument;
    private final String address;
    private final String phone;

    public BillingData(
            String user,
            String fullName,
            String peopleDocument,
            String address,
            String phone
    ) {
        this.user = user;
        this.fullName = fullName;
        this.peopleDocument = peopleDocument;
        this.address = address;
        this.phone = phone;
    }

    public String user() {
        return user;
    }

    public String fullName() {
        return fullName;
    }

    public String peopleDocument() {
        return peopleDocument;
    }

    public String address() {
        return address;
    }

    public String phone() {
        return phone;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (BillingData) obj;
        return Objects.equals(this.user, that.user) &&
                Objects.equals(this.fullName, that.fullName) &&
                Objects.equals(this.peopleDocument, that.peopleDocument) &&
                Objects.equals(this.address, that.address) &&
                Objects.equals(this.phone, that.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, fullName, peopleDocument, address, phone);
    }

    @Override
    public String toString() {
        return "BillingData[" +
                "user=" + user + ", " +
                "fullName=" + fullName + ", " +
                "peopleDocument=" + peopleDocument + ", " +
                "address=" + address + ", " +
                "phone=" + phone + ']';
    }

    public static BillingData of(String user,
                                 String fullName,
                                 String peopleDocument,
                                 String address,
                                 String phone) {
        return new BillingData(user,
                fullName,
                peopleDocument,
                address,
                phone);
    }
}
