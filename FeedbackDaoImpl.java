package org.medex.dao;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.medex.beans.Feedback;
@Repository("feedbackDao")
public class FeedbackDaoImpl implements FeedbackDao {

	/*Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs =null;
*/
    @Autowired
    private SessionFactory sessionFactory;

	@Override
	public void feedbackFormRegister(Feedback f) {
	
		/*try {
			con = (Connection) DBUtil.getConnection(DBConstants.DRIVER,
					DBConstants.URL, DBConstants.UNAME, DBConstants.PWD);
			
			pst = con.prepareStatement("insert into feedbackform values(?,?,?,?)");
			pst.setString(1, f.getName());
			pst.setString(2, f.getEmail());
			pst.setString(3, f.getSubjectLine());
			pst.setString(4, f.getMsg());
			r1 = pst.executeUpdate();

			con.close();
		}
		catch (Exception e2) {
			e2.printStackTrace();
		}
		*/
		SQLQuery<Feedback>  feedbackInsert = sessionFactory.getCurrentSession().createSQLQuery("insert into feedbackform values(?,?,?,?)");
        feedbackInsert.addEntity(Feedback.class);
        int r1=feedbackInsert.executeUpdate();
		
}

}
