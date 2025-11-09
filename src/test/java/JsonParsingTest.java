import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import model.IncidentsRoot;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class JsonParsingTest {

    @Test
    void jsonFilesParsingTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        File file = new File("src/test/resources/incidents.json");
        IncidentsRoot incidentsRoot = objectMapper.readValue(file, IncidentsRoot.class);

        assertEquals(LocalDate.of(2025, 11, 1), incidentsRoot.getIncidentsDate());
        assertEquals("ORD123456", incidentsRoot.getOrderNumber());
        assertEquals("some_business_key1", incidentsRoot.getIncidents().getBusinessKey());
        assertEquals("some_queue", incidentsRoot.getIncidents().getQueue());
        assertEquals("C123456", incidentsRoot.getIncidents().getClientNumber());
        assertEquals("INC123456", incidentsRoot.getIncidents().getIncidentNumber());
        assertEquals("some_source", incidentsRoot.getIncidents().getIncidentSource());
    }
}