package callback;

import static gen.Configurator.getInstance;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import gen.Configurator;
import gen.Loader;

/**
 * Servlet implementation class SubCallback
 */
@WebServlet("/SubCallback")
public class SubCallback extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String CallbackPath = "/home/SDPLOGS/mcfunny/";
	private static Configurator configurator = getInstance();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubCallback() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			response.setContentType("text/xml");
			PrintWriter out = response.getWriter();
			
			StringBuffer jb = new StringBuffer();
			String line = null;
			try {
				  	BufferedReader reader = request.getReader();
				  	while ((line = reader.readLine()) != null)
				  		jb.append(line);
			  	} catch (Exception e) {
			  			e.printStackTrace();
			  }
			CreateFile(jb);
			System.out.println("Sub Callback Data::::"+jb);
			
			  final JSONObject jsonObj = new JSONObject(jb.toString());
			  String ani = (String)jsonObj.get("user_msisdn");
			  final String countyCode = "27";
	            final int len = countyCode.length();
	            if (ani.substring(0, len).equals(countyCode)) {
	                ani = ani.substring(len);
	            }
	            String last_billed_at = (String)jsonObj.get("last_billed_at");
	            String next_billing_at = (String)jsonObj.get("next_billing_at");
	            final String channel_name = (String)jsonObj.get("channel_name");
	            final String status_name = (String)jsonObj.get("status_name");
	            final String svc_name = (String)jsonObj.get("svc_name");
	            int amount = (int)jsonObj.get("billing_rate");
	            int campaign_id = (int) jsonObj.get("campaign_id");
	            String amt = Integer.toString(amount);
	            String camp_id = Integer.toString(campaign_id);
	            String subscription_at = (String)jsonObj.get("subscription_started_at");
	            
	            next_billing_at = this.getDateFormat(next_billing_at);
	            last_billed_at = this.getDateFormat(last_billed_at);
	            subscription_at = this.getDateFormat(subscription_at);
	            
	            Statement stmtup = Loader.Conn.createStatement();
	            String instQry = configurator.getProperty("insertDLR");
	            instQry = instQry.replace("<ani>", ani).replace("<last_billed_date>", last_billed_at).replace("<next_billed_date>", next_billing_at)
	            		.replace("<channel_name>", channel_name).replace("<status_name>", status_name).replace("<svc_name>", svc_name).replace("<amount>", amt)
	            		.replace("<campaign_id>", camp_id).replace("<type>", "sub").replace("<sub_date_time>", subscription_at)
	            		.replace("<result_name>", "");
	            System.out.println(instQry);
		    	stmtup.executeUpdate(instQry);
			
			
			out.print("Callback received successfully");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String getDateFormat(String date) {
		final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String dateInString = date;
        final String[] arr = dateInString.split("\\+");
        final String[] newarr = arr[0].split("T");
        System.out.println(newarr[0]);
        final String data = String.valueOf(newarr[0]) + " " + newarr[1];
        System.out.println(data);
        return data;
		
	}

	private void CreateFile(StringBuffer Data) {
		Long FileName = get_Time();
		String Rand = get_rand();
		try {
			File hFile=new File(CallbackPath+"SUB"+FileName+Rand+".txt");
			FileWriter hFileWriter = new FileWriter(hFile, false);
			hFileWriter.write(Data.toString());
			hFileWriter.close();
			//System.out.println(Data.toString());
			System.out.println("Text File Created :: "+CallbackPath+"SUB"+FileName+Rand+".txt");
						
						
			File hFilelck=new File(CallbackPath+"SUB"+FileName+Rand+".lck");
			FileWriter hFilelck1 = new FileWriter(hFilelck, false);
			hFilelck1.write("0");
			hFilelck1.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
private static long get_Time() {
		
		long Time1;
		Calendar lCDateTime = Calendar.getInstance();
		Time1=lCDateTime.getTimeInMillis();
		//System.out.println("Time in milliseconds :"+Time1);
	    return Time1;
	}
	
	private static String get_rand(){
		
		
		Random r = new Random();
		int value=r.nextInt(10)+9;
		String rand=String.valueOf(value);
		
		
		return rand;
	}

}
