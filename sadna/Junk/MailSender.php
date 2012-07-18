<?php
class MailSender {
    
    public static function sendMail($mailingList, $subject, $message){
        mail($mailingList, $subject, $message);
    }

}

?>