<?php
error_reporting(E_ALL);
ini_set("display_errors", 1);

$host = "localhost";
$user = "root";
$passwd = "123456789";
$dbName = "world";

$con = new mysqli($host, $user, $passwd, $dbName);
if(!$con) {die("Coudln't connect to DB".mysqli_error($con));}
// else{die("Coudln't connect to DB".mysqli_error($con));}

mysqli_set_charset($con, "utf8");

$sql = "select * from city limit 10";

$result = mysqli_query($con, $sql);
$data = array();
if($result) {
    while($row = mysqli_fetch_array($result)){
        array_push($data,
        array('ID'=>$row[0],
        'Name'=>$row[1],

        'Population'=>$row[4]
        ));
    }

    // echo "<pre>";
    // print_r($data);
    // echo "</pre>";
    header('Content-Type: application/json; charset=utf8');
    $json = json_encode(array("webnautes"=>$data), JSON_PRETTY_PRINT+JSON_UNESCAPED_UNICODE);
    echo $json;

} else {
    echo "SQL문 처리중 에러 발생 : ";
    echo mysqli_error($con);
}

mysqli_close($con);

?>