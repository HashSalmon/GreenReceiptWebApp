<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<html>
<head>
  <title>About Us</title>
  <%@include file="mainHead.jsp"%>
</head>
<body>
<%@include file="exteriorNavBar.jsp"%>
<div class="container">
  <div class="panel panel-default">
    <div class="panel-heading">
      <h3>The Team</h3>
    </div>
    <div class="panel-body">
      <div class="text-center addBottomMargin">
        <img src="/resources/teamInfo.JPG" height="400" width="500"><br>
        From left to right: Jordan Wanlass, Alex Gritton, Jinggong Zheng, Boya Song
      </div>
      <div class="panel panel-success">
        <div class="panel-heading">
          <h4>Jinggong Zheng</h4>
        </div>
        <div class="panel-body">
          <p>Skills: Java, C#, C++, Swift, PHP, Javascript, Python, etc.</p>
          <p>
            About me: Just like the blood in my body, programming is a vital aspect of my everyday life.
            I love programming and love solving the questions I encounter. It is enjoyable and challenging.
            I am sure programming will be my career throughout the course of my whole life.
          </p>
          Contribution to this project:
          <ul>
            <li> Studied, researched and improved on server architecture. Implemented few server APIs.</li>
            <li>Studied and researched on OCR for Android.</li>
            <li>Studied, researched and modified on an open source POS system.</li>
          </ul>
          Contact Info:
          Email: kingsleyzheng0716@gmail.com Phone: 626-512-0716
        </div>
      </div>

      <div class="panel panel-success">
        <div class="panel-heading">
          <h4>Alex Gritton</h4>
        </div>
        <div class="panel-body">
          <ul>
            <li>I enjoy playing the piano.</li>
            <li>I love Batman.</li>
            <li>I'm obsessed with Hans Zimmer.</li>
            <li>I never miss a Patriots game.</li>
            <li>I eat way to much candy.</li>
            <li>I'm an avid sunbather.</li>
            <li>I'm an adrenaline junkie.</li>
            <li>I hate the cold, pickles, mushrooms and the smell of cats.</li>
          </ul>
          Contribution to this project:
          <ul>
            <li>Designed and Implemented RESTful API's</li>
            <li>Designed and Implemented MySQL database</li>
            <li>Setup/Maintain RESTful API server and MySQL server</li>
            <li>Helped manage team tasking and organization</li>
          </ul>

          Contact Info:
          u0667623@utah.edu
        </div>
      </div>

      <div class="panel panel-success">
        <div class="panel-heading">
          <h4>Jordan Wanlass</h4>
        </div>
        <div class="panel-body">
          <p>Skills: Java, Python, Javascript, C, C#, PHP, etc.</p>
          <p>
            About me: I began programming back in 2011 and never looked back.  I have worked in industry for the past two years and have loved every minute of it.
            I enjoy playing video games and basically any form of physical fitness.  I am always looking for the next challenge and will always have fun along the way.
          </p>
          Contribution to this project:
          <ul>
            <li>Designed and implemented the web application</li>
            <li>Setup and maintain the web application Tomcat server</li>
            <li>Helped manage team tasking and organization</li>
          </ul>
          Contact Info:
          Email: u0760056@utah.edu
        </div>
      </div>

      <div class="panel panel-success">
        <div class="panel-heading">
          <h4>Boya Song</h4>
        </div>
        <div class="panel-body">
          My name is Boya Song.  I was born in China and moved to Salt Lake when I was 11.
          I'll finish my bachelor's degree in Computer Science this spring and start my master's program this fall.
          For our GreenReceipt project, I am responsible for designing and implementing the android app.
          If you have any questions or suggestions, please feel free to contact me.  You can reach me at 801-557-5812, or email me at boya.song0808@gmail.com
        </div>
      </div>
    </div>
  </div>
</div>
</body>
</html>