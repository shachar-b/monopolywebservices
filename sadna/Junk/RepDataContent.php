<?php

include 'DataBase.php';
$db = DataBase::getInstance();

$firstName = mysql_real_escape_string($_POST['firstname']);
$lastName = mysql_real_escape_string($_POST['lastname']);
$phone = mysql_real_escape_string($_POST['phone']);

if ($firstName != NULL && $lastName != NULL) {

    $queryWithoutPhone = "Select * from `Representative` where `FirstName` = '$firstName' and `LastName` = '$lastName'";
    $queryWithPhone = "Select * from `Representative` where `FirstName` = '$firstName' and `LastName` = '$lastName' and `Phone` ='$phone' ";

    if ($phone == NULL) {
        $representative = $db->query($queryWithoutPhone);
    } else {
        $representative = $db->query($queryWithPhone);
    }

    $foundedRowsNum = mysql_num_rows($representative);
    if ($foundedRowsNum == 0) {
        echo 'Representative does not exist';
    } else {
        $repsArray = array();
        while (($row = mysql_fetch_assoc($representative)) !== false) {
            $repsArray[] = array(
                'ID' => $row['ID'],
                'Phone' => $row['Phone'],
                'FirstName' => $row['FirstName'],
                'LastName' => $row['LastName'],
                'E-mail' => $row['E-mail'],
                'UserName' => $row['UserName'],
                'Password' => $row['Password'],
                'Permission' => $row['Permission']
            );
        }
        echo '<br>
            <br>';
        echo "<table border='1'>
            <tr>
            <th>ID</th>
            <th>FirstName</th>
            <th>LastName</th>
            <th>Phone</th>
            <th>E-mail</th>
            <th>UserName</th>
            <th>Password</th>
            <th>Permission</th>
            <th>Info/Editing</th>
            </tr>";
        foreach ($repsArray as $rep) {
            echo "<form action=ManagerRepresentativeViewScreen.php method=post>";
            echo "<td>" . $rep['ID'] . " </td>";

            echo "<td>" . $rep['FirstName'] . " </td>";
            echo "<td>" . $rep['LastName'] . " </td>";
            echo "<td>" . $rep['Phone'] . " </td>";
            echo "<td>" . $rep['E-mail'] . " </td>";
            echo "<td>" . $rep['UserName'] . " </td>";
            echo "<td>" . $rep['Password'] . " </td>";
            echo "<td>" . $rep['Permission'] . " </td>";
            echo "<td>" . '<a href="employeeDataEditing.php?id=' . $rep['ID'] . '"  ">View Info/ Edit</a>' . " </td>";
            echo "</tr>";
            echo "</form>";
        }
        echo "</table>";
    }
} else {
    echo '<br> Please enter First name and Last name!';
}
?>
