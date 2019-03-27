package org.medex.dao;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.medex.beans.Display;
import org.medex.beans.Patient;
import org.medex.beans.User;
import org.medex.util.DBConstants;
import org.medex.util.DBUtil;
@Repository("patientDao")
public class PatientDaoImpl implements PatientDao {

	/*Connection con = null;
	Statement st = null;
	PreparedStatement pst = null;
	ResultSet rs = null;*/
	 @Autowired
	 private SessionFactory sessionFactory;
	
	 public String insertRegistration(Patient p) {
        int c = 0,r1=0,r2=0;
        String role="patient";
        String pid=null;
    
        SQLQuery<Patient> patientSelect=sessionFactory.getCurrentSession().createSQLQuery("select * from patient");
        patientSelect.addEntity(Patient.class);
        List<Patient> patientList=patientSelect.list();
   for(Patient i:patientList)
   {
                        c=c+1;
                        pid="P"+c;
                        SQLQuery<Patient> patientInsert=sessionFactory.getCurrentSession().createSQLQuery("insert into patient values(?,?,?,?,?,?,?,?,?,?)");
                        patientInsert.addEntity(Patient.class);
      
                        patientInsert.setString(1, pid);
                        patientInsert.setString(2, p.getFname());
                        patientInsert.setString(3, p.getLname());
                        patientInsert.setInteger(4, p.getAge());
                        patientInsert.setString(5, p.getGender());
                        patientInsert.setString(6, p.getPhn_number());
                        patientInsert.setString(7, p.getEmail());
                        patientInsert.setString(8, p.getAddress());
                        patientInsert.setInteger(9, p.getZipcode());
                        patientInsert.setString(10, p.getCity());
                        r1 = patientInsert.executeUpdate();
                        SQLQuery<User> userInsert=sessionFactory.getCurrentSession().createSQLQuery("insert into user values(?,?,?)");
                        userInsert.addEntity(User.class);
                      //  pst = con.prepareStatement("insert into user values(?,?,?)");
                        userInsert.setString(1, pid);
                        userInsert.setString(2, p.getPwd());
                        userInsert.setString(3, role);
                        r2 = userInsert.executeUpdate();
                        
   }
        if(r1>0 && r2>0)
        {
                        return pid;
        }
        else{
                        return null;
        }
        

}

	
	public List<Display> viewAppointmentDetails(Display d)
	{
		
        SQLQuery<Display> appointmentSelect=sessionFactory.getCurrentSession().createSQLQuery("select * from appointment where date_of_app=? and pat_id=?");
        appointmentSelect.addEntity(Display.class);
        List<Display> doctorList= appointmentSelect.list();
        appointmentSelect.setString(1, d.getAppDate());
        appointmentSelect.setString(2, d.getPatId());

        List<Display> display=new ArrayList<>();
        //ResultSet rs1=null;
       
      /*  try {
               con=DBUtil.getConnection(DBConstants.DRIVER,DBConstants.URL,DBConstants.UNAME, DBConstants.PWD);
              
             String selectQuery="select * from appointment where date_of_app=? and pat_id=?";
            pst=con.prepareStatement(selectQuery);*/
   		/*	
   			rs=pst.executeQuery();*/
        for(Display i:doctorList)
        {
           
                                        	
                                        	 Display d1=new Display();
                                        	 SQLQuery<Display> selectDoctor=sessionFactory.getCurrentSession().createSQLQuery("select * from doctor where did=?:a");
                                        	 selectDoctor.addEntity(Display.class);
                                        	/* String selectDoctor="select * from doctor where did=?";	                            
                                        	 pst=con.prepareStatement(selectDoctor);*/
                                        	 selectDoctor.setString(1, rs.getString("doc_id"));
                                        	 rs1=pst.executeQuery();
                                        	 if(rs1.next())
                                        	 {
                                        		 d1.setDocName(rs1.getString("fname"));
                                        		 d1.setDocCategory(rs1.getString("specialization"));
                                        	 }
                                        	 
                                        	   d1.setAppBookDate(rs.getString("date_time_booked"));
                                        	   d1.setAppDate(rs.getString("date_of_app"));
                                        	   d1.setTimeSlot(rs.getString("time_of_app"));
                                        	  
                                               display.add(d1);
                                              
                                        }
                                        con.close();
                        } 
                        catch (Exception e) 
                        {
                                        e.printStackTrace();
                        }
                        
                        return display;
        }


	@Override
	public List<Display> viewAppointment(Display d) {
		List<Display> display=new ArrayList<>();
		try
		{
			con=DBUtil.getConnection(DBConstants.DRIVER,DBConstants.URL,DBConstants.UNAME, DBConstants.PWD);
            
            String selectQuery="select t1.id,t1.date_of_app,t1.time_of_app,t1.date_time_booked,t2.fname,t2.lname,t2.specialization from appointment t1 join doctor t2 on t1.doc_id=t2.did  where t1.pat_id=?";
            pst=con.prepareStatement(selectQuery);
  			pst.setString(1, d.getPatId());
  			rs=pst.executeQuery();
  			while(rs.next())
            {
  				Display d1=new Display();
  				 d1.setDocName(rs.getString("fname")+" "+rs.getString("lname"));
        		 d1.setDocCategory(rs.getString("specialization"));
        		 d1.setAppDate(rs.getString("date_of_app"));
          	   d1.setTimeSlot(rs.getString("time_of_app"));
          	   d1.setAppBookDate(rs.getString("date_time_booked"));
          	   
          	 d1.setAppId(rs.getString("id"));
          	 display.add(d1);
  				
            }
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return display;
	}

}