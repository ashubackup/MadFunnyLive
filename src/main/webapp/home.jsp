<%@page import="javax.xml.crypto.Data"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="gen.*"%>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
DataCollector obj = new DataCollector();

Enumeration headerNames = request.getHeaderNames();
String paramName = (String)headerNames.nextElement();
  String paramValue = request.getHeader(paramName);
  String Data = paramName+"#"+paramValue;
  System.out.println("Header data:::"+Data);
  
  String os = request.getHeader("user-agent");
  System.out.println("os::"+os);
  
  String ani = (String) session.getAttribute("ani");
  String status = new DataCollector().getStatus(ani);
//     if (ani == null) {
//    	response.sendRedirect("landing");
//    } else if (status.equalsIgnoreCase("2") || status.equalsIgnoreCase("0")) {
//    	response.sendRedirect("Login?number=" + ani + "");
//    } 
    
    obj.MobileLogs(ani,os,Loader.Conn);
    obj.UserJourney(ani,"home",Loader.Conn);
%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MadFunny | Home</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=DM+Sans:wght@400;500;700&family=Jost:wght@200;300;400;500;600;700;800;900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">
    
</head>
<body>
    <main class="min-vh-100 d-flex flex-column">
        <jsp:include page="header.jsp"></jsp:include>
        <div class="wrapper d-flex flex-column flex-grow-1">
            <section class="py-5 tinder-sec">
                <div class="container pt-lg-5">
                    <div class="row justify-content-center">
                        <div class="col-md-8">
                            <div class="cus_card p-md-4">
                                <div class="tinder" id="tin">
                                    <div class="tinder--status">
                                        <i class="fa fa-times"></i>
                                        <i class="fa fa-heart"></i>
                                    </div>
                                    
                                    <div class="tinder--cards">
                                    <div class="tinder--card">
                                       <span class="card_inner">
                                           <span class="imgbox">
                                                <img src="images/Picture1.png" alt="" class="img-fluid" />
                                           </span>
                                           <span class="box_content">
                                                    <h3>How to use the site:</h3>
                                                    <!-- <p><i class="fa fa-map-marker-alt"></i> Punjab, India</p> -->
                                                    <%-- <span class="category"><i class="fa fa-list-alt"></i> Comedian, #<%=rs.getString("id") %></span> --%>
                                                    <span class="desc">
                                                        <p>Swipe right if you like what you see.</p>
                                                        <p>Swipe left if you want to see something else.</p>
                                                    </span>
                                                </span>
                                       </span>
                                    </div>
                                
                                    
                                    <%
                                    ResultSet rs = obj.getArtist(Loader.Conn);
                                    int count=1;
                                    while(rs.next()){
                                    %>
                                    	<span id="swap<%=count %>" style="display:none;"><%=rs.getString("id") %></span>
                                        <div class="tinder--card" >
                                        	
                                            <span class="card_inner">
                                                <span class="imgbox">
                                                    <img src="<%=rs.getString(3) %>" alt="" class="img-fluid" />
                                                </span>
                                                <span class="box_content">
                                                    <h3><%=rs.getString(2) %></h3>
                                                    <!-- <p><i class="fa fa-map-marker-alt"></i> Punjab, India</p> -->
                                                    <span class="category"><i class="fa fa-list-alt"></i> Comedian, #<%=rs.getString("id") %></span>
                                                    <span class="desc">
                                                        <p><%=rs.getString(4) %></p>
                                                    </span>
                                                </span>
                                            </span>
                                        </div>
                                        <%
                                        count++;
                                    }
                                        %>
                                        
                                        <div class="tinder--card">
                                       <span class="card_inner">
                                           <span class="imgbox">
                                                <img src="images/Picture1.png" alt="" class="img-fluid" />
                                           </span>
                                           <span class="box_content">
                                                    <h3>Come back for more content soon.</h3>
                                                    <!-- <p><i class="fa fa-map-marker-alt"></i> Punjab, India</p> -->
                                                    <%-- <span class="category"><i class="fa fa-list-alt"></i> Comedian, #<%=rs.getString("id") %></span> --%>
                                                    <span class="desc">
                                                        <!-- <p>Swipe right if you like what you see.</p> -->
                                                        
                                                    </span>
                                                </span>
                                       </span>
                                    </div>
                                        
                                    </div>
                                
                                    <!-- <div class="tinder--buttons">
                                        <button id="nope"><i class="fa fa-times"></i></button>
                                        <button id="love"><i class="fa fa-heart"></i></button>
                                    </div> -->
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
        
    </main>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="js/popper.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/hammer.js"></script>
    <script src="js/custom.js"></script>
</body>
</html>