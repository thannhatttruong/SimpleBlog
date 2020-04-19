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
import truongtn.daos.ArticleDAO;
import truongtn.entity.Article;

/**
 *
 * @author truongtn
 */
public class ReadController extends HttpServlet {
    private static final String SUCCESS = "detail.jsp";
    private static final String ERROR = "welcome.jsp";
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
        String url = ERROR;
        try {
            String txtArticleId = request.getParameter("txtArticleId");
            System.out.println("Article Id: " + txtArticleId);
            HttpSession session = request.getSession();
            String txtSearch = (String) session.getAttribute("SEARCH");
            System.out.println("Search: " + txtSearch);
            int articleId = Integer.parseInt(txtArticleId);
            ArticleDAO dao = new ArticleDAO();
            Article[] articleArr;
            if(txtSearch != null){
                articleArr = dao.search(txtSearch);
            }else{
                articleArr = dao.getArticles();
            }
            
            Article article = dao.getDetail(articleId);
            if(article != null){
                url = SUCCESS;
                session.setAttribute("ARTICLE", article);
            }else{
                request.setAttribute("ERROR", "Load data error!");
            }
            request.setAttribute("LIST", articleArr);
        } catch (Exception e) {
            log("Error at ReadController: " + e.getMessage());
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
