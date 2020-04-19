/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package truongtn.controller;

import java.io.IOException;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import truongtn.utils.MyToys;

/**
 *
 * @author truongtn
 */
public class SendCodeController extends HttpServlet {
    private static final String SUCCESS = "active.jsp";
    private static final String ERROR = "registered.jsp";
    private static final String hostEmail = "thannhattruong17021999@gmail.com";
    private static final String password = "truong1999";
    private static final String host = "localhost";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = ERROR;
        try {
            HttpSession session = request.getSession();
            if(session != null){
                String emailVertify = request.getParameter("txtEmailVerify");
                Random random = new Random();
                double random_dub = random.nextDouble();
                String vertifyCode = String.valueOf((int)(random_dub * 10000));
                if(MyToys.sendEmail(hostEmail, password, emailVertify, host, vertifyCode)){
                    url = SUCCESS;
                    session.setAttribute("VERIFY", vertifyCode);
                }else{
                    request.setAttribute("ERROR", "Email not exist. Please try again!!");
                }   
            }
        } catch (Exception e) {
            log("Error at SendCodeController: " + e.getMessage());
        }finally{
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
