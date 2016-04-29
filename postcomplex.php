<?php
$dbhost = 'mysql.hostinger.co.uk';
$dbuser = 'u748942186_ben';
$dbpass = 'opVG6yKSKIVvNGyW';

$subject = $_POST["subject"];
$message = $_POST["message"];
$group = $_POST["group"];
$type = $_POST["type"];
$time = date("Y-m-d H:i:s");

$conn = @mysql_connect($dbhost, $dbuser, $dbpass);

if(! $conn ) {
	die('Could not connect: ' . mysql_error());
}

$sql = 'INSERT INTO `u748942186_app`.`complex_message` (`id`, `type`, `group`, `time`, `subject`,`message`) VALUES (NULL, '.$type.', '.$group.', \''.$time.'\', \''. $subject.'\', \''.$message.'\');';
echo $sql;
mysql_select_db('u748942186_app');
$retval = mysql_query( $sql, $conn );

if(! $retval ) {
	die('Could not get data: ' . mysql_error());
}


?>