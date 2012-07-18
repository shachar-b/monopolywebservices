<?php
/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

include("LoginAuthanticator.php");

$loginAuth = new LoginAuthanticator($_POST);
echo $loginAuth->authenticate();

?>
