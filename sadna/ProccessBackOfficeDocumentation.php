<?php
include_once 'DataBase.php';
include_once 'BODocumentation.php';

class ProccessBackOfficeDocumentation {
    //isvalid saveData sendResponse

    const LEN_OF_COMMENTS_FIELD = 16777215;

    private $m_DB;
    private $m_BODObject;

    public function __construct($i_BOObject) {

        $this->m_DB = Database::getInstance();
        $this->m_BODObject = $i_BOObject;
    }

    public function saveData() {
        $saveOutcome = FALSE;
        $saveOutcome = $this->m_DB->insertObjectIntoDB("BO_doumentation", $this->m_BODObject);
        return $saveOutcome;
    }

    public function IsValid() {
        $validUserIDFlag = FALSE;
        $validBODFlag = FALSE;
        $validContentFlag = FALSE;
        $validConversation_IDFlag = FALSE;

        $validUserIDFlag = $this->isUserIDValid();
        $validConversation_IDFlag = $this->isConversationIDValid();
        $validContentFlag = $this->isCommentsValid($this->m_BODObject->getComments());
        //$validBOD_IDFlag = $this->isBodIdValid();

        if ($validUserIDFlag && $validContentFlag && $validConversation_IDFlag) {
            $validBODFlag = TRUE;
        }

        return $validBODFlag;
    }

    private function isUserIDValid() {
        return $this->m_DB->isUserIDValid($this->m_BODObject->getFeedBackCode());
    }

    private function isBodIdValid() {
        return $this->m_DB->isBodIdValid($this->m_BODObject->getFeedBackCode());
    }

    private function isConversationIDValid() {
        return $this->m_DB->isConversationIDValid($this->m_BODObject->getConversationID());
    }

    private function isCommentsValid($i_comments) {
        $commentsSizeOK = FALSE;
        //Size of DB field for comments is 'mediumtext' : 16777215 chars.
        //Check that comments recieved is not larger.
        if (strlen($i_comments) <= self::LEN_OF_COMMENTS_FIELD) {
            $commentsSizeOK = TRUE;
        }
        return $commentsSizeOK;
    }

    public function sendResponse($i_isSuccess) {
        //TODO : Write function
        if ($i_isSuccess == TRUE) {
            ; //Successful operation
        } else {
            ; //Failed operation
        }
    }

}

//Test code
echo "</br>";
$testBOD = new BODocumentation("127", "127", "GOOD");
$testProccess = new ProccessBackOfficeDocumentation($testBOD);
if ($testProccess->isValid()) {
    echo "Valid == TRUE";
    echo "</br>";
    if ($testProccess->saveData()) {
        echo "Save == TRUE";
        echo "</br>";
        $testProccess->sendResponse(TRUE);
    } else {
        echo "Save == FALSE";
        echo "</br>";
        $testProccess->sendResponse(FALSE);
    }
} else {
    echo "Valid == FALSE";
    echo "</br>";
}
echo "</br>";
?>

}

?>
