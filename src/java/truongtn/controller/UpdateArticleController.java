/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truongtn.controller;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import truongtn.daos.ArticleDAO;
import truongtn.entity.Article;
import truongtn.utils.HibernateUtil;

/**
 *
 * @author truongtn
 */
public class UpdateArticleController extends HttpServlet {

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
        try {
            String[] idDelete = request.getParameterValues("chkDelete");
            if (idDelete != null) {
                ArrayList<Integer> idDel = new ArrayList<>();
                for (String id : idDelete) {
                    idDel.add(Integer.parseInt(id));
                }
                ArticleDAO dao = new ArticleDAO();
                dao.deleteArticle(idDel, "Deleted");
            }

            String[] idApproval = request.getParameterValues("chkApproval");
            if (idApproval != null) {
                ArrayList<Integer> idAppr = new ArrayList<>();
                for (String id : idApproval) {
                    idAppr.add(Integer.parseInt(id));
                }
                ArticleDAO dao = new ArticleDAO();
                
                //activeAritcle, account still willbe active too
                dao.activeArticle(idAppr, "Active");
                
            }

            ArticleDAO articleDAO = new ArticleDAO();
            Session sessionArticle = HibernateUtil.getSessionFactory().openSession();
            sessionArticle.getTransaction().begin();
            Article[] articleArr = articleDAO.getArticlesByAdmin();
            request.setAttribute("LIST", articleArr);
        } catch (Exception e) {
            log("Error at UpdateArticleController: " + e.getMessage());
        } finally {
            request.getRequestDispatcher("admin.jsp").forward(request, response);
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
