<!--this page displays the current bottom five movers-->
<html>
<head>
<title>Bottom 5 Movers</title>
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
   <h3 style=text-align:center><u>Bottom Five Movers </u></h3>
<table border = 1 style="margin: auto;" >
  <tr><td><b>No</b></td><td><b>Company Name</b></td><td><b>Shares Moved</b></td></tr>

<?php
      #code to select bottom five movers and save the records to the database
      include 'dbconnection.php';
      $select="select Company_Name,Shares_Moved from TradingCompanies tc, CompanyTradingHistory cth where tc.Tiker_Name = cth.Tiker_Name order by Shares_Moved limit 5";
      $result = mysql_query($select, $conn) or die(mysql_error());
      $bottomNumber = 1;
      while($row = mysql_fetch_array($result)){
             echo"<tr>";
             echo "<td>$bottomNumber</td>";
             $company = $row['Company_Name'];
             echo"<td>$company</td>";
	     $sharesMoved  = $row['Shares_Moved'];
             echo "<td>$sharesMoved</td>";
             $bottomNumber++;
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

