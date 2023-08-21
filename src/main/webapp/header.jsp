<%@page import="java.sql.ResultSet"%>
<%@page import="gen.*"%>
<%
DataCollector obj = new DataCollector();
%>
<header>
            <nav class="navbar py-3 navbar-expand-lg navbar-light fixed-top">
                <div class="container">
                    <div class="mobile_icons d-lg-none">
                        <a href="home.jsp"><i class="fa fa-home"></i></a>
                    </div>
                    <a class="navbar-brand fw-bolder" href="home.jsp">MadFunny</a>
                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="fa fa-bars"></span>
                    </button>
                    <div class="collapse navbar-collapse" id="navbarSupportedContent">
                        <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
                            <li class="nav-item">
                                <a class="nav-link active" aria-current="page" href="home.jsp">Home</a>
                            </li>
                            <!-- <li class="nav-item">
                                <a class="nav-link" href="#">Link</a>
                            </li> -->
                            <%
                                    ResultSet rs = obj.getArtist(Loader.Conn);
                                    while(rs.next()){
                                    %>
                            <li class="nav-item">
                                <a class="nav-link" href="AllVideos.jsp?id=<%=rs.getString(1) %>"><%=rs.getString(2) %></a>
                            </li>
                             <%
                                    }
                                %>
                            <!-- <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                    Dropdown
                                </a>
                                <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                    <li><a class="dropdown-item" href="#">Action</a></li>
                                    <li><a class="dropdown-item" href="#">Another action</a></li>
                                    <li><hr class="dropdown-divider"></li>
                                    <li><a class="dropdown-item" href="#">Something else here</a></li>
                                </ul>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Disabled</a>
                            </li> -->
                        </ul>
                    </div>
                </div>
            </nav>
        </header>