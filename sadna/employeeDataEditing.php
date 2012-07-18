<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <TITLE>Displaying tables</TITLE>
        <script type="text/javascript" src ="js/jquery-1.7.2.min.js"></script>

    </head>

    <body> 
        <form action="employeeDataEditingLogic.php" method="post">
            <fieldset> 
                <h1>Employee Info and Editing data</h1>

                <?php
                include_once 'DataBase.php';

                if (isset($_POST['id'])) {
                    $employeeId = (int) $_POST['id'];
                    if (EmployeeExists($employeeId)) {
                        $result = DataBase::getInstance()->query("select * from `Representative` WHERE `ID` = '$employeeId'");

                        $repsArray = array();
                        while (($row = mysql_fetch_assoc($result)) !== false) {
                            $repsArray[] = array(
                                'ID' => $row['ID'],
                                'Phone' => $row['Phone'],
                                'FirstName' => $row['FirstName'],
                                'LastName' => $row['LastName'],
                                'E-mail' => $row['E-mail'],
                                'Password' => $row['Password'],
                                'Permission' => $row['Permission']
                            );
                        }

                        if (count($repsArray) != 0) {
                            echo '<br>';
                            echo "<table border='1'>
                    <tr>
                        <th>FirstName</th>
                        <th>LastName</th>
                        <th>Phone</th>
                        <th>E-mail</th>
                        <th>Password</th>
                        <th>Permission</th>

                    </tr>";
                            foreach ($repsArray as $rep) {
                                echo "<td>" . "<input id=first_id type=text name=first value=" . $rep['FirstName'] . " </td>";
                                echo "<td>" . "<input id=last_id type=text name=last value=" . $rep['LastName'] . " </td>";
                                echo "<td>" . "<input id=phone_id type=text name=phone value=" . $rep['Phone'] . " </td>";
                                echo "<td>" . "<input id=email_id type=text name=email value=" . $rep['E-mail'] . " </td>";
                                echo "<td>" . "<input id=password_id type=text name=password value=" . $rep['Password'] . " </td>";
                                echo "<td>" . "<input id=permission_id type=text name=permission value=" . $rep['Permission'] . " </td>";
                                echo "<input id=_id type=hidden name=_id value=" . $employeeId;

                                echo "</tr>";
                            }
                            echo "</table>";
                        }
                    }
                }

                function EmployeeExists($employeeId) {
                    $employeeId = (int) $employeeId;
                    return (mysql_result(DataBase::getInstance()->query("SELECT COUNT(`ID`) FROM `Representative` WHERE `ID`=$employeeId"), 0) == 0) ? false : true; //checks if employee exists
                }
                ?>
                <p> 
                    <input TYPE="button" onClick="parent.location='ManagerRepresentativeViewScreen.php'" value="Back">
                    <input type ="submit" name ="submit"  id ="Save_changes" value="Save"/>  

                </p>
            </fieldset>
        </form>     

    </body>
</html>

