<?php

/**
 * @author omer shenhar
 * @version 1.0
 */
class FeedBack implements IDBSerlizebleClass
{

    /**
     * the conversation id(long)
     * @var long
     */
    private $m_ConversationID;

    /**
     * the ID of the user who have submited the feedback (long)
     * @var long
     */
    private $m_UserID;

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
        */
    public function __construct()
    {
      //do nothing
    }

    /**
     *
     * @param long $i_ConversationID - the conversation id in qustion (long)
     * @param long $i_UserID - the ID of the user who have submited the feedback (long)
     * @param byte $i_Grade - the service rating given by the user(from a scale of 1 to 10)
     * @param string $i_Comments  - if the user has submited any comments- the comments should be placed here
     */
    public function init($i_ConversationID, $i_UserID, $i_Grade, $i_Comments)
    {
        $this->m_ConversationID = $i_ConversationID;
        $this->m_Comments = $i_Comments;
        $this->m_Grade = $i_Grade;
        $this->m_UserID = $i_UserID;
    }

    public function initInstanceFromDBRow($i_row)
    {
        $conversationID = $row['conversation_code'];
        $userID = $row['feedback_code'];
        $grade = $row['servise_grade'];
        $comments = $row['comments'];
        return new FeedBack($conversationID, $userID, $grade, $comments);
    }

    //getters
    /**
     *  getter for the Conversation ID
     * @return long number conversation ID
     */
    public function getConversationID()
    {

        return $this->m_ConversationID;
    }

    /**
     *  getter for the User ID
     * @return long number UserID ID
     */
    public function getUserID()
    {

        return $this->m_UserID;
    }

    /**
     * getter for comments field
     * @return String - free text
     */
    public function getComments()
    {

        return $this->m_Comments;
    }

    /**
     * getter for the user given grade
     * @return byte a number from 1 to 10
     */
    public function getGrade()
    {

        return $this->m_Grade;
    }

    //setters
    /**
     * sets the conversationID
     * @param long $i_ConversationID - the conversation id in qustion (long)
     */
    public function setConversationID($i_ConversationID)
    {

        $this->m_ConversationID = $i_ConversationID;
    }

    /**
     * sets the UserID
     * @param long $i_UserID - the ID of the user who have submited the feedback (long)
     */
    public function setUserID($i_UserID)
    {

        $this->m_UserID = $i_UserID;
    }

    /**
     * sets the free text section
     * @param string $i_Comments  - if the user has submited any comments- the comments should be placed here
     */
    public function setComments($i_Comments)
    {

        $this->m_Comments = $i_Comments;
    }

    /**
     * sets the coversation rating
     * @param byte $i_Grade - the service rating given by the user(from a scale of 1 to 10)
     */
    public function setGrade($i_Grade)
    {

        $this->m_Grade = $i_Grade;
    }

    public function getInstanceAsDBInsetString()
    {
        return "(`feedback_code`, `conversation_code`, `servise_grade`, `comments`) VALUES" .
                "(\'" . $this->m_ConversationID .
                "\', \'" . $this->m_ConversationID .
                "\', \'" . $this->m_Grade .
                "\', \'" . $this->m_Comments . "\')'";
    }

}

?>
