<?php
    //require("password.php");
$connect = mysqli_connect("mysql.hostinger.co.uk", "u748942186_ben", "opVG6yKSKIVvNGyW", "u748942186_app");


$username = $_POST["username"];
$group = $_POST["group"];

global $connect, $firstname,$lastname, $type, $username, $password;


$statement = mysqli_prepare($connect, "SELECT id FROM `user` WHERE username = ?"); 
mysqli_stmt_bind_param($statement, "s", $username);
mysqli_stmt_execute($statement);
mysqli_stmt_store_result($statement);
mysqli_stmt_bind_result($statement, $colId);
while($row = mysqli_stmt_fetch($statement)){
 $id = $colId;
}



$statement = mysqli_prepare($connect, "INSERT INTO `u748942186_app`.`member` (`gr_id`,`user_id`) VALUES ( ?, ?);");
mysqli_stmt_bind_param($statement, "ii", $group, $id);
mysqli_stmt_execute($statement);
mysqli_stmt_close($statement);  




echo json_encode($response);
?>
