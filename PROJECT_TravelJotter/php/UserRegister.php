<?php

$dsn = 'mysql:host=localhost; dbname=user; port=3307;';
$user = 'root';
$password = 'bigdatar';

$con = new PDO($dsn, $user, $password);
$con->exec("set names utf8");
$con->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

// $userID = $_POST["Id"];
// $userPW = $_POST["Password"];
// $userName = $_POST["Name"];
// $userAge = $_POST["Age"];

// $stmt = con->prepare("insert into userinfo values(:id, :pw, :name, :age)");
// $stmt->bindparam(":id", $userID);
// $stmt->bindparam(":pw", $userPW);
// $stmt->bindparam(":name", $userName);
// $stmt->bindparam(":age", $userAge);
// $stmt->execute();

if(($_SERVER['REQUEST_METHOD']=='POST') && isset($_POST['submit'])){
    $userID = $_POST["id"];
    $userPW = $_POST["password"];
    $userName = $_POST["name"];
    $userBirth = $_POST["birth"];

    if(empty($userID)){
        $errMSG = "ID를 입력하세요";
    } else if(empty($userPW)){
        $errMSG = "비밀번호를 입력하세요";
    } else if(empty($userName)){
        $errMSG = "이름을 입력하세요";
    } else if(empty($userBirth)){
        $errMSG = "생일을 입력하세요";
    }

    if(!isset($errMSG)){
        try{
            $stmt = $con->prepare("insert into userinfo(id, password, name, birth) values(:id, :pw, :name, :birth);");
            $stmt->bindparam(":id", $userID);
            $stmt->bindparam(":pw", $userPW);
            $stmt->bindparam(":name", $userName);
            $stmt->bindparam(":birth", $userBirth);

            if($stmt->execute()){
                $successMSG = "새로운 사용자 추가 완료";
            }
            else {
                $errMSG = "사용자 추가 중 에러 발생";
            }
        } catch(PDOException $e){ die("Database Error : ".$e->getMessage()); }
    }
}

$con = null;

?>

<html>
    <body>
        <?php
        if(isset($errMSG)) echo $errMSG;
        if(isset($successMSG)) echo $successMSG;
        ?>

        <form action="<?php $_PHP_SELF ?>" method="POST">
            ID : <input type="text" name="id">
            PW : <input type="text" name="password">
            Name : <input type="text" name="name">
            Birth : <input type="date" name="birth">
            <input type="submit" name="submit">
        </form>
    </body>
</html>