package autotest.framework;

import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.xml.XmlSuite;
import org.uncommons.reportng.HTMLReporter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CustomReportListener extends HTMLReporter implements IReporter {

    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        suites.remove(0); // empty suite

        List<ISuite> reordered = new ArrayList<>();
        for(ISuite suite : suites) {
            if (suite.getName().contains("Prepare")) {
                reordered.add(0, suite);
            } else {
                reordered.add(suite);
            }
        }
        super.generateReport(xmlSuites, reordered, outputDirectory);

        // Read the HTML report file
        String reports = outputDirectory + "\\html";
        File[] filesList = new File(reports).listFiles();
        assert filesList != null;
        for (File f: filesList) {
            if(f.isFile() && f.getName().contains("suite") && f.getName().contains(".html")) {
                String reportContent = null;
                try {
                    reportContent = new String(Files.readAllBytes(Paths.get(f.toURI())));
                    // Modify the table HTML to remove the first two columns
                    //reportContent = reportContent.replace("class=\"method\"", "class=\"method\" style=\"display:none;\"");
                    reportContent = reportContent.replace("class=\"duration\"", "class=\"duration\" style=\"display:none;\"");

                    // Write the modified HTML report file
                    try (FileWriter writer = new FileWriter(f)) {
                        writer.write(reportContent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }


    }
}
