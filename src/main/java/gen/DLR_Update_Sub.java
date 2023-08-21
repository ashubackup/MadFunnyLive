package gen;

import static gen.Configurator.getInstance;

import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import gen.Configurator;
import gen.Conversion;

public class DLR_Update_Sub {
	
	private static final String colSep="#";
	private static Configurator configurator = getInstance();
	Statement stmt = null;
	Statement stmt1 = null;
	Statement stmt2 = null;
	Statement stmt3 = null;
	Statement stmtUpdate = null;
	Connection conn = null;

	public static void main(String[] args) {
		DLR_Update_Sub DLR_obj = new DLR_Update_Sub();
		DLR_obj.connect_db();
		try {
			while(true){
				DLR_obj.startSub();
				System.out.println("Waiting for new DLR .... Sleep 10000");
				Thread.sleep(1000*10);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}


	private void connect_db() {
		try {
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			//conn = DriverManager.getConnection("jdbc:mysql://5.189.169.12:3306/httmtn?autoReconnect=true", "root","gloadmin123");
			conn = DriverManager.getConnection("jdbc:mysql://91.205.172.123:3306/McFunny?autoReconnect=true", "root","gloadmin123");
			//conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bobble?autoReconnect=true", "root","gloadmin123");
			stmt = conn.createStatement();
			stmt1 = conn.createStatement();
			stmt2 = conn.createStatement();
			stmt3 = conn.createStatement();
			stmtUpdate = conn.createStatement();
			System.out.println("DB Connected");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	private void startSub() {
		try {
			String ani="",servicename="",m_act="",action="",next_billed_date="",last_billed_date="",default_amount="",sub_date_time="";
			int amount = 0,campaign_id=0;
			String service_type = "";
			
			String getDLR = configurator.getProperty("getDLR");
			getDLR = getDLR.replace("<type>", "sub");
			System.out.println(getDLR);
			ResultSet rsd = stmt.executeQuery(getDLR);
			if(rsd.next()) {
				rsd.beforeFirst();
				while(rsd.next()) {
					ani = rsd.getString(1);
					servicename = rsd.getString(2);
					m_act = rsd.getString(3);
					action = rsd.getString(4);
					amount = rsd.getInt(5);
					campaign_id = rsd.getInt(6);
					next_billed_date = rsd.getString(7);
					last_billed_date = rsd.getString(8);
					sub_date_time = rsd.getString(9);
				
				amount = amount/100;
				default_amount = Integer.toString(amount);
				
				
				/*
				 * if(servicename.equalsIgnoreCase("Happy Tube Games")) { service_type =
				 * "Games"; } else if(servicename.equalsIgnoreCase("Happy Tube TV")){
				 * service_type = "SVOD"; } else if(servicename.equalsIgnoreCase("Cash Rider")){
				 * service_type = "cashrider"; }
				 */
				
				/*
				 * String getsvc = configurator.getProperty("getsvc"); getsvc =
				 * getsvc.replace("<desc>", servicename); System.out.println(getsvc); ResultSet
				 * rssvc = stmt3.executeQuery(getsvc); if(rssvc.next()) { service_type =
				 * rssvc.getString(1); }
				 */
				
				service_type = servicename;
				
				addLoggingDLR(ani+"#"+servicename+"#"+m_act+"#"+action+"#"+amount+"#"+campaign_id+"#"+next_billed_date+"#"+last_billed_date);
				
				String updDLR = configurator.getProperty("Update_DLR");
				updDLR = updDLR.replace("<ani>", ani).replace("<servicename>", servicename).replace("<type>", "sub");
				System.out.println(updDLR);
				stmtUpdate.executeUpdate(updDLR);
				
				if(action.equalsIgnoreCase("ACTIVE")) {
					
					int count = 0;
					String updtusr = "";
					String chkUsr = configurator.getProperty("check_User");
					chkUsr = chkUsr.replace("<ani>", ani).replace("<service_type>", service_type);
					System.out.println(chkUsr);
					ResultSet rsc = stmt1.executeQuery(chkUsr);
					if(rsc.next()) {
						count = rsc.getInt(1);
					}
					if(count!=0) {
						updtusr = configurator.getProperty("Sub_Update");
						updtusr = updtusr.replace("<ani>", ani).replace("<m_act>", m_act).replace("<next_billed_date>", next_billed_date)
			            		.replace("<last_billed_date>", last_billed_date).replace("<amount>", default_amount)
			            		.replace("<servicename>", service_type);
						System.out.println(updtusr);
						stmtUpdate.executeUpdate(updtusr);
					}
					else {
						updtusr = configurator.getProperty("Sub_Insert");
						updtusr = updtusr.replace("<ani>", ani).replace("<m_act>", m_act).replace("<next_billed_date>", next_billed_date)
			            		.replace("<last_billed_date>", last_billed_date).replace("<amount>", default_amount)
			            		.replace("<servicename>", service_type).replace("<sub_date_time>", sub_date_time);
						System.out.println(updtusr);
						stmtUpdate.executeUpdate(updtusr);
						
					}
					
					/*
					 * String billInst = configurator.getProperty("Billing_Insert"); billInst =
					 * billInst.replace("<ani>", ani).replace("<amount>",
					 * default_amount).replace("<type_event>", "SUB") .replace("<m_act>",
					 * m_act).replace("<servicename>", service_type); System.out.println(billInst);
					 * stmtUpdate.executeUpdate(billInst);
					 */
					
					
					/*
					 * if(campaign_id!=0) { Conversion obj = new Conversion();
					 * 
					 * String provider = getProvider(campaign_id);
					 * System.out.println("provider:"+provider);
					 * 
					 * if(!provider.equalsIgnoreCase("social")) {
					 * obj.updateOptickConv(Integer.toString(campaign_id), ani, service_type,
					 * action,conn,servicename); } else {
					 * obj.updateSocial(Integer.toString(campaign_id), ani, service_type,
					 * action,conn,servicename); } }
					 */
				}
				else if (action.equalsIgnoreCase("CANCELLED") || action.equalsIgnoreCase("Expired") || action.equalsIgnoreCase("GRACE")) {
					String unsub = configurator.getProperty("Unsub_user");
					unsub = unsub.replace("<ani>", ani).replace("<m_deact>", m_act).replace("<action>", action)
							.replace("<servicename>", service_type);
					System.out.println(unsub);
					stmtUpdate.executeUpdate(unsub);
					
					String instUnsub = configurator.getProperty("Insert_Unsub");
					instUnsub = instUnsub.replace("<ani>", ani).replace("<servicename>", service_type);
					System.out.println(instUnsub);
					stmtUpdate.executeUpdate(instUnsub);
							
					String delSub = configurator.getProperty("Delete_Sub");
					delSub = delSub.replace("<ani>", ani).replace("<servicename>", service_type);
					System.out.println(delSub);
					stmtUpdate.executeUpdate(delSub);
				}
			  }
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	private String getProvider(int campaign_id) {
		String provider = "";
		try {
			String camp_id = Integer.toString(campaign_id) ;
			System.out.println("camp_id::"+camp_id);
			String qry = configurator.getProperty("getProvider");
			qry = qry.replace("<camp_id>", camp_id);
			System.out.println(qry);
			ResultSet rsc = stmt2.executeQuery(qry);
			if(rsc.next()) {
				provider = rsc.getString(1);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return provider;
	}


	private void addLoggingDLR(String data) {
		try {
			SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");
			Date now = new Date();
			String strDate = sdfDate.format(now);
			String filename = "/home/app/Moneta_Mcfunny/REPORT/SubReport"+strDate+".log";
			
			FileWriter fw = new FileWriter(filename, true);
			fw.write(data+"\n");
			fw.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
