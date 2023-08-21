package gen;

import static gen.Configurator.getInstance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DataCollector {
	String response = "{\"status\":\"0\",\"message\":\"something went wrong!!\",\"data\":{}}";
	private static Configurator configurator = getInstance();
	
	
	public ResultSet getArtist(Connection conn) {
		ResultSet res = null;
		try {
			String query = "select id,artist,artist_img_url,description from tbl_artist where status=0";
			System.out.println(query);
			PreparedStatement ps = conn.prepareStatement(query);
			res = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;

	}
	
	public ResultSet getAllVideos(Connection conn, String id) {
		ResultSet res = null;
		try {
			String query = "select vurl,videoid,imgurl,video_name from tbl_videos where artist='"+id+"' and status=0";
			System.out.println(query);
			PreparedStatement ps = conn.prepareStatement(query);
			res = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;

	}
	
	public ResultSet getArtistName(Connection conn, String id) {
		ResultSet res = null;
		try {
			String query = "select name from tbl_videos where artist='"+id+"' and status=0";
			System.out.println(query);
			PreparedStatement ps = conn.prepareStatement(query);
			res = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;

	}
	
	public ResultSet getVideoDetail(Connection conn, String vid) {
		ResultSet res = null;
		try {
			String query = "select vurl,name,video_name from tbl_videos where videoid='"+vid+"' and status=0";
			System.out.println(query);
			PreparedStatement ps = conn.prepareStatement(query);
			res = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;

	}
	
	public ResultSet getRelatedVideos(Connection conn, String id) {
		ResultSet res = null;
		try {
			String query = "select vurl,videoid,imgurl,video_name from tbl_videos where artist='"+id+"' and status=0 order by RAND() limit 4";
			System.out.println(query);
			PreparedStatement ps = conn.prepareStatement(query);
			res = ps.executeQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;

	}
	
	public String getStatus(String ani) {
		String State = "";
		try {
			
			  String countyCode = "27";
			  int len = countyCode.length();
			  ani=ani.trim(); 
			  if(ani.startsWith("+"))
				  ani=ani.substring(1);
			  if (ani.startsWith("0"))
				  ani = ani.substring(1);
			  if(ani.startsWith(countyCode))
				  ani = ani.substring(len);
			 

			final Statement stmt = Loader.Conn.createStatement();
			final String chkqry = "select * from tbl_subscription where ani='" + ani + "' and service_type='Mc Funny' ";
			System.out.println(chkqry);
			final ResultSet rs = stmt.executeQuery(chkqry);
			if (rs.next()) {
				State = this.getUserState(ani, "Mc Funny");
			} else {
				State = "2";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return State;
	}
	
	public String getUserState(String ani, final String service) {
		String State = "0";
		try {
			String countyCode = "27";
			  int len = countyCode.length();
			  ani=ani.trim(); 
			  if(ani.startsWith("+"))
				  ani=ani.substring(1);
			  if (ani.startsWith("0"))
				  ani = ani.substring(1);
			  if(ani.startsWith(countyCode))
				  ani = ani.substring(len);
			
			Statement stmt = null;
			Statement select = null;
			stmt = Loader.Conn.createStatement();
			select = Loader.Conn.createStatement();
			int cnt = 0;
			final String subQry = "select count(1) as cnt from tbl_subscription where ani='" + ani
					+ "' and service_type='" + service + "' " + "and date(next_billed_date)>= Date(subdate(now(),45)) ";
			System.out.println("subQry::::" + subQry);
			final ResultSet rssub = stmt.executeQuery(subQry);
			if (rssub.next()) {
				cnt = rssub.getInt(1);
				System.out.println("cnt~~" + cnt);
			}
			if (cnt == 0) {
				int count = 0;
				String Qry = "select count(1) from tbl_subscription where ani = '"+ani+"' and service_type='"+service+"'"
						+ " and (DATE(sub_date_time)=DATE(SUBDATE(NOW(),1)) OR DATE(sub_date_time)=DATE(SUBDATE(NOW(),0))) ";
				System.out.println(Qry);
				ResultSet rsselect = select.executeQuery(Qry);
				if(rsselect.next()) {
					count = rsselect.getInt(1);
				}
				if(count == 1) {
					State = "1";
				}
				else {
					State = "0";
				}
				
			} 
			else {
				State = "1";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return State;
	}

	public String checkAni(String ani) {
		String countyCode = "27";
		if (ani.startsWith("0"))
			ani = ani.substring(1);
		if (ani.startsWith("+"))
			ani = ani.substring(1);
		int len = countyCode.length();
		if (ani.substring(0, len).equals(countyCode))
			ani = ani.substring(len);
		if (ani.contains(" "))
			ani = ani.replace(" ", "");
		return ani;
	}
	
	public void LogPlayvideo(String ani, String id, String vid, Connection conn, String vurl, String name) {
		
		try {
			Statement stmt = conn.createStatement();
			String datetime = getCurrentDateTime();
			String insertQry = "insert into tbl_played_video(ani,videoid,artist,vurl,name,datetime)"
					+ " values ('"+ani+"','"+vid+"','"+id+"','"+vurl+"','"+name+"','"+datetime+"')";
			System.out.println(insertQry);
			stmt.executeUpdate(insertQry);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String getCurrentDateTime() {
		String dt = "";
		try {
			Date localTime = new Date();
			   
		     DateFormat converter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		   
		     converter.setTimeZone(TimeZone.getTimeZone("GMT+2"));
		   
		     System.out.println("local time : " + localTime);;
		     System.out.println("time in Tanzania : " + converter.format(localTime));
		     
		     dt = converter.format(localTime);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return dt;
	}
	
public void MobileLogs(String ani, String os,  Connection conn) {
		
		try {
			Statement stmt = conn.createStatement();
			
			if(os.contains("iPhone")) {
				os = "iPhone";
			}
			else if (os.contains("Android")) {
				os = "Android";
			}
			else {
				os = "Windows";
			}
			
			String datetime = getCurrentDateTime();
			String insertQry = "insert into tbl_handsetlogs(ani,os,datetime)"
					+ " values ('"+ani+"','"+os+"','"+datetime+"')";
			System.out.println(insertQry);
			stmt.executeUpdate(insertQry);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	
 public void UserJourney(String ani, String page,  Connection conn) {
	
	try {
		Statement stmt = conn.createStatement();
		
		String datetime = getCurrentDateTime();
		String insertQry = "insert into tbl_user_Journey(ani,page,datetime)"
				+ " values ('"+ani+"','"+page+"','"+datetime+"')";
		System.out.println(insertQry);
		stmt.executeUpdate(insertQry);
		
	} catch (Exception e) {
		e.printStackTrace();
	}
}

}
