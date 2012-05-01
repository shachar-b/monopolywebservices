<?php
include_once 'DataBase.php';
include_once 'FeedBack.php';

class ProcessFeedBack
{
    const LEN_OF_COMMENTS_FIELD = '16777215';
    
    private $m_dataBase;
    private $m_feedBackObject;
    
    public function __construct($i_feedBackObject) {
        $this->m_feedBackObject = $i_feedBackObject;
       $this->m_dataBase = Database::getInstance();
       $this->isValid();
    }
    
    private function isValid()
    {
        $validUserIDFlag = FALSE;
        $validConversationIDFlag = FALSE;
        $validGradeFlag = FALSE;
        $validCommentsFlag = FALSE;
        
        $validUserIDFlag = $this->isUserIDValid($this->m_feedBackObject->getUserID());
        $validConversationIDFlag = $this->isConversationIDValid($this->m_feedBackObject->getConversationID());
        $validGradeFlag = $this->isGradeValid($this->m_feedBackObject->getGrade());
        $validCommentsFlag = $this->isCommentsValid($this->m_feedBackObject->getComments());
        
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
        $userExists = FALSE;
        //Query DB to see that user with $i_userID exists.
        
        return $userExists;
    }
    
    private function isConversationIDValid($i_conversationID)
    {
        $conversationExists = FALSE;
        //Query DB to see that conversation with $i_conversationID exists.
        
        return $conversationExists;
    }
    
    private function isGradeValid($i_grade)
    {
        //Check that $i_grade is within acceptable value list (from DB?)
        return TRUE;
    }
    
    private function isCommentsValid($i_comments)
    {
        $commentsSizeOK = FALSE;
        //Size of DB field for comments is 'mediumtext' : 16777215 chars.
        //Check that comments recieved is not larger.
        $commentsSizeOK =  (strlen($i_comments) <= self::LEN_OF_COMMENTS_FIELD);
        
        return $commentsSizeOK;
    }
        
    private function sendResponse($i_dataSaved)
    {
        if ($i_dataSaved == TRUE)
        {
            ; //Successful operation
        }
        else
        {
            ; //Failed operation
        }
    }
    
        private function saveData()
    {
            $operationSuccess = TRUE; //TODO : Modify according to insert outcome.
            $this->m_dataBase->insertObjectIntoDB( "Client_feedback",$this->m_feedBackObject);
            $this->sendResponse($operationSuccess);
    }

}

$test_Feedback = new FeedBack("1291", "1242", "A", "ABC");
$testFeedBackProcess = new ProcessFeedBack($test_Feedback);
?>
