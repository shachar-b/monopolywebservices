<?php


include 'ManagerRepresentativeViewScreen.php';

$representatives = ManagerRepresentativeViewScreen::getRepresentatives();

if (count($representatives) == 0) {
    echo 'There is no representatives in database';
} else {
    echo '<br>
    <br>';
    echo "<table border='1'>
    <tr>
    <th>ID</th>
    <th>First Name</th>
    <th>Last Name</th>
    <th>Phone</th>
    <th>E-mail</th>
    <th>UserName</th>
    <th>Password</th>
    <th>Permission</th>
    <th>Info/Editing</th>
    </tr>";
    foreach ($representatives as $rep) {
        echo "<form action=ManagerRepresentativeViewScreen.php method=post>";
        echo "<td>" . $rep['ID'] . " </td>";
        echo "<td>" . $rep['First Name'] . " </td>";
        echo "<td>" . $rep['Last Name'] . " </td>";
        echo "<td>" . $rep['Phone'] . " </td>";
        echo "<td>" . $rep['E-mail'] . " </td>";
        echo "<td>" . $rep['UserName'] . " </td>";
        echo "<td>" . $rep['Password'] . " </td>";
        echo "<td>" . $rep['Permission'] . " </td>";
        echo "<td>" . '<a type="hidden" href="employeeDataEditing.php?id=' . $rep['ID'] . '"  ">View Info/ Edit</a>' . " </td>";


        echo "</tr>";
        echo "</form>";
    }
    echo "</table>";
}

?>    
