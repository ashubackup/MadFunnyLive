<%@page import="java.sql.ResultSet"%>
<%@page import="gen.*"%>
<%
String id = request.getParameter("id");
String vid = request.getParameter("vid");
DataCollector obj = new DataCollector();

String ani = (String) session.getAttribute("ani");
String status = new DataCollector().getStatus(ani);
//   if (ani == null) {
//  	response.sendRedirect("landing");
//  } else if (status.equalsIgnoreCase("2") || status.equalsIgnoreCase("0")) {
//  	response.sendRedirect("Login?number=" + ani + "");
//  } 
 
 
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MadFunny | Player</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=DM+Sans:wght@400;500;700&family=Jost:wght@200;300;400;500;600;700;800;900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <main class="min-vh-100 d-flex flex-column pb-72">
        <jsp:include page="header.jsp"></jsp:include>
        <div class="wrapper d-flex flex-column flex-grow-1">
            <section class="player-videos pt-lg-5">
                <!-- <div class="player_bg">
                    <img src="images/bg.jpg" alt="bg" class="img-fluid" />
                    <div class="page_title text-right">
                        <div class="container">
                            <h2>Video Name</h2>
                        </div>
                    </div>
                </div> -->
                <%
                ResultSet res = obj.getVideoDetail(Loader.Conn,vid);
                while(res.next()){
                %>
                <div class="single-play py-3 py-lg-5">
                    <div class="container pt-lg-3">
                        <div class="row">
                            <div class="col-md-7">
                                <div class="video-play">
                                    <iframe width="100%" height="400" src="<%=res.getString(1) %>" title="YouTube video player" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
                                </div>
                            </div>
                            <div class="col-md-5">
                                <div class="video-content-right">
                                    <h4><a href="#"><%=res.getString(3) %></a></h4>
                                    <div class="meta mb-2">
                                        <span><i class="fa fa-user-alt"></i> <%=res.getString(2) %></span> | <span><i class="fa fa-calendar-alt"></i> 12 July, 2021</span>
                                    </div>
                                    <div class="description">
                                        <!-- <p>Video Description.</p> -->
                                        <!-- <p>Lorem ipsum, dolor sit amet consectetur adipisicing elit. Lorem ipsum dolor sit amet consectetur adipisicing elit. Saepe quidem illum odit eos veniam assumenda debitis magni, temporibus, atque animi sequi corporis fuga pariatur. Necessitatibus ad cum est fuga aliquam.</p> -->
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <%
                obj.LogPlayvideo(ani,id,vid,Loader.Conn,res.getString(1),res.getString(2));
                }
                %>
                <div class="related-videos bg-light py-5">
                    <div class="container">
                        <div class="cusheading">
                            <h3>Related Videos</h3>
                        </div>
                        <div class="row">
                        <%
                        ResultSet rs = obj.getRelatedVideos(Loader.Conn,id);
                        while(rs.next()){
                        %>
                            <div class="col-lg-3 my-3 col-sm-6">
                                <div class="video-box h-100">
                                    <div class="video-img">
                                        <a href="PlayVideo.jsp?vid=<%=rs.getString(2) %>&id=<%=id %>"><img src="<%=rs.getString(3) %>" alt="Video Name" class="img-fluid"></a>
                                        <div class="play_icon">
                                            <a href="PlayVideo.jsp?vid=<%=rs.getString(2) %>&id=<%=id %>"><i class="fa fa-play"></i></a>
                                        </div>
                                    </div>
                                    <div class="video_content">
                                        <h4><a href="#"><%=rs.getString(4) %></a></h4>
                                        <h5><i class="fa fa-calendar-alt"></i> 12 July, 2021</h5>
                                        <!-- <p>Description.</p> -->
                                    </div>
                                </div>
                            </div>
                            <%
                        }
                            %>
                        </div>
                    </div>
                </div>
            </section>
        </div>
        <jsp:include page="footer.jsp"></jsp:include>
    </main>

    <script src="js/popper.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
</body>
</html>