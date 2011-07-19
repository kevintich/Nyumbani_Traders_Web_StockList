<html>
<body>
   <style type="text/css">
   body {
 	font-family:verdana,arial,sans-serif;
	font-size:10pt;
	margin:30px;
	background-color:#ffffff;
	}
    table { margin: auto; } 
   </style>

   <h3 style=text-align:center><u>Company Details</u></h3>

    <table border = 1 style="margin: auto;">
      <tr><td><b>No</b></td><td><b>Ticker Name</b><td><b>Company Name</b><td><b>Stock Type</b><td><b>Share Quantity</b><td><b>Share Description</b></tr>
  <?php 

        // open the connection
        include 'dbconnection.php';
        // create the SQL statement
        $select  = "select * from TradingCompanies";
	$result = mysql_query($select, $conn) or die(mysql_error());
        $compNumber =1;
        while($row = mysql_fetch_array($result)){
             echo "<tr>";
             echo "<td>$compNumber</td>";
             $tika = $row['Tiker_Name'];
	     echo "<td>$tika</td>";
             $company  = $row['Company_Name'];
	     echo "<td>$company</td>";             
	     $type = $row['Stock_Type'];
	     echo "<td>$type</td>";
             $quantity = $row['Share_Quantity'];
	     echo "<td>$quantity</td>";
             $desc = $row['Share_Description'];
             echo "<td>$desc</td>";
             #echo "Details obtained $tika, $company, $type ,$quantity ,$desc";
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
      <FORM action  = "homepage.html" >
        <input type = submit value = "Back To Main Menu"></input>
      </FORM>
      </td> 
      </tr>
   </table>
</body>
</html>
