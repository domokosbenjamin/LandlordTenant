<?php

$dbhost = 'mysql.hostinger.co.uk';
$dbuser = 'u748942186_ben';
$dbpass = 'opVG6yKSKIVvNGyW';

$conn = @mysql_connect($dbhost, $dbuser, $dbpass);

if(! $conn ) {
	die('Could not connect: ' . mysql_error());
}

$group = $_POST["group"];
$type = $_POST["type"];



$sql = 'SELECT `time`, `subject`, `message` FROM complex_message WHERE (`group`='.$group.' AND `type`='.$type.') order by time;';
//echo $sql;
mysql_select_db('u748942186_app');
$retval = mysql_query( $sql, $conn );

if(! $retval ) {
	die('Could not get data: ' . mysql_error());
}


while($r = mysql_fetch_assoc($retval,MYSQL_ASSOC)) {
	$rows[] = $r;
			 
}

$val['value']=$rows;
	  // echo "Fetched data successfully\n";

mysql_close($conn);

print json_encode($val)
?>