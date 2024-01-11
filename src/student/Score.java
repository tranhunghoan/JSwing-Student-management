package student;

import Db.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author ASUS TUF
 */
public class Score {

    Connection con = DbConnection.getConnection();
    PreparedStatement ps;

    public int getMax() {
        int id = 0;
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("select max(id) from score");
            while (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }

        return id + 1;

    }

    public boolean getDetail(int sid, int semNo) {
        try {
            ps = con.prepareStatement("SELECT * FROM course WHERE student_id = ? AND semester = ?");
            ps.setInt(1, sid);
            ps.setInt(2, semNo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                home.jTextField10.setText(String.valueOf(rs.getInt(2)));
                home.jTextField33.setText(String.valueOf(rs.getInt(3)));
                home.jTextCourse1.setText(rs.getString(4));
                home.jTextCourse2.setText(rs.getString(5));
                home.jTextCourse3.setText(rs.getString(6));
                home.jTextCourse4.setText(rs.getString(7));
                home.jTextCourse5.setText(rs.getString(8));
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Student id and semester  doesn't exist");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean isIdExist(int id) {
        String sql = "SELECT * FROM score WHERE  id = ? ";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean isSidandSemNoExist(int sid, int semNO) {
        String sql = "SELECT * FROM score WHERE  student_id = ? and semester = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, sid);
            ps.setInt(2, semNO);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void insert(int id, int sid, int semester, String course1, String course2,
            String course3, String course4, String course5, Double score1, Double score2,
            Double score3, Double score4, Double score5, Double average) {
        String sql = "INSERT INTO score VALUES (?, ?, ?, ?, ?, ?, ?, ? ,? ,? ,? ,? ,? ,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, sid);
            ps.setInt(3, semester);
            ps.setString(4, course1);
            ps.setDouble(5, score1);
            ps.setString(6, course2);
            ps.setDouble(7, score2);
            ps.setString(8, course3);
            ps.setDouble(9, score3);
            ps.setString(10, course4);
            ps.setDouble(11, score4);
            ps.setString(12, course5);
            ps.setDouble(13, score5);
            ps.setDouble(14, average);
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "New Score added successfully");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to add new Score");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getAllScore(JTable table, String searchvalue) {
        String sql = "SELECT * FROM score WHERE CONCAT(id) LIKE ? ORDER BY id DESC";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, "%" + searchvalue + "%");
            ResultSet rs = ps.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            Object[] row;
            while (rs.next()) {
                row = new Object[14];
                row[0] = rs.getInt(1);
                row[1] = rs.getInt(2);
                row[2] = rs.getInt(3);
                row[3] = rs.getString(4);
                row[4] = rs.getDouble(5);
                row[5] = rs.getString(6);
                row[6] = rs.getDouble(7);
                row[7] = rs.getString(8);
                row[8] = rs.getDouble(9);
                row[9] = rs.getString(10);
                row[10] = rs.getDouble(11);
                row[11] = rs.getString(12);
                row[12] = rs.getDouble(13);
                row[13] = rs.getDouble(14);
                model.addRow(row);

            }
        } catch (SQLException ex) {
            Logger.getLogger(Score.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    void update(int id, double score1, double score2, double score3, double score4, double score5, double average) {
        String sql = "update score set score1=?, score2=?, score3=?, "
                + "score4=?, score5=?, average=? where id=?";

        try {
            ps = con.prepareStatement(sql);
            ps.setDouble(1, score1);
            ps.setDouble(2, score2);
            ps.setDouble(3, score3);
            ps.setDouble(4, score4);
            ps.setDouble(5, score5);
            ps.setDouble(6, average);
            ps.setInt(7, id);
            if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Score data updated successfully");
            } else {
                JOptionPane.showMessageDialog(null, "Update failed");
            }

        } catch (SQLException ex) {
            Logger.getLogger(Student.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
