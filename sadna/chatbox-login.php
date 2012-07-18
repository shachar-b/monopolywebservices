<?php

$ret = array("user_exists" => false);
include_once("DataBase.php");
$mDB = Database::getInstance();
$mConnection = $mDB->getConnection();

if (isset($_POST['email']) && isset($_POST['password'])) {

    $sql = "SELECT first_name, id FROM users WHERE ";
    $sql .= "email='" . $_POST['email'] . "' AND password='" . $_POST['password'] . "' LIMIT 1";
    $res = $mDB->query($sql);
    if ($res) {
        $user = mysql_fetch_array($res);
        $ret = array(
            'user_exists' => 'true',
            'user' => array(
                'first_name' => $user['first_name'],
                'id' => $user['id']
            )
        );
    }
}

echo json_encode($ret);
?>
