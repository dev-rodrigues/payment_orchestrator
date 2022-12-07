package br.com.devrodrigues.orchestrator.core;

public record BillingData(
        String user,
        String fullName,
        String peopleDocument,
        String address,
        String phone
) {

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
