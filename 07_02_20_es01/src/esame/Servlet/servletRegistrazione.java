package esame.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import esame.Beans.Campo;
import esame.Beans.Player;

public class servletRegistrazione extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String homeURL = null;
	private List<Player> players = new ArrayList<Player>();
	private List<Campo> campi = new ArrayList<Campo>();
	
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		this.homeURL = config.getInitParameter("homeURL");
		for (int i=0;i<4;i++) {
			Campo campo = new Campo();
			campi.add(campo);
		}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
		boolean found = false;
		boolean foundC = false;
		boolean forYou=false;
		Player newPlayer=null;
		char ability = 0;
		
		if ( req.getParameter("Prenota") != null && req.getParameter("Prenota").equals("Prenota") ) {
			
			Player p = (Player) req.getSession().getAttribute("player");
			p.notify=false;
			for (Campo x : campi) {
				x.timeOver(players);
				x.outRichiesta(players);
			}
			for (Campo x : campi) {
				if (!foundC) {
					 foundC = x.available(p);
				}
			}
			req.getSession().setAttribute("disponibile", forYou);
			req.getSession().setAttribute("found", foundC);
			String destination = "book.jsp";
			RequestDispatcher requestDispatcher = req.getRequestDispatcher(destination);
			requestDispatcher.forward(req, response);
			
			
		}else if (req.getParameter("Insert") != null && req.getParameter("Insert").equals("Insert") ){
			Player p = (Player) req.getSession().getAttribute("player");
			p.notify=false;
			for (Campo x : campi) {
				x.timeOver(players);
				x.outRichiesta(players);
			}
			for (Campo x : campi) {
				if (!forYou) {
					 forYou= x.isForYou(p);
				}
			}
		req.getSession().setAttribute("disponibile", forYou);
		req.getSession().setAttribute("found", foundC);
		
		String destination = "book.jsp";
		RequestDispatcher requestDispatcher = req.getRequestDispatcher(destination);
		requestDispatcher.forward(req, response);
		
		}else if ( req.getParameter("Register") != null && req.getParameter("Register").equals("Registrati") ) {
			String name = req.getParameter("name");
			String pwd = req.getParameter("pwd");
				if (name.equals("admin") && pwd.equals("admin")) {
						String destination = "Admin.jsp";
						req.getSession().setAttribute("campi", campi);
						RequestDispatcher requestDispatcher = req.getRequestDispatcher(destination);
						requestDispatcher.forward(req, response);
				}else {
				for (Player x : players ) {
					if (x.nome.equals(name) && x.password.equals(pwd)) {
						found=true;
						newPlayer = x;
					}
				}
				
				if (!found) {
					if (req.getParameter("ability")=="") {
						String destination = "home.jsp";
						RequestDispatcher requestDispatcher = req.getRequestDispatcher(destination);
						requestDispatcher.forward(req, response);
					}
					ability = req.getParameter("ability").charAt(0);	
						if (!(ability=='A' || ability=='B' || ability=='C' || ability=='D' || ability=='E')){
							String destination = "home.jsp";
							RequestDispatcher requestDispatcher = req.getRequestDispatcher(destination);
							requestDispatcher.forward(req, response);
					}
					newPlayer = new Player(name,pwd,ability);
					players.add(newPlayer);
				}
			req.getSession().setAttribute("disponibile", forYou);
			req.getSession().setAttribute("found", foundC);
			req.getSession().setAttribute("player", newPlayer);
			String destination = "book.jsp";
			RequestDispatcher requestDispatcher = req.getRequestDispatcher(destination);
			requestDispatcher.forward(req, response);
				}
		
		}
	}

}
