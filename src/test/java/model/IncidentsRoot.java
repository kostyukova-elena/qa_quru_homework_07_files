package model;

import java.time.LocalDate;

public class IncidentsRoot {

    private LocalDate incidentsDate;
    private String orderNumber;
    private Incidents incidents;

    public LocalDate getIncidentsDate() {
        return incidentsDate;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public Incidents getIncidents() {
        return incidents;
    }

    @Override
    public String toString() {
        return "IncidentsRoot {" +
                ", orderNumber='" + orderNumber + '\'' +
                ", incidents=" + incidents +
                '}';
    }
}
