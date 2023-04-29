package autotest.framework;


import org.apache.commons.io.FileUtils;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

public class PrepareTest extends InitializeTest{

    @Test
    private void cleanupOutputFromPreviousRun() throws IOException {
        results.info("Removing previous run output...", false);
        File output = new File(System.getProperty("user.dir") + "\\test-output");
        FileUtils.deleteDirectory(output);
        results.assertTrue(!FileUtils.isDirectory(output), "Output cleaned successfully", "Output not removed", false);
    }
}
