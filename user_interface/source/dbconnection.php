<?php 
  
 // Connects to the database(main database configuration file); 
 $conn = mysql_connect("mysql-database-server", "mysql username", "mysql password") or die(mysql_error()); 
 mysql_select_db("Nyumbani", $conn) or die(mysql_error()); 
 ?> 
