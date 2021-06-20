package com.codegym.controller;

import com.codegym.dao.UserDAO;
import com.codegym.model.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "UserServlet", urlPatterns = "/users")
public class UserServlet extends HttpServlet {
    final long serialVersionUID = 1L;
    private UserDAO userDAO;

    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if(action== null){
            action = "";
        }
            switch (action){
                case "create":
                    showNewForm(request,response);
                    break;
                case "edit":
                    showEditForm(request,response);
                    break;
                case "delete":
                    try {
                        deleteUser(request,response);

                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                    break;
                case  "find":
                    findUser(request,response);
                    break;
                default:
                    listUser(request,response);
                    break;
            }
    }

    private void findUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String key = request.getParameter("key");
        List<User> userList = userDAO.find(key);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("user/find.jsp");
        request.setAttribute("data",userList);
        requestDispatcher.forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action==null){
            action="";
        }
        switch (action){
            case "create":
                insertUser(request,response);
                break;
            case "edit":
                try {
                    updateUser(request,response);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                break;
        }
    }

    private void listUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> listUser = userDAO.selectAllUsers();
        request.setAttribute("listUser",listUser);
        RequestDispatcher dis = request.getRequestDispatcher("user/list.jsp");
        dis.forward(request,response);
    }
    private  void  showNewForm(HttpServletRequest request , HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dis = request.getRequestDispatcher("user/create.jsp");
        dis.forward(request,response);
    }
    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        User existingUser = userDAO.selectUser(id);
        request.setAttribute("user",existingUser);
        RequestDispatcher dis = request.getRequestDispatcher("user/edit.jsp");
        dis.forward(request,response);
    }
    private void insertUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String  country = request.getParameter("country");

        User newUser = new User(name,email,country);
        userDAO.insertUser(newUser);

        RequestDispatcher dis =  request.getRequestDispatcher("user/create.jsp");
        dis.forward(request,response);
    }
    private void updateUser(HttpServletRequest request , HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String country = request.getParameter("country");

        User book = new User(id,name,email,country);
        userDAO.updateUser(book);

        RequestDispatcher dis = request.getRequestDispatcher("user/edit.jsp");
        dis.forward(request,response);
    }
    private void deleteUser(HttpServletRequest request , HttpServletResponse response) throws SQLException, ServletException, IOException {

        int id =  Integer.parseInt(request.getParameter("id"));
        userDAO.deleteUser(id);

        List<User> listUser = userDAO.selectAllUsers();

        request.setAttribute("listUser",listUser);
        RequestDispatcher dis = request.getRequestDispatcher("user/list.jsp");
        dis.forward(request,response);
    }
}