<?php
 
    error_reporting(E_ALL);
    ini_set('display_errors',1);
 
    $dsn = 'mysql:host=localhost; dbname=user; port=3307;';
    $user = 'root';
    $password = 'bigdatar';

    $con = new PDO($dsn, $user, $password);
    $con->exec("set names utf8");
    $con->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
 
    $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");
 
    if( (($_SERVER['REQUEST_METHOD'] == 'POST') && isset($_POST['submit'])) || $android )
    {
        $name=$_POST['name'];
        $address=$_POST['address'];
        $review=$_POST['review'];
 
        if(empty($name)){
            $errMSG = "장소명을 입력하세요";
        }
        else if(empty($address)){
            $errMSG = "주소를 입력하세요";
        }
        else if(empty($review)){
            $errMSG = "한줄평을 입력하세요";
        }
 
        if(!isset($errMSG)){
            try{
                $stmt = $con->prepare('INSERT INTO place(name, address, review) values(:name, :address, :review);');
                $stmt->bindParam(':name', $name);
                $stmt->bindParam(':address', $address);
                $stmt->bindParam(':review', $review);
 
                if($stmt->execute())
                {
                    $successMSG = "장소 추가 완료";
                }
                else
                {
                    $errMSG = "장소 추가 실패";
                }
 
            } catch(PDOException $e) {
                die("Database error: " . $e->getMessage());
            }
        }
 
    }
 
?>
 
<?php
    if (isset($errMSG)) echo $errMSG;
    if (isset($successMSG)) echo $successMSG;
 
        $android = strpos($_SERVER['HTTP_USER_AGENT'], "Android");
 
    if( !$android )
    {
?>
    <html>
       <body>
            <form action="<?php $_PHP_SELF ?>" method="POST">
                장소명: <input type = "text" name = "name" />
                주소: <input type = "text" name = "address" />
                한줄평: <input type = "text" name = "review" />
                <input type = "submit" name = "submit" />
            </form>
 
       </body>
    </html>
<?php
    }
?>