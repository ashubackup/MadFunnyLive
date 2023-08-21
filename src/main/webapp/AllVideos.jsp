<%@page import="java.sql.ResultSet"%>
<%@page import="gen.*"%>
<%
String id = request.getParameter("id");
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
    <title>MadFunny | All Videos</title>
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
            <section class="player-videos">
                <div class="player_bg">
                    <img src="images/bg2.png" alt="bg" class="img-fluid" />
                    <div class="page_title text-right">
                    <%
                    ResultSet res = obj.getArtistName(Loader.Conn,id);
                    if(res.next()){
                    %>
                        <div class="container">
                            <h2><%=res.getString(1) %></h2>
                        </div>
                        <%
                    }
                        %>
                    </div>
                </div>
                <div class="player_videos_inner py-5">
                    <div class="container">
                        <div class="grid">
                        <%
                        ResultSet rs = obj.getAllVideos(Loader.Conn,id);
                        while(rs.next()){
                        %>
                            <div class="grid-item">
                                <div class="video-box">
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

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="js/popper.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/imagesloaded.pkgd.min.js"></script>
    <script src="js/masonry.pkgd.min.js"></script>
    <script type="text/javascript">
        /* <![CDATA[ */
        jQuery(".grid").imagesLoaded(function() {
            jQuery(".grid").masonry({
                itemSelector: ".grid-item"
            });
        });
        /* ]]> */
    </script>
    <!-- <script src="js/custom.js"></script> -->
</body>
</html>