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
import truongtn.entity.Account;

/**
 *
 * @author truongtn
 */
public class MainController extends HttpServlet {

    private static final String LOGIN = "LoginController";
    private static final String REGISTER_PAGE = "register.jsp";
    private static final String ERROR = "error.jsp";
    private static final String REGISTER = "RegisterController";
    private static final String LOGOUT = "LogOutController";
    private static final String COUNTINUE = "ContinueController";
    private static final String loginPage = "index.jsp";
    private static final String SEARCH = "SearchController";
    private static final String READ = "ReadController";
    private static final String ACTIVE = "ActiveController";
    private static final String SEND_CODE = "SendCodeController";
    private static final String LOGIN_POST = "LoginPostController";
    private static final String POST_ARTICLE = "PostArticleController";
    private static final String SEARCH_ADMIN = "AdminSearchController";
    private static final String UPDATE_ARTICLE = "UpdateArticleController";
    private static final String POST_COMMENT = "PostCommentController";
    private static final String LOGIN_BY_FB = "LoginFacebookController";

    /**
     * /**
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
            String action = request.getParameter("action");
            System.out.println("Action: " + action);
            //basic action
            if (action.equals("login")) {
                url = loginPage;
            } else if (action.equals("Login")) {
                url = LOGIN;
            } else if (action.equals("LogOut")) {
                url = LOGOUT;
            } else if (action.equals("loginByFb")) {
                url = LOGIN_BY_FB;
            } else if (action.equals("Register")) {
                url = REGISTER;
            } else if (action.equals("continue")) {
                url = COUNTINUE;
            } else if (action.equals("register")) {
                url = REGISTER_PAGE;
            } else if (action.equals("read")) {
                url = READ;
            } else if (action.equals("Send Code")) {
                url = SEND_CODE;
            } else if (action.equals("Verify")) {
                url = ACTIVE;
            } else if (action.equals("loginPost")) {
                url = LOGIN_POST;
            } else {
                HttpSession session = request.getSession(false);
                String role = "";
                if (session != null) {
                    Account acc = (Account) session.getAttribute("USER");
                    if (acc != null) {
                        role = acc.getRole();
                    }
                    if (role.equals("admin")) {//admin action
                        if (action.equals("Search")) {
                            url = SEARCH_ADMIN;
                        } else if (action.equals("Update Article")) {
                            url = UPDATE_ARTICLE;
                        }
                    } else {//user action
                        if (action.equals("Post Article")) {
                            url = POST_ARTICLE;
                        } else if (action.equals("Post Comment")) {
                            url = POST_COMMENT;
                        } else if (action.equals("Search")) {
                            url = SEARCH;
                        }
                    }
                } else {
                    url = LOGIN;
                }

            }
        } catch (Exception e) {
            log("Error at MainController: " + e.getMessage());
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
