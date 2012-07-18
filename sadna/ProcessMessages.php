<?php

include_once 'Message.php';
include_once 'Conversation.php';
include_once 'DataBase.php';

class ProcessMessages {

    const LEN_OF_MESSAGE_TEXT_FIELD = 10000;
    const STATUS_NEW = 1;
    const STATUS_PENDING = 2;
    const STATUS_RESOLVED = 3;

    //@var Message
    private $m_messageObject;
    //@var DataBase
    private $m_dataBase;

    public function __construct($i_messageObject) {
        $this->m_messageObject = $i_messageObject;
        $this->m_dataBase = Database::getInstance();
    }

    public function saveMessage() {
        $saveOutcome = $this->m_dataBase->insertObjectIntoDB("Message", $this->m_messageObject);
        return $saveOutcome;
    }

    public function isValid() {
        $validSenderIDFlag = FALSE;
        $validRecipientIDFlag = FALSE;
        $validMessageTextFlag = FALSE;
        $validConversationIDFlag = FALSE;
        $validMessageFlag = FALSE;

        $validSenderIDFlag = $this->isUserIDValid($this->m_messageObject->getSenderCode());
        $validRecipientIDFlag = $this->isUserIDValid($this->m_messageObject->getRecipientCode());
        $validMessageTextFlag = $this->isMessageTextValid();
        $validConversationIDFlag = $this->isConversationIDValid();

        if ($validSenderIDFlag && $validRecipientIDFlag && $validMessageTextFlag && $validConversationIDFlag) {
            $validMessageFlag = TRUE;
        }
        return $validMessageFlag;
    }

    private function isUserIDValid($i_id) {
        $foundSenderFlag = FALSE;
        if (!$foundSenderFlag) { //Check if user is in managers
            $foundSenderFlag = $this->m_dataBase->isManngerIDValid($i_id);
        }
        if (!$foundSenderFlag) { //Check if user is in employees
            $foundSenderFlag = $this->m_dataBase->isRepresentativeIDValid($i_id);
        }
        if (!$foundSenderFlag) { //Check if user is in clients
            $foundSenderFlag = $this->m_dataBase->isUserIDValid($this->m_senderId);
        }
        return $foundSenderFlag;
    }

    private function isMessageTextValid() {
        $textSizeOK = FALSE;
        if (strlen($this->m_messageText) <= self::LEN_OF_MESSAGE_TEXT_FIELD) {
            $textSizeOK = TRUE;
        }

        return $textSizeOK;
    }

    private function isConversationIDValid() {
        return $this->m_dataBase->isConversationIDValid($this->m_messageObject->getConversationID());
    }

    public function createNewConversation() {
        $newStatus = self::STATUS_NEW;
        $newConversation = new Conversation($this->m_messageObject->getRecipientCode(), $this->m_messageObject->getSenderCode(), $newStatus);
        $this->m_dataBase->insertObjectIntoDB(DataBase::ConversationTable, $newConversation);
        if ($newConversation) {//Successful
            return TRUE;
        }
        else
            return FALSE;
//TODO : Define enum (if possible), where new=1,pending=2,resolved=3
    }

}

if (FALSE) {
    $testMsg = new Message("4", "147", "754", "BAD MSG");
    $testPrc = new ProcessMessages($testMsg);
    $testPrc->createNewConversation();
}

if (FALSE) {
    echo "</br>";
    $testMessage = new Message("3", "147", "741", "This is a new message");
// @var ProcessMessages
    $testProccess = new ProcessMessages($testMessage);
    if ($testProccess->isValid()) {
        echo "Valid == TRUE";
        echo "</br>";
        if ($testProccess->saveMessage()) {
            echo "Save == TRUE";
            echo "</br>";
        } else {
            echo "Save == FALSE";
            echo "</br>";
        }
    } else {
        echo "Valid == FALSE";
        echo "</br>";
    }
    echo "</br>";
}
?>
