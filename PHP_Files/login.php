<?php
error_reporting(E_ALL);
ini_set("display_errors", 1);

$dsn = 'mysql:host=localhost; dbname=user; port=3307;';
$user = 'root';
$password = 'bigdatar';

$con = new PDO($dsn, $user, $password);
$con->exec("set names utf8");
$con->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

$userID = $_POST["Id"];
$userPW = $_POST["Password"];

$stmt = $con->prepare("select * from userinfo where id = :id");
$stmt->bindparam(":id", $userID);
$stmt->execute();

$result = $con->query($stmt);
$data = array();

try {
    while($row = $result->fetch()){
        array_push($data,
        array('Id'=>$row[0],
        'Password'=>$row[1],
        'Name'=>$row[2],
        'Age'=>$row[3]));
    }
} catch(PDOException $e) {
    die("Database Execution Error : " . $e->getMessage());
}

$json = json_encode(array("webnautes"=>$data), JSON_PRETTY_PRINT + JSON_UNESCAPED_UNICODE);
echo $json;

$con = null;

?>