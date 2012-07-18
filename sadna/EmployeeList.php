<?php

include_once 'DataBase.php';

class EmployeeList {

    private $mDB;
    private $mConnection;
    
    public function getRepresentativesArray() {

        $reps = $this->GetRepresentativeList();
        $repsArray = array();
        while (($row = mysql_fetch_assoc($reps)) !== false) {
            $repsArray[] = array(
                'ID' => $row['ID'],
                'Phone' => $row['Phone'],
                'First Name' => $row['FirstName'],
                'Last Name' => $row['LastName'],
                'E-mail' => $row['E-mail'],
                'UserName' => $row['UserName'],
                'Password' => $row['Password'],
                'Permission' => $row['Permission']
            );
        }
        return $repsArray;
        $this->closeDBConnection();
    }

    public function __construct() {
        $this->mDB = Database::getInstance();
        $this->mConnection = $this->mDB->getConnection();
    }

    public function GetManagerListAsJSON() {
        $res = $this->mDB->query("select * from Manager");
        $json = array();

        while ($row = mysql_fetch_assoc($res)) {
            $json[][] = $row;
        }

        return json_encode($json);
    }

    public function GetRepresentativeListAsJSON() {


        $res = $this->mDB->query("select * from Representative");

        $json = array();

        while ($row = mysql_fetch_assoc($res)) {
            $json[][] = $row;
        }

        return json_encode($json);
    }

    public function GetManagerList() {
        $res = $this->mDB->query("select * from Manager");
        return $res;
    }

    public function GetRepresentativeList() {

        $query = $this->mDB->query("select * from Representative");
        return $query;
    }

    public function closeDBConnection() {
        $this->mDB->__destruct();
    }

}

?>
