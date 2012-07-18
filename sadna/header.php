<?php
include_once("authenticate.php");
$authenticateLogin = new AuthenticateLogin();
$authenticateLogin->redirects($_POST);
?>


<!DOCTYPE>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.min.css" />
        <link rel="stylesheet" type="text/css" href="css/docs.css" />
        <?php $authenticateLogin->setUserData(); ?>
        <script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
        <script type="text/javascript" src="chatbox.js"></script>
        <script type="text/javascript" src="js/jquery.validate.min.js"></script>
    </head>
    <body>


