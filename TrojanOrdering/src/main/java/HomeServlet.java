import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/HomeServlet")
public class HomeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	PrintWriter out = response.getWriter();
        ArrayList<Restaurant> r = JDBCConnector.getRestaurants();
        for (Restaurant rest : r){ //placeholder stuff please give me a format to work with
            System.out.println(rest.getName());
        } 
        String html = makeHtml(r, request);
        //return html to javascript using fetch 
        response.setContentType("text/html");
        out.println(html);
        out.flush();
        out.close();

    }

    protected String makeHtml(ArrayList<Restaurant> r, HttpServletRequest request) {
    	HttpSession session = request.getSession(false);
    	String email = "";
    	if (session != null) {
    	    int user_id = (Integer) session.getAttribute("user_id");
    	    email = (String) session.getAttribute("email"); //get session 
    	    double balance = (Double) session.getAttribute("balance");
    	}
        String html = "";
        int count = 0;
        for (Restaurant rest : r) {
            String name = rest.getName();
            String address = rest.getAddress();
            String image = rest.getURL();
            int id = rest.getID();
            if (count % 2 == 0) { // start a new row
                html += "<div class=\"row\">\n";
            }
            html += "<a id=\"" + id + "\" class=\"container\" href=\"menu.html\"> <!--redirect to restaurants page for specified restaurant-->\n"
                    + "<img src=\"" + image + "\">\n"
                    + "<div class=\"caption\">" + name + "</div>\n"
                    + "</a>\n";
            if (count % 2 != 0 || count == r.size() - 1) { // close the row if it's the second restaurant in the row or if it's the last restaurant in the list
                html += "</div>\n";
            }
            count++;
        }
        //html += email;
        return html;
    }
}

