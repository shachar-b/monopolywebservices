<?php
include 'Employee.php';
class foo{
   static function username()
    {
        $mUserName = "Rudman";
        echo $mUserName;
        echo '<br>';
        //return $mUserName;
    }
    
    static function createPassword()
    {
        $mix = "Rudman Vika";
        $str_shuffle = str_shuffle($mix);
        echo $str_shuffle;
        //return $str_shuffle;
    } 
}

?>
