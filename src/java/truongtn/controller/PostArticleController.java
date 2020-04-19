/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truongtn.controller;

import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import truongtn.daos.ArticleDAO;
import truongtn.utils.MyToys;

/**
 *
 * @author truongtn
 */
public class PostArticleController extends HttpServlet {
    private static final String SUCCESS = "posted.jsp";
    private static final String ERROR = "error.jsp";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = ERROR;
        try {
            String email = request.getParameter("email");
            String title = request.getParameter("txtTitle");
            String shortDescription = request.getParameter("txtShortDescription");
            String content = request.getParameter("txtContent");
            String author = request.getParameter("author");
            Date date = MyToys.getCurrentDate("yyyy-MM-dd HH:mm:ss");
            
            ArticleDAO dao = new ArticleDAO();
            if(dao.insertAnArticle(email, title, shortDescription, content, author, date)){
                url = SUCCESS;
            }else{
                url = ERROR;
            }
        } catch (Exception e) {
            log("Error at PostController: " + e.getMessage());
        } finally{
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
