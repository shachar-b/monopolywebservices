<?php

include_once 'DataBase.php';
include_once 'Client.php';
include_once 'Employee.php';
include_once 'personalInfoPageGenerator.php';

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of EmployeeScreenGenerator
 *
 * @author omer
 */
class EmployeeScreenGenerator
{

    //@var User
    private $m_user;

    public function __construct($i_UserID)
    {
        $this->m_user = personalInfoPageGenerator::getUserFromDatabase($i_UserID); //TODO:move to DB?
    }
    public function getFullName()
    {
        return $this->m_user->getFirstName()." ".$this->m_user->getLastName();
    }


}

?>
