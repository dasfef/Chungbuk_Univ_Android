<?php
error_reporting(E_ALL);
ini_set("display_errors", 1);

$dsn = 'mysql:dbname=user; host=localhost; port=3307;';
$user = "root";
$passwd = "bigdatar";

$con = new PDO($dsn, $user, $passwd);
$con->exec("set names utf8");
// $con->exec('set names utf8 COLLATE utf8mb4_unicode_ci');
$con->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

$sql = "select * from place";

$result = $con->query($sql);
$data = array();
if($result){
    while($row = $result->fetch()){
        // echo "<pre>";
        // print_r($row);
        array_push($data,
        array('name'=>$row[0],
        'address'=>$row[1],
        'review'=>$row[2]));
    }

    // header('Content-Type: application/json; charset=utf8');
    $json = json_encode(array("webnautes"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
    echo $json;
} else{
    echo "SQL문 처리중 에러 발생 : " + PDO::errorInfo();

}

$con = null;
?>