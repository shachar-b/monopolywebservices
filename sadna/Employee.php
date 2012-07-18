<?php

include_once 'User.php';

/**
 * Description of Employees
 *
 * @author Vika
 */
class Employee extends User {

    /**
     * The Permission of Employee (Representative / Manager)
     * @var string
     */
    private $permissionsLevel;

    public function __construct($i_FirstName = null, $i_SecondName = null, $i_Phone = null, $i_Email = null, $i_PermisLevel = null, $i_Password = null) {
        parent::__construct($i_FirstName, $i_SecondName, $i_Phone, $i_Email, $i_Password);
        $this->permissionsLevel = $i_PermisLevel;
    }

    //getters:

    /**
     * getter for the Permission
     * @return string The Permission
     * @DBAttribute(ColName = 'Permission', type='get')
     */
    public function getPermissionsLevel() {
        return $this->permissionsLevel;
    }

    //setters

    /**
     * sets Permission of Employee
     * @param string $i_PermisLevel
     * @DBAttribute(ColName = 'Permission', type='set')
     */
    public function setPermissionsLevel($i_PermisLevel) {
        $this->permissionsLevel = $i_PermisLevel;
    }

    public function getJSON() {
        $returnVal = array(
            'FirstName' => parent::getFirstName(),
            'LastName' => parent::getLastName(),
            'Password' => parent::getPassword(),
            'E-mail' => parent::getEmail(),
            'Phone' => parent::getPhoneNum(),
            'Permission' => $this->permissionsLevel,
            'ID' => parent::getID()
        );
        return json_encode($returnVal);
    }

    public function createPassword() {
        $firstName = $this->getFirstName();
        $lastName = $this->getLastName();
        $mix = $firstName . $lastName;
        $str_shuffle = str_shuffle($mix);
        $this->setPassword($str_shuffle);
        // echo $str_shuffle;
        //return $str_shuffle;
    }

}

?>
