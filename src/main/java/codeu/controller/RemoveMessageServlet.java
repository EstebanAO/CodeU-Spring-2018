package codeu.controller;

import codeu.model.data.User;
import codeu.model.data.Message;
import codeu.model.store.basic.UserStore;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.util.UUID;
import codeu.model.data.User;
import codeu.model.store.basic.UserStore;
import org.mindrot.jbcrypt.BCrypt;
import java.util.ArrayList;
import java.util.List;
import codeu.model.store.basic.MessageStore;
import codeu.model.store.basic.ConversationStore;

/**
 * Servlet class responsible for displaying the user profile.
 */
public class RemoveMessageServlet extends HttpServlet {

    private UserStore userStore;

    /**
     * Set up state for handling registration-related requests. This method is only called when
     * running in a server, not when running in a test.
     */
    @Override
    public void init() throws ServletException {
        super.init();
        setUserStore(UserStore.getInstance());
    }

    /**
     * Sets the UserStore used by this servlet. This function provides a common setup method
     * for use by the test framework or the servlet's init() function.
     */
    public void setUserStore(UserStore userStore) {
        this.userStore = userStore;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String requestUrl = request.getRequestURI();
        String userName = requestUrl.substring(Servlets.PROFILE_PATH.length());
        User user = userStore.getUser(userName);
        if (user == null) {
            // couldn't find user, redirect to conversation list
            System.out.println("The user " + userName + " doesn't exist. " );
            response.sendRedirect(Servlets.CONVERSATION_PATH);
            return;
        }
        request.setAttribute("user", user);
        request.getRequestDispatcher("/WEB-INF/view/profile.jsp").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String userName = (String) request.getSession().getAttribute("user");

        User user = userStore.getUser(userName);
        List<Message> messagesByUser = user.getMessages();

        for(Message message : messagesByUser)
        {
            if(request.getParameter(message.getId().toString()) != null)
            {
                user.removeMessage(message);
            }
        }

        response.sendRedirect(Servlets.PROFILE_PATH + userName);
    }
}
