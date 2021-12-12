package by.goncharov.epamsound.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

public class FileUploader {
    static final Logger LOGGER = LogManager.getLogger();
    private static final String SAVE_DIR = "uploadTracks";
    private static final String PATH = "path";
    public boolean uploadFile(final HttpServletRequest request,
                              final SessionRequestContent
                                      servletSessionRequestContent) {
        try {
            String appPath = request.getServletContext().getRealPath("");
            String savePath = appPath + File.separator + SAVE_DIR;

            File fileSaveDir = new File(savePath);
            if (!fileSaveDir.exists()) {
                fileSaveDir.mkdir();
                LOGGER.info("Directory " + savePath + "was created");
            }

            for (Part part : request.getParts()) {
                if (PATH.equals(part.getName())) {
                    String fileName = extractFileName(part);
                    fileName = new File(fileName).getName();
                    String path = savePath + File.separator + fileName;
                    part.write(path);
                    servletSessionRequestContent.setRequestAttribute(PATH,
                            path);
                }
            }
            LOGGER.info("Upload has been done successfully!");
            return true;
        } catch (ServletException | IOException e) {
            LOGGER.error("Exception during uploading file to server", e);
            return false;
        }
    }

    private String extractFileName(final Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }
}
