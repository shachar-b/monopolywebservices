<?php

require_once("IDBSerializableClass.php");

/**
 * @author omer shenhar
 * @version 1.0
 * @DBTableRepresntionOf('Client_feedback')
 */
class FeedBack implements IDBSerializableClass
{

    /**
     * the conversation id(long)
     * @var long
     */
    private $m_ConversationID;

    /**
     * the ID of the feedback in the system (long)
     * @var long
     */
    private $m_FeedbackCode;

    /**
     * the service rating given by the user(from a scale of 1 to 10)
     * @var byte
     */
    private $m_Grade;

    /**
     * if the user has submited any comments- the comments should be placed here
     * @var string
     */
    private $m_Comments;

    /**
     *  public constractor for feedback- dose nothing- an init function must be called
     * @param long $i_ConversationID - the conversation id in qustion (long)
     * @param long $i_FeedBackCode - the feedback code(long)
     * @param byte $i_Grade - the service rating given by the user(from a scale of 1 to 10)
     * @param string $i_Comments  - if the user has submited any comments- the comments should be placed here
     */
    public function __construct($i_ConversationID=null, $i_FeedBackCode=null, $i_Grade=null, $i_Comments=null)
    {
        $this->m_ConversationID = $i_ConversationID;
        $this->m_Comments = $i_Comments;
        $this->m_Grade = $i_Grade;
        $this->m_FeedbackCode = $i_FeedBackCode;
    }

    public static function initInstanceFromDBRows($i_row)
    {
        $conversationID = $i_row['conversation_code'];
        $userID = $i_row['feedback_code'];
        $grade = $i_row['servise_grade'];
        $comments = $i_row['comments'];
        $Obj = new FeedBack($conversationID, $userID, $grade, $comments);
        return $Obj;
    }

    //getters
    /**
     *  getter for the Conversation ID
     * @return long number conversation ID
     * @DBAttribute(ColName = 'conversation_code', type='get')
     */
    public function getConversationID()
    {

        return $this->m_ConversationID;
    }

    /**
     *  getter for the User ID
     * @return long number UserID ID
     * @DBPrime('feedback_code')
     */
    public function getUserID()
    {

        return $this->m_FeedbackCode;
    }

    /**
     * getter for comments field
     * @return String - free text
     * @DBAttribute(ColName = 'comments', type='get')
     */
    public function getComments()
    {

        return $this->m_Comments;
    }

    /**
     * getter for the user given grade
     * @return byte a number from 1 to 10
     * @DBAttribute(ColName = 'servise_grade', type='get')
     */
    public function getGrade()
    {

        return $this->m_Grade;
    }

    //setters
    /**
     * sets the conversationID
     * @param long $i_ConversationID - the conversation id in qustion (long)
     * @DBAttribute(ColName = 'conversation_code', type='set')
     */
    public function setConversationID($i_ConversationID)
    {

        $this->m_ConversationID = $i_ConversationID;
    }

    /**
     * sets the UserID
     * @param long $i_UserID - the ID of the user who have submited the feedback (long)
     * @DBAttribute(ColName = 'feedback_code', type='set')
     */
    public function setUserID($i_UserID)
    {

        $this->m_FeedbackCode = $i_UserID;
    }

    /**
     * sets the free text section
     * @param string $i_Comments  - if the user has submited any comments- the comments should be placed here
     * @DBAttribute(ColName = 'comments', type='set')
     */
    public function setComments($i_Comments)
    {

        $this->m_Comments = $i_Comments;
    }

    /**
     * sets the coversation rating
     * @param byte $i_Grade - the service rating given by the user(from a scale of 1 to 10)
     * @DBAttribute(ColName = 'servise_grade', type='set')
     */
    public function setGrade($i_Grade)
    {

        $this->m_Grade = $i_Grade;
    }

    public function getInstanceAsDBInsetString()
    {
        return "(`feedback_code`, `conversation_code`, `servise_grade`, `comments`) VALUES" .
                "('" . $this->m_ConversationID .
                "', '" . $this->m_ConversationID .
                "', '" . $this->m_Grade .
                "', '" . $this->m_Comments . "')";
    }

    public function __toString()
    {
        return "('" . $this->m_ConversationID .
                "', '" . $this->m_FeedbackCode .
                "', '" . $this->m_Grade .
                "', '" . $this->m_Comments . "')";
    }

}

?>
