<?php
    error_reporting(E_ALL);
    ini_set("display_errors", 1);
    $db_host = "localhost";
    $db_user = "root";
    $pass = "bigdatar";
    $dbName = "testdatabase";

    $con = new mysqli($db_host, $db_user, $pass, $dbName, 3307);
    if($con) {echo "DB Connection Succeed"."<br>";}
    else{die("Coudln't connect to DB".mysqli_error($con));}

    $sql = "select * from testtable";
    $result = mysqli_query($con, $sql);
    while($row = mysqli_fetch_array($result)){
        echo $row['idx']." ".$row['Time']." ".$row['Temp']." ".$row['Humi']."<br>";
    }

    mysqli_close($con);
?>

<!-- <!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>PHP_MySQL 연동</title>
</head>
<body>
    <div>
        <table border="1" style="margin : 0 auto;">
            <tr style="background-color:#d8d8d8; text-align:center;">
                <td style="width:70px;">ID</td>
                <td style="width:70px;">Name</td>
                <td style="width:70px;">CountryCode</td>
                <td style="width:70px;">District</td>
                <td style="width:70px;">Population</td>
            </tr>
            
            while($row){
                $filtered = array(
                    'id' => htmlspecialchars($row['id']),
                    'name' => htmlspecialchars($row['name']),
                    'code' => htmlspecialchars($row['countrycode']),
                    'district' => htmlspecialchars($row['district']),
                    'population' => htmlspecialchars($row['population'])
                );

                <tr style="text-align:center;">
                    <td>=$filtered['name']</td>
                    <td>=$filtered['code']?></td>
                    <td>=$filtered['district']?></td>
                    <td>=$filtered['population']?></td>
                </tr>
            }
    </div>
</body>
</html> -->




    <!-- // if($con->connect_errno){
    //     echo "실패ㅠㅠ";
    // } else{
    //     echo "성공..!!";
    // } -->
