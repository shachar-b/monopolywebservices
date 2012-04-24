<?php

class Employee extends User
{
    static $s_ID = 0;
    private $mID = 0;
    private $permissionsLevel = "a";
    
    public function Employee($i_FirstName, $i_SecondName, $i_Phone, $i_Email, $i_permisLevel)
    {
        parent::User($i_FirstName, $i_SecondName, $i_Phone, $i_Email);
        $this->permissionsLevel = $i_permisLevel;
        $this->mID = $s_ID++;
    }
    
    public function setPermissionsLevel($i_PermisLevel)
    {
        $this->permissionsLevel = $i_PermisLevel;
    }
    
    public function getPermissionsLevel()
    {
        return $this->permissionsLevel;
    }
    
    public function getID()
    {
        return $this->permissionsLevel;
    }    
}
?>
