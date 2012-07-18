<?php

include_once 'User.php';

/**
 * Description of Client, inherits from User
 * @DBTableRepresntionOf('Client')
 */
class Client extends User {

    /**
     * Can be A-Important user/ B-Less Important user/ C-Casual user 
     * The rank of Web User
     * @var char
     */
    private $mRank;

    public function __construct($i_FirstName = null, $i_SecondName = null, $i_Phone = null, $i_Email = null,  $i_Password = null, $i_Rank = "C", $i_ID = null) {
        parent::__construct($i_FirstName, $i_SecondName, $i_Phone, $i_Email, $i_Password, $i_ID);
        $this->mRank = $i_Rank;
    }

    /**
     * getter for the rank
     * @return string The Rank
     * @DBAttribute(ColName = 'Rank', type='get')
     */
    public function getRank() {
        return $this->mRank;
    }

    /**
     * sets the Rank
     * @param string $i_Rank 
     * @DBAttribute(ColName = 'Rank', type='set')
     */
    public function setRank($i_Rank) {
        $this->mRank = $i_Rank;
    }

    public function getJSON() {
        $returnVal = array(
            'FirstName' => parent::getFirstName(),
            'LastName' => parent::getLastName(),
            'Password' => parent::getPassword(),
            'E-mail' => parent::getEmail(),
            'Phone' => parent::getPhoneNum(),
            'Rank' => $this->mRank,
            'ID' => parent::getID()
        );
        return json_encode($returnVal);
    }

}

?>
