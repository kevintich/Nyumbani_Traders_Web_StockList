<!--This page displays current stock values in tabular format -->
<html>
<head>
<title>Companies Stock Values - Content</title>
<style type="text/css">
body {
	font-family:verdana,arial,sans-serif;
	font-size:10pt;
	margin:30px;
	background-color:#ffffff;
	}
</style>
</head>
<body>

<h1 style=text-align:center><u>Company Current Trading Details</u></h1>
<table border = 1 style="margin: auto;" >
  <tr><td><b>No</b></td><td><b>Company Name</b></td><td><b>Stock type</b></td><td><b>Trading Value</b></td><td><b>Current Trading Price</b></td><td><b>Change from Previous Price</b></tr>
<?php
      include 'dbconnection.php';
      $select="select Company_Name, Stock_Type, Company_Trading_Value, Current_Trading_Price, Change_From_Previous_Price from TradingCompanies tc, CompanyTradingHistory cth where tc.Tiker_Name = cth.Tiker_Name order by Company_Trading_Value";
      $result = mysql_query($select, $conn) or die(mysql_error());
      $compNumber = 1;
      while($row = mysql_fetch_array($result)){
             echo"<tr>";
             echo"<td>$compNumber</td>";
             $company = $row['Company_Name'];
             echo"<td>$company</td>";
             $stocktype = $row['Stock_Type'];
             echo"<td>$stocktype</td>";
             $tradingvalue = $row['Company_Trading_Value'];
             echo"<td>$tradingvalue</td>";
	     $tradingPrice  = $row['Current_Trading_Price'];
             echo "<td>$tradingPrice</td>";
             $previousPrice  = $row['Change_From_Previous_Price'];
             echo "<td>$previousPrice</td>";
             #echo "Company : $company<br>";
             #echo "Shares Moved : $sharesMoved<br>";
             echo "</tr>";
             $compNumber++;
	  }
  ?>
</table>
<br>
<br>
<table cellspacing = 30>
      <tr>
      <td>
<FORM action  = "links.html" >
        <input type = submit value = "Back To link"></input>
      </FORM>
      </td> 
      </tr>
   </table>
</body>
</html>
