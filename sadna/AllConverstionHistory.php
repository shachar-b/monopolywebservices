<?php
include_once 'DataBase.php';

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of AllConverstionHistory
 *
 * @author omer
 */
class AllConverstionHistory
{
    private $m_converstions;


    public function getConverstions()
    {
        $this->update();
        return $this->m_converstions;

    }
    public function getDivsForConverstions()
    {
        $this->update();
        foreach ($this->m_converstions as $singleConverstion)
        {
            echo '<div>'.$singleConverstion.'</div></BR>';
        }

    }
    private function update()
    {
        $this->m_converstions= DataBase::getInstance()->getObjectArrayForClass(null,Database::ConversationTable, 'OneConverstionHistory');
    }

}

if(false)
{
    $f= new AllConverstionHistory();
    $f->getDivsForConverstions();
}

?>
