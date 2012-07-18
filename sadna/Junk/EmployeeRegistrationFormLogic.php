<!--//check input-->

<html>
    <body>
        
<?php
include_once 'Employee.php';
include_once 'User.php';
include_once 'DataBase.php';

$firstName = $_POST['firstname'];
$lastName = $_POST['lastname'];
$phone = $_POST['phone'];
$mail= $_POST['mail'];
$permission= $_POST['permission'];

//בדיקות תקינות הקלט
//למה A - ההרשאה לא נרשם ל-DB??
//add data to database
$newEmployee = new Employee($firstName, $lastName, $phone, $mail, $permission);

$db = Database::getInstance();
if($permission === 'a')
{

    $db->insertObjectIntoDB(DataBase::ManagerTable, $newEmployee) ;
    echo 'A new Manager was added succesfully';
}
 else if($permission === 'b')
 {
     $db->insertObjectIntoDB(DataBase::RepresentativeTable, $newEmployee);     
     echo 'A new Representative was added succesfully';
 }
 else {
     echo 'There is no ';
     echo $permission;
     echo ' permission';

//  $url = htmlspecialchars($_SERVER['employeeDataEditing.php']);
//  echo "<br><a href='$url'>Back</a>"; 
   echo '<script type="text/javascript">'
   , 'reloadPage()'
   , '</script>';
     
}

?>
        
   <script type="text/javascript">
function reloadPage()
  {
  location.replace("EmployeeRegistrationForm.php")
  }
</script>

<!--<input type="button" value="Reload page" onclick="reloadPage()" />-->
</body>
</html>
