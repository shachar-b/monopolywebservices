<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

class Conversation {

    private $m_Conversation_code;
    private $m_Date_of_creation;
    private $m_Client_code;
    private $m_Employee_code;
    private $m_Status;
    private $m_mesages = null;

    public function __construct($client_code = null, $employee_code = null, $status = null, $conversation_code = null, $date_of_creation = null) {
        $this->m_Client_code = $client_code;
        $this->m_Conversation_code = $conversation_code;
        $this->m_Date_of_creation = $date_of_creation;
        $this->m_Employee_code = $employee_code;
        $this->m_Status = $status;
    }

    /**
     * sets the user code
     * @param long $i_Code - the code of the client who started this converstion
     * @DBAttribute(ColName = 'client_code', type='set')
     */
    public function setClientCode($i_Code) {
        $this->m_Client_code = $i_Code;
    }

    /**
     * getter for the user code
     * @return long user code
     * @DBAttribute(ColName = 'client_code', type='get')
     */
    public function getClientCode() {
        return $this->m_Client_code;
    }

    /**
     * sets the employee code
     * @param long $i_Code - the code of the employee who is listed in this conversation
     * @DBAttribute(ColName = 'employee_code', type='set')
     */
    public function setEmployeeCode($i_Code) {
        $this->m_Employee_code = $i_Code;
    }

    /**
     * getter for the employee code
     * @return long employee code
     * @DBAttribute(ColName = 'employee_code', type='get')
     */
    public function getEmployeeCode() {
        return $this->m_Employee_code;
    }

    /**
     * sets the status
     * @param short $i_Status - the new status of the conversation
     * @DBAttribute(ColName = 'status', type='set')
     */
    public function setStatus($i_Status) {
        $this->m_Status = $i_Status;
    }

    /**
     * getter for the Conversation Status
     * @return short status
     * @DBAttribute(ColName = 'status', type='get')
     */
    public function getStatus() {
        return $this->m_Status;
    }

    /**
     *
     * @param long $i_Code - the code of this converstion
     * @DBAttribute(ColName = 'conversation_code', type='set')
     */
    public function setConversationCode($i_Code) {
        $this->m_Conversation_code = $i_Code;
    }

    /**
     *
     * @return long converstion code
     * @DBPrime('conversation_code')
     */
    public function getConversationCode() {
        return $this->m_Conversation_code;
    }

    /**
     * sets the date of creation for the conversation the the the the
     * @param dateTime $i_time - the time of creation of this converstion
     * @DBAttribute(ColName = 'date_of_creation', type='set')
     */
    public function setDateOfCreation($i_time) {
        $this->m_Date_of_creation = $i_time;
    }

    /**
     * Gets the date of creation for the conversation
     * @return dateTime cratetion time
     */
    public function getDateOfCreation() {
        return $this->m_Date_of_creation;
    }

    public function getMessages() {
        $this->update();
        return $this->m_mesages;
    }

    private function update() {
        $this->m_mesages = DataBase::getInstance()->getObjectArrayForClass("conversation_code = " . $this->m_Conversation_code, Database::MessageTable, 'Message');
    }

    public function __toString() {
        $ret = "";
        $this->update();
        if ($this->m_mesages == NULL) {
            return "no messages for converstion code=" . $this->m_Conversation_code . "<br>";
        } else {
            $ret.="messages for converstion code=" . $this->m_Conversation_code . "<br>";
        }
        foreach ($this->m_mesages as $current) {
            $ret.=$current . "<br>";
        }
        return $ret;
    }

}

?>
