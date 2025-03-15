package ui.presenter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import infrastructure.Intersection;
import org.junit.jupiter.api.*;
import java.io.File;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.*;

class JsonSimulationTest {
    private static final String INPUT_FILE_PATH = "src/test/resources/input.json";
    private static final String EXPECTED_OUTPUT_FILE_PATH = "src/test/resources/expected_output.json";
    private static final String OUTPUT_FILE_PATH = "src/main/resources/test_output.json";

    @BeforeEach
    void setUp() {
        File outputFile = new File(OUTPUT_FILE_PATH);
        if (outputFile.exists()) {
            assertTrue(outputFile.delete(), "Error deleting output file.");
        }
    }

    @Test
    void testJsonSimulation() throws Exception {
        Intersection intersection = new Intersection(true);

        JsonSimulationTestable simulator = new JsonSimulationTestable(
                new File(INPUT_FILE_PATH),
                "test_output",
                intersection
        );
        simulator.runSimulation();

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode actualOutputNode = objectMapper.readTree(new File(OUTPUT_FILE_PATH));
        JsonNode expectedOutputNode = objectMapper.readTree(new File(EXPECTED_OUTPUT_FILE_PATH));

        assertEquals(expectedOutputNode, actualOutputNode);
    }
}
