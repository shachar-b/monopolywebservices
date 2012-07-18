<?php

include_once 'DataBase.php';

/**
 * Description of AllConverstionHistory
 *
 * @author omer
 */
class FullConverstionHistory {

    private $m_conversations;
    private $m_userID;

    public function __construct($i_userID) {
        $this->m_userID = $i_userID;
    }

    public function getConversations() {
        $this->update();
        return $this->m_conversations;
    }

    public function getDivsForConversations() {
        $this->update();
        foreach ($this->m_conversations as $singleConverstion) {
            echo '<div>' . $singleConverstion . '</div><br>';
        }
    }

    private function update() {
        $this->m_conversations = DataBase::getInstance()->getObjectArrayForClass("client_code = " . $this->m_userID . " OR employee_code=" . $this->m_userID, Database::ConversationTable, 'Conversation');
    }

}

if (FALSE) {
    $f = new FullConverstionHistory("321");
    $f->getDivsForConversations();
}
?>
