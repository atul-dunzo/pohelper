package com.testhelper;

import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Base class for both CodeBucket and HintsBucket file writing.  Contains 3 buffers for managing a header, body, and
 * trailer portions of the output.  Methods here handle the file opening, naming, and writing
 * also with providing an interface for setting the page object name via an abstract method.
 * User: pgrandje
 * Date: 4/27/14
 */
public abstract class AbstractBucket {

    private final Logger logger = Logger.getLogger(AbstractBucket.class);

    // The source or hints to be generated.
    /* Note:  I'm not catching exceptions for these being null.  Body is initialized in constructor.  But header and
              trailer are not and are only set if they are explicitely set with the methods.  I decided to just throw a
              null ptr exception if this occurs.
    */
    protected StringBuffer header;
    protected StringBuffer body;
    protected StringBuffer trailer;

    // For the output file.
    protected String fileName;
    protected BufferedWriter outputFile;


    AbstractBucket() {
        body = new StringBuffer();
    }


    public abstract void setPageObjectName(String pageName);


    public void setHeader(StringBuffer header) {
        this.header = header;
    }


    public void setTrailer(StringBuffer trailer) {
        this.trailer = trailer;
    }


    public void addCode(String string) {
        logger.trace("Adding string to bucket:\n" + string);
        body.append(string);
        body.append("\n");
    }


    public void dumpToFile(String filePath) {

        try {
            if (filePath == null) {
                 throw new TestHelperException("Output file path is null.");
            }
            filePath = filePath + "/" + fileName;
            logger.info("Using current working directory: " + System.getProperty("user.dir"));
            logger.info("Writing output file: " + filePath);
            outputFile = new BufferedWriter(new FileWriter(filePath));
            logger.trace("Writing code header:\n" + header.toString());
            outputFile.write(header.toString());
            logger.trace("Writing code buffer:\n" + body.toString());
            outputFile.write(body.toString());
            logger.trace("Writing code trailer:\n" + trailer.toString());
            outputFile.write(trailer.toString());
            logger.info("Closing output file.");
            outputFile.close();
        } catch (IOException e) {
            logger.error("I/O Exception writing output file");
            logger.error("Message: " + e.getMessage());
            throw new TestHelperException("Caught I/O Exception writing the file.  Message: " + e.getMessage() + ".");
        }

    }

}
