package by.goncharov.epamsound.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FileDownloader {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String MIME_TYPE = "application/octet-stream";
    private static final String HEADER_KEY = "Content-Disposition";
    private static final String ATTACHMENT = "attachment; filename=\"%s\"";
    private static final int END = -1;
    private static final int BUFFER_LENGTH = 4096;
    private static final int START_OFFSET = 0;
    boolean downloadTrack(final String filePath,
                          final HttpServletResponse response,
                          final ServletContext context) {
        try {
            File downloadFile = new File(filePath);
            if (downloadFile.exists() && downloadFile.isFile()) {
                FileInputStream inStream = new FileInputStream(downloadFile);
                String mimeType = context.getMimeType(filePath);
                if (mimeType == null) {
                    mimeType = MIME_TYPE;
                }
                response.setContentType(mimeType);
                response.setContentLength((int) downloadFile.length());
                String headerValue = String.format(ATTACHMENT,
                        downloadFile.getName());
                response.setHeader(HEADER_KEY, headerValue);
                OutputStream outStream = response.getOutputStream();
                byte[] buffer = new byte[BUFFER_LENGTH];
                int bytesRead;
                while ((bytesRead = inStream.read(buffer)) != END) {
                    outStream.write(buffer, START_OFFSET, bytesRead);
                }
                return true;
            }
        } catch (IOException e) {
            LOGGER.error("Exception during file downloading");
        }
        return false;
    }
}
