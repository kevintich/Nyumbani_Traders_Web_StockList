<!--This code is supposed to display the top 5 movers currently-->
<html>
<head>
<title>Top 5 Movers</title>
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
   <h3 style=text-align:center>Top Five Movers </h3>
<table border = 1 style="margin: auto;" >
  <tr><td>No</td><td><b>Company Name</b></td><td><b>Price</b></td><td><b>Change</b></td><td><b>Shares Moved</b></td></tr>
<?php
      #php code meant to select top 5 movers currnetly from the database. Displays data in a HTML table
      include 'dbconnection.php';
      $select="select Company_Name,Current_Trading_Price,Change_From_Previous_Price,Shares_Moved from TradingCompanies tc, CompanyTradingHistory cth where tc.Tiker_Name = cth.Tiker_Name order by Shares_Moved desc limit 5";
      $result = mysql_query($select, $conn) or die(mysql_error());
       $numb = 1;      
	while($row = mysql_fetch_array($result)){
             echo"<tr>";
             echo"<td>$numb</td>"
             $company = $row['Company_Name'];
             echo"<td>$company</td>";
             $price = $row['Current_Trading_Price'];
             echo "<td>$price</td>";
             $change = $row['Change_From_Previous_Price'];
             echo "<td>$change</td>";
	     $sharesMoved  = $row['Shares_Moved'];
             echo "<td>$sharesMoved</td>";
             $numb++;
	  }
  ?>
</table>
<br>
<br>
<table>
      <tr>
      <td></td><td></td>
      <td>
      <FORM action  = "homepage.html" >
        <input type = submit value = "Back To Main Menu"></input>
      </FORM>
      </td> 
      </tr>
   </table>
</body>
</html>

