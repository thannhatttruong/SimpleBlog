/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truongtn.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import truongtn.daos.AccountDAO;
import truongtn.daos.ArticleDAO;
import truongtn.entity.Account;
import truongtn.entity.Article;
import truongtn.utils.HibernateUtil;
import truongtn.utils.MyToys;

/**
 *
 * @author truongtn
 */
public class LoginController extends HttpServlet {

    private static final String SUCCESS = "detail.jsp";
    private static final String FAIL = "index.jsp";
    private static final String POST = "post.jsp";
    private static final String ADMIN = "admin.jsp";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = FAIL;
        try {
            HttpSession session = request.getSession(true);
            String username = request.getParameter("txtUsername");
            String password = request.getParameter("txtPassword");
            String passwordHash = MyToys.toHexString(MyToys.getSHA(password));
            AccountDAO dao = new AccountDAO();
            Account acc = dao.authenticate(username, passwordHash);
            String role = acc.getRole();
            if (acc != null) {
                session.setAttribute("USER", acc);
                String action = request.getParameter("ACTION");
                if (role.equals("admin")) {
                    url = ADMIN;
                    
                    ArticleDAO articleDAO = new ArticleDAO();
                    Session sessionArticle = HibernateUtil.getSessionFactory().openSession();
                    sessionArticle.getTransaction().begin();
                    Article[] articleArr = articleDAO.getArticlesByAdmin();
                    request.setAttribute("LIST", articleArr);
                } else {
                    if (action.equals("LoginPost")) {
                        url = POST;
                    } else {
                        url = SUCCESS;
                        
                        ArticleDAO articleDAO = new ArticleDAO();
                        Session sessionArticle = HibernateUtil.getSessionFactory().openSession();
                        sessionArticle.getTransaction().begin();
                        Article[] articleArr = articleDAO.getArticles();
                        request.setAttribute("LIST", articleArr);
                    }
                }

            } else {
                request.setAttribute("ERROR", "Invalid username or password!!");
            }
        } catch (Exception e) {
            log("Error at LoginController: " + e.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
