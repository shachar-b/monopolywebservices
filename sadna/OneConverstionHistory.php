<?php

include_once 'Conversation.php';
include_once 'DataBase.php';

/**
 * Description of OneConverstionHistory :
 *  Display HTML divs of a single conversation in the system.
 * @author omer
 */
class OneConverstionHistory {

    private $m_ConversationObject;
    private $m_ConversationID;

    public function __construct($i_ConversationID) {
        $this->m_ConversationID = $i_ConversationID;
        $this->m_ConversationObject = DataBase::getInstance()->getObjectForClass("conversation_code = " . $this->m_ConversationID, Database::ConversationTable, 'Conversation');
    }

    public function GetDivForConversation() {
        return "" . $this->m_ConversationObject;
    }

    public function __toString() {
        return "" . $this->m_ConversationObject;
    }

}

if (FALSE) {
    $OneC1 = new OneConverstionHistory("2");
    echo $OneC1;
}
?>
