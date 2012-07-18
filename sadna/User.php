<?php

/**
 * Description of User
 * @version 1.1 (Edited by Shachar)
 * @author Vika
 * @DBTableRepresntionOf('Client')
 */
class User {

    /**
     * the user id(long)
     * @var long
     */
    private $mUserID = null;

    /**
     * The First Name of user
     * @var string
     */
    private $mFirstName;

    /**
     * The Last Name of user
     * @var string
     */
    private $mSecondName;

    /**
     * The Phone of user
     * @var string
     */
    private $mPhone;

    /**
     * The E-Mail of user
     * @var string
     */
    private $mEmail;

    /**
     * The user name of Employee
     * @var string
     */
    private $mUserName;

    /**
     * The password of Employee
     * @var string
     */
    private $mPassword;

    /**
     * Can be A-Important user/ B-Less Important user/ C-Casual user
     * The rank of Web User
     * @var string
     */
    //private $mRank = "C";
    // c'tor
    public function __construct($i_FirstName = null, $i_SecondName = null, $i_Phone = null, $i_Email = null, $i_UserName = null, $i_Password = null, $i_ID = null) {
        $this->mFirstName = $i_FirstName;
        $this->mSecondName = $i_SecondName;
        $this->mPhone = $i_Phone;
        $this->mEmail = $i_Email;
        $this->mUserName = $i_UserName;
        $this->mPassword = $i_Password;
        $this->mUserID = $i_ID;
    }

    // Get functions
    /**
     * getter for the First Name
     * @return string The First Name
     * @DBAttribute(ColName = 'FirstName', type='get')
     */
    public function getFirstName() {
        return $this->mFirstName;
    }

    /**
     * getter for the rank
     * @return string The Rank
     * @DBAttribute(ColName = 'Rank', type='get')
     */
//    public function getRank() {
//        return $this->mRank;
//    }

    /**
     * getter for the Last Name
     * @return string Last Name
     * @DBAttribute(ColName = 'LastName', type='get')
     */
    public function getLastName() {
        return $this->mSecondName;
    }

    /**
     * getter for the Phone
     * @return string Phone
     * @DBAttribute(ColName = 'Phone', type='get')
     */
    public function getPhoneNum() {
        return $this->mPhone;
    }

    /**
     * getter for the Mail
     * @return string Mail
     * @DBAttribute(ColName = 'E-mail', type='get')
     */
    public function getEmail() {
        return $this->mEmail;
    }

    /**
     * getter for the username
     * @return string The username
     * @DBAttribute(ColName = 'UserName', type='get')
     */
    public function getUserName() {
        return $this->mUserName;
    }

    /**
     * getter for the password
     * @return string The password
     * @DBAttribute(ColName = 'Password', type='get')
     */
    public function getPassword() {
        return $this->mPassword;
    }

    /**
     * getter for the user ID
     * @return long the user ID
     * @DBPrime('ID')
     */
    function getID() {
        return $this->mUserID;
    }

    // Set functions
    /**
     * sets the First Name
     * @param string $i_FirstName
     * @DBAttribute(ColName = 'FirstName', type='set')
     */
    public function setFirstName($i_FirstName) {
        $this->mFirstName = $i_FirstName;
    }

    /**
     * sets the Rank
     * @param string $i_Rank
     * @DBAttribute(ColName = 'FirstName', type='set')
     */
//    public function setRank($i_Rank) {
//        $this->mRank = $i_Rank;
//    }

    /**
     * sets the Last Name
     * @param string $i_SecondName
     * @DBAttribute(ColName = 'LastName', type='set')
     */
    public function setLastName($i_SecondName) {
        $this->mSecondName = $i_SecondName;
    }

    /**
     * sets the Phone
     * @param string $i_Phone
     * @DBAttribute(ColName = 'Phone', type='set')
     */
    public function setPhoneNum($i_Phone) {
        $this->mPhone = $i_Phone;
    }

    /**
     * sets the Email
     * @param string $i_Email
     * @DBAttribute(ColName = 'E-mail', type='set')
     */
    public function setEmail($i_Email) {
        $this->mEmail = $i_Email;
    }

    /**
     * sets username of Employee
     * @param string $i_Username
     * @DBAttribute(ColName = 'UserName', type='set')
     */
    public function setUserName($i_Username) {
        $this->mUserName = $i_Username;
    }

    /**
     * sets password of Employee
     * @param string $i_Password
     * @DBAttribute(ColName = 'Password', type='set')
     */
    public function setPassword($i_Password) {
        $this->mPassword = $i_Password;
    }

    /**
     * Sets User ID
     * @param long $i_ID
     * @DBAttribute(ColName = 'ID', type='set')
     */
    public function setID($i_ID) {
        $this->mUserID = $i_ID;
    }

}

?>
