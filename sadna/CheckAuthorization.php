<?php
/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

class CheckAuthorization {

    public static function isLoggedIn(){
        session_start();

        if (!isset($_SESSION['user_id']))
        {
            // Fetch current URL
            $this_url = $_SERVER['REQUEST_URI'];

            // Redirect to login page passing current URL
            header('Location: login.php?return_url=' . urlencode($this_url));
            exit;
        }

    }

}

?>


