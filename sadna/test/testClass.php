<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of testClass
 *
 * @author omer
 */
class testClass
{

    /**
     * @var long
     */
    private $m_a;

    /**
     * @var long
     */
    private $m_b;

    /**
     *
     * @param type $o
     * @param type $j
     */
    public function __construct($o,$j)
    {
        $this->m_a=$o;
        $this->m_b=$j;
    }

    //getters
    /**
     *  @DBAttribute(RowName='a' ,type='get')
     */
    public function getConversationID()
    {

        return $this->m_a;
    }

    /**
     * @return long number UserID ID
     * @DBAttribute(RowName = 'b', type='get')
     */
    public function getUserID()
    {

        return $this->m_b;
    }

    /**
     * @return String - free text
     */
    public function getComments()
    {

        return $this->m_b;
    }

}

?>
