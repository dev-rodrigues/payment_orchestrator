package br.com.devrodrigues.orchestrator.core;

public class ExternalResponse {
    private String queueName;
    private Response response;

    public ExternalResponse() {
    }

    public ExternalResponse(
            String queueName,
            Response response
    ) {
        this.queueName = queueName;
        this.response = response;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
