<?php
include_once("authenticate.php");
$authenticateLogin = new AuthenticateLogin();
$authenticateLogin->redirects($_POST);
?>
<!DOCTYPE html>
<html>
    <head>
        <link rel="stylesheet" type="text/css" href="bootstrap/css/bootstrap.min.css" />
        <!--<link rel="stylesheet" type="text/css" href="css/docs.css" />-->
        <?php $authenticateLogin->setUserData(); ?>
        <title> Costumer Communications Service  </title>
        <script type="text/javascript" src="js/jquery-1.7.2.min.js"></script>
        <script type="text/javascript" src="chatbox.js"></script>
        <script type="text/javascript" src="js/jquery.validate.min.js"></script>
    </head>
    <body>
        <?php {
            include_once 'EmployeeScreenGenerator.php';
            $generator = new EmployeeScreenGenerator($_SESSION['id']);
            if ($generator->isAllowedToViewPage()) {
                echo $generator->getContentByPermissions();
                echo $generator->getJSFunctionsAccordingToPremmisions();
            } else {
                //header("Location: http://bit.ly/MtR2ky"); //TODO : Change the relocation
            }
        }
        ?>
        <script type='text/javascript' src="js/scripts.js"></script>
        <script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
    </body>
</html>