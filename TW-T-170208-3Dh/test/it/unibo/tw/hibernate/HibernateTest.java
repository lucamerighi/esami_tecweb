package it.unibo.tw.hibernate;

import java.io.BufferedWriter;
import java.io.File;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

public class HibernateTest {

	  public static void main(String[] args){// throws Exception{
		  
		  Session session = new Configuration().configure().buildSessionFactory().openSession();
		  Transaction tx = null;
		  
		  try {		  
			  tx = session.beginTransaction();	
			    
			  
			  OggettoDigitale oggettoDigitale = new OggettoDigitale();
			  oggettoDigitale.setIdOgg(11);
			  oggettoDigitale.setCodOgg("O1");
			  oggettoDigitale.setFormato("mp3");
			  oggettoDigitale.setNome("Oggetto 11");
			  oggettoDigitale.setDataDigit(new Date());
			  
			  
			  OggettoDigitale oggettoDigitale2 = new OggettoDigitale();
			  oggettoDigitale2.setIdOgg(22);
			  oggettoDigitale2.setCodOgg("O2");
			  oggettoDigitale2.setFormato("jpeg");
			  oggettoDigitale2.setNome("Oggetto 22");
			  oggettoDigitale2.setDataDigit(new Date());
			//  session.persist(oggettoDigitale2);
			  
			  OggettoDigitale oggettoDigitale3 = new OggettoDigitale();
			  oggettoDigitale3.setIdOgg(33);
			  oggettoDigitale3.setCodOgg("O3");
			  oggettoDigitale3.setFormato("jpeg");
			  oggettoDigitale3.setNome("Oggetto 33");
			  oggettoDigitale3.setDataDigit(new Date());
			//  session.persist(oggettoDigitale3);
			  
			  MaterialeFisico materialeFisico = new MaterialeFisico();
			  materialeFisico.setIdMat(1);
			  materialeFisico.setCodMat("M1");
			  materialeFisico.setNome("materiale 1");
			  materialeFisico.setDataCreaz(new Date());
			  materialeFisico.setDescrizione("blablabla");
			  materialeFisico.getOggetti().add(oggettoDigitale);
			  materialeFisico.getOggetti().add(oggettoDigitale2);
			 
			  oggettoDigitale.setMat(materialeFisico);
			  oggettoDigitale2.setMat(materialeFisico);
			  
			  
			  MaterialeFisico materialeFisico2 = new MaterialeFisico();
			  materialeFisico2.setIdMat(2);
			  materialeFisico2.setCodMat("M2");
			  materialeFisico2.setNome("materiale 2");
			  materialeFisico2.setDataCreaz(new Date());
			  materialeFisico2.setDescrizione("blablabla");
			  materialeFisico2.getOggetti().add(oggettoDigitale3);
			
			  oggettoDigitale3.setMat(materialeFisico2);
			
			  
			  ArchivioFisico archivio= new ArchivioFisico();
			  archivio.setIdArch(111);
			  archivio.setNome("archivio 111");
			  archivio.setDescrizione("dfhnjkf");
			  archivio.setDataCreazione(new Date());
			  archivio.setCodArch("A111");
			  archivio.getMateriali().add(materialeFisico);
			 
			  materialeFisico.setArchivio(archivio);
			
			  
			  ArchivioFisico archivio2= new ArchivioFisico();
			  archivio2.setIdArch(222);
			  archivio2.setNome("archivio 222");
			  archivio2.setDescrizione("dfhnjkf");
			  archivio2.setDataCreazione(new Date());
			  archivio2.setCodArch("A222");
			  archivio2.getMateriali().add(materialeFisico2);
			  materialeFisico2.setArchivio(archivio2);
			  
			  session.persist(archivio);
			  session.persist(archivio2);
			  session.persist(materialeFisico);
			  session.persist(materialeFisico2);
			  session.persist(oggettoDigitale);
			  session.persist(oggettoDigitale2);
			  session.persist(oggettoDigitale3);
			  
			  
			  
			  tx.commit();

			  File f= new File("archive.txt");
			  Writer w = new PrintWriter(f);
			  BufferedWriter bw = new BufferedWriter(w);
			  
			  // per ogni archivio fisico post 2014, il numero dei formati distinti 
			  //in cui sono raggruppati i corrispondenti oggetti digitalizzati
			  // versione SQL
			  System.out.println();
			  bw.write("Formati oggetti digitali degli archivi: \n");
			
			  Query query = session.createQuery(" from "+ArchivioFisico.class.getSimpleName()+ 
					  " where year(dataCreazione) > ? ");
			  
			  query.setInteger(0, 2014);
			  Set<String> formati= new HashSet<String>();
			  List<ArchivioFisico> archivi = query.list();
			  for(ArchivioFisico a: archivi) {
				  Set<MaterialeFisico> mat = a.getMateriali();
				  for(MaterialeFisico m: mat) {
					  Set<OggettoDigitale> ogg = m.getOggetti();
					  for(OggettoDigitale o: ogg) {
						  if(!formati.contains(o.getFormato())) {
							  formati.add(o.getFormato());
						  }
					  }
				  }
				  bw.write( a.getCodArch() + ": "+ formati.size()+"\n");
				  formati= new HashSet<String>();
				  
			  }
			  
			  bw.write("\n\nArchivi con formati jpg: \n");
				
			   query = session.createQuery("from "+ArchivioFisico.class.getSimpleName() );
	
			  archivi = query.list();
			  boolean trov;
			  for(ArchivioFisico a: archivi) {
				  trov=false;
				  Set<MaterialeFisico> mat = a.getMateriali();
				  for(MaterialeFisico m: mat) {
					  Set<OggettoDigitale> ogg = m.getOggetti();
					  for(OggettoDigitale o: ogg) {
						  if(o.getFormato().contentEquals("jpeg")) {
							  bw.write(a.getCodArch()+"\n");
							  trov=true;
							  break;
						  }
					  }
					  if(trov) break;
				  }				  
			  }
			  
			  
			  
			 
			  System.out.println();
			  bw.close();
			  w.close();
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
