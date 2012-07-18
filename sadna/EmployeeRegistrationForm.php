<?php
include_once 'Employee.php';
include_once 'User.php';
include_once 'DataBase.php';


$firstName = $_POST['firstname'];
$lastName = $_POST['lastname'];
$phone = $_POST['phone'];
$mail = $_POST['mail'];
$permission = $_POST['permission'];

if ($_POST) {
    if ($firstName != '' && $lastName != '' && $phone != '' && $permission != '') {
        //add data to database

        $newEmployee = new Employee($firstName, $lastName, $phone, $mail, $permission);
        $newEmployee->createPassword();


        $db = Database::getInstance();


        if ($permission === 'a') {
            $db->insertObjectIntoDB(DataBase::ManagerTable, $newEmployee);
            $query = "Update `Manager` Set `Permission` = 'a' where `LastName`= '" . $lastName . "' and `FirstName`= '" . $firstName . "' and `Phone` = '" . $phone . "' and `E-mail` = '" . $mail . "' ";
            $db->query($query);
            $feedback = 'A new Manager was added succesfully';
        } else if ($permission === 'b') {
            $db->insertObjectIntoDB(DataBase::RepresentativeTable, $newEmployee);
            $query = "Update `Representative` Set `Permission` = 'b' where `LastName`= '" . $lastName . "' and `FirstName`= '" . $firstName . "' and `Phone` = '" . $phone . "' and `E-mail` = '" . $mail . "' ";
            $db->query($query); // update permission of a new employee
            $feedback = 'A new Representative was added succesfully';
        } else {
            $feedback = 'There is no ' . $permission . ' permission!';

            echo '<script type="text/javascript">'
            , 'reloadPage()'
            , '</script>';
        }
    } else {
        //echo 'Fill out all the fields';
        $feedback = "Fill out all the fields";
    }
}
?>          

<form action="?" method="post">
        <p id="feedback"><?php echo $feedback; ?></p>
    <fieldset>
        <ul>
            <li>
            <label for="FirstName">First name:</label>
            <input type="text" name="firstname" id="firstname"/>
            </li>
            <li>
            <label for="LastName">Last name:</label>
            <input type="text" name="lastname" id="lastname"/>
            </li>
            <li>
            <label for="Phone">Phone:</label>
            <input type="text" name="phone" id="phone"/>
            </li>
            <li>
            <label for="mail">E-mail:</label>
            <input type="text" name="mail" id="mail"/>
            </li>
            <li>
            <label for="permission">Permission:</label>
            <input type="text" name="permission" id="permission"/>
            </li>
            <li>
                <input type="submit" name="formSubmit" class="btn btn-primary"  value="Register">                    
            </li>

        </ul>

    </fieldset>
</form> 


