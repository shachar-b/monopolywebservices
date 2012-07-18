<?php

require_once("IDBSerializableClass.php");

/**
 * Description of Messages
 *
 * @author Shachar
 * @DBTableRepresntionOf('Message')
 */
class Message {

    /**
     * The Message ID(long)
     * @var long
     */
    private $m_MessageID;

    /**
     * the conversation id(long)
     * @var long
     */
    private $m_ConversationID;

    /**
     * The date of creation of the message
     * @var int
     */
    private $m_DateOfCreation;

    /**
     * The ID code of the sending user
     * @var int
     */
    private $m_SenderCode;

    /**
     * The ID code of the receiving user
     * @var int
     */
    private $m_RecipientCode;

    /**
     * The message content
     * @var string
     */
    private $m_Content;

    /**
     *  public constractor for Message- dose nothing- an init function must be called
     * @param long $i_ConversationID - the conversation id in qustion (long)
     * @param int $i_DateOfCreation - The date/time of message creation
     * @param int $i_SenderCode - The ID code of the sender
     * @param int $i_RecipientCode - The ID code of the recipient
     * @param string $i_Content  - Content of message
     */
    public function __construct($i_ConversationID = null, $i_SenderCode = null, $i_RecipientCode = null, $i_Content = null, $i_MessageID = null, $i_DateOfCreation = null) {
        $this->m_MessageID = $i_MessageID;
        $this->m_ConversationID = $i_ConversationID;
        $this->m_DateOfCreation = $i_DateOfCreation;
        $this->m_SenderCode = $i_SenderCode;
        $this->m_RecipientCode = $i_RecipientCode;
        $this->m_Content = $i_Content;
    }

    //getters

    /**
     *  getter for the Message code
     * @return long The ID code of the message
     * @DBPrime('message_code')
     */
    public function getMessageID() {
        return $this->m_MessageID;
    }

    /**
     *  getter for the Conversation ID
     * @return long number conversation ID
     * @DBAttribute(ColName = 'conversation_code', type='get')
     */
    public function getConversationID() {
        return $this->m_ConversationID;
    }

    /**
     *  getter for the Date of creation
     * @return int number of seconds since epoch
     */
    public function getDateOfCreation() {

        return $this->m_DateOfCreation;
    }

    /**
     * getter for ID of sender
     * @return int - ID code of sender of message
     * @DBAttribute(ColName = 'sender_code', type='get')
     */
    public function getSenderCode() {
        return $this->m_SenderCode;
    }

    /**
     * getter for ID of recipient
     * @return int - ID code of receiver of message
     * @DBAttribute(ColName = 'receiver_code', type='get')
     */
    public function getRecipientCode() {
        return $this->m_RecipientCode;
    }

    /**
     * getter for Content of Message
     * @return String - Textual content of message
     * @DBAttribute(ColName = 'content', type='get') 
     */
    public function getContent() {
        return $this->m_Content;
    }

    //setters
    /**
     * sets the MessageID
     * @param long $i_MessageID - the Message ID in question (long)
     * @DBAttribute(ColName = 'message_code', type='set')
     */
    public function setMessageID($i_MessageID) {
        $this->m_MessageID = $i_MessageID;
    }

    /**
     * sets the conversationID
     * @param long $i_ConversationID - the Conversation ID in question (long)
     * @DBAttribute(ColName = 'conversation_code', type='set')
     */
    public function setConversationID($i_ConversationID) {
        $this->m_ConversationID = $i_ConversationID;
    }

    /**
     * sets the user ID code of the sender
     * @param int $i_SenderCode - the ID of the user that sent the message
     * @DBAttribute(ColName = 'sender_code', type='set')
     */
    public function setSenderCode($i_SenderCode) {
        $this->m_SenderCode = $i_SenderCode;
    }

    /**
     * sets the user ID code of the recipient
     * @param int $i_RecipientCode - the ID of the user that should receive the message.
     * @DBAttribute(ColName = 'receiver_code', type='set')
     */
    public function setRecipientCode($i_RecipientCode) {
        $this->m_RecipientCode = $i_RecipientCode;
    }

    /**
     * sets the message content
     * @param string $i_Content  - The content of the message is stored here
     * @DBAttribute(ColName = 'content', type='set')
     */
    public function setContent($i_Content) {
        $this->m_Content = $i_Content;
    }

    public function __toString() {
        return "('" . $this->m_MessageID .
                "', '" . $this->m_ConversationID .
                "', '" . $this->m_DateOfCreation .
                "', '" . $this->m_SenderCode .
                "', '" . $this->m_RecipientCode .
                "', '" . $this->m_Content . "')";
    }

}

?>