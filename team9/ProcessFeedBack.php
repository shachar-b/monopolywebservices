<?php
include_once 'DataBase.php';

class ProcessFeedBack
{
    private $m_dataBase;
    private $m_feedBackObject;
    
    public function __construct($i_feedBackObject) {
        $this->m_feedBackObject = $i_feedBackObject;
       $this->m_dataBase = Database::getInstance();
       
       $this->isValid();
    }
    
    private function isValid()
    {
        $validUserIDFlag = TRUE;
        $validConversationIDFlag = TRUE;
        $validGradeFlag = TRUE;
        $validCommentsFlag = TRUE;
        
        $validUserIDFlag = $this->isUserIDValid($this->m_feedBackObject->m_UserID);
        $validConversationIDFlag = $this->isConversationIDValid($this->m_feedBackObject->m_ConversationID);
        $validGradeFlag = $this->isGradeValid($this->m_feedBackObject->m_Grade);
        $validCommentsFlag = $this->isCommentsValid($this->m_feedBackObject->m_Comments);
        
        if ($validUserIDFlag && $validConversationIDFlag && $validGradeFlag && $validCommentsFlag)
        {
            $this->saveData();
        }
        else
        {
            $this->sendResponse (FALSE);
        }
    }
    
    private function isUserIDValid($i_userID)
    {
        //Query DB to see that user with $i_userID exists.
        return TRUE;
    }
    
    private function isConversationIDValid($i_conversationID)
    {
        //Query DB to see that conversation iwth $i_conversationID exists.
        return TRUE;        
    }
    
    private function isGradeValid($i_grade)
    {
        //Check that $i_grade is within acceptable value list (from DB?)
        return TRUE;
    }
    
    private function isCommentsValid($i_comments)
    {
        //Check that not illegal characters, maybe?
        return TRUE;
    }
    
    private function saveData()
    {
        $this->m_dataBase->insertObjectIntoDB( "Client_feedback",$this->m_feedBackObject); //Do something with DB here
    }
    
    private function sendResponse($i_dataSaved)
    {
        if ($i_dataSaved)
        {
            ; //Successful operation
        }
        else
        {
            ; //Failed operation
        }
    }
}
?>
