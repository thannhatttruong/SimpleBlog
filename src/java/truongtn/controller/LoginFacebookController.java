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
import truongtn.daos.AccountDAO;
import truongtn.daos.ArticleDAO;
import truongtn.entity.Account;
import truongtn.entity.Article;
import truongtn.entity.FbUserInfo;
import truongtn.utils.APIWrapper;

/**
 *
 * @author truongtn
 */
public class LoginFacebookController extends HttpServlet {

    private static final String SUCCESS = "detail.jsp";
    private static final String FAIL = "index.jsp";

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
            String code = request.getParameter("code");

            APIWrapper wrapper = new APIWrapper();
            String accessToken = wrapper.getAccessToken(code);
            wrapper.setAccessToken(accessToken);

            HttpSession session = request.getSession(true);
            FbUserInfo fbUserInfo = wrapper.getFbUserInfo();
            AccountDAO dao = new AccountDAO();
            boolean exist = dao.authenticate(fbUserInfo.getFacebookID()) != null;
            Account acc;
            if (exist) {
                acc = dao.authenticate(fbUserInfo.getFacebookID());
            } else {
                acc = dao.registerByFb(fbUserInfo);
            }

            if (acc != null) {
                url = SUCCESS;
                
                ArticleDAO articleDAO = new ArticleDAO();
                Article[] articleArr = articleDAO.getArticles();
                request.setAttribute("LIST", articleArr);
                session.setAttribute("USER", acc);
            }else{
                request.setAttribute("ERROR", "Login with facebook failly!!");
            }

        } catch (Exception e) {
            log("Error at LoginFacebookController: " + e.getMessage());
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
