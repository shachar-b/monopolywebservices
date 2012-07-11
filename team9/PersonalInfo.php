<?php
include_once 'personalInfoPageGenerator.php';

$genaretor = new personalInfoPageGenerator($_POST['queriedUserID'], $_POST['queryingUserID']);
?>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="jquery-1.7.2.min.js"></script>
        <script type="text/javascript" src ="bootstrap/js/bootstrap.min.js"></script>
        <link href="bootstrap/css/bootstrap.css" rel="stylesheet">
        <TITLE>Personal Info Screen</TITLE>
        <script type="text/javascript">





<?php
echo $genaretor->getInitScripttAccordingToViewingPreviliges();
echo $genaretor->getJSFunctionsAccordingToPremmisions();
?>

    $(document).ready(init);
        </script>
    </head>
    <body>


        <div id='PersonalInfoForm'>
            <div class='header'><p>Personal Info Screen: </p></div>
            <?php echo $genaretor->getUserDataListIfAllowed(); ?>
            <div id="errorMSG">
            </div>
            <div id="sucsessMSG">
            </div>
        </div>
    </body>
</html>

<!--
        if (TRUE) {
            //Manager accessing - read/write on all
        } else if (FALSE) {
            //Representative accessing - can view own data and Clients' data, read only on own data, read/write on clients.
        } else if (FALSE) {
            //Client accessing - can view own details (read only)
        }
-->
