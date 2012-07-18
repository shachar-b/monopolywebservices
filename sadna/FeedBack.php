<?php

require_once("IDBSerializableClass.php");

/**
 * @author omer shenhar
 * @version 1.0
 * @DBTableRepresntionOf('Client_feedback')
 */
class FeedBack {

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
    public function __construct($i_ConversationID = null, $i_Grade = null, $i_Comments = null, $i_FeedBackCode = null) {
        $this->m_ConversationID = $i_ConversationID;
        $this->m_Comments = $i_Comments;
        $this->m_Grade = $i_Grade;
        $this->m_FeedbackCode = $i_FeedBackCode;
    }

    //getters
    /**
     *  getter for the Conversation ID
     * @return long number conversation ID
     * @DBAttribute(ColName = 'conversation_code', type='get')
     */
    public function getConversationID() {
        return $this->m_ConversationID;
    }

    /**
     *  getter for the FeedbackCode
     * @return long number FeedbackCode
     * @DBPrime('feedback_code')
     */
    public function getFeedBackCode() {
        return $this->m_FeedbackCode;
    }

    /**
     * getter for comments field
     * @return String - free text
     * @DBAttribute(ColName = 'comments', type='get')
     */
    public function getComments() {
        return $this->m_Comments;
    }

    /**
     * getter for the user given grade
     * @return byte a number from 1 to 10
     * @DBAttribute(ColName = 'servise_grade', type='get')
     */
    public function getGrade() {
        return $this->m_Grade;
    }

    //setters
    /**
     * sets the conversationID
     * @param long $i_ConversationID - the conversation id in qustion (long)
     * @DBAttribute(ColName = 'conversation_code', type='set')
     */
    public function setConversationID($i_ConversationID) {
        $this->m_ConversationID = $i_ConversationID;
    }

    /**
     * sets the FeedBackCode
     * @param long $i_FeedBackCode - the ID of the feedback object
     * @DBAttribute(ColName = 'feedback_code', type='set')
     */
    public function setFeedBackCode($i_FeedBackCode) {
        $this->m_FeedbackCode = $i_FeedBackCode;
    }

    /**
     * sets the free text section
     * @param string $i_Comments  - if the user has submited any comments- the comments should be placed here
     * @DBAttribute(ColName = 'comments', type='set')
     */
    public function setComments($i_Comments) {
        $this->m_Comments = $i_Comments;
    }

    /**
     * sets the coversation rating
     * @param byte $i_Grade - the service rating given by the user(from a scale of 1 to 10)
     * @DBAttribute(ColName = 'servise_grade', type='set')
     */
    public function setGrade($i_Grade) {
        $this->m_Grade = $i_Grade;
    }

    public function __toString() {
        return "('" . $this->m_ConversationID .
                "', '" . $this->m_Grade .
                "', '" . $this->m_Comments .
                "', '" . $this->m_FeedbackCode . "')";
    }
}
?>
