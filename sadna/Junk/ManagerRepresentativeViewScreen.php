
//<?php
//
//include 'EmployeeList.php';
//
//class ManagerRepresentativeViewScreen {
//
//    public static function getRepresentatives() {
//        $repList = new EmployeeList();
//        $reps = $repList->GetRepresentativeList();
//        $repsArray = array();
//        while (($row = mysql_fetch_assoc($reps)) !== false) {
//            $repsArray[] = array(
//                'ID' => $row['ID'],
//                'Phone' => $row['Phone'],
//                'First Name' => $row['FirstName'],
//                'Last Name' => $row['LastName'],
//                'E-mail' => $row['E-mail'],
//                'UserName' => $row['UserName'],
//                'Password' => $row['Password'],
//                'Permission' => $row['Permission']
//            );
//        }
//        return $repsArray;
//
//
//        $repList->closeDBConnection();
//    }
//
//}
//?>

