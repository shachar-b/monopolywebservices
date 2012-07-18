<?php
include_once 'DataBase.php';
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of ConversationsList
 *
 * @author eli
 */
class ConversationsList {
    private $mDB ;
    private $mConnection;
    
    public function __construct() {
        $this->mDB = Database::getInstance();
        $this->mConnection = $this->mDB->getConnection();                
    }
    
    public function GetConversationsList()
    {                  
        $res = $this->mDB->query("select * from `Conversation`");
        return $res;
    }        
    
    public function closeDBConnection()
    {
        $this->mDB->__destruct();
    }
}

?>
