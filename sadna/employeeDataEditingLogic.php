<?php
include_once 'DataBase.php';

$firstName = $_POST['first'];
$lastName = $_POST['last'];
$phone = $_POST['phone'];
$mail = $_POST['email'];
$userName = $_POST['username'];
$password = $_POST['password'];
$permission = $_POST['permission'];
$id = $_POST['_id'];

//add data to database
$db = Database::getInstance();
$queryRepresentative = "Update `Representative` 
                        Set `FirstName` = '" . $firstName . "', `LastName` = '" . $lastName . "', `Phone` = '" . $phone . "', `E-mail` = '" . $mail . "', `UserName` = '" . $userName . "', `Password` ='" . $password . "', `Permission` = '" . $permission . "'
                        Where `ID` = '$id' ";
$db->query($queryRepresentative);

echo 'Completed updating successfuly!';
echo '<br>';
echo 'Wait a second..';
?>

<meta http-equiv="refresh" content="1; URL=ManagerRepresentativeViewScreen.php">
