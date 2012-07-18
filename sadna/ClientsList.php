<?php
/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

class ClientsList {

    private $mDB ;
    private $mConnection;

    public function __construct() {
        $this->mDB = Database::getInstance();
        $this->mConnection = $this->mDB->getConnection();
    }

    public function GetConversationsList()
    {
        $res = $this->mDB->query("SELECT * FROM client");
        $json = array();

        while($row=mysql_fetch_assoc($res)){
        $json[][]=$row;
        }

        return json_encode($json); 

    }

    public function closeDBConnection()
    {
        $this->mDB->__destruct();
    }

}

?>
