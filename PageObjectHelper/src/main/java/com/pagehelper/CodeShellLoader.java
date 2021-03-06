package com.pagehelper;

import com.pagehelper.outputbucket.CodeOutputBucket;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Loads the code template file that defines the class, or encapsulating code, for the generated page object code.
 * @author Paul Grandjean
 * @since 10/23/11
 * @version 1.0alpha
 */
public class CodeShellLoader {

    private final Logger logger = Logger.getLogger(this.getClass());
    private Configurator configurator;
    private BufferedReader configFile;


    public CodeShellLoader() {
        this.configurator = Configurator.getConfigurator();
    }


    private void openConfigFile(String filePath) throws FileNotFoundException{

        try {
            logger.debug("Looking for code shell configuration file: " + filePath);
            configFile = new BufferedReader(new FileReader(filePath));
            logger.debug("Found it, using file: " + filePath);
        }
        catch (FileNotFoundException fileNotFoundException) {
            logger.fatal("File Not Found Exception in: " + fileNotFoundException.getClass());
            logger.fatal("Cause: " + fileNotFoundException.getCause());
            logger.fatal("Message: " + fileNotFoundException.getMessage());
            logger.fatal("Stack Trace: " + fileNotFoundException.getStackTrace().toString());
            throw fileNotFoundException;
        }

    }


    public void loadConfig(CodeOutputBucket codeBucket) throws PageHelperException {

      try {

          openConfigFile(configurator.getCodeShellTemplateFilePath());
          StringBuffer stringBuffer = new StringBuffer();

          String line = configFile.readLine();

          logger.debug("Read the first line: " + line);

          while (line != null && !line.contains(configurator.getCodeShellCodeBlockIndicator())){

              // Be sure the blank lines are also saved to the CodeOutputBucket buffer as whitespace.
              stringBuffer.append(line);
              stringBuffer.append("\n");

              // Get the next line from the config file.
              line = configFile.readLine();
              logger.debug("Read next line: " + line);

          }

          logger.debug("Found code-block indicator, storing the header code:\n" + stringBuffer);
          codeBucket.setHeader(stringBuffer);

          if (line == null) {
              logger.debug("Unexpected null line found, with code-block indicator not found.");
              throw new PageHelperException("Unexpected null line in Code Shell Loader");
          }

          // clear the StringBuffer by starting a new one.
          logger.debug("Erasing temp StringBuffer with a new StringBuffer for processing the trailer code.");
          stringBuffer = new StringBuffer();

          // Advance the line so we don't write the CodeBlockIndicator; it's only used as a delimeter.
          line = configFile.readLine();

          while(line != null) {

              // Be sure the blank lines are also saved to the CodeOutputBucket buffer as whitespace.
              logger.debug("Adding to temp StringBuffer the trailer line: " + line);
              stringBuffer.append(line);
              stringBuffer.append("\n");

              // Get the next line from the config file.
              line = configFile.readLine();
              logger.debug("Found the line: " + line);

          }

          logger.debug("Adding to CodeOutputBucket the trailer code:\n" + stringBuffer);
          codeBucket.setTrailer(stringBuffer);

      } catch (FileNotFoundException e) {
          throw new PageHelperException("Code Shell File not found: " + e.getMessage());
      } catch (IOException e) {
          throw new PageHelperException("I/O Exception reading Code Shell File.  Message: " + e.getMessage());
      }

    }

}
