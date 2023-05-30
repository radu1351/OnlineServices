import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;


public class LogTest {
    private static final Logger logger = LogManager.getLogger(LogTest.class.getName());
    @Test
    public void logTest(){
        logger.info("ceva nenea");
        logger.error("o eroare");
        logger.fatal("pling");
    }

}
