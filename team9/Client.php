<?php
class Client extends User
{
    static $s_ID = 0;
    private $mRank = "a";
    private $mID = 0;
    
    public function Client($i_FirstName, $i_SecondName, $i_Phone, $i_Email, $i_Rank)
    {
        parent::User($i_FirstName, $i_SecondName, $i_Phone, $i_Email);
        $this->mRank = $i_Rank;
        $this->mID = $s_ID++;
    }
    
    public function getRank()
    {
        return $this->mRank;
    }
    
    public function getID()
    {
        return $this->mID;
    }
    
    public function setRank($i_Rank)
    {
        $this->mRank = $i_Rank;
    }
    
}
?>
