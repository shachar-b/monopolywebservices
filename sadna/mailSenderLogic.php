<?php
include 'DataBase.php';

$to = $_POST['receiver'];
$subject = $_POST['subject'];
$message = $_POST['message'];

if ($_POST) {

        $db = DataBase::getInstance();

        //Get emails list
        if ($to == "Representatives") {
            $emailsListQuery = "select `E-mail` from `Representative` ";
        } else {
            $emailsListQuery = "select `E-mail` from `Client` ";
        }
        $emailsList = $db->query($emailsListQuery);


        //Parsing query to strings array
        $count = 0;
        $emailStringList[] = array();
        while (($row = mysql_fetch_assoc($emailsList)) !== false) {
            $emailStringList[$count] = $row['E-mail'];
            $count++;
        }

        //implode the list into a single variable, put commas in, apply as $to value.
        $to = implode(",", $emailStringList);
        $isSentSuccessfully = mail($to, $subject, $message);

        //feedback message
        if ($isSentSuccessfully) {
            echo 'The email has been sent!';
        } else {
            echo 'The email has failed!';
        }
}

?>