<?php
    //require("password.php");
$con = mysqli_connect("mysql.hostinger.co.uk", "u748942186_ben", "opVG6yKSKIVvNGyW", "u748942186_app");

$username = $_POST["username"];
$password = $_POST["password"];


$statement = mysqli_prepare($con, "SELECT * FROM user LEFT JOIN member ON user.id = member.user_id WHERE user.username =  ?");
mysqli_stmt_bind_param($statement, "s", $username);
mysqli_stmt_execute($statement);
mysqli_stmt_store_result($statement);
mysqli_stmt_bind_result($statement, $colId, $colFirstname,$colLastname,$colType, $colUsername, $colPassword, $grId, $uId);

$response = array();
$response["success"] = false;  

while($row = mysqli_stmt_fetch($statement)){
    if (password_verify($password, $colPassword)) {
        $response["success"] = true;  
        $response["name"] = $colFirstname." ".$colLastname;
        $response["type"] = $colType;
        $response["id"] = $colId;
        $response["gr_id"] = $grId;
        $response["username"]=$colUsername;

    }
}
$response["group"] = false;
if($colId == $uId)
    $response["group"]=true;


//echo $response["group"];
echo json_encode($response);
?>				