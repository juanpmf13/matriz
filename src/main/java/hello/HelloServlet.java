/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hello;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
/**
 *
 * @author viter
 */
@WebServlet("/alomundo")
public class HelloServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet HelloServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet HelloServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
    protected double[][] verifica(double[][] matriz, int posi,int posj){
        if(matriz[posi][posj]==0.0){
            double[] temp;
            for(int x = posi;x<4-x;x++){
                if(matriz[x][posj]!=0){
                    temp=matriz[posi];
                    matriz[posi]=matriz[x];
                    matriz[x]=temp;
                    return matriz;
                }
            }
        }
        return matriz;
    }
    
    protected double calcular_determinantes(double [][]m){
        double resultado =1;
        for(int i=0;i<m.length;i++){
            resultado= resultado*m[i][i];
        }
        return resultado;
    }
    
    protected  double[] calc(double[] m, double[] v, int j, int sinal) {
        double tempa = Math.abs(m[j]);
        double tempb = Math.abs(v[j]);
        for(int k = j;k<m.length;k++){
            m[k]=m[k]+(sinal*((tempa/tempb)*v[k]));
        }
        return m;
    }
    
    protected  double[][] transforma(double [][] matriz){
        for(int j = 0;j<matriz.length-1;j++){
            double[] v = matriz[j];// temporarios para linha pivo
            for( int i=j+1;i<matriz.length;i++){
                matriz = verifica(matriz,j,j); // verifica se o pivo é diferente de 0 e o altera
                if(matriz[i][j]!=0){ //verifica se o valor comparado já é 0, se for não precisa fazer nada
                    if(matriz[i][j]*v[j]>0){ // verifica se é positivo, se for não precisa fazer nenhuma alteração
                        matriz[i]= calc(matriz[i],v,j,-1);
                    }
                    else{
                        matriz[i]= calc(matriz[i],v,j,1);
                    }
                }
            }
        }
        return matriz;
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        double[][] matriz;
        matriz = new double [4][4];
        for(int i =0; i<4 ; i++){
                matriz[i][0]=Double.parseDouble(request.getParameter("a"+String.valueOf(i)));
                matriz[i][1]=Double.parseDouble(request.getParameter("b"+String.valueOf(i)));
                matriz[i][2]=Double.parseDouble(request.getParameter("c"+String.valueOf(i)));
                matriz[i][3]=Double.parseDouble(request.getParameter("d"+String.valueOf(i)));
            }
        matriz= transforma(matriz);
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet HelloServlet</title>");            
            out.println("<style type=\"text/css\">");
            out.println("body{ margin:0;"
                    + "padding:0; backgound-color: #c0ffc1; text-align:center;}"
                    + " #tab{"
                    + "width: 1024px; margin-top:10px;margin:0 auto;"
                    + "}");
            out.println("table, td, th {\n" +
                        "  border: 1px solid black;\n" +
                        "}\n" +
                        "\n" +
                        "table {\n" +
                        "  border-collapse: collapse;\n" +
                        "  width: 100%;\n" +
                        "}\n" +
                        "\n" +
                        "td {\n" +
                        "  text-align: center;\n" +
                        "}");
            out.println("</style>");;
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet HelloServlet</h1>");
            out.println("<p>");
            out.println("<div id = \"tab\">");
            out.println("<table border = \"1\" bgcolor=\"green\" align: center  width=300 height=100  ");
            for(int i =0 ;i<matriz.length;i++){
                out.println("<tr>");
                for(int j =0 ;j<matriz.length;j++){
                    out.println("<td>");
                    out.println(matriz[i][j]+" ");
                    out.println("</td>");
                }
                out.println("</tr>");
                out.println("<br>");
            }
            out.println("</div>");
            NumberFormat formatação=new DecimalFormat("#0.00");
            out.println ("Determinante : "+ formatação.format(calcular_determinantes(matriz)));
            out.println ("</p>");
            out.println("</body>");
            out.println("</html>");  
            
        }
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
        double[][] matriz;
        matriz = new double [4][4];
        for(int i =0; i<4 ; i++){
                matriz[i][0]=Double.parseDouble(request.getParameter("a"+String.valueOf(i)));
                matriz[i][1]=Double.parseDouble(request.getParameter("b"+String.valueOf(i)));
                matriz[i][2]=Double.parseDouble(request.getParameter("c"+String.valueOf(i)));
                matriz[i][3]=Double.parseDouble(request.getParameter("d"+String.valueOf(i)));
            }
        matriz= transforma(matriz);
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet HelloServlet</title>");        
            out.println("<style type=\"text/css\">");
            out.println("body{ margin:0;"
                    + "padding:0; backgound-color: #c0ffc1; text-align:center;}"
                    + " #tab{"
                    + "width: 1024px; margin-top:10px;margin:0 auto;"
                    + "}");
            out.println("table, td, th {\n" +
                        "  border: 1px solid black;  \n" +
                        "\n" +
                        "table {\n" +
                        "  border-collapse: collapse;\n" +
                        "  width: 100%;\n" +
                        "}\n" +
                        "\n" +
                        "td {\n" +
                        "  text-align: center;\n" +
                        "}");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet HelloServlet</h1>");
            out.println("<p>");
            out.println("<div id = \"tab\" >");
            out.println("<table border = \"1\" bgcolor=\"green\" align: center  width=300 height=100  ");
            for(int i =0 ;i<matriz.length;i++){
                out.println("<tr>");
                for(int j =0 ;j<matriz.length;j++){
                    out.println("<td>");
                    out.println(matriz[i][j]+" ");
                    out.println("</td>");
                }
                out.println("</tr>");
                out.println("<br>");
            }
            out.println("</div>");
            NumberFormat formatação=new DecimalFormat("#0.00");
            out.println ("Determinante : "+ formatação.format(calcular_determinantes(matriz)));
            out.println ("</p>");
            out.println("</body>");
            out.println("</html>");
        }
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
    


