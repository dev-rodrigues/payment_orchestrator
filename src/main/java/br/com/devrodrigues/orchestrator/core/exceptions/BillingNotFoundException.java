package br.com.devrodrigues.orchestrator.core.exceptions;

public class BillingNotFoundException extends RuntimeException {
    public BillingNotFoundException(Throwable throwable) {
        super(throwable);
    }
}
