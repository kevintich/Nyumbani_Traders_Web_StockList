<!--this document is supposed to display the top 5 prices currently-->
<html>
<head>
<title>Top 5 Prices</title>
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
   <h3 style=text-align:center><u>Top Five Prices </u></h3>
<table border = 1 style="margin: auto;" >
  <tr><td><b>No</b></td><td><b>Company Name</b></td><td><b>Trading Prices</b></td></tr>
<?php
      #php code for retriving top 5 prices from the database
      include 'dbconnection.php';
      $select="select Company_Name, Current_Trading_Price from TradingCompanies tc, CompanyTradingHistory cth where tc.Tiker_Name = cth.Tiker_Name order by Current_Trading_Price desc limit 5";
      $result = mysql_query($select, $conn) or die(mysql_error());
$topPrices = 1;
      while($row = mysql_fetch_array($result)){
             echo"<tr>";
             echo "<td>$topPrices</td>";
             $company = $row['Company_Name'];
             echo"<td>$company</td>";
	     $current  = $row['Current_Trading_Price'];
             echo "<td>$current</td>";
             echo "<tr>";
             $topPrices++;
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

