<?php

class User{
    static $s_ID = 0;
    private $mUserID = 0 ;
    private $mFirstName = "";
    private $mSecondName = "";
    private $mPhone = "";
    private $mEmail = "";
    
    // c'tor
    public function User($i_FirstName, $i_SecondName, $i_Phone, $i_Email)
    {
        $this->mUserID = $s_ID++;
        $this->mFirstName = $i_FirstName;
        $this->mSecondName = $i_SecondName;
        $this->mPhone = $i_Phone;
        $this->mEmail = $i_Email;
    }

    // Get functions
    public function getName()
    { 
        $fullName = $this->mFirstName.$this->mSecondName;
        return $fullName;
    }
    
    public function getPhoneNum()
    {
        return $this->mPhone;
    }
    
    public function getEmail()
    {
        return $this->mEmail;
    }
    
     function getID()
     {
         return $this->mUserID;
     }
    
    // Set functions
    public function setName($i_FirstName, $i_SecondName)
    {
        $this->mFirstName = $i_FirstName;
        $this->mSecondName = $i_SecondName;
    }
    
    public function setPhoneNum($i_Phone)
    {
        $this->mPhone = $i_Phone;
    }
    
    public function setEmail($i_Email)
    {
        $this->mEmail = $i_Email;
    }  
}
?>
