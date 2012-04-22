<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author omer
 */
interface IDBSerlizebleClass
{

    /**
     *
     * @return IDBSerlizebleClass - returns an instance of the class
     */
    public  function initInstanceFromDBRow($i_row);

    /**
     * @return string
     */
    public function getInstanceAsDBInsetString();
}

?>
