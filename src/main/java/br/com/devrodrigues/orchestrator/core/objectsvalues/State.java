package br.com.devrodrigues.orchestrator.core.objectsvalues;

public enum State {
    CREATED,
    WAITING,
    PROCESSING,
    PROCESSED,
    ERROR;

    public static State fromString(String state) {
        return State.valueOf(state.toUpperCase());
    }
}
