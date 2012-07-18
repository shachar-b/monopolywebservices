<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author omer
 */
interface IDBSerializableClass
{
    /**
     *
     * @return IDBSerlizebleClass - returns an instance of the class
     * @param array $i_row
     */
    public static function initInstanceFromDBRows($i_row);

    /**
     * @return string
     */
    public function getInstanceAsDBInsetString();
}

?>
