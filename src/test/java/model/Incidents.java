package model;

public class Incidents {

    private String businessKey;
    private String queue;
    private String clientNumber;
    private String incidentNumber;
    private String incidentSource;

    public String getBusinessKey() {
        return businessKey;
    }

    public String getQueue() {
        return queue;
    }

    public String getClientNumber() {
        return clientNumber;
    }

    public String getIncidentNumber() {
        return incidentNumber;
    }

    public String getIncidentSource() {
        return incidentSource;
    }

    @Override
    public String toString() {
        return "model.Incidents{" +
                "businessKey='" + businessKey + '\'' +
                ", queue='" + queue + '\'' +
                ", clientNumber='" + clientNumber + '\'' +
                ", incidentNumber='" + incidentNumber + '\'' +
                ", incidentSource='" + incidentSource + '\'' +
                '}';
    }
}
