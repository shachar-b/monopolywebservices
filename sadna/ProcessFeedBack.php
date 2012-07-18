<?php

include_once 'DataBase.php';
include_once 'FeedBack.php';

/*
 * @author Shachar Butnaro
 * This class handles submitting of the feedback form to the system.
 * It validates input and interacts with the database.
 */

class ProcessFeedBack {
    /**
     * Constant depicting maximum length of comments field in database.
     * @var int
     */

    const LEN_OF_COMMENTS_FIELD = 16777215;

    /**
     * Constant depictive minimum value of Costumer's service grade in feedback. 
     * @var int
     */
    const MINIMUM_SERVICE_GRADE = 1;

    /**
     * Constant depictive maximum value of Costumer's service grade in feedback. 
     * @var int
     */
    const MAXIMUM_SERVICE_GRADE = 10;

    /**
     * Class member : An instance of the application DB.
     * @var DataBase
     */
    private $m_dataBase;

    /**
     * Class member : An instance of a Feedback Object.
     * @var FeedBack
     */
    private $m_feedBackObject;

    /**
     * public constructor for ProcessFeedBack - initializes the class members,
     * then checks if FeedBack to be processed is valid.
     * @param FeedBack $i_feedBackObject 
     */
    public function __construct($i_feedBackObject) {
        $this->m_feedBackObject = $i_feedBackObject;
        $this->m_dataBase = Database::getInstance();
    }

    /**
     * public function isValid()
     * Checks that the feedback recieved is valid, by checking each field.
     * Returns TRUE IFF all fields are valid.
     * @return boolean 
     */
    public function isValid() {
        $validConversationIDFlag = FALSE;
        $validGradeFlag = FALSE;
        $validCommentsFlag = FALSE;
        $validFeedback = FALSE;

        $validConversationIDFlag = $this->isConversationIDValid();
        $validGradeFlag = $this->isGradeValid($this->m_feedBackObject->getGrade());
        $validCommentsFlag = $this->isCommentsValid($this->m_feedBackObject->getComments());

        if ($validConversationIDFlag && $validGradeFlag && $validCommentsFlag) {
            $validFeedback = TRUE;
        }

        return $validFeedback;
    }

    /**
     * private function isConversationIDValid()
     * Checks if ConversationID given in feedback object is valid
     * @return boolean - TRUE IFF userID is valid
     */
    private function isConversationIDValid() {
        return $this->m_dataBase->isConversationIDValid($this->m_feedBackObject->getConversationID());
    }

    /**
     * private function isGradeValid($i_grade)
     * Checks if the grade given in the feedback object is valid.
     * If so, returns TRUE. Otherwise returns FALSE.
     * @param int $i_grade
     * @return boolean - Signals if data is valid.
     */
    private function isGradeValid($i_grade) {
        if ($i_grade >= self::MINIMUM_SERVICE_GRADE && $i_grade <= self::MAXIMUM_SERVICE_GRADE) {
            return TRUE;
        } else {
            return FALSE;
        }
    }

    /**
     * private function isCommentsValid($i_comments)
     * No special constraints over comments, so only checks length of field.
     * @param type $i_comments
     * @return boolean - Signals if data is valid
     */
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
        if ($i_isSuccess == TRUE) {
            return "Success";
        } else {
            return "Failure";
        }
    }

    /**
     * public function saveData()
     * INSERTs the feedback object into the database.
     * Returns the insertion outcome.
     * @return boolean 
     */
    public function saveData() {
        $saveOutcome = FALSE;
        $saveOutcome = $this->m_dataBase->insertObjectIntoDB("Client_feedback", $this->m_feedBackObject);
        return $saveOutcome;
    }

}

//Test code
if (FALSE) {
    echo "</br>";
    $testFeedback = new FeedBack("3", "10", "GOOD");
    $testProccess = new ProcessFeedBack($testFeedback);
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
}
?>
