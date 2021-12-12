package by.goncharov.epamsound.controller;

import by.goncharov.epamsound.controller.command.Command;
import by.goncharov.epamsound.controller.command.CommandFactory;
import by.goncharov.epamsound.controller.command.DownloadErrorCommand;
import by.goncharov.epamsound.controller.command.admin.AddTrackCommand;
import by.goncharov.epamsound.controller.command.user.CommentCommand;
import by.goncharov.epamsound.controller.command.user.DownloadCommand;
import by.goncharov.epamsound.dao.ConnectionPool;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@MultipartConfig(fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 20,
        maxRequestSize = 1024 * 1024 * 50)
@WebServlet("/controller")
public class SoundServlet extends HttpServlet
        implements ServletContextListener {
    @Override
    public void contextInitialized(final ServletContextEvent
                                               servletContextEvent) {
    }
    @Override
    public void contextDestroyed(final ServletContextEvent
                                             servletContextEvent) {
        ConnectionPool.getInstance().terminatePool();
    }

    @Override
    protected void doGet(final HttpServletRequest request,
                         final HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(final HttpServletRequest request,
                          final HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }


    protected void processRequest(final HttpServletRequest request,
                                  final HttpServletResponse response)
            throws ServletException, IOException {

        SessionRequestContent sessionRequestContent
                = new SessionRequestContent();
        sessionRequestContent.extractValues(request);
        CommandFactory client = new CommandFactory();
        Command command = client.defineCommand(
                sessionRequestContent);
        String page;

        if (command instanceof DownloadCommand) {
            String filePath = command.execute(sessionRequestContent);
            FileDownloader downloader = new FileDownloader();
            if (!downloader.downloadTrack(filePath, response,
                    getServletContext())) {
                DownloadErrorCommand errorCommand = new DownloadErrorCommand();
                page = errorCommand.execute(sessionRequestContent);
                sessionRequestContent.insertAttributes(request);
                RequestDispatcher dispatcher = getServletContext()
                        .getRequestDispatcher(page);
                dispatcher.forward(request, response);
            }
        } else {
            if (command instanceof AddTrackCommand) {
                FileUploader uploader = new FileUploader();
                boolean res = uploader.uploadFile(request,
                        sessionRequestContent);
                sessionRequestContent.setRequestAttribute("result", res);
                sessionRequestContent.setRequestAttribute("realPath",
                        request.getServletContext().getContextPath());
            }
            page = command.execute(sessionRequestContent);
            sessionRequestContent.insertAttributes(request);
            if (command instanceof CommentCommand) {
                response.sendRedirect(request.getServletContext()
                        .getContextPath() + page);
            } else {
                RequestDispatcher dispatcher = getServletContext()
                        .getRequestDispatcher(page);
                dispatcher.forward(request, response);
            }
        }
    }
}

