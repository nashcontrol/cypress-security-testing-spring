package dev.buildsecurity.cypress.demo;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

import javax.validation.constraints.NotNull;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.Testcontainers;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.SelinuxContext;
import org.testcontainers.containers.output.OutputFrame;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.containers.startupcheck.OneShotStartupCheckStrategy;

import io.github.wimdeblauwe.testcontainers.cypress.CypressContainer;
import io.github.wimdeblauwe.testcontainers.cypress.CypressTestResults;

abstract class CypressContainerBaseTest {

    @LocalServerPort
    private static int port;

    static final CypressContainer Cypress_CONTAINER;

    static {
        Cypress_CONTAINER = new CypressContainer();  //(5)
        Cypress_CONTAINER.start();
    }
}

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration-tests")
class CypressIntegrationTest extends CypressContainerBaseTest{

    private final Logger logger = LoggerFactory.getLogger(CypressIntegrationTest.class);

    private static final int MAX_TOTAL_TEST_TIME_IN_MINUTES = 5;

    @LocalServerPort
    private int port;


     // will be shared between test methods
     
     @Test
    void someTestMethod() {
        try {
            CypressTestResults results = Cypress_CONTAINER.getTestResults();
        } catch (InterruptedException | TimeoutException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // create a connection and run test as normal
    }

    @Test
    void runCypressTests() throws InterruptedException, IOException, TimeoutException {

        //Testcontainers.exposeHostPorts(port); //(4)
        logger.info("Springboot start on port: {}", port);
        logger.info("Starting Cypress Container");

        try (CypressContainer container = new CypressContainer().withLocalServerPort(port)) { //(5)
            container.start();
            CypressTestResults testResults = container.getTestResults(); //(6)

            if (testResults.getNumberOfFailingTests() > 0) {
                fail("There was a failure running the Cypress tests!\n\n" + testResults); //(7)
            }
        }
    }

}
