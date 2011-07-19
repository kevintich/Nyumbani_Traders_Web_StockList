<!--Comparisong of saved marginal database details-->
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
<h1 style=text-align:center><u>Marginal Values</u></h1>
<table border = 1 style="margin: auto;" >
  <tr><td><b>No</b></td><td><b>Highest Price</b></td><td><b>Lowest Price</b></td><td><b>Opening Price</b></td><td><b>Last Price</b></td><td><b>Gains/Losses</b></tr>
<?php
      include 'dbconnection.php';
      $select="select Highest_Value_This_Year, Lowest_Value_This_Year, Opening_Price_Today, Current_Trading_Price, Change_From_Previous_Price from TradingCompanies tc, CompanyTradingHistory cth where tc.Tiker_Name = cth.Tiker_Name";
      $result = mysql_query($select, $conn) or die(mysql_error());
      $compNumber = 1;
      while($row = mysql_fetch_array($result)){
             echo"<tr>";
             echo"<td>$compNumber</td>";
             $highestValue = $row['Highest_Value_This_Year'];
             echo"<td>$highestValue</td>";
             $lowestValue = $row['Lowest_Value_This_Year'];
             echo"<td>$lowestValue</td>";
             $openingPrice = $row['Opening_Price_Today'];
             echo"<td>$openingPrice</td>";
	     $currentPrice  = $row['Current_Trading_Price'];
             echo "<td>$currentPrice</td>";
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
