<?php
    //require("password.php");
$connect = mysqli_connect("mysql.hostinger.co.uk", "u748942186_ben", "opVG6yKSKIVvNGyW", "u748942186_app");

$firstname = $_POST["firstname"];
$lastname = $_POST["lastname"];

$type = $_POST["type"];
$username = $_POST["username"];
$password = $_POST["password"];
function registerUser() {
    global $connect, $firstname,$lastname, $type, $username, $password;
    $passwordHash = password_hash($password, PASSWORD_DEFAULT);
    $statement = mysqli_prepare($connect, "INSERT INTO user (firstname,lastname, type, username, password) VALUES (?, ?, ?, ?,?)");
    mysqli_stmt_bind_param($statement, "ssiss", $firstname, $lastname, $type, $username, $passwordHash);
    mysqli_stmt_execute($statement);
    mysqli_stmt_close($statement);  
    if($type==0){

        $statement = mysqli_prepare($connect, "INSERT INTO `u748942186_app`.`group` (`id`, `address`, `name`) VALUES (NULL, ?, ?);");
        mysqli_stmt_bind_param($statement, "ss", $username, $username);
        mysqli_stmt_execute($statement);
        mysqli_stmt_close($statement);  

        $statement = mysqli_prepare($connect, "SELECT id FROM `group` WHERE name = ?"); 
        mysqli_stmt_bind_param($statement, "s", $username);
        mysqli_stmt_execute($statement);
        mysqli_stmt_store_result($statement);
        mysqli_stmt_bind_result($statement, $colId);
        while($row = mysqli_stmt_fetch($statement)){
         $id = $colId;
     }

     $statement = mysqli_prepare($connect, "SELECT id FROM `user` WHERE username = ?"); 
     mysqli_stmt_bind_param($statement, "s", $username);
     mysqli_stmt_execute($statement);
     mysqli_stmt_store_result($statement);
     mysqli_stmt_bind_result($statement, $colId);
     while($row = mysqli_stmt_fetch($statement)){
         $uId = $colId;
     }

     $statement = mysqli_prepare($connect, "INSERT INTO `u748942186_app`.`member` (`gr_id`,`user_id`) VALUES ( ?, ?);");
     mysqli_stmt_bind_param($statement, "ii", $id, $uId);
     mysqli_stmt_execute($statement);
     mysqli_stmt_close($statement);  
 }

}   

function usernameAvailable() {
    global $connect, $username;
    $statement = mysqli_prepare($connect, "SELECT * FROM user WHERE username = ?"); 
    mysqli_stmt_bind_param($statement, "s", $username);
    mysqli_stmt_execute($statement);
    mysqli_stmt_store_result($statement);
    $count = mysqli_stmt_num_rows($statement);
    mysqli_stmt_close($statement); 
    if ($count < 1){
        return true; 
    }else {
        return false; 
    }
}
$response = array();
$response["success"] = false;  
if (usernameAvailable()){
    registerUser();
    $response["success"] = true;  
}

mysqli_commit($connect);
mysqli_close($connect);
echo json_encode($response);
?>
Status 	