<?php include_once 'DataBase.php'; ?>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript" src="jquery-1.7.2.min.js"></script>
        <script type="text/javascript" src ="bootstrap/js/bootstrap.min.js"></script>
        <link href="bootstrap/css/bootstrap.css" rel="stylesheet">
        <TITLE>Personal Info Screen</TITLE>
        <script type="text/javascript">
            function disableAllInputs(){
                $(":input").attr("disabled", "disabled");
            }
            
            function enableAllInputs(){
                $(":input").removeAttr("disabled", "disabled");
            }
            
            $(document).ready(function() {
                disableAllInputs();
                var queriedUserJSON = <?php getUserFromDatabase($_POST["queriedUserID"]) ?>;
                var queryingUserJSON = <?php getUserFromDatabase($_POST["queryingUserID"]) ?>; //TODO : Take this from browser session.
                var queriedUserID = queriedUserJSON["ID"];
                var queryingUserID = queryingUserJSON["ID"];
                $('#firstNameLbl').val(queriedUserJSON["FirstName"]);
                $('#lastNameLbl').val(queriedUserJSON["LastName"]);
                $('#phoneLbl').val(queriedUserJSON["Phone"]);
                $('#emailLbl').val(queriedUserJSON["E-mail"]);
                $('#userNameLbl').val(queriedUserJSON["UserName"]);
                $('#passwordLbl').val(queriedUserJSON["Password"]);
                $('#rankLbl').val(queriedUserJSON["Rank"]);
                $('#permisLbl').val(queriedUserJSON["Permission"]);
            });
        </script>
    </head>
    <body>
        <form id="PersonalInfoForm">
            <div class="header"><p>Personal Info Screen: </p></div>
            <dl class="dl-horizontal">
                <dt>Username</dt>
                <dd><input id="userNameLbl" type="text"/></dd>
                <dt>First Name</dt>
                <dd><input id="firstNameLbl" type="text"/></dd>
                <dt>Last Name</dt>
                <dd><input id="lastNameLbl" type="text"/></dd>
                <dt>Phone</dt>
                <dd><input id="phoneLbl" type="text"/></dd>
                <dt>eMail</dt>
                <dd><input id="emailLbl" type="text"/></dd>
                <dt>Password</dt>
                <dd><input id="passwordLbl" type="text"/></dd>
                <dt>Rank of user:</dt>
                <dd><input id="rankLbl" type="text"/></dd>
                <dt>Permission of employee:</dt>
                <dd><input id="permisLbl" type="text"/></dd>
            </dl>
            <div class="btn-group">
                <button class="btn" id="editBtn" disabled>Edit</button>
                <button class="btn" id="saveBtn" disabled>Save</button>
                <button class="btn" id="cancelBtn" disabled>Cancel</button>
            </div>
        </form>
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

<?php

function getUserFromDatabase($i_UserID) {
    // @var Database
    $dataBase = DataBase::getInstance();
    $tableName = $dataBase->getTableForPersonID($i_UserID);
    $className = $dataBase->getClassNameForTable($tableName);
    $returnVal = $dataBase->getObjectForClass("ID = " . $i_UserID, $tableName, $className);
    echo $returnVal->getJSON();
}
?>