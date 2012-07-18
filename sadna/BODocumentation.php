<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
require_once("IDBSerializableClass.php");

class BODocumentation implements IDBSerializableClass {
    
    private $m_ConversationID;
  
    private $m_employeeID;

    private $m_Content;

    
    public function __construct($i_ConversationID=null, $i_employeeID=null,$i_Comments=null)
    {
        $this->m_ConversationID = $i_ConversationID;
        $this->m_Content = $i_Comments;
        $this->m_employeeID = $i_employeeID;
    }

    public static function initInstanceFromDBRows($i_row)
    {
        $conversationID = $i_row['conversation_code'];
        $m_employeeID = $i_row['employee_code'];
        $content = $i_row['content'];
        $Obj = new BODocumentation($conversationID, $BoID, $content);
        return $Obj;
    }

    //getters
    
    public function getConversationID()
    {

        return $this->m_ConversationID;
    }

    public function getUserID()
    {

        return $this->m_employeeID;
    }

    public function getComments()
    {

        return $this->m_Content;
    }


    public function setConversationID($i_ConversationID)
    {

        $this->m_ConversationID = $i_ConversationID;
    }

   
    public function setUserID($i_employeeID)
    {

        $this->m_employeeID = $i_employeeID;
    }

    public function setComments($i_Content)
    {

        $this->m_Content = $i_Content;
    }


    public function getInstanceAsDBInsetString()
    {
        return "(`bo_code`, `conversation_code`, `content`) VALUES" .
                "('" . $this->m_employeeID .
                "', '" . $this->m_ConversationID .
                "', '" . $this->m_Content . "')";
    }

    public function __toString()
    {
        return "('" . $this->m_employeeID .
                "', '" . $this->m_ConversationID .
                "', '" . $this->m_Content . "')";
    }

}

?>
