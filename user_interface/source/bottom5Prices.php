<!--This code is supposed to obtain and display the bottom 5 prices and companies associated with them-->
<html>
<head>
<title>Bottom 5 Prices</title>
<style type="text/css">
    body {
	font-family:verdana,arial,sans-serif;
	font-size:10pt;
	margin:30px;
	background-color:#ffffff;
	}
    table { margin: auto; } 
</style>
</head>
<body>
   <h3 style=text-align:center><u>Bottom Five Prices</u></h3>
<table border = 1 style="margin: auto;" >
  <tr><td><b>No</b></td><td><b>Company Name</b></td><td><b>Prices</b></td></tr>
<?php
      include 'dbconnection.php';
      $select="select Company_Name, Current_Trading_Price from TradingCompanies tc, CompanyTradingHistory cth where tc.Tiker_Name = cth.Tiker_Name order by Current_Trading_Price limit 5";
      $result = mysql_query($select, $conn) or die(mysql_error());
      $topNumber = 1;
      while($row = mysql_fetch_array($result)){
             echo"<tr>";
             echo "<td>$topNumber</td>";
             $company = $row['Company_Name'];
             echo"<td>$company</td>";
	     $current  = $row['Current_Trading_Price'];
             echo "<td>$current</td>";
             echo "<tr>";
             $topNumber++;
	  }
  ?>
</table>
<br>
<br>
<table>
      <tr>
      <td></td><td></td>
      <td>
      <FORM action  = "links.html" >
        <input type = submit value = "Back To link shortcuts"></input>
      </FORM>
      </td> 
      </tr>
   </table>
</body>
</html>

