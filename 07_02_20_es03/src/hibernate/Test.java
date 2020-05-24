package hibernate;

import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

public class Test {
	
		public static void main(String[] argv)
		{
			 
			Session session = new Configuration().configure().buildSessionFactory().openSession();
			Transaction tx = null;
			
			 try {		  
				  tx = session.beginTransaction();
				  Progetto p0 = new Progetto("A","Tec",1998,10);
				  Progetto p1 = new Progetto("B","Scienza",1999,15);
				  
				  Work_Package wp0 =new Work_Package("gruppo1","servlet","sviluppo",p0);
				  Work_Package wp1 =new Work_Package("gruppo2","jsp","sviluppo",p0);
				
				  Work_Package wp2 =new Work_Package("gruppo3","microscopi","osservazione",p1);
				  Work_Package wp3 =new Work_Package("gruppo4","atomi","ricerca",p1);
				  Work_Package wp4 =new Work_Package("gruppo5","astronomia","osservazione",p1);
				 
				  p0.add(wp0);
				  p0.add(wp1);
				  p1.add(wp2);
				  p1.add(wp3);
				  p1.add(wp4);
				  
				  session.persist(p0);
				  session.persist(p1);
				  session.persist(wp0);
				  session.persist(wp1);
				  session.persist(wp2);
				  session.persist(wp3);
				  session.persist(wp4);
				  
				 
				  List<Progetto> progetti ;
				  int size=0;
				  String codice = null;
				  String name = null;
				  Progetto toDelete =null;
				  Query firstQuery = session.createQuery("from "+Progetto.class.getSimpleName());
				  progetti = firstQuery.list();
				  for (Progetto x : progetti) {
					 if (x.getWorkPackage().size()>size) {
						 size=x.getWorkPackage().size();
						 name=x.getNomeProgetto();
						 codice=x.getCodiceProgetto();
						 toDelete=x;
					 }
				  }
				  PrintWriter pw = new  PrintWriter(new FileWriter("BigProgetto.txt"));
				  pw.println("Codice: "+codice+" Name: "+name);
				  pw.close();
				  for (Work_Package y : toDelete.getWorkPackage()) {
				  Query q = session.createQuery("delete from "+Work_Package.class.getSimpleName()+"  where  nomeWp = ?");
				  	q.setString(0, y.getNomeWp());
				  	q.executeUpdate();
				  }
				  Query q = session.createQuery("delete from "+Progetto.class.getSimpleName()+"  where  codiceProgetto = ?");
				  q.setString(0,toDelete.getCodiceProgetto());
				  q.executeUpdate();
				  tx.commit();
				  
				 
				  
			 }
			 catch (Exception e1) {
			      if (tx != null){
			    	  try{
			    		  tx.rollback();
			    	  }
			    	  catch(Exception e2){
			    		  e2.printStackTrace();
			    	  }
			      }
			      e1.printStackTrace();
			  }
			  finally {
				  session.close();
			  }
		}

}
