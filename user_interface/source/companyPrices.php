<html>
<head>
<title>HTML Frames Example - Content</title>
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
<h1 style=text-align:center>Company Prices</h1>
<form action="">
<select name="cars">
<option value="volvo">Select Company</option>
<?php
      session_start();
      $conn = $_SESSION['conn'];
      mysql_select_db("Nyumbani",$conn);
      $select="select Company_Name,Current_Trading_Price, Stock_Type, Trading_Value from TradingCompanies tc, CompanyTradingHistory cth where tc.Tiker_Name = cth.Tiker_Name order by Shares_Moved";
      $result = mysql_query($select, $conn) or die(mysql_error());
      while($row = mysql_fetch_array($result)){
             echo"";
             $company = $row['Company_Name'];
             echo"$company";
	         $tradingPrice  = $row['Current_Trading_Price'];
             echo "$tradingPrice";
             $stocktype = $row['Stock_Type'];
             echo"$stocktype";
             $tradingvalue = $row['Trading_Value'];
             echo"$tradingvalue";
             #echo "Company : $company<br>";
             #echo "Shares Moved : $sharesMoved<br>";
	  }
  ?>
</select>
</form>
<p>
<a href="content.html" target="content">Back to original page</a>
</p>
</body>
</html>
