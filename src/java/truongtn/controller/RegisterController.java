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
import truongtn.entity.Account;
import truongtn.utils.MyToys;

/**
 *
 * @author truongtn
 */
public class RegisterController extends HttpServlet {
    private static final String ERROR = "register.jsp";
    private static final String REGISTER_USER = "register_user.jsp";
    private static final String LOGIN = "index.jsp";
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
            String name = request.getParameter("txtName");
            String role = request.getParameter("txtRole");
            String email = request.getParameter("txtEmail");
            String password = request.getParameter("txtPassword");
            String confirm = request.getParameter("txtConfirm");
            
//            check validate in backend
            if (password.equals(confirm)) {
                AccountDAO dao = new AccountDAO();
                String passwordHash = MyToys.toHexString(MyToys.getSHA(password));
                if(dao.register(email, passwordHash, name, "member", "New")){
                    HttpSession session = request.getSession(true);
                    Account acc = new Account(email, name);
                    session.setAttribute("MEMBER", acc);
                    if(role.equals("member")){
                        url = LOGIN;
                    }else{
                        url = REGISTER_USER;
                    }              
                }else{
                    request.setAttribute("ERROR", "Username is duplicate");
                }
            }else{
                request.setAttribute("ERROR", "An error occured. Please try again!");
            }
        } catch (Exception e) {
            log("Error at RegisterController: " + e.getMessage());
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
